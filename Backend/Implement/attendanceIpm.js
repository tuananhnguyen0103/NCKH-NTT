const attendanceModel = require('../Models/DiemDanh').DiemDanh
const dao = require('../Dao/Connection')


const StudentAttendance = async(data, res) => {
    //check if student had attendance for today
  
        return await dao.sequelize.query(

            `exec insert_diemdanh @idHocSinh= '` + data.idHocSinh + `'
            , @ngayDiemDanh= '` + data.ngayDiemDanh + `'
            , @trangThai= '` + data.trangThai + `'`, { raw: true, nest: true }
        );

}


const getDiemDanhByIdGiaoVien = async(id, date) => {
    const attendance = await dao.sequelize.query("exec getAttendance @idGiaovien= '" + id + "' , @date= '" + date + "'", { raw: true, nest: true })

    //   console.log(attendance)
    return attendance
}
const InsertAllAttendanceStudentDaily = async(year, time) => {
    const InsertAllAttendanceStudentDaily = await dao.sequelize.query(
        `Exec Insert_All_Attendance_Student_Daily ${year} ,'${time}'`, { raw: true, nest: true })
    return InsertAllAttendanceStudentDaily
}

module.exports = {
    StudentAttendance,
    getDiemDanhByIdGiaoVien,
    InsertAllAttendanceStudentDaily,
    
}