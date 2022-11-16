package com.tondz.thehocsinhdientu.Models;

public class ThoiKhoaBieu {

    private String duongDan;
    private String ngayBatDau;
    private String ngayKetThuc;

    public String getDuongDan() {
        return duongDan;
    }

    public void setDuongDan(String duongDan) {
        this.duongDan = duongDan;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public ThoiKhoaBieu(String duongDan, String ngayBatDau, String ngayKetThuc) {
        this.duongDan = duongDan;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }
}
