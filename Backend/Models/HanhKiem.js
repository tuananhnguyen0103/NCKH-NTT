const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('HanhKiem', {
    idHanhKiem: {
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
    diemHanhKiem: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    namHoc: {
      type: DataTypes.STRING(50),
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
    tableName: 'HanhKiem',
    schema: 'dbo',
    timestamps: false,
    indexes: [
      {
        name: "PK_HanhKiem",
        unique: true,
        fields: [
          { name: "idHanhKiem" },
          { name: "idHocSinh" },
        ]
      },
    ]
  });
};
