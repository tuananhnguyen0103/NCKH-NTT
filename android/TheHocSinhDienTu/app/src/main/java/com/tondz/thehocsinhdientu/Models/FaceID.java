package com.tondz.thehocsinhdientu.Models;

public class FaceID {
    private String idHocSinh, password, labels;
    private int status;

    public String getIdHocSinh() {
        return idHocSinh;
    }

    public void setIdHocSinh(String idHocSinh) {
        this.idHocSinh = idHocSinh;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public FaceID(String idHocSinh, String password, String labels, int status) {
        this.idHocSinh = idHocSinh;
        this.password = password;
        this.labels = labels;
        this.status = status;
    }

    @Override
    public String toString() {
        return "FaceID{" +
                "idHocSinh='" + idHocSinh + '\'' +
                ", password='" + password + '\'' +
                ", labels='" + labels + '\'' +
                ", status=" + status +
                '}';
    }
}
