var accountSid = process.env.TWILIO_ACCOUNT_SID; // Your Account SID from www.twilio.com/console
var authToken = process.env.TWILIO_AUTH_TOKEN;   // Your Auth Token from www.twilio.com/console
const client = require('twilio')(accountSid, authToken, {
    lazyLoading: true
});

// //sample code
// client.messages
//   .create({
//      body: 'đm tú',
//      from: '+16205571569',
//      to: ''
//    })
//   .then(message => console.log(message));

module.exports = client;
