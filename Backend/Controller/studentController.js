const attendanceIml = require('../Implement/attendanceIpm')
const studentIpm = require('../Implement/studentIpm')
    //require tools
const tools = require('../Server/tools')
const token = require('../Server/token')
const fileUpload = require('../Server/fileUpload')
const fs = require('fs');
const Cryptr = require('cryptr');
const socket = require('../server').io

const cryptr = new Cryptr(process.env.PASSWORD_ENCRYPTION);
const login = async(req, res) => {
    const user = await studentIpm.login(req, res)
    if (user === undefined) {

        res.status(401).json("Wrong username or password")

    } else {
        console.log(user)
        console.log()
        if (req.body.password == cryptr.decrypt(user.password)) {
            const tokenString = await token.createToken(user.idHocSinh)
            delete user.password;
            return res.status(200).cookie('token', tokenString, { maxAge: 86400000, httpOnly: false })
                .cookie('user', user, { maxAge: 86400000, httpOnly: false, sameSite: false }).json({

                    hocSinh: user,
                    token: tokenString
                })
        }
        return res.status(401).json("Wrong username or password");
    }

}

const uploadFaceID = async(req, res) => {
    if (req.files.file.length < 5) {
        return res.status(400).json({ data: "hãy chọn đủ 10 ảnh trở lên" })
    }
    const filepath = await fileUpload.uploadFaceId(req.files.file, req.user.idHocSinh, res)
    if (filepath == null) {
        res.status(400).json({ data: 'chỉ chấp nhận ảnh jpg,png' })
    } else {
        const path = "storage/uploads/Faceid/" + req.user.idHocSinh;
        const fileList = ["/anh1.jpg", "/anh2.jpg", "/anh3.jpg", "/anh4.jpg", "/anh5.jpg", "/anh6.jpg", "/anh7.jpg", "/anh8.jpg", "/anh9.jpg", "/anh10.jpg"]
        studentIpm.uploadFaceId(req.user.idHocSinh, fileList, path)
        res.status(201).json("done")

    }
}
const changePassword = async(req, res) => {
    const data = await studentIpm.changePassword(req);
    if (data != null) {
        res.status(200).send(data)
    } else {
        res.status(401).send("Mật khẩu hiện tại không đúng")
    }
}
const uploadFaceIDMobile = async(req, res) => {
    if (req.files.file.length < 10) {
        return res.status(400).json({ data: "hãy chọn đủ 10 ảnh trở lên" })
    }
    const filepath = await fileUpload.uploadFaceId(req.files.file, req.user.idHocSinh, res)
    if (filepath == null) {
        res.status(400).json({ data: 'chỉ chấp nhận ảnh jpg,png' })
    } else {

        const path = "storage/uploads/Faceid/" + req.user.idHocSinh;
        const fileList = ["/anh1.jpg", "/anh2.jpg", "/anh3.jpg", "/anh4.jpg", "/anh5.jpg", "/anh6.jpg", "/anh7.jpg", "/anh8.jpg", "/anh9.jpg", "/anh10.jpg"]
            // studentIpm.uploadFaceIdMobile(req.user.idHocSinh, fileList, path)
            //upload firebase
        studentIpm.uploadFaceIdMobile(req.user.idHocSinh, filepath, path)
        res.status(201).json("done")

    }
}
const uploadAvatar = async(req, res) => {
    const filepath = await fileUpload.uploadAvatarStudent(req.files, req.user.idHocSinh, res)
    if (filepath == null) {
        res.status(400).json({ data: 'chỉ chấp nhận ảnh jpg,png' })
    } else {
        studentIpm.uploadAvatar(req.user.idHocSinh, filepath)
        res.status(201).json("done")
    }
}
const getTimeTable = async(req, res) => {
    const timeTable = await studentIpm.getTimeTable(req.user.idHocSinh)
    if (timeTable.length < 1) {
        return res.status(400).send("Không có thời khóa biểu")
    } else {
        return res.status(201).send(timeTable)
    }
}
const getAllTimeTable = async(req, res) => {
    const allTimeTable = await studentIpm.getAllTimeTable(req.user.idHocSinh)
    if (allTimeTable.length < 1) {
        return res.status(400).send("Không có thời khóa biểu")
    } else {
        return res.status(200).send(allTimeTable)
    }
}
const getstudentByid = async(req, res) => {
    res.status(200).send(await studentIpm.getstudentByid(req.params.id))
}

// start Get_All_Score_Student
const Get_All_Score_Student = async(req, res) => {
        const listScoreStudent = await studentIpm.Get_All_Score_Student(req.user.idHocSinh)
        return res.status(200).send(listScoreStudent)
    }
    // end Get_All_Score_Student

// start GetAllScoreStudentInSemester
const GetAllScoreStudentInSemester = async(req, res) => {
        // console.log(req)
        const scoreStudent = await studentIpm.GetAllScoreStudentInSemester(req.user.idHocSinh, req.body.kiHoc, req.body.namHoc)

        return res.status(200).send(scoreStudent)
    }
    // end GetAllScoreStudentInSemester
    // start GetAllScoreStudentInSemester
const InsertDayOffByStudent = async(req, res) => {
        // console.log(req)
        const dateCreate = tools.getDate();
        const scoreStudent = await studentIpm.InsertDayOffByStudent(req.user.idHocSinh, req.body.noiDung,dateCreate)
        socket.emit(req.user.idThongTinLop, { "Thông báo": "Có yêu cầu nghỉ học mới" })

        return res.status(200).send(scoreStudent)
    }
    // end InsertDayOffByStudent
module.exports = {
    login,
    uploadFaceID,
    getstudentByid,
    Get_All_Score_Student,
    GetAllScoreStudentInSemester,
    uploadFaceIDMobile,
    uploadAvatar,
    getTimeTable,
    InsertDayOffByStudent,
    getAllTimeTable,
    changePassword
}