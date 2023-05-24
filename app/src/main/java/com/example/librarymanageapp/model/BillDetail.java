package com.example.librarymanageapp.model;

import java.io.Serializable;

public class BillDetail implements Serializable {
    String id, book, billID, bookName;

    public BillDetail() {
    }

    public BillDetail(String id, String book, String billID, String bookName) {
        this.id = id;
        this.book = book;
        this.billID = billID;
        this.bookName = bookName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getBillID() {
        return billID;
    }

    public void setBillID(String billID) {
        this.billID = billID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
