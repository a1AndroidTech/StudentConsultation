package com.a1techandroid.studentconsultant.Models;

public class Student_Model {

    String name;
    String applyFor;
    String uniName;
    String ieltsScore;

    public Student_Model(){

    }

    public Student_Model(String name, String applyFor, String uniName, String ieltsScore) {
        this.name = name;
        this.applyFor = applyFor;
        this.uniName = uniName;
        this.ieltsScore = ieltsScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplyFor() {
        return applyFor;
    }

    public void setApplyFor(String applyFor) {
        this.applyFor = applyFor;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getIeltsScore() {
        return ieltsScore;
    }

    public void setIeltsScore(String ieltsScore) {
        this.ieltsScore = ieltsScore;
    }
}
