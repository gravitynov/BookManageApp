package com.example.librarymanageapp.model;

import java.io.Serializable;

public class Book implements Serializable {
    String id, name, author, des, price, nxb, img, categoryID, cart;

    public Book() {
    }

    public Book(String id, String name, String author, String des, String price, String nxb, String img, String categoryID, String cart) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.des = des;
        this.price = price;
        this.nxb = nxb;
        this.img = img;
        this.categoryID = categoryID;
        this.cart = cart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNxb() {
        return nxb;
    }

    public void setNxb(String nxb) {
        this.nxb = nxb;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }
}
