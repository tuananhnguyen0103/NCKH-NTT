var accountSid = process.env.TWILIO_ACCOUNT_SID; // Your Account SID from www.twilio.com/console
var authToken = process.env.TWILIO_AUTH_TOKEN; // Your Auth Token from www.twilio.com/console
const VoiceResponse = require('twilio').twiml.VoiceResponse;
const teacherIlm = require('../Implement/teacherIpm')
    // const client = require('twilio')(accountSid, authToken, {
    //     lazyLoading: true
    // });
const GetCallParentTeacher = async(req, res) => {
    const idXinNghiHoc = req.query.idXinNghiHoc;
    var xacNhan = 3;
    const twiml = new VoiceResponse();
    //   const key = 0;
    // If the user entered digits, process their request
    if (req.body.Digits) {
        switch (req.body.Digits) {
            case '1':
                {
                    twiml.play('https://utehy-student-management.herokuapp.com/storage' + '/voices/acpect_get_day_off.mp3');
                    xacNhan = 1;
                    // twiml.
                    break;
                }
            case '2':
                {
                    twiml.play('https://utehy-student-management.herokuapp.com/storage' + '/voices/reject_get_day_off.mp3');
                    xacNhan = 2
                    break;
                }
        }
    }
    twiml.play('https://utehy-student-management.herokuapp.com/storage' + '/voices/thank_you.mp3');
    teacherIlm.UpdatedStateConfirmedParentToDayOffById(idXinNghiHoc, xacNhan)
    res.type('text/xml');
    res.send(twiml.toString());
    // }
    //   const gather = twiml.gather({
    //     numDigits: 1,
    //     //thay bằng link api muốn gọi
    //     // action: '/gather',
    //   });
    //   gather.say('if you can hear this you the best ');
    //   console.log(req.body.Digits)
    //
    //   // If the user doesn't enter input, loop
    //   twiml.redirect('/test/a');

    // Render the response as XML in reply to the webhook request

}


module.exports = {
    GetCallParentTeacher
}