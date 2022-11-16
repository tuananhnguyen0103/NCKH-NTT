package com.tondz.thehocsinhdientu.Models;

public class HocSinh {

    private int idHocSinh;
    private int idLop;
    private int idLopCu;
    private String ngaySinh;
    private String SDT;
    private String queQuanTinh;
    private String queQuanHuyen;
    private String queQuanXa;
    private String queQuanThonXom;
    private String noiSinh;
    private String noiThuongTru;
    private String gioiTinh;
    private String hoTen;
    private String namNhapHoc;
    private String namRaTruong;
    private String khoaAnh;
    private String anhThe;
    private String anh1;
    private String anh2;
    private String anh3;
    private String anh4;
    private String anh5,anh6,anh7,anh8,anh9,anh10;
    private int created_by_user_id;
    private String created_date_time;
    private int active_flag;
    private String updated_date_time;
    private int updated_by_user_id;

    public HocSinh(int idHocSinh, int idLop, int idLopCu, String ngaySinh, String SDT, String queQuanTinh, String queQuanHuyen, String queQuanXa, String queQuanThonXom, String noiSinh, String noiThuongTru, String gioiTinh, String hoTen, String namNhapHoc, String namRaTruong, String khoaAnh, String anhThe, String anh1, String anh2, String anh3, String anh4, String anh5, String anh6, String anh7, String anh8, String anh9, String anh10, int created_by_user_id, String created_date_time, int active_flag, String updated_date_time, int updated_by_user_id) {
        this.idHocSinh = idHocSinh;
        this.idLop = idLop;
        this.idLopCu = idLopCu;
        this.ngaySinh = ngaySinh;
        this.SDT = SDT;
        this.queQuanTinh = queQuanTinh;
        this.queQuanHuyen = queQuanHuyen;
        this.queQuanXa = queQuanXa;
        this.queQuanThonXom = queQuanThonXom;
        this.noiSinh = noiSinh;
        this.noiThuongTru = noiThuongTru;
        this.gioiTinh = gioiTinh;
        this.hoTen = hoTen;
        this.namNhapHoc = namNhapHoc;
        this.namRaTruong = namRaTruong;
        this.khoaAnh = khoaAnh;
        this.anhThe = anhThe;
        this.anh1 = anh1;
        this.anh2 = anh2;
        this.anh3 = anh3;
        this.anh4 = anh4;
        this.anh5 = anh5;
        this.anh6 = anh6;
        this.anh7 = anh7;
        this.anh8 = anh8;
        this.anh9 = anh9;
        this.anh10 = anh10;
        this.created_by_user_id = created_by_user_id;
        this.created_date_time = created_date_time;
        this.active_flag = active_flag;
        this.updated_date_time = updated_date_time;
        this.updated_by_user_id = updated_by_user_id;
    }

    public String getAnh6() {
        return anh6;
    }

    public void setAnh6(String anh6) {
        this.anh6 = anh6;
    }

    public String getAnh7() {
        return anh7;
    }

    public void setAnh7(String anh7) {
        this.anh7 = anh7;
    }

    public String getAnh8() {
        return anh8;
    }

    public void setAnh8(String anh8) {
        this.anh8 = anh8;
    }

    public String getAnh9() {
        return anh9;
    }

    public void setAnh9(String anh9) {
        this.anh9 = anh9;
    }

    public String getAnh10() {
        return anh10;
    }

    public void setAnh10(String anh10) {
        this.anh10 = anh10;
    }

    public int getIdHocSinh() {
        return idHocSinh;
    }

    public void setIdHocSinh(int idHocSinh) {
        this.idHocSinh = idHocSinh;
    }

    public int getIdLop() {
        return idLop;
    }

    public void setIdLop(int idLop) {
        this.idLop = idLop;
    }

    public int getIdLopCu() {
        return idLopCu;
    }

    public void setIdLopCu(int idLopCu) {
        this.idLopCu = idLopCu;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getQueQuanTinh() {
        return queQuanTinh;
    }

    public void setQueQuanTinh(String queQuanTinh) {
        this.queQuanTinh = queQuanTinh;
    }

    public String getQueQuanHuyen() {
        return queQuanHuyen;
    }

    public void setQueQuanHuyen(String queQuanHuyen) {
        this.queQuanHuyen = queQuanHuyen;
    }

    public String getQueQuanXa() {
        return queQuanXa;
    }

    public void setQueQuanXa(String queQuanXa) {
        this.queQuanXa = queQuanXa;
    }

    public String getQueQuanThonXom() {
        return queQuanThonXom;
    }

    public void setQueQuanThonXom(String queQuanThonXom) {
        this.queQuanThonXom = queQuanThonXom;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getNoiThuongTru() {
        return noiThuongTru;
    }

    public void setNoiThuongTru(String noiThuongTru) {
        this.noiThuongTru = noiThuongTru;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamNhapHoc() {
        return namNhapHoc;
    }

    public void setNamNhapHoc(String namNhapHoc) {
        this.namNhapHoc = namNhapHoc;
    }

    public String getNamRaTruong() {
        return namRaTruong;
    }

    public void setNamRaTruong(String namRaTruong) {
        this.namRaTruong = namRaTruong;
    }

    public String getKhoaAnh() {
        return khoaAnh;
    }

    public void setKhoaAnh(String khoaAnh) {
        this.khoaAnh = khoaAnh;
    }

    public String getAnhThe() {
        return anhThe;
    }

    public void setAnhThe(String anhThe) {
        this.anhThe = anhThe;
    }

    public String getAnh1() {
        return anh1;
    }

    public void setAnh1(String anh1) {
        this.anh1 = anh1;
    }

    public String getAnh2() {
        return anh2;
    }

    public void setAnh2(String anh2) {
        this.anh2 = anh2;
    }

    public String getAnh3() {
        return anh3;
    }

    public void setAnh3(String anh3) {
        this.anh3 = anh3;
    }

    public String getAnh4() {
        return anh4;
    }

    public void setAnh4(String anh4) {
        this.anh4 = anh4;
    }

    public String getAnh5() {
        return anh5;
    }

    public void setAnh5(String anh5) {
        this.anh5 = anh5;
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
}
