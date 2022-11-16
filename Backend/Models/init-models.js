var DataTypes = require("sequelize").DataTypes;
var _Acount = require("./Acount");
var _CMND = require("./CMND");
var _Diem = require("./Diem");
var _DiemDanh = require("./DiemDanh");
var _GiaDinh = require("./GiaDinh");
var _HanhKiem = require("./HanhKiem");
var _HocSinh = require("./HocSinh");
var _Lop = require("./Lop");
var _ThoiKhoaBieu = require("./ThoiKhoaBieu");
var _ThongBao = require("./ThongBao");
var _ThongTinLop = require("./ThongTinLop");

function initModels(sequelize) {
  var Acount = _Acount(sequelize, DataTypes);
  var CMND = _CMND(sequelize, DataTypes);
  var Diem = _Diem(sequelize, DataTypes);
  var DiemDanh = _DiemDanh(sequelize, DataTypes);
  var GiaDinh = _GiaDinh(sequelize, DataTypes);
  var HanhKiem = _HanhKiem(sequelize, DataTypes);
  var HocSinh = _HocSinh(sequelize, DataTypes);
  var Lop = _Lop(sequelize, DataTypes);
  var ThoiKhoaBieu = _ThoiKhoaBieu(sequelize, DataTypes);
  var ThongBao = _ThongBao(sequelize, DataTypes);
  var ThongTinLop = _ThongTinLop(sequelize, DataTypes);


  return {
    Acount,
    CMND,
    Diem,
    DiemDanh,
    GiaDinh,
    HanhKiem,
    HocSinh,
    Lop,
    ThoiKhoaBieu,
    ThongBao,
    ThongTinLop,
  };
}
module.exports = initModels;
module.exports.initModels = initModels;
module.exports.default = initModels;
