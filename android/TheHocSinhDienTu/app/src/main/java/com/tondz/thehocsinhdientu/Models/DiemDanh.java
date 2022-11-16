package com.tondz.thehocsinhdientu.Models;

public class DiemDanh {
   private int idDiemDanh; 
    private int  idHocSinh; 
  private String  ngayDiemDanh; 
    private int  trangThai; 
    private int  created_by_user_id; 
    private String created_date_time; 
    private int  active_flag; 
    private String updated_date_time; 
    private int  updated_by_user_id;

    public int getIdDiemDanh() {
        return idDiemDanh;
    }

    public void setIdDiemDanh(int idDiemDanh) {
        this.idDiemDanh = idDiemDanh;
    }

    public int getIdHocSinh() {
        return idHocSinh;
    }

    public void setIdHocSinh(int idHocSinh) {
        this.idHocSinh = idHocSinh;
    }

    public String getNgayDiemDanh() {
        return ngayDiemDanh;
    }

    public void setNgayDiemDanh(String ngayDiemDanh) {
        this.ngayDiemDanh = ngayDiemDanh;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getCreated_by_user_id() {
        return created_by_user_id;
    }

    public void setCreated_by_user_id(int created_by_user_id) {
        this.created_by_user_id = created_by_user_id;
    }

    public String getCreated_date_time() {
        return created_date_time;
    }

    public void setCreated_date_time(String created_date_time) {
        this.created_date_time = created_date_time;
    }

    public int getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(int active_flag) {
        this.active_flag = active_flag;
    }

    public String getUpdated_date_time() {
        return updated_date_time;
    }

    public void setUpdated_date_time(String updated_date_time) {
        this.updated_date_time = updated_date_time;
    }

    public int getUpdated_by_user_id() {
        return updated_by_user_id;
    }

    public void setUpdated_by_user_id(int updated_by_user_id) {
        this.updated_by_user_id = updated_by_user_id;
    }

    public DiemDanh(int idDiemDanh, int idHocSinh, String ngayDiemDanh, int trangThai, int created_by_user_id, String created_date_time, int active_flag, String updated_date_time, int updated_by_user_id) {
        this.idDiemDanh = idDiemDanh;
        this.idHocSinh = idHocSinh;
        this.ngayDiemDanh = ngayDiemDanh;
        this.trangThai = trangThai;
        this.created_by_user_id = created_by_user_id;
        this.created_date_time = created_date_time;
        this.active_flag = active_flag;
        this.updated_date_time = updated_date_time;
        this.updated_by_user_id = updated_by_user_id;
    }
}
