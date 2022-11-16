var express = require('express');
//Use router funtion from express
var router = express.Router();
const voiceController = require("../../Server/voice.js")
router.post('/getDayOffsXML', voiceController.GetCallParentTeacher);

module.exports = router;