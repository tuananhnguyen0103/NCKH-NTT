const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('ThongTinLop', {
    idThongTinLop: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    idLop: {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    khoi: {
      type: DataTypes.CHAR(10),
      allowNull: true
    },
    namhoc: {
      type: DataTypes.CHAR(10),
      allowNull: false,
      primaryKey: true
    },
    idGiaoVien: {
      type: DataTypes.CHAR(10),
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
    }
  }, {
    sequelize,
    tableName: 'ThongTinLop',
    schema: 'dbo',
    timestamps: false,
    indexes: [
      {
        name: "PK_ThongTinLop",
        unique: true,
        fields: [
          { name: "idThongTinLop" },
          { name: "idLop" },
          { name: "namhoc" },
        ]
      },
    ]
  });
};
