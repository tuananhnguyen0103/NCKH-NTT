package com.tondz.thehocsinhdientu.Models;

public class LoginModel {
    private String token;
    private HocSinh hocSinh;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HocSinh getHocSinh() {
        return hocSinh;
    }

    public void setHocSinh(HocSinh hocSinh) {
        this.hocSinh = hocSinh;
    }

    public LoginModel(String token, HocSinh hocSinh) {
        this.token = token;
        this.hocSinh = hocSinh;
    }
}
