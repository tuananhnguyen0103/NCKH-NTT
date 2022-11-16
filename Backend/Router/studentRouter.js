var express = require('express');
//Use router funtion from express
var router = express.Router();
// middlaware to check out the request
const middleware = require("../Middleware/studentAuth")
    //requie teacher controller
const attendanceController = require("../Controller/attendanceController")
const studentController = require("../Controller/studentController")
    //handles post request with url /api/login
router.post('/attendance', middleware, attendanceController.studentAttendance);
router.post('/login', studentController.login);
router.post('/changePassword', middleware, studentController.changePassword);
router.post('/uploadFaceid', middleware, studentController.uploadFaceID);
router.get('/getByid/:id', middleware, studentController.getstudentByid);
router.get('/GetAllScoreStudent', middleware, studentController.Get_All_Score_Student);
router.get('/GetAllScoreStudentInSemester', middleware, studentController.GetAllScoreStudentInSemester);
router.post('/InsertDayOffByStudent', middleware, studentController.InsertDayOffByStudent);
// export router to use in index file
module.exports = router;