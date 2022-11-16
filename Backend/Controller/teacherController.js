const teacherIlm = require('../Implement/teacherIpm')
var request = require('request').defaults({ encoding: null });
//require time table models
const timetablesModels = require('../Models/ThoiKhoaBieu').ThoiKhoaBieu
const hocSinhModels = require('../Models/HocSinh').HocSinh
    //send sms library
const sms = require('../Server/sms')
const createExcel = require('../Server/createExcel')
    //require upload files
const fileUpload = require('../Server/fileUpload')
const Cryptr = require('cryptr');
const cryptr = new Cryptr(process.env.PASSWORD_ENCRYPTION);
const socket = require('../server').io

const accountSid = process.env.TWILIO_ACCOUNT_SID
const authToken = process.env.TWILIO_AUTH_TOKEN
const client = require('twilio')(accountSid, authToken);

const VoiceResponse = require('twilio').twiml.VoiceResponse;
//require token 
const token = require('../Server/token')
    //require tools
const tools = require('../Server/tools')

const fs = require('fs');


const login = async(req, res) => {

    // const pass= await libeaes.encrypt(req.body.password, process.env.PASSWORD_ENCRYPTION) ;
    // console.log(pass)
    // req.body.password=pass;
    const user = await teacherIlm.login(req, res)
    if (user === undefined) {

        res.status(401).json("Wrong username or password")

    } else {
        console.log(user)
        console.log()
        if (req.body.password == cryptr.decrypt(user.password)) {
            const tokenString = await token.createToken(user.idGiaoVien)
            const classByTeacher = await teacherIlm.getClassByTeacherNow(user.idGiaoVien)
            user.ttl=classByTeacher[0].idThongTinLop
            delete user.password;
            user.token = tokenString
            res.setHeader('Access-Control-Allow-Headers','Set-Cookie')
            return res.status(200).cookie('token', tokenString, { maxAge: 86400000, httpOnly: false })
                .cookie('user', JSON.stringify(user), { maxAge: 86400000, httpOnly: false, sameSite: false }).send(user)
        }
        return res.status(401).json("Wrong username or password");

    }


}
const uploadTimetable = async(req, res) => {
    //file from request
    const file = req.files;
    //upload time table and return file path

    const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
    const duongDan = await fileUpload.uploadTimetable(file, classByTeacher[0].idThongTinLop)


    if (duongDan === null) { return res.status(400).json({ 'data': "File phải thuộc dạng pdf" }); }
    const data = {
        duongDan: duongDan,
        ngayBatDau: req.body.startDate,
        ngayKetThuc: req.body.endDate,
        // i think it should be 0 and then when the teacher confirmed it will be 1 
        idLop: classByTeacher[0].idThongTinLop,
        //user get from middleware
        createByID: req.user.idGiaoVien,

    }
    console.log(data);

    //call implement class
    teacherIlm.uploadTimetable(req, res, data)
    socket.emit(req.user.chuNhiemLop, { "Thông báo": "Đã có thời khoá biểu mới" })
    res.status(201).json(duongDan)
}
const sendSms = async(req, res) => {
    if (req.body.selected == "all") {
        const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
        if (classByTeacher.length == 0) {
            return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
        }
        const listcontact = await teacherIlm.getContactStudent(classByTeacher[0].idThongTinLop);
        for (const contact of listcontact) {
            if (contact.SDTCha || contact.SDTMe) {
                const sdt = contact.SDTCha == null ? contact.SDTMe : contact.SDTCha
                sms.messages
                    .create({
                        body: req.body.messenger,
                        from: process.env.PHONE_NUMBER_TWIllO,
                        to: sdt
                    })
                    .then(message => console.log(message));
            }
        }
    } else {
        sms.messages
            .create({
                body: req.body.messenger,
                from: process.env.PHONE_NUMBER_TWIllO,
                to: req.body.selected
            })
            .then(message => console.log(message));
    }
    console.log(req.body)
    res.status(200).json(req.body)
        //todo: read sample code from sms.js
}
const acceptAllAttendance = async(req, res) => {
        //todo : call implement class
    }
    // unlock faceid upload if student as for
const unlockFaceId = async(req, res) => {
    const studentId = req.params.studentId
    hocSinhModels.findByPk(studentId).then(async(student) => {
        //check 
        const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
        if (classByTeacher.length == 0) {
            return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
        }
        // const studentByClass = await teacherIlm.Get_All_Student_By_Class(classByTeacher[0].idThongTinLop)
        if (student.idThongTinLop == classByTeacher[0].idThongTinLop) {
            student.khoaAnh = student.khoaAnh == 0 ? 1 : 0;
            student.save()
            socket.emit(studentId, { "Thông báo": "Đã mở khoá faceID" })

            return res.status(200).json({
                data: "đã mở khoá/khoá faceId cho học sinh " + student.hoTen
            })

        } else {
            return res.status(403).json({
                data: "học sinh " + student.hoTen + " không thuộc lớp của bạn"
            })
        }

    })
}

const HomeroomClasses = async(req, res) => {
    const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
    if (classByTeacher.length == 0) {
        return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
    }
    const studentByClass = await teacherIlm.Get_All_Student_By_Class(classByTeacher[0].idThongTinLop)
    return res.status(200).send(studentByClass)

}
const getCurrentTimeTable = async(req, res) => {
    
    const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
    if (classByTeacher.length == 0) {
        return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
    }
    const path = await teacherIlm.getCurrentTimeTable(classByTeacher[0].idThongTinLop)
    if (path[0] != undefined)
    {
            request.get(path[0].duongDan, function (error, response, body) {
        if (!error && response.statusCode == 200) {
            data = "data:" + response.headers["content-type"] + ";base64," + Buffer.from(body).toString('base64');
            return res.status(200).send(data)
        }
    });
    }

    // // fs.readFile(path[0].duongDan, { encoding: 'base64' }, (error, data) => {
        // //     if (error) {
        // //         throw error;
        // //     }
        //     return res.status(200).send(path[0].duongDan)
        // // });
    else
        return res.status(200).send(null)

}



const addStudentViaElsx = async(req, res) => {
    const data = JSON.parse(req.body.data)
    const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
    if (classByTeacher.length == 0) {
        return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
    }

    //const teacherInsertSingle = await teacherIlm.Insert_Single_Student(classByTeacher, req.body)
    for (let i = 1; i < data.length; i++) {

        const info = {
            idThongTinLop: classByTeacher[0].idThongTinLop,
            ngaySinh: data[i][3],
            SDT: data[i][15],
            queQuanTinh: data[i][9],
            queQuanHuyen: data[i][10],
            queQuanXa: data[i][11],
            queQuanThonXom: data[i][13],
            noiSinh: data[i][12],
            noiThuongTru: data[i][14],
            danToc: data[i][7],
            quocTich: data[i][8],
            doanVien: data[i][22],
            luuBanNamTruoc: data[i][23],
            doiVien: data[i][21],
            tonGiao: "",
            doiTuongChinhSach: data[i][17],
            gioiTinh: data[i][4],
            hoTen: data[i][2],
            namNhapHoc: "",
            anhThe: "",
            time: data[i][5],
            trangThai: data[i][6],

            danTocCha: data[i][25],
            tenCha: data[i][26],
            ngheNghiepCha: data[i][27],
            namSinhCha: data[i][28],
            danTocMe: data[i][24],
            tenMe: data[i][29],
            ngheNghiepMe: data[i][30],
            namSinhMe: data[i][31],
            hoTenNguoiGiamHo: data[i][32],
            ngheNghiepNGH: data[i][32],
            namSinhNGH: data[i][34],
        }
        await teacherIlm.Insert_Single_Student_xlsx(info)
    }
    res.status(200).json(data)
}

// start getClassByTeacherNow
const getClassByTeacherNow = async(req, res) => {
        const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
        if (classByTeacher.length == 0) {
            return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
        }
        return res.status(200).send(classByTeacher)
    }
    // end getClassByTeacherNow


// start InsertSingleStudent
const InsertSingleStudent = async(req, res) => {
    // console.log(req.body)
    // console.log(res)
    const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
    if (classByTeacher.length == 0) {
        return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
    }

    let filePathAvatar
    if(req.files.anhThe)
    {
        filePathAvatar = await fileUpload.uploadAvatarStudent(req.files.anhThe, classByTeacher[0].idThongTinLop)

    }
    else {
        filePathAvatar='uploads/Students/default-avata.jpg'
    }
    req.body.anhThe = filePathAvatar
    const teacherInsertSingle = await teacherIlm.Insert_Single_Student(classByTeacher, req.body)
    return res.status(200).send(teacherInsertSingle)
}

const CreateTeacher = async(req, res) => {
    try {
        const encrypted = await cryptr.encrypt(req.body.password);
        console.log(encrypted)
        req.body.password = encrypted;
        const teacher = await teacherIlm.CreateTeacher(req)
        return res.status(201).send(teacher)
    } catch (e) {
        return res.status(500).send(e)

    }
}

const changePassword = async(req, res) => {
    const data = await teacherIlm.changePassword(req);
    if (data != null) {
        res.status(200).send(data)
    } else {
        res.status(401).send("Mật khẩu hiện tại không đúng")
    }
}
const getContactStudent = async(req, res) => {
        const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
        if (classByTeacher.length == 0) {
            return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
        }
        return res.status(200).send(await teacherIlm.getContactStudent(classByTeacher[0].idThongTinLop));
    }
    // end InsertSingleStudent

//start 
const GetAllConductStudentByIdClassYearAndSemster = async(req, res) => {
        const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
        const idGiaoVien = req.user.idGiaoVien
        const idClass = classByTeacher[0].idThongTinLop
        return res.status(200).send(
            await teacherIlm.GetAllConductStudentByIdClassYearAndSemster(idGiaoVien, idClass, req.body.kiHoc, req.body.namHoc));
    }
    //end
    //start 
const GetConductStudentByYear = async(req, res) => {

        const idHocSinh = req.body.idHocSinh;
        const namHoc = req.body.namHoc;
        return res.status(200).send(
            await teacherIlm.GetConductStudentByYear(idHocSinh, namHoc));
    }
    //end//start 
const GetAllConductDetailsStudentByYearAndSemester = async(req, res) => {
    const idHocSinh = req.body.idHocSinh;
    const namHoc = req.body.namHoc;
    const kiHoc = req.body.kiHoc
    return res.status(200).send(
        await teacherIlm.GetAllConductDetailsStudentByYearAndSemester(idHocSinh, kiHoc, namHoc));
}
const InsertConductDetailsStudent = async(req, res) => {
    const idHanhKiem = req.body.idHanhKiem;
    const diem = req.body.diem < 0 ? 0 : req.body.diem > 100 ? 100 : req.body.diem;
    const loaiDiem = req.body.loaiDiem;
    const idGiaoVien = req.user.idGiaoVien
    const noiDung = req.body.noiDung;
    const ghiChu = req.body.ghiChu;
    return res.status(200).send(
        await teacherIlm.InsertConductDetailsStudent(idHanhKiem, diem, loaiDiem, idGiaoVien, noiDung, ghiChu));
}

const CallParentTeacher = async(req, res) => {
        const idXinNghiHoc = req.body.idXinNghiHoc;
        const sdt = req.body.number;
        const xacNhan = 3;
        const noiDung = req.body.text;
        console.log('ss')
            // console.log(req)
        const from = process.env.PHONE_NUMBER_TWIllO
        const to = req.body.number;
        const link = 'https://raw.githubusercontent.com/tuananhnguyen0103/AppVoice/main/sample.mp3'
            // Note that TwiML can be hosted at a URL and accessed by Twilio
            // đọc  văn bản => giọng  nói xong lưu = mp3
        var gtts = require('node-gtts')('vi');
        var path = require('path');
        const { uuid } = require('uuidv4');
        // đường dẫn lưu file mp3 
        const filename = `temp/call/${uuid()}.mp3`
        const pathFile = `${process.env.BACKEND_STOGARE}/${filename}`
            // const pathFile = `${process.env.BACKEND_STOGARE}/${filename}`

        // convert sang mp3 r lưu trên sever twilo
        gtts.save('storage/' + filename, req.body.text || 'không cho chữ à ?', function() {
                console.log('save done');
            })
            // link file xml có giá trị truyền vào là file mp3 
            // abc là name truyền vào trong file xml
        const url = 'https://handler.twilio.com/twiml/EH9712ba9021f05a7cf0d178d248b2af7c?abc=' + pathFile + '&idXinNghiHoc=' + idXinNghiHoc;
        console.log(url)
        client.calls
            .create({ to, from, url })
            .then((call) => {
                console.log('Call successfully placed');
                console.log(call.sid);
                // Make sure to only call `callback` once everything is finished, and to pass
                // null as the first parameter to signal successful execution.
                console.log(`Success! Call SID: ${call.sid}`);

                teacherIlm.UpdatedConfirmedParentToDayOffById(idXinNghiHoc, sdt, xacNhan, noiDung);
                // return res.status(200).send(`Success! Call SID: ${call.sid}`);
            })
            .catch((error) => {
                console.error(error);
                return callback(error);
            });



        // const InsertConductDetails = await dao.sequelize.query(
        //     `[Updated_Confirmed_By_Parent_To_Day_Off] ${idXinNghiHoc}, ${sdt}, ${xacNhan} `, 
        //     {raw:true,nest:true})
        // return InsertConductDetails
        return res.status(200).send("ok")
    }
    // start GetAllDayOffStudentByClass
const GetAllDayOffStudentByClass = async(req, res) => {
        const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
        if (classByTeacher.length == 0) {
            return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
        }
        const studentByClass = await teacherIlm.GetAllDayOffStudentByClass(classByTeacher[0].idThongTinLop)
        return res.status(200).send(studentByClass)
    }
const UpdateStateAttendanceByTeacher = async(req, res) => {
        const idDiemDanh = req.body.idDiemDanh;
        const updateTime = tools.getDate();
        const trangThai = req.body.trangThai;
        const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
        if (classByTeacher.length == 0) {
            return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
        }
        const updateAttendence = await teacherIlm.UpdateStateAttendanceByTeacher(idDiemDanh,updateTime,trangThai,classByTeacher[0].idThongTinLop)
        return res.status(200).send(updateAttendence)
    }
const UpdatedStateConfirmedParentToDayOffById = async(req, res) => {
        const idXinNghiHoc = req.body.idXinNghiHoc;
        const trangThai = req.body.trangThai;
        const updateDayOff= await teacherIlm.UpdatedStateConfirmedParentToDayOffById(idXinNghiHoc,trangThai)
        return res.status(200).send(updateDayOff)
    }
    // end GetAllDayOffStudentByClass
const GetDetailsDayOffStudentByIdDayOff = async(req, res) => {
        const idXinNghiHoc = req.params.idXinNghiHoc;
        const DetailsDayOffs = await teacherIlm.GetDetailsDayOffStudentByIdDayOff(idXinNghiHoc)
        return res.status(200).send(DetailsDayOffs)
    }
const InsertScoreByYearAndSemmesterIdHocSinh_XLSX = async(req, res) => {
        const data = JSON.parse(req.body.data)
        const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
        if (classByTeacher.length == 0) {
            return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
        }
    
        //const teacherInsertSingle = await teacherIlm.Insert_Single_Student(classByTeacher, req.body)
        for (let i = 1; i < data.length; i++) {
            // Toán 1
            const infoToan = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][4],
                idMonHoc: 1,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoToan)       
            const infoLy = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][5],
                idMonHoc: 2,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoLy)  
            const infoHoa = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][6],
                idMonHoc: 3,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoHoa)  
            const infoSinh = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][7],
                idMonHoc: 4,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoSinh)  
            const infoTin = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][8],
                idMonHoc: 5,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoTin)  
            const infoVan = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][9],
                idMonHoc: 6,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoVan)  
            const infoSu = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][10],
                idMonHoc: 7,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoSu)  
            const infoDiaLy = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][11],
                idMonHoc: 8,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoDiaLy)  
            const infoNgoaiNgu = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][12],
                idMonHoc: 9,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoNgoaiNgu)  
            const infoCongNghe = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][13],
                idMonHoc: 10,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoCongNghe)  
            const GDQPAN = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][14],
                idMonHoc: 11,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(GDQPAN)  
            const infoTheDuc = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][15],
                idMonHoc: 12,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoTheDuc)
            const infoGDCD = {
                // idThongTinLop: classByTeacher[0].idThongTinLop,
                idHocSinh: data[i][1],
                diemmonHoc: data[i][16],
                idMonHoc: 13,
                kiHoc: req.body.kiHoc,
                namHoc: req.body.namHoc
            }
            await teacherIlm.InsertScoreByYearAndSemmesterIdHocSinh_XLSX(infoGDCD)         
        }
        res.status(200).json(data)
}
const CreateFileExcel = async(req, res) => {
    const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
    if (classByTeacher.length == 0) {
        return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
    }
    const studentByClass = await teacherIlm.Get_All_Student_By_Class(classByTeacher[0].idThongTinLop)
    const CreateFileExcel = await createExcel.createFile(studentByClass) 
    return res.status(200).send(CreateFileExcel)

}
const GetAllScoreStudentInSemesterByClass = async(req, res) => {
    const kiHoc = req.body.kiHoc;
    const namHoc = req.body.namHoc;
    const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
    if (classByTeacher.length == 0) {
        return res.status(403).send("Bạn chưa chủ nhiệm lớp nào")
    }
    // const studentByClass = await teacherIlm.Get_All_Student_By_Class(classByTeacher[0].idThongTinLop)
    const GetAllScoreStudentInSemesterByClass = await teacherIlm.GetAllScoreStudentInSemesterByClass(classByTeacher[0].idThongTinLop, kiHoc,namHoc)
    return res.status(200).send(GetAllScoreStudentInSemesterByClass)
}
    //end
module.exports = {
    login,
    uploadTimetable,
    sendSms,
    acceptAllAttendance,
    unlockFaceId,
    HomeroomClasses,
    getCurrentTimeTable,
    addStudentViaElsx,
    getClassByTeacherNow,
    InsertSingleStudent,
    CreateTeacher,
    changePassword,
    getContactStudent,
    GetAllConductStudentByIdClassYearAndSemster,
    GetConductStudentByYear,
    GetAllConductDetailsStudentByYearAndSemester,
    InsertConductDetailsStudent,
    CallParentTeacher,
    GetAllDayOffStudentByClass,
    UpdateStateAttendanceByTeacher,
    UpdatedStateConfirmedParentToDayOffById,
    GetDetailsDayOffStudentByIdDayOff,
    CreateFileExcel,
    InsertScoreByYearAndSemmesterIdHocSinh_XLSX,
    GetAllScoreStudentInSemesterByClass
}