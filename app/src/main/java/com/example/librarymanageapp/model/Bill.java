package com.example.librarymanageapp.model;

import java.io.Serializable;

public class Bill implements Serializable {
    String id, msv, nameU, classU, sdate, returned, eDate,email;

    public Bill() {
    }



    public Bill(String id, String msv, String nameU, String classU, String sdate, String returned, String eDate, String email) {
        this.id = id;
        this.msv = msv;
        this.nameU = nameU;
        this.classU = classU;
        this.sdate = sdate;
        this.returned = returned;
        this.eDate = eDate;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }

    public String getNameU() {
        return nameU;
    }

    public void setNameU(String nameU) {
        this.nameU = nameU;
    }

    public String getClassU() {
        return classU;
    }

    public void setClassU(String classU) {
        this.classU = classU;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }
}
