package com.example.librarymanageapp.model;

import java.io.Serializable;

public class Category implements Serializable {
    private String id, img, name, des;

    public Category() {
    }

    public Category(String id, String img, String name, String des) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.des = des;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
