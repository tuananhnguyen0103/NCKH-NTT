const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('Diem', {
    idDiem: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    idMonHoc: {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    idHocSinh: {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    kiHoc: {
      type: DataTypes.CHAR(10),
      allowNull: false,
      primaryKey: true
    },
    namHoc: {
      type: DataTypes.STRING(50),
      allowNull: false,
      primaryKey: true
    },
    diem: {
      type: DataTypes.FLOAT,
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
    tableName: 'Diem',
    schema: 'dbo',
    timestamps: false,
    indexes: [
      {
        name: "PK_Diem",
        unique: true,
        fields: [
          { name: "idDiem" },
          { name: "idMonHoc" },
          { name: "idHocSinh" },
          { name: "kiHoc" },
          { name: "namHoc" },
        ]
      },
    ]
  });
};
