package com.example.librarymanageapp.model;

import java.io.Serializable;

public class News implements Serializable {
    String id,  title, des;

    public News() {
    }

    public News(String id, String title, String des) {
        this.id = id;
        this.title = title;
        this.des = des;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
