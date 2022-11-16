




const { Sequelize, Model, DataTypes } = require("sequelize");
const { sequelize } = require('../Dao/Connection')
// const { Sequelize } = require('sequelize');
// Option 3: Passing parameters separately (other dialects)

// console.log(sequelize)
class ThoiKhoaBieu extends Model { };
ThoiKhoaBieu.init(
  {
    idThoiKhoaBieu: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    duongDan: {
      type: DataTypes.STRING(300),
      allowNull: true
    },
    ngayBatDau: {
      type: DataTypes.DATE,
      allowNull: true
    },
    ngayKetThuc: {
      type: DataTypes.DATE,
      allowNull: true
    },
    idLop: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    createByID: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    updateTime: {
      type: DataTypes.DATEONLY,
      allowNull: true
    },
    flag: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    createTime: {
      type: DataTypes.DATEONLY,
      allowNull: true
    },
    updateById: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
}, {
  sequelize, modelName: 'ThoiKhoaBieu'
});

module.exports.ThoiKhoaBieu = ThoiKhoaBieu;