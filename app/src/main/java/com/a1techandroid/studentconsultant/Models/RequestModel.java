package com.a1techandroid.studentconsultant.Models;

import java.io.Serializable;

public class RequestModel implements Serializable {
    String uniName;
    String scName;
    String Stype;
    String SDate;
    String passport;
    String id;
    String ssc;
    String hssc;
    String ba;
    String ma;
    String status;
    String userID;
    String userName;

    public RequestModel(){

    }


    public RequestModel(String uniName, String scName, String stype, String SDate, String passport, String id, String ssc, String hssc, String ba, String ma, String status, String userID, String userName) {
        this.uniName = uniName;
        this.scName = scName;
        Stype = stype;
        this.SDate = SDate;
        this.passport = passport;
        this.id = id;
        this.ssc = ssc;
        this.hssc = hssc;
        this.ba = ba;
        this.ma = ma;
        this.status = status;
        this.userID = userID;
        this.userName = userName;
    }


    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public String getStype() {
        return Stype;
    }

    public void setStype(String stype) {
        Stype = stype;
    }

    public String getSDate() {
        return SDate;
    }

    public void setSDate(String SDate) {
        this.SDate = SDate;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSsc() {
        return ssc;
    }

    public void setSsc(String ssc) {
        this.ssc = ssc;
    }

    public String getHssc() {
        return hssc;
    }

    public void setHssc(String hssc) {
        this.hssc = hssc;
    }

    public String getBa() {
        return ba;
    }

    public void setBa(String ba) {
        this.ba = ba;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
