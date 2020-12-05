package com.a1techandroid.studentconsultant.Models;

public class Scholorship_model {


    String schName;
    String endDate;
    String uniName;

    public Scholorship_model(){

    }

    public Scholorship_model(String schName, String endDate, String uniName) {
        this.schName = schName;
        this.endDate = endDate;
        this.uniName = uniName;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }
}
