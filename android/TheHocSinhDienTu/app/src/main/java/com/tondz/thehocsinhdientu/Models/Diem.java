package com.tondz.thehocsinhdientu.Models;

import com.google.gson.annotations.SerializedName;

public class Diem {
    @SerializedName("hoTen")
    private String hoten;
    @SerializedName("namHoc")
    private int namhoc;
    @SerializedName("kiHoc")
    private int kihoc;
    @SerializedName("Thể dục")
    private int theduc;
    @SerializedName("Toán")
    private double toan;
    @SerializedName("Vật lí")
    private double vatli;
    @SerializedName("Hóa học")
    private double hoahoc;
    @SerializedName("Sinh học")
    private double sinhhoc;
    @SerializedName("Tin học")
    private double tinhoc;
    @SerializedName("Ngữ văn")
    private double nguvan;
    @SerializedName("Lịch sử")
    private double lichsu;
    @SerializedName("Địa lý")
    private double diali;
    @SerializedName("Ngoại ngữ 1")
    private double ngoaingu1;
    @SerializedName("Công nghệ")
    private double congnghe;
    @SerializedName("GDGQ - AN")
    private double ghqp;
    @SerializedName("GDCD")
    private double gdcd;
    @SerializedName("TBM")
    private double tbm;

    public Diem(String hoten, int namhoc, int kihoc, int theduc, double toan, double vatli, double hoahoc, double sinhhoc, double tinhoc, double nguvan, double lichsu, double diali, double ngoaingu1, double congnghe, double ghqp, double gdcd, double tbm) {
        this.hoten = hoten;
        this.namhoc = namhoc;
        this.kihoc = kihoc;
        this.theduc = theduc;
        this.toan = toan;
        this.vatli = vatli;
        this.hoahoc = hoahoc;
        this.sinhhoc = sinhhoc;
        this.tinhoc = tinhoc;
        this.nguvan = nguvan;
        this.lichsu = lichsu;
        this.diali = diali;
        this.ngoaingu1 = ngoaingu1;
        this.congnghe = congnghe;
        this.ghqp = ghqp;
        this.gdcd = gdcd;
        this.tbm = tbm;
    }

    public int getTheduc() {
        return theduc;
    }

    public double getLichsu() {
        return lichsu;
    }

    public void setLichsu(double lichsu) {
        this.lichsu = lichsu;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public int getNamhoc() {
        return namhoc;
    }

    public void setNamhoc(int namhoc) {
        this.namhoc = namhoc;
    }

    public int getKihoc() {
        return kihoc;
    }

    public void setKihoc(int kihoc) {
        this.kihoc = kihoc;
    }

    public int isTheduc() {
        return theduc;
    }

    public void setTheduc(int theduc) {
        this.theduc = theduc;
    }

    public double getToan() {
        return toan;
    }

    public void setToan(double toan) {
        this.toan = toan;
    }

    public double getVatli() {
        return vatli;
    }

    public void setVatli(double vatli) {
        this.vatli = vatli;
    }

    public double getHoahoc() {
        return hoahoc;
    }

    public void setHoahoc(double hoahoc) {
        this.hoahoc = hoahoc;
    }

    public double getSinhhoc() {
        return sinhhoc;
    }

    public void setSinhhoc(double sinhhoc) {
        this.sinhhoc = sinhhoc;
    }

    public double getTinhoc() {
        return tinhoc;
    }

    public void setTinhoc(double tinhoc) {
        this.tinhoc = tinhoc;
    }

    public double getNguvan() {
        return nguvan;
    }

    public void setNguvan(double nguvan) {
        this.nguvan = nguvan;
    }

    public double getDiali() {
        return diali;
    }

    public void setDiali(double diali) {
        this.diali = diali;
    }

    public double getNgoaingu1() {
        return ngoaingu1;
    }

    public void setNgoaingu1(double ngoaingu1) {
        this.ngoaingu1 = ngoaingu1;
    }

    public double getCongnghe() {
        return congnghe;
    }

    public void setCongnghe(double congnghe) {
        this.congnghe = congnghe;
    }

    public double getGhqp() {
        return ghqp;
    }

    public void setGhqp(double ghqp) {
        this.ghqp = ghqp;
    }

    public double getGdcd() {
        return gdcd;
    }

    public void setGdcd(double gdcd) {
        this.gdcd = gdcd;
    }

    public double getTbm() {
        return tbm;
    }

    public void setTbm(double tbm) {
        this.tbm = tbm;
    }
}
