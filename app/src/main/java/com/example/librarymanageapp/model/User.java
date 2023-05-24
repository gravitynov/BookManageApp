package com.example.librarymanageapp.model;

import java.io.Serializable;

public class User implements Serializable {
    private String uid,name, email, address, phone, image;

    public User() {
    }

    public User(String uid, String name, String email, String address, String phone, String image) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
