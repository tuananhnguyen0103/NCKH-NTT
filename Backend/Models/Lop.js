const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('Lop', {
    idLop: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    tenLop: {
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
    }
  }, {
    sequelize,
    tableName: 'Lop',
    schema: 'dbo',
    timestamps: false,
    indexes: [
      {
        name: "PK_Lop",
        unique: true,
        fields: [
          { name: "idLop" },
        ]
      },
    ]
  });
};
