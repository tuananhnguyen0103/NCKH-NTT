const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('ThongBao', {
    idThongBao: {
      autoIncrement: true,
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true
    },
    noiDung: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    tieuDe: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    lienKet: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    hinhAnh: {
      type: DataTypes.TEXT,
      allowNull: true
    },
    ngayThongBao: {
      type: DataTypes.DATEONLY,
      allowNull: true
    },
    idHocSinh: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    SDTChaMe: {
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
    update_date_time: {
      type: DataTypes.DATE,
      allowNull: true
    },
    update_by_user_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }, {
    sequelize,
    tableName: 'ThongBao',
    schema: 'dbo',
    timestamps: false,
    indexes: [
      {
        name: "PK_ThongBao",
        unique: true,
        fields: [
          { name: "idThongBao" },
        ]
      },
    ]
  });
};
