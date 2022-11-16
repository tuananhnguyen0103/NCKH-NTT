package com.tondz.thehocsinhdientu.Models;

public class CMND {
    private int idHocSinh;
    private String soThe;
    private String ngayCap;
    private String noiCap;
    private int created_by_user_id;
    private String created_date_time;
    private String active_flag;
    private String updated_date_time;
    private String updated_by_user_id;
    private int idCMND;

    public int getIdHocSinh() {
        return idHocSinh;
    }

    public void setIdHocSinh(int idHocSinh) {
        this.idHocSinh = idHocSinh;
    }

    public String getSoThe() {
        return soThe;
    }

    public void setSoThe(String soThe) {
        this.soThe = soThe;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
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

    public String getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(String active_flag) {
        this.active_flag = active_flag;
    }

    public String getUpdated_date_time() {
        return updated_date_time;
    }

    public void setUpdated_date_time(String updated_date_time) {
        this.updated_date_time = updated_date_time;
    }

    public String getUpdated_by_user_id() {
        return updated_by_user_id;
    }

    public void setUpdated_by_user_id(String updated_by_user_id) {
        this.updated_by_user_id = updated_by_user_id;
    }

    public int getIdCMND() {
        return idCMND;
    }

    public void setIdCMND(int idCMND) {
        this.idCMND = idCMND;
    }

    public CMND(int idHocSinh, String soThe, String ngayCap, String noiCap, int created_by_user_id, String created_date_time, String active_flag, String updated_date_time, String updated_by_user_id, int idCMND) {
        this.idHocSinh = idHocSinh;
        this.soThe = soThe;
        this.ngayCap = ngayCap;
        this.noiCap = noiCap;
        this.created_by_user_id = created_by_user_id;
        this.created_date_time = created_date_time;
        this.active_flag = active_flag;
        this.updated_date_time = updated_date_time;
        this.updated_by_user_id = updated_by_user_id;
        this.idCMND = idCMND;
    }
}
