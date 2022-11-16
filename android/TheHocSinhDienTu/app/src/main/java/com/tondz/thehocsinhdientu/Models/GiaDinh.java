package com.tondz.thehocsinhdientu.Models;

public class GiaDinh {
   private int idGiaDinh;
    private int  idHocSinh;
  private String  tenCha;
    private String   tenMe;
    private String  namSinhCha;
    private String namSinhMe;
    private String  namSinhNGH;
    private String   ngheNghiepCha;
    private String   ngheNghiepMe;
    private String  SDTCha;
    private String   SDTMe;
    private String  hoTenNguoiGiamHo;
    private String  ngheNghiepNGH;
    private String  tonGiaoCha;
    private String tonGiaoMe;
    private int  created_by_user_id;
    private String  created_date_time;
    private int  active_flag;
    private String  updated_date_time;
    private int  updated_by_user_id;

    public int getIdGiaDinh() {
        return idGiaDinh;
    }

    public void setIdGiaDinh(int idGiaDinh) {
        this.idGiaDinh = idGiaDinh;
    }

    public int getIdHocSinh() {
        return idHocSinh;
    }

    public void setIdHocSinh(int idHocSinh) {
        this.idHocSinh = idHocSinh;
    }

    public String getTenCha() {
        return tenCha;
    }

    public void setTenCha(String tenCha) {
        this.tenCha = tenCha;
    }

    public String getTenMe() {
        return tenMe;
    }

    public void setTenMe(String tenMe) {
        this.tenMe = tenMe;
    }

    public String getNamSinhCha() {
        return namSinhCha;
    }

    public void setNamSinhCha(String namSinhCha) {
        this.namSinhCha = namSinhCha;
    }

    public String getNamSinhMe() {
        return namSinhMe;
    }

    public void setNamSinhMe(String namSinhMe) {
        this.namSinhMe = namSinhMe;
    }

    public String getNamSinhNGH() {
        return namSinhNGH;
    }

    public void setNamSinhNGH(String namSinhNGH) {
        this.namSinhNGH = namSinhNGH;
    }

    public String getNgheNghiepCha() {
        return ngheNghiepCha;
    }

    public void setNgheNghiepCha(String ngheNghiepCha) {
        this.ngheNghiepCha = ngheNghiepCha;
    }

    public String getNgheNghiepMe() {
        return ngheNghiepMe;
    }

    public void setNgheNghiepMe(String ngheNghiepMe) {
        this.ngheNghiepMe = ngheNghiepMe;
    }

    public String getSDTCha() {
        return SDTCha;
    }

    public void setSDTCha(String SDTCha) {
        this.SDTCha = SDTCha;
    }

    public String getSDTMe() {
        return SDTMe;
    }

    public void setSDTMe(String SDTMe) {
        this.SDTMe = SDTMe;
    }

    public String getHoTenNguoiGiamHo() {
        return hoTenNguoiGiamHo;
    }

    public void setHoTenNguoiGiamHo(String hoTenNguoiGiamHo) {
        this.hoTenNguoiGiamHo = hoTenNguoiGiamHo;
    }

    public String getNgheNghiepNGH() {
        return ngheNghiepNGH;
    }

    public void setNgheNghiepNGH(String ngheNghiepNGH) {
        this.ngheNghiepNGH = ngheNghiepNGH;
    }

    public String getTonGiaoCha() {
        return tonGiaoCha;
    }

    public void setTonGiaoCha(String tonGiaoCha) {
        this.tonGiaoCha = tonGiaoCha;
    }

    public String getTonGiaoMe() {
        return tonGiaoMe;
    }

    public void setTonGiaoMe(String tonGiaoMe) {
        this.tonGiaoMe = tonGiaoMe;
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

    public GiaDinh(int idGiaDinh, int idHocSinh, String tenCha, String tenMe, String namSinhCha, String namSinhMe, String namSinhNGH, String ngheNghiepCha, String ngheNghiepMe, String SDTCha, String SDTMe, String hoTenNguoiGiamHo, String ngheNghiepNGH, String tonGiaoCha, String tonGiaoMe, int created_by_user_id, String created_date_time, int active_flag, String updated_date_time, int updated_by_user_id) {
        this.idGiaDinh = idGiaDinh;
        this.idHocSinh = idHocSinh;
        this.tenCha = tenCha;
        this.tenMe = tenMe;
        this.namSinhCha = namSinhCha;
        this.namSinhMe = namSinhMe;
        this.namSinhNGH = namSinhNGH;
        this.ngheNghiepCha = ngheNghiepCha;
        this.ngheNghiepMe = ngheNghiepMe;
        this.SDTCha = SDTCha;
        this.SDTMe = SDTMe;
        this.hoTenNguoiGiamHo = hoTenNguoiGiamHo;
        this.ngheNghiepNGH = ngheNghiepNGH;
        this.tonGiaoCha = tonGiaoCha;
        this.tonGiaoMe = tonGiaoMe;
        this.created_by_user_id = created_by_user_id;
        this.created_date_time = created_date_time;
        this.active_flag = active_flag;
        this.updated_date_time = updated_date_time;
        this.updated_by_user_id = updated_by_user_id;
    }
}
