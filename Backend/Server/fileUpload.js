require("dotenv").config();
//libraly to create uuid id
const { v4: uuidv4 } = require("uuid");
//handle path file
var getpath = require("path");
const firebase = require("./firebase");
const tools = require("./tools")
    //module to manager file system
const fs = require("fs");
//check if folder exit then create
if (!fs.existsSync("./storage/uploads/timetable")) {
    console.log("create");
    //Indicates whether parent folders should be created. If a folder was created, the path to the first created folder will be returned.
    fs.mkdirSync("./storage/uploads/timetable", { recursive: true });
}

const uploadTimetable = async(files, idLop) => {

    file = files.files
    console.log(file)
    const ext = getpath.extname(file.name).toLowerCase();
    let today = tools.getDate()
    const url = `https://firebasestorage.googleapis.com/v0/b/${process.env.REACT_APP_PROJECT_ID}.appspot.com/o/${idLop}%2FTimeTable%2Fthoikhoabieu${today}${ext}?alt=media`
    console.log(url);
    var filepath = url;
    (async() => {
        const buffer = file.data;
        console.log(buffer);
        const fileUpload = {
            originalname: file.name,
            mimetype: file.mimetype,
            buffer,
        };
        try {
            await uploadFile(fileUpload, `${idLop}/TimeTable/thoikhoabieu${today}${ext}`);
        } catch (err) {
            console.log(err);
        }
    })();
    //end

    return filepath;
};

const uploadAvatarStudent = async(files, idHs) => {

    files = files.file
    console.log(files)
    const ext = getpath.extname(files.name).toLowerCase();
    const url = `https://firebasestorage.googleapis.com/v0/b/${process.env.REACT_APP_PROJECT_ID}.appspot.com/o/Student%2F${idHs}%2Favatar${ext}?alt=media`
    console.log(url);
    var filepath = url;
    (async() => {
        const buffer = files.data;
        console.log(buffer);
        const fileUpload = {
            originalname: files.name,
            mimetype: files.mimetype,
            buffer,
        };
        try {
            await uploadFile(fileUpload, `Student/${idHs}/avatar${ext}`);
        } catch (err) {
            console.log(err);
        }
    })();
    //end
    return filepath;
};
const uploadFaceId = async(files, idHs, res) => {
    if (!fs.existsSync("./storage/uploads/Faceid/" + idHs)) {
        console.log("create");
        //Indicates whether parent folders should be created. If a folder was created, the path to the first created folder will be returned.
        fs.mkdirSync("./storage/uploads/Faceid/" + idHs, { recursive: true });
    }
    var filepath = [];
    await files.forEach(async(file, i) => {
        const ext = getpath.extname(file.name).toLowerCase();
        // const path = "storage/uploads/Faceid/" + idHs + "/anh" + (i + 1) + ext;
        // await filepath.push(path);
        // if (ext != ".jpeg" && ext != ".png" && ext != ".jpg") return null;
        // await file.mv(path, async function(err) {
        //     //if err return error
        //     if (err) console.log(err);
        // });
        //upload file
        //start
        const url = `https://firebasestorage.googleapis.com/v0/b/${process.env.REACT_APP_PROJECT_ID}.appspot.com/o/Student%2F${idHs}%2Fface${i+1}${ext}?alt=media`
        console.log(url);
        filepath.push(url);
        (async() => {
            const buffer = file.data;
            console.log(buffer);
            const fileUpload = {
                originalname: file.name,
                mimetype: file.mimetype,
                buffer,
            };
            try {
                await uploadFile(fileUpload, `Student/${idHs}/face${i+1}${ext}`);
            } catch (err) {
                console.log(err);
            }
        })();
        //end
    });
    return filepath;
};
const uploadFile = (file, filename) => {
    let prom = new Promise((resolve, reject) => {
        if (!file) {
            reject("No image file");
        }
        let newFileName = filename;
        let fileUpload = firebase.bucket.file(newFileName);
        const blobStream = fileUpload.createWriteStream({
            metadata: {
                contentType: file.mimetype,
            },
        });
        blobStream.on("error", (error) => {
            reject("Something is wrong! Unable to upload at the moment.");
        });

        blobStream.on("finish", () => {
            // The public URL can be used to directly access the file via HTTP.
            const url = `https://storage.googleapis.com/${firebase.bucket.name}/${fileUpload.name}`;
            resolve(url);
        });

        blobStream.end(file.buffer);
    });
    return prom;
};
module.exports = {
    uploadTimetable,
    uploadFaceId,
    uploadAvatarStudent,
};