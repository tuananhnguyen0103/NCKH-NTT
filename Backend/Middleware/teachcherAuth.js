// use jwt token library
const jwt = require("jsonwebtoken");
const teacher = require("../Models/GiaoVien").GiaoVien

require('dotenv').config()


const verifyToken = async (req, res, next) => {
    //4 way to send token
  const token =
    req.body.token || req.query.token || req.headers["x-access-token"]||req.cookies.token;
    //if token not exists
  if (!token) {
    return res.status(403).send({
      "err": "token missing"
    });
  }
  
  try {
    //check if token valid and decoded token to get token value
    const decoded = jwt.verify(token, process.env.JWT_SECRET);
    console.log(decoded);
    const user = await teacher.findByPk(decoded._id)
    // // req.token=token
    req.user = user
  } catch (err) {
      //if error send 403 
    return res.status(401).send(
      {
        "error": "Token invalid",
        "detail": err
      }
    );
  }
  //if token is  valid continue request with user info 
  return next();
};

module.exports = verifyToken;
