


const { Sequelize, Model, DataTypes } = require("sequelize");
const { sequelize } = require('../Dao/Connection')
// const { Sequelize } = require('sequelize');
// Option 3: Passing parameters separately (other dialects)

// console.log(sequelize)
class DiemDanh extends Model { };
DiemDanh.init({
  idDiemDanh: {
    autoIncrement: true,
    type: DataTypes.INTEGER,
    allowNull: false,
    primaryKey: true
  },
  idHocSinh: {
    type: DataTypes.INTEGER,
    allowNull: false,
    primaryKey: true
  },
  ngayDiemDanh: {
    type: DataTypes.STRING(100),
    allowNull: true
  },
  trangThai: {
    type: DataTypes.INTEGER,
    allowNull: true
  },
  created_by_user_id: {
    type: DataTypes.INTEGER,
    allowNull: true
  },
  created_date_time: {
    type: DataTypes.STRING(100),
    allowNull: true
  },
  active_flag: {
    type: DataTypes.INTEGER,
    allowNull: true
  },
  updated_date_time: {
    type: DataTypes.STRING(100),
    allowNull: true
  },
  updated_by_user_id: {
    type: DataTypes.INTEGER,
    allowNull: true
  }
}, {
  sequelize, modelName: 'DiemDanh'
});

module.exports.DiemDanh = DiemDanh;