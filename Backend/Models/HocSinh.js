



const { Sequelize, Model, DataTypes } = require("sequelize");
const { sequelize } = require('../Dao/Connection')
// const { Sequelize } = require('sequelize');
// Option 3: Passing parameters separately (other dialects)

// console.log(sequelize)
class HocSinh extends Model { };
HocSinh.init(
  {
    idHocSinh: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    idThongTinLop: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    idThongTInLopCu: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    ngaySinh: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    anh1: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    anh2: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    anh3: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    anh4: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    anh5: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    SDT: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    queQuanTinh: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    queQuanHuyen: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    queQuanXa: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    queQuanThonXom: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    noiSinh: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    noiThuongTru: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    doanVien: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    luuBanNamTruoc: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    doiVien: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    danToc: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    gioiTinh: {
      type: DataTypes.STRING(50),
      allowNull: true
    },
    tonGiao: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    doiTuongChinhSach: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    hoTen: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    namNhapHoc: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    namRaTruong: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    khoaAnh: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    anhThe: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    created_by_user_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    created_date_time: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    active_flag: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    updated_date_time: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    updated_by_user_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }
, {
  sequelize, modelName: 'HocSinh'
});

module.exports.HocSinh = HocSinh;