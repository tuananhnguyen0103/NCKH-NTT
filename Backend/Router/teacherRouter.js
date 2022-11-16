var express = require('express');
//Use router funtion from express
var router = express.Router();
// middlaware to check out the request
const middleware = require("../Middleware/teachcherAuth")
const attendanceController = require("../Controller/attendanceController")
    //requie teacher controller
const teachcherController = require("../Controller/teacherController")
    //handles post request with url /api/login
router.post('/login', teachcherController.login);

router.post('/uploadTimeTable', middleware, teachcherController.uploadTimetable);

router.post('/sendsms', middleware, teachcherController.sendSms);

router.post('/acceptAllAttendance', middleware, teachcherController.acceptAllAttendance);

router.post('/getAttendance', middleware, attendanceController.getDiemDanhByIdGiaoVien);

router.get('/unlockFaceId/:studentId', middleware, teachcherController.unlockFaceId)

router.get('/HomeroomClasses', middleware, teachcherController.HomeroomClasses)

router.get('/getCurrentTimeTable', middleware, teachcherController.getCurrentTimeTable)

router.get('/getClassByTeacherNow', middleware, teachcherController.getClassByTeacherNow)

router.post('/insertStudentXlsx', middleware, teachcherController.addStudentViaElsx)

router.post('/InsertSingleStudent', middleware, teachcherController.InsertSingleStudent)

router.post('/changePassword', middleware, teachcherController.changePassword)

router.get('/getContactStudent', middleware, teachcherController.getContactStudent)


router.post('/InsertSingleTeacher', middleware, teachcherController.CreateTeacher)

router.post('/GetAllConductStudentByIdClassYearAndSemster', middleware, teachcherController.GetAllConductStudentByIdClassYearAndSemster)

router.post('/GetConductStudentByYear', middleware, teachcherController.GetConductStudentByYear)

router.post('/GetAllConductDetailsStudentByYearAndSemester', middleware, teachcherController.GetAllConductDetailsStudentByYearAndSemester)

router.post('/InsertConductDetailsStudent', middleware, teachcherController.InsertConductDetailsStudent)

router.post('/CallParentTeacher', middleware, teachcherController.CallParentTeacher)

router.get('/GetAllDayOffStudentByClass', middleware, teachcherController.GetAllDayOffStudentByClass)

router.post('/UpdateStateAttendanceByTeacher', middleware, teachcherController.UpdateStateAttendanceByTeacher)

router.post('/UpdatedStateConfirmedParentToDayOffById', middleware, teachcherController.UpdatedStateConfirmedParentToDayOffById)

router.get('/GetDetailsDayOffStudentByIdDayOff/:idXinNghiHoc', middleware, teachcherController.GetDetailsDayOffStudentByIdDayOff)

router.get('/ListStudentWithinScore.xlsx',middleware, teachcherController.CreateFileExcel)

router.post('/InsertScoreByYearAndSemmesterIdHocSinh_XLSX', middleware, teachcherController.InsertScoreByYearAndSemmesterIdHocSinh_XLSX)

router.post('/GetAllScoreStudentInSemesterByClass', middleware, teachcherController.GetAllScoreStudentInSemesterByClass)
    // export router to use in index file
module.exports = router;