const attendanceIml = require('../Implement/attendanceIpm')
const teacherIlm = require('../Implement/teacherIpm')
const socket = require('../server').io

//require tools
const tools = require('../Server/tools')


const studentAttendance = async (req, res) => {
    idstudent = req.user.idHocSinh;
    var hours = tools.hours;
    var minutes = tools.minutes;
    var TrangThai = 0;
    if (hours < 6) {
        return res.status(405).send("Quá sớm để điểm danh");
    } else if (hours == 6) {
        if (minutes >= 40) {
            TrangThai = 1;
        } else {
            return res.status(405).send("Quá sớm để điểm danh");
        }
    } else if (hours == 7) {
        if (minutes <= 40) {
            TrangThai = 1;
        } else {
            TrangThai = 2
        }
    } else if (hours == 8) {
        if (minutes <= 10)
            TrangThai = 2
        else
            return res.status(405).send("Quá muộn để điểm danh");
    } else {
        return res.status(405).send("Quá muộn để điểm danh");
    }
    const attendance = {
        idHocSinh: idstudent,
        ngayDiemDanh: tools.getDate(),
        trangThai: TrangThai
    }

        const idDiemDanh=await attendanceIml.StudentAttendance(attendance, res)
        console.log(idDiemDanh)
        socket.emit(req.user.idThongTinLop+"att",{
            idDiemDanh:idDiemDanh[0].idDiemDanh,
            trangThai:attendance.trangThai
        })
        res.status(201).json(attendance)
        // em tuyệt vời

}



const getDiemDanhByIdGiaoVien = async (req, res) => {
    const idGiaoVien = req.user.idGiaoVien
    const date = req.body.date ? req.body.date : tools.getMonth()
   
    const attendanceList = await attendanceIml.getDiemDanhByIdGiaoVien(idGiaoVien, date)
    if (attendanceList === undefined) {

        res.status(204).json("Attendence empty")

    } else {

        const month = date.split('-')[0]
        const year = date.split('-')[1]
        const daysInMonth = new Date(year, month, 0).getDate();

        let data = []
        let final = [];
        const classByTeacher = await teacherIlm.getClassByTeacherNow(req.user.idGiaoVien)
        const studentByClass = await teacherIlm.Get_All_Student_By_Class(classByTeacher[0].idThongTinLop)
     



        const dateF = new Date()
        console.log(data.length)
        studentByClass.forEach((da, daindex, daobject) => {
            da.data = []
            attendanceList.forEach((att, attindex, attobject) => {
             
                 

                    if (da.idHocSinh == att.idHocSinh) {
                        for (let k = 0; k < daysInMonth; k++) {

                            let object_info;
                            let dateCompare = new Date(month + '-' + (k + 1) + '-' + year)
                            let dateMonth = ("0" + (k + 1)).slice(-2)
                            if (dateF < dateCompare) {
                                break
                            }
                            else if (att.ngayDiemDanh.split('-')[0] == dateMonth) {
                                object_info = {
                                    date: att.ngayDiemDanh,
                                    status: att.trangThai,
                                    id: attindex + "" + daindex,
                                    idDiemDanh: att.idDiemDanh
                                }
                                da.data[k] = object_info
                            }
                        }
                    }
                
            })
            final.push(da)


        })




        res.status(200).send(final)
    }
}
const InsertAllAttendanceStudentDaily = async () => {
    const year = tools.year
    const time = tools.getDate()

    const attendanceList = await attendanceIml.InsertAllAttendanceStudentDaily(year, time)
}

module.exports = {
    studentAttendance,
    getDiemDanhByIdGiaoVien,
    InsertAllAttendanceStudentDaily
}