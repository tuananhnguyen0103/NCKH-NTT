// use jwt token library
const jwt = require("jsonwebtoken");
const account = require("../Models/Account")
const studentModel = require("../Models/HocSinh").HocSinh
require('dotenv').config()


const verifyToken = async(req, res, next) => {
    //4 way to send token
    const token =
        req.body.token || req.query.token || req.headers["x-access-token"] || req.cookies.token || req.params.token;

    if (!token) {
        return res.status(403).send({
            "err": "token missing"
        });
    }

    //check if token valid and decoded token to get token value
    const decoded = jwt.verify(token, process.env.JWT_SECRET, async function(err, decoded) {
        if (err) {
            console.log(err);
            return res.status(401).send({
                "error": "Token invalid",
                "detail": err
            });
        }
        const user = await studentModel.findByPk(decoded._id)
        req.token = token
        req.user = user
        return next();
    });;


    //if token is  valid continue request with user info 

};

module.exports = verifyToken;