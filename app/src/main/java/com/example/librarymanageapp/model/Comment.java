package com.example.librarymanageapp.model;

import java.io.Serializable;

public class Comment implements Serializable {
    String id, uid,bid, comment, img;

    public Comment() {
    }

    public Comment(String id, String uid, String bid, String comment, String img) {
        this.id = id;
        this.uid = uid;
        this.bid = bid;
        this.comment = comment;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
