const { Sequelize, Model, DataTypes } = require("sequelize");
const { sequelize } = require("../Dao/Connection");
// const { Sequelize } = require('sequelize');
// Option 3: Passing parameters separately (other dialects)

// console.log(sequelize)
class GiaoVien extends Model {}
GiaoVien.init(
  {
    hoTen: {
      type: DataTypes.TEXT,
      allowNull: true,
    },
    anh: {
      type: DataTypes.TEXT,
      allowNull: true,
    },
    idGiaoVien: {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true,
      autoIncrement: true,
    },
  },
  {
    sequelize,
    modelName: "GiaoVien",
  }
);

module.exports.GiaoVien = GiaoVien;
