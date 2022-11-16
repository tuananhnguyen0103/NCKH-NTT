//require time table models
const timetablesModels = require('../Models/ThoiKhoaBieu').ThoiKhoaBieu
const Sequelize = require('sequelize');
const DataTypes = Sequelize.DataTypes;
const Acount = require('../Models/Account').Acount;
const dao = require('../Dao/Connection')
const GiaoVien = require('../Models/GiaoVien').GiaoVien;
const tools = require('../Server/tools')
const Cryptr = require('cryptr');
const cryptr = new Cryptr(process.env.PASSWORD_ENCRYPTION);
/*function*/

// start login
const login = async(req, res) => {

        // const user=await Acount.findOne({where:{account:body.id,password:body.password}
        //     ,raw: true
        // ,attributes:['idGiaoVien']  })
        // return user!=null ? user :null
        const user = await dao.sequelize.query("exec teacherLogin @id= '" + req.body.id + "'", { raw: true, nest: true })

        return user[1] != 0 ? user[0] : null
    }
    // end login

// start uploadTimetable
const uploadTimetable = async(req, res, timetable) => {
        console.log("Thời khóa biểu cmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
        console.log(timetable)
            // @duongdan nvarchar(300),@idLop int,@createBy int, @ngayBatDau nvarchar(max),@ngayKetThuc nvarchar(max)

        const query = `exec Insert_ThoiKhoaBieu @duongdan=N'${timetable.duongDan}', @idLop=${timetable.idLop}, 
            @createBy=${timetable.createByID}, @ngayBatDau = '${timetable.ngayBatDau}',
            @ngayKetThuc = '${timetable.ngayKetThuc}'`
        const user = await dao.sequelize.query(query, { raw: true, nest: true })
            //or use this
            //    await timetablesModels.create(timetable).then(function(data){
            //         res.status(201).json(data)
            //        //data is model just install
            //     }).catch(function(error){
            //         res.status(400).json(error)
            //     });
    }
    // end uploadTimetable

// start acceptAllAttendance
const acceptAllAttendance = async(req, res) => {
        //todo call procedures accept attendace
        //sequelize.query('write proc name and parameters here')
    }
    // end acceptAllAttendance

// start getCurrentTimeTable
const getCurrentTimeTable = async(id) => {
        const time = await dao.sequelize.query(

            `exec getCurrentTimeTable ` + id, { raw: true, nest: true }
        )
        return time;
    }
    // end getCurrentTimeTable

// start getClassByTeacherNow
const getClassByTeacherNow = async(idGiaoVien) => {
        const classByTeacher = await dao.sequelize.query(
            `Exec Get_Class_By_Teacher_Now ` + idGiaoVien, { raw: true, nest: true }
        )
        return classByTeacher;
    }
    // end getClassByTeacherNow

//start Get_All_Student_By_Class
const Get_All_Student_By_Class = async(idThongTinLop) => {
    const studentByClass = await dao.sequelize.query(
        `Exec Get_All_Student_By_Class ` + idThongTinLop, { raw: true, nest: true }
    )
    return studentByClass;
}
const Insert_Single_Student = async(classByTeacher, body) => {
    // console.log(body);
    dataStudent = {
        "idThongTinLop": classByTeacher[0].idThongTinLop,
        "ngaySinh": body.ngaySinh,
        "SDT": body.SDT,
        "queQuanTinh": body.queQuanTinh,
        "queQuanHuyen": body.queQuanHuyen,
        "queQuanXa": body.queQuanXa,
        "queQuanThonXom": body.queQuanThonXom,
        "noiSinh": body.noiSinh,
        "noiThuongTru": body.noiThuongTru,
        "danToc": body.danToc,
        "doanVien": body.doanVien,
        "luuBanNamTruoc": body.luuBanNamTruoc,
        "doiVien": body.doiVien,
        "tonGiao": body.tonGiao,
        "doiTuongChinhSach": body.doiTuongChinhSach,
        "gioiTinh": body.gioiTinh,
        "hoTen": body.hoTen,
        "namNhapHoc": body.namNhapHoc,
        "anhThe": body.anhThe,

        "danTocCha": body.danTocCha,
        "tenCha": body.tenCha,
        "ngheNghiepCha": body.ngheNghiepCha,
        "namSinhCha": body.namSinhCha,
        "danTocMe": body.danTocMe,
        "tenMe": body.tenMe,
        "ngheNghiepMe": body.ngheNghiepMe,
        "namSinhMe": body.namSinhMe,
        "hoTenNguoiGiamHo": body.hoTenNguoiGiamHo,
        "ngheNghiepNGH": body.ngheNghiepNGH,
        "namSinhNGH": body.namSinhNGH,

    }
    console.log(dataStudent)
        // return dataStudent;
    const teacherInsertSingle = await dao.sequelize.query(
        `Exec Insert_Single_Student 
        ${dataStudent.idThongTinLop}, '${dataStudent.ngaySinh}', '${dataStudent.SDT}', 
        N'${dataStudent.queQuanTinh}', N'${dataStudent.queQuanHuyen}', N'${dataStudent.queQuanXa}', N'${dataStudent.queQuanThonXom}',
        N'${dataStudent.noiSinh}', N'${dataStudent.noiThuongTru}', N'${dataStudent.danToc}', 
        ${dataStudent.doanVien}, ${dataStudent.luuBanNamTruoc}, ${dataStudent.doiVien}, '${dataStudent.tonGiao}', ${dataStudent.doiTuongChinhSach}, 
        N'${dataStudent.gioiTinh}', N'${dataStudent.hoTen}', '${dataStudent.namNhapHoc}', N'${dataStudent.anhThe}', 

        N'${dataStudent.danTocCha}', N'${dataStudent.tenCha}', N'${dataStudent.ngheNghiepCha}', N'${dataStudent.namSinhCha}', 
        N'${dataStudent.danTocMe}', N'${dataStudent.tenMe}', N'${dataStudent.ngheNghiepMe}', N'${dataStudent.namSinhMe}',
        N'${dataStudent.hoTenNguoiGiamHo}', N'${dataStudent.ngheNghiepNGH}', N'${dataStudent.namSinhNGH}' `, { raw: true, nest: true }
    )
    return teacherInsertSingle;
}

const CreateTeacher = async(req) => {
        const teacher = await dao.sequelize.query(
            `Exec Create_teacher N'${req.body.hoTen}',N'${req.body.password}',N'${req.body.tenDangNhap}'`, { raw: true, nest: true }
        )
        return teacher;
    }
    // end Get_All_Student_By_Class
    // 
const changePassword = async(req) => {
    const user = await Acount.findOne({ where: { idGiaoVien: req.user.idGiaoVien } });
    const de = cryptr.decrypt(user.password)
    if (cryptr.decrypt(user.password) == req.body.oldPassword) {

        const teacher = await dao.sequelize.query(
            `Exec teacher_changePassword N'${req.user.idGiaoVien}','${cryptr.encrypt(req.body.password)}'`, { raw: true, nest: true }
        )
        return teacher;
    } else {
        return null;
    }
}
const getContactStudent = async(idttlop) => {
    console.log(idttlop)
    return await dao.sequelize.query(`getContactStudent ${idttlop}`, { raw: true, nest: true })
}

const GetAllConductStudentByIdClassYearAndSemster = async(idGiaoVien, idThongTinLop, kiHoc, namHoc) => {
    // console.log(idThongTinLop)
    const ConductByClass = await dao.sequelize.query(
        `exec Get_All_Conduct_Student_By_Id_Class_Year_And_Semster ${idGiaoVien} ,${idThongTinLop}, ${kiHoc}, ${namHoc}`, { raw: true, nest: true })
    console.log(ConductByClass.length)
    return ConductByClass
}

const GetConductStudentByYear = async(idHocSinh, namHoc) => {
    const ConductStudentByYear = await dao.sequelize.query(
        `Get_Conduct_Student_By_Year ${idHocSinh}, ${namHoc}`, { raw: true, nest: true })
    return ConductStudentByYear
}

const GetAllConductDetailsStudentByYearAndSemester = async(idHocSinh, kiHoc, namHoc) => {
    const ConductDetailsStudent = await dao.sequelize.query(
        `[Get_All_Conduct_Details_Student_By_Year_And_Semester] ${idHocSinh}, ${kiHoc}, ${namHoc} `, { raw: true, nest: true })
    return ConductDetailsStudent
}

const InsertConductDetailsStudent = async(idHanhKiem, diem, loaiDiem, idGiaoVien, noiDung, ghiChu) => {
    const InsertConductDetails = await dao.sequelize.query(
        `[Insert_Conduct_Details_Student] ${idHanhKiem}, ${diem}, ${loaiDiem}, ${idGiaoVien}, N'${noiDung}', N'${ghiChu}','${tools.getDateToSecond()}' `, { raw: true, nest: true })
    return InsertConductDetails
}
const UpdatedConfirmedParentToDayOffById = async(idXinNghiHoc, sdt, xacNhan, noiDung) => {
    const UpdatedConfirmedParentToDayOffById = await dao.sequelize.query(
        `[Updated_Confirmed_Parent_To_Day_Off_By_Id] ${idXinNghiHoc}, '${sdt}', ${xacNhan}, N'${noiDung}' `, { raw: true, nest: true })
    return UpdatedConfirmedParentToDayOffById
}

const UpdatedStateConfirmedParentToDayOffById = async(idXinNghiHoc, xacNhan) => {
    const UpdatedStateConfirmedParentToDayOffById = await dao.sequelize.query(
        `[Update_State_Confirmed_Parent_To_Day_Off_By_Id] ${idXinNghiHoc}, ${xacNhan}`, { raw: true, nest: true })
    return UpdatedStateConfirmedParentToDayOffById
}
const GetAllDayOffStudentByClass = async(idThongTinLop) => {
        const GetAllDayOffStudentByClass = await dao.sequelize.query(
            `[Get_All_Day_Off_Student_By_Class] ${idThongTinLop}`, { raw: true, nest: true })
        return GetAllDayOffStudentByClass
    }
    /*end function*/


const Insert_Single_Student_xlsx = async(dataStudent) => {
    // console.log(body);

    console.log(dataStudent);
    // return dataStudent;
    const teacherInsertSingle = await dao.sequelize.query(
        `Exec Insert_Single_Student 
            ${dataStudent.idThongTinLop}, '${dataStudent.ngaySinh}', '${dataStudent.SDT}', 
            N'${dataStudent.queQuanTinh}', N'${dataStudent.queQuanHuyen}', N'${dataStudent.queQuanXa}', N'${dataStudent.queQuanThonXom}',
            N'${dataStudent.noiSinh}', N'${dataStudent.noiThuongTru}', N'${dataStudent.danToc}', 
            ${dataStudent.doanVien}, ${dataStudent.luuBanNamTruoc}, ${dataStudent.doiVien}, '${dataStudent.tonGiao}', ${dataStudent.doiTuongChinhSach}, 
            N'${dataStudent.gioiTinh}', N'${dataStudent.hoTen}', '${dataStudent.namNhapHoc}', N'${dataStudent.anhThe}', 
            N'${dataStudent.danTocCha}', N'${dataStudent.tenCha}', N'${dataStudent.ngheNghiepCha}', N'${dataStudent.namSinhCha}', 
            N'${dataStudent.danTocMe}', N'${dataStudent.tenMe}', N'${dataStudent.ngheNghiepMe}', N'${dataStudent.namSinhMe}',
            N'${dataStudent.hoTenNguoiGiamHo}', N'${dataStudent.ngheNghiepNGH}', N'${dataStudent.namSinhNGH}','${tools.getDate()}',N'Đang học' , N'${dataStudent.quocTich}' `, { raw: true, nest: true }
    );
    return teacherInsertSingle;
};

const UpdateStateAttendanceByTeacher = async(idDiemDanh, updateTime, trangThai, updateById) => {
    const UpdateStateAttendanceByTeacher = await dao.sequelize.query(
        `Exec [Update_Attendance_By_Teacher] ${idDiemDanh} ,'${updateTime}',  ${trangThai} ,${updateById}`, { raw: true, nest: true })
    return UpdateStateAttendanceByTeacher
}

const GetDetailsDayOffStudentByIdDayOff = async(idXinNghiHoc) => {
        const GetDetailsDayOffStudentByIdDayOff = await dao.sequelize.query(
            `[Get_Details_Day_Off_Student_By_Id_Day_Off] ${idXinNghiHoc}`, { raw: true, nest: true })
        return GetDetailsDayOffStudentByIdDayOff
    }
const InsertScoreByYearAndSemmesterIdHocSinh_XLSX = async(InfoDiem) => {
    
        const InsertScoreByYearAndSemmesterIdHocSinh = await dao.sequelize.query(
            `[Insert_Score_By_Year_And_Semmester_IdHocSinh] ${InfoDiem.idHocSinh}, ${InfoDiem.kiHoc},${InfoDiem.namHoc},${InfoDiem.diemmonHoc},${InfoDiem.idMonHoc}`, { raw: true, nest: true })
        return InsertScoreByYearAndSemmesterIdHocSinh
    }
const GetAllScoreStudentInSemesterByClass = async(idThongTinLop, kiHoc, namHoc) => {
        const GetAllScoreStudentInSemesterByClass = await dao.sequelize.query(
            `[Get_All_Score_Student_In_Semester_By_Class] ${idThongTinLop} ,${kiHoc}, ${namHoc}`, { raw: true, nest: true })
        return GetAllScoreStudentInSemesterByClass
    }
    // start export
module.exports = {
        login,
        uploadTimetable,
        getCurrentTimeTable,
        getClassByTeacherNow,
        Get_All_Student_By_Class,
        Insert_Single_Student,
        CreateTeacher,
        changePassword,
        getContactStudent,
        GetAllConductStudentByIdClassYearAndSemster,
        GetConductStudentByYear,
        GetAllConductDetailsStudentByYearAndSemester,
        InsertConductDetailsStudent,
        UpdatedConfirmedParentToDayOffById,
        UpdatedStateConfirmedParentToDayOffById,
        GetAllDayOffStudentByClass,
        Insert_Single_Student_xlsx,
        UpdateStateAttendanceByTeacher,
        GetDetailsDayOffStudentByIdDayOff,
        InsertScoreByYearAndSemmesterIdHocSinh_XLSX,
        GetAllScoreStudentInSemesterByClass
    }
    // end export