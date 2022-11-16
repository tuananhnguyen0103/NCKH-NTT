//require time table models
const timetablesModels = require('../Models/ThoiKhoaBieu').ThoiKhoaBieu
const Sequelize = require('sequelize');
const DataTypes = Sequelize.DataTypes;
const Acount = require('../Models/Account').Acount;
const dao = require('../Dao/Connection')
const Cryptr = require('cryptr');
const cryptr = new Cryptr(process.env.PASSWORD_ENCRYPTION);
/*function*/
const login = async(req, res) => {

    // const user=await Acount.findOne({where:{account:body.id,password:body.password}
    //     ,raw: true
    // ,attributes:['idGiaoVien']  })
    // return user!=null ? user :null
    var account = req.body.id;
    const user = await dao.sequelize.query(
        `Exec studentLogin ${account}`, { raw: true, nest: true }
    )
    return user[1] != 0 ? user[0] : null
}
const changePassword = async(req) => {
    const user = await Acount.findOne({ where: { idHocSinh: req.user.idHocSinh } });
    const de = cryptr.decrypt(user.password)
    if (cryptr.decrypt(user.password) == req.body.oldPassword) {

        const teacher = await dao.sequelize.query(
            `Exec Student_Change_Password N'${req.user.idHocSinh}','${cryptr.po(req.body.NewPassword)}'`, { raw: true, nest: true }
        )
        return teacher;
    } else {
        return null;
    }
}
const getstudentByid = async(id, res) => {
    const user = await dao.sequelize.query("exec studentId @id  = '" + id + "'", { raw: true, nest: true })
    return user[1] != 0 ? user[0] : null
}




const uploadFaceId = async function(id, file, path) {
    const user = await dao.sequelize.query(`exec setFaceId @id= '` + id + `' 
  , @anh1= '` + path + file[0] + `'
  , @anh2= '` + path + file[1] + `'
  , @anh3= '` + path + file[2] + `'
  , @anh4= '` + path + file[3] + `'
  , @anh5= '` + path + file[4] + `'
  , @anh6= '` + path + file[5] + `'
  , @anh7= '` + path + file[6] + `'
  , @anh8= '` + path + file[7] + `'
  , @anh9= '` + path + file[8] + `'
  , @anh10= '` + path + file[9] + `'`, { raw: true, nest: true })
}



const uploadAvatar = async function(id, path) {
    const query = `exec setAvatar @id='${id}',@anhThe= '${path}'`;
    const user = await dao.sequelize.query(query, { raw: true, nest: true })
}
const uploadFaceIdMobile = async function(id, file, path) {
    //     const user = await dao.sequelize.query(`exec setFaceId @id= '` + id + `' 
    // , @anh1= '` + path + file[0] + `'
    // , @anh2= '` + path + file[1] + `'
    // , @anh3= '` + path + file[2] + `'
    // , @anh4= '` + path + file[3] + `'
    // , @anh5= '` + path + file[4] + `'
    // , @anh6= '` + path + file[5] + `'
    // , @anh7= '` + path + file[6] + `'
    // , @anh8= '` + path + file[7] + `'
    // , @anh9= '` + path + file[8] + `'
    // , @anh10= '` + path + file[9] + `'`, { raw: true, nest: true })
    // }
    // start
    const user = await dao.sequelize.query(`exec setFaceId @id= '` + id + `' 
    , @anh1= '` + file[0] + `'
    , @anh2= '` + file[1] + `'
    , @anh3= '` + file[2] + `'
    , @anh4= '` + file[3] + `'
    , @anh5= '` + file[4] + `'
    , @anh6= '` + file[5] + `'
    , @anh7= '` + file[6] + `'
    , @anh8= '` + file[7] + `'
    , @anh9= '` + file[8] + `'
    , @anh10= '` + file[9] + `'`, { raw: true, nest: true })
}
const getTimeTable = async(idHocSinh) => {
    const timeTable = await dao.sequelize.query(`exec getCurrentThoiKhoaBieu ${idHocSinh}`, { raw: true, nest: true })
    return timeTable[0]
}
const getAllTimeTable = async(idHocSinh) => {
    const timeTable = await dao.sequelize.query(`exec getAllThoiKhoaBieu ${idHocSinh}`, { raw: true, nest: true })
    return timeTable
}

const Get_All_Score_Student = async(idHocSinh) => {
        const listScoreStudent = await dao.sequelize.query(
            `Exec Get_All_Score_Student ` + idHocSinh, { raw: true, nest: true }
        )
        return listScoreStudent
    }
    // end
    // start Get_All_Score_Student_In_Semester
const GetAllScoreStudentInSemester = async(idHocSinh, kiHoc, namHoc) => {
        const scoreStudent = await dao.sequelize.query(
            // `Exec Get_All_Score_Student_In_Semester `+idHocSinh +' '+kiHoc+' '+namHoc
            `Exec Get_All_Score_Student_In_Semester ${idHocSinh}, ${kiHoc}, ${namHoc}`, { raw: true, nest: true }

        )
        return scoreStudent
    }
    // end Get_All_Score_Student_In_Semester

// start Insert_Day_Off_By_Student
const InsertDayOffByStudent = async(idHocSinh, noiDung,dateCreate) => {
        const scoreStudent = await dao.sequelize.query(
            `Exec Insert_Day_Off_By_Student ${idHocSinh}, N'${noiDung}',N'${dateCreate}'`, { raw: true, nest: true }
        )
        return scoreStudent
    }
    // end Insert_Day_Off_By_Student
    /*end function*/

module.exports = {
    login,
    uploadFaceId,
    getstudentByid,
    Get_All_Score_Student,
    GetAllScoreStudentInSemester,
    uploadFaceIdMobile,
    uploadAvatar,
    getTimeTable,
    getAllTimeTable,
    InsertDayOffByStudent,
    changePassword
}