

const { Sequelize, Model, DataTypes } = require("sequelize");
const { sequelize } = require('../Dao/Connection')
// const { Sequelize } = require('sequelize');
// Option 3: Passing parameters separately (other dialects)

// console.log(sequelize)
class GiaDinh extends Model { };
GiaDinh.init(
  {
    idGiaDinh: {
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
    tenCha: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    tenMe: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    namSinhCha: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    namSinhMe: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    namSinhNGH: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    ngheNghiepCha: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    ngheNghiepMe: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    SDTCha: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    SDTMe: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    danTocCha: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    danTocMe: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    hoTenNguoiGiamHo: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    ngheNghiepNGH: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    tonGiaoCha: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    tonGiaoMe: {
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
      type: DataTypes.DATE,
      allowNull: true
    },
    updated_by_user_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }
  , {
    sequelize,
    tableName: 'GiaDinh',
    schema: 'dbo',
    timestamps: false,
    indexes: [
      {
        name: "PK_GiaDinh",
        unique: true,
        fields: [
          { name: "idGiaDinh" },
          { name: "idHocSinh" },
        ]
      },
    ]
});

module.exports.GiaDinh = GiaDinh;