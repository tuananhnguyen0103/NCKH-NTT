package com.tondz.thehocsinhdientu.Models;

public class News {
    private String id;
    private String titile;
    private String content;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public News(String id, String titile, String content, String image) {
        this.id = id;
        this.titile = titile;
        this.content = content;
        this.image = image;
    }
}
