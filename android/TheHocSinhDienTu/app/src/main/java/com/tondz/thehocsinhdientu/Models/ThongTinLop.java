package com.tondz.thehocsinhdientu.Models;

public class ThongTinLop {
    private int idThongTinLop;
    private int idLop;
    private String khoi;
    private String namhoc;
    private String idGiaoVien;
    private int created_by_user_id;
    private String created_date_time;
    private int active_flag;
    private String updated_date_time;
    private int updated_by_user_id;

    public int getIdThongTinLop() {
        return idThongTinLop;
    }

    public void setIdThongTinLop(int idThongTinLop) {
        this.idThongTinLop = idThongTinLop;
    }

    public int getIdLop() {
        return idLop;
    }

    public void setIdLop(int idLop) {
        this.idLop = idLop;
    }

    public String getKhoi() {
        return khoi;
    }

    public void setKhoi(String khoi) {
        this.khoi = khoi;
    }

    public String getNamhoc() {
        return namhoc;
    }

    public void setNamhoc(String namhoc) {
        this.namhoc = namhoc;
    }

    public String getIdGiaoVien() {
        return idGiaoVien;
    }

    public void setIdGiaoVien(String idGiaoVien) {
        this.idGiaoVien = idGiaoVien;
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

    public ThongTinLop(int idThongTinLop, int idLop, String khoi, String namhoc, String idGiaoVien, int created_by_user_id, String created_date_time, int active_flag, String updated_date_time, int updated_by_user_id) {
        this.idThongTinLop = idThongTinLop;
        this.idLop = idLop;
        this.khoi = khoi;
        this.namhoc = namhoc;
        this.idGiaoVien = idGiaoVien;
        this.created_by_user_id = created_by_user_id;
        this.created_date_time = created_date_time;
        this.active_flag = active_flag;
        this.updated_date_time = updated_date_time;
        this.updated_by_user_id = updated_by_user_id;
    }
}
