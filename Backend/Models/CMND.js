const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('CMND', {
    idHocSinh: {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    soThe: {
      type: DataTypes.STRING(50),
      allowNull: true
    },
    ngayCap: {
      type: DataTypes.DATEONLY,
      allowNull: true
    },
    noiCap: {
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
      type: DataTypes.CHAR(10),
      allowNull: true
    },
    updated_date_time: {
      type: DataTypes.CHAR(10),
      allowNull: true
    },
    updated_by_user_id: {
      type: DataTypes.CHAR(10),
      allowNull: true
    },
    idCMND: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    }
  }, {
    sequelize,
    tableName: 'CMND',
    schema: 'dbo',
    timestamps: false,
    indexes: [
      {
        name: "PK_CMND",
        unique: true,
        fields: [
          { name: "idHocSinh" },
          { name: "idCMND" },
        ]
      },
    ]
  });
};
