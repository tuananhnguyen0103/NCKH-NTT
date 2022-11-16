package com.tondz.thehocsinhdientu.Models;

public class Account {
    private String account;
    private int idHocSinh;
    private String idGiaoVien;
    private String password;
    private String history;
    private int created_by_user_id;
    private String created_date_time;
    private int active_flag;
    private String updated_date_time;
    private int updated_by_user_id;
    private int idAcount;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getIdHocSinh() {
        return idHocSinh;
    }

    public void setIdHocSinh(int idHocSinh) {
        this.idHocSinh = idHocSinh;
    }

    public String getIdGiaoVien() {
        return idGiaoVien;
    }

    public void setIdGiaoVien(String idGiaoVien) {
        this.idGiaoVien = idGiaoVien;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
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

    public int getIdAcount() {
        return idAcount;
    }

    public void setIdAcount(int idAcount) {
        this.idAcount = idAcount;
    }

    public Account(String account, int idHocSinh, String idGiaoVien, String password, String history, int created_by_user_id, String created_date_time, int active_flag, String updated_date_time, int updated_by_user_id, int idAcount) {
        this.account = account;
        this.idHocSinh = idHocSinh;
        this.idGiaoVien = idGiaoVien;
        this.password = password;
        this.history = history;
        this.created_by_user_id = created_by_user_id;
        this.created_date_time = created_date_time;
        this.active_flag = active_flag;
        this.updated_date_time = updated_date_time;
        this.updated_by_user_id = updated_by_user_id;
        this.idAcount = idAcount;
    }
}
