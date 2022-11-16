var admin = require("firebase-admin");

var serviceAccount = require("../thehocsinhdientu-firebase-adminsdk-wwcyi-cde79348b5.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    storageBucket: 'thehocsinhdientu.appspot.com'
});
const bucket = admin.storage().bucket()
const storage = admin.storage()
module.exports = {
    bucket,
    storage
}