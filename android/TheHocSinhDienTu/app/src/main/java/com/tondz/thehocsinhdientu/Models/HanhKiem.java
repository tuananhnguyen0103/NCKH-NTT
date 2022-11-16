package com.tondz.thehocsinhdientu.Models;

public class HanhKiem {
    private int idHanhKiem;
    private int idHocSinh;
    private int diemHanhKiem;
    private String namHoc;
    private int created_by_user_id;
    private String created_date_time;
    private int active_flag;
    private String updated_date_time;
    private int updated_by_user_id;

    public int getIdHanhKiem() {
        return idHanhKiem;
    }

    public void setIdHanhKiem(int idHanhKiem) {
        this.idHanhKiem = idHanhKiem;
    }

    public int getIdHocSinh() {
        return idHocSinh;
    }

    public void setIdHocSinh(int idHocSinh) {
        this.idHocSinh = idHocSinh;
    }

    public int getDiemHanhKiem() {
        return diemHanhKiem;
    }

    public void setDiemHanhKiem(int diemHanhKiem) {
        this.diemHanhKiem = diemHanhKiem;
    }

    public String getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(String namHoc) {
        this.namHoc = namHoc;
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

    public HanhKiem(int idHanhKiem, int idHocSinh, int diemHanhKiem, String namHoc, int created_by_user_id, String created_date_time, int active_flag, String updated_date_time, int updated_by_user_id) {
        this.idHanhKiem = idHanhKiem;
        this.idHocSinh = idHocSinh;
        this.diemHanhKiem = diemHanhKiem;
        this.namHoc = namHoc;
        this.created_by_user_id = created_by_user_id;
        this.created_date_time = created_date_time;
        this.active_flag = active_flag;
        this.updated_date_time = updated_date_time;
        this.updated_by_user_id = updated_by_user_id;
    }
}
