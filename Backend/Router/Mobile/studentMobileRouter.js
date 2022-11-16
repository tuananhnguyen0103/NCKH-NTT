var express = require('express');
//Use router funtion from express
var router = express.Router();
// middlaware to check out the request
const middleware = require("../../Middleware/studentAuth")
    //requie teacher controller
const attendanceController = require("../../Controller/attendanceController")
const studentController = require("../../Controller/studentController")


router.post('/uploadFaceIdMobile/:token', middleware, studentController.uploadFaceIDMobile);
router.post('/uploadAvatar/:token', middleware, studentController.uploadAvatar);
router.get('/getTimeTable/:token', middleware, studentController.getTimeTable);
router.get('/getAllTimeTable/:token', middleware, studentController.getAllTimeTable);
router.get('/GetAllScoreStudent/:token', middleware, studentController.Get_All_Score_Student);
// export router to use in index file
module.exports = router;