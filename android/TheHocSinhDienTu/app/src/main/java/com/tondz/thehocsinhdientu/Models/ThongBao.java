package com.tondz.thehocsinhdientu.Models;

public class ThongBao {
    private int idThongBao;
    private String noiDung;
    private String tieuDe;
    private String lienKet;
    private String hinhAnh;
    private String ngayThongBao;
    private int idHocSinh;
    private String SDTChaMe;
    private int created_by_user_id;
    private String created_date_time;
    private int active_flag;
    private String update_date_time;
    private int update_by_user_id;

    public int getIdThongBao() {
        return idThongBao;
    }

    public void setIdThongBao(int idThongBao) {
        this.idThongBao = idThongBao;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getLienKet() {
        return lienKet;
    }

    public void setLienKet(String lienKet) {
        this.lienKet = lienKet;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getNgayThongBao() {
        return ngayThongBao;
    }

    public void setNgayThongBao(String ngayThongBao) {
        this.ngayThongBao = ngayThongBao;
    }

    public int getIdHocSinh() {
        return idHocSinh;
    }

    public void setIdHocSinh(int idHocSinh) {
        this.idHocSinh = idHocSinh;
    }

    public String getSDTChaMe() {
        return SDTChaMe;
    }

    public void setSDTChaMe(String SDTChaMe) {
        this.SDTChaMe = SDTChaMe;
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

    public String getUpdate_date_time() {
        return update_date_time;
    }

    public void setUpdate_date_time(String update_date_time) {
        this.update_date_time = update_date_time;
    }

    public int getUpdate_by_user_id() {
        return update_by_user_id;
    }

    public void setUpdate_by_user_id(int update_by_user_id) {
        this.update_by_user_id = update_by_user_id;
    }

    public ThongBao(int idThongBao, String noiDung, String tieuDe, String lienKet, String hinhAnh, String ngayThongBao, int idHocSinh, String SDTChaMe, int created_by_user_id, String created_date_time, int active_flag, String update_date_time, int update_by_user_id) {
        this.idThongBao = idThongBao;
        this.noiDung = noiDung;
        this.tieuDe = tieuDe;
        this.lienKet = lienKet;
        this.hinhAnh = hinhAnh;
        this.ngayThongBao = ngayThongBao;
        this.idHocSinh = idHocSinh;
        this.SDTChaMe = SDTChaMe;
        this.created_by_user_id = created_by_user_id;
        this.created_date_time = created_date_time;
        this.active_flag = active_flag;
        this.update_date_time = update_date_time;
        this.update_by_user_id = update_by_user_id;
    }

    public ThongBao(String noiDung, String tieuDe, String hinhAnh, String ngayThongBao) {
        this.noiDung = noiDung;
        this.tieuDe = tieuDe;
        this.hinhAnh = hinhAnh;
        this.ngayThongBao = ngayThongBao;
    }
}
