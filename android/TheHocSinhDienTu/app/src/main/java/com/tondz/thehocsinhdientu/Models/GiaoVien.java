package com.tondz.thehocsinhdientu.Models;

public class GiaoVien {
   private String hoTen; 
   private String anh; 
  private int  chuNhiemLop; 
   private int idGiaoVien;

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getChuNhiemLop() {
        return chuNhiemLop;
    }

    public void setChuNhiemLop(int chuNhiemLop) {
        this.chuNhiemLop = chuNhiemLop;
    }

    public int getIdGiaoVien() {
        return idGiaoVien;
    }

    public void setIdGiaoVien(int idGiaoVien) {
        this.idGiaoVien = idGiaoVien;
    }

    public GiaoVien(String hoTen, String anh, int chuNhiemLop, int idGiaoVien) {
        this.hoTen = hoTen;
        this.anh = anh;
        this.chuNhiemLop = chuNhiemLop;
        this.idGiaoVien = idGiaoVien;
    }
}
