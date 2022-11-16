const { Sequelize, Model, DataTypes } = require("sequelize");
const { sequelize } = require('../Dao/Connection')
// const { Sequelize } = require('sequelize');
// Option 3: Passing parameters separately (other dialects)

// console.log(sequelize)
class Acount extends Model { };
Acount.init({
  account: {
    type: DataTypes.TEXT,
    allowNull: true
  },
  idHocSinh: {
    type: DataTypes.INTEGER,
    allowNull: false
  },
  idGiaoVien: {
    type: DataTypes.TEXT,
    allowNull: true
  },
  password: {
    type: DataTypes.TEXT,
    allowNull: true
  },
  history: {
    type: DataTypes.TEXT,
    allowNull: true
  },
  created_by_user_id: {
    type: DataTypes.INTEGER,
    allowNull: true
  },
  created_date_time: {
    type: DataTypes.DATE,
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
  },
  idAcount: {
    type: DataTypes.INTEGER,
    allowNull: false,
    primaryKey: true,
    autoIncrement: true
  }
}, {
  sequelize, modelName: 'Acount'
});

module.exports.Acount = Acount;