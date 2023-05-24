package com.example.librarymanageapp.model;

import java.io.Serializable;

public class cart implements Serializable {
    String id, bookid;

    public cart() {
    }

    public cart(String id, String bookid) {
        this.id = id;
        this.bookid = bookid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }
}
