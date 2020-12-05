package com.a1techandroid.studentconsultant.Models;

import java.io.Serializable;

public class Uni_Model implements Serializable {

    String uniName;
    String country;
    String date;

    public Uni_Model(){

    }

    public Uni_Model(String uniName, String country, String date) {
        this.uniName = uniName;
        this.country = country;
        this.date = date;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
