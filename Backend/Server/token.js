//use token library
const jwt = require('jsonwebtoken')

const createToken=async function(id){
    //create token from user id and key from env file
    const token=await jwt.sign({ _id:id  }, process.env.JWT_SECRET, { expiresIn: '24h' })
    return token;
}

module.exports = {
  createToken
}