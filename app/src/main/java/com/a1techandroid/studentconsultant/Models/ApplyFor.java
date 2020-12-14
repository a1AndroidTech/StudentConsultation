package com.a1techandroid.studentconsultant.Models;

public class ApplyFor {

    String uniName;
    String schName;

    public ApplyFor(){

    }

    public ApplyFor(String uniName, String schName) {
        this.uniName = uniName;
        this.schName = schName;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }
}
