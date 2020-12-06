package com.a1techandroid.studentconsultant.Models;

import java.util.ArrayList;

public class StudentProfileModel {

    String email;
    String name;
    String phone;
    String cgpa;
    String ieltsScore;
    String reading;
    String listneing;
    String speaking;
    String writing;


   public StudentProfileModel(){

    }
    public StudentProfileModel(String email, String name, String phone, String cgpa, String ieltsScore, String reading, String listneing, String speaking, String writing) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.cgpa = cgpa;
        this.ieltsScore = ieltsScore;
        this.reading = reading;
        this.listneing = listneing;
        this.speaking = speaking;
        this.writing = writing;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getIeltsScore() {
        return ieltsScore;
    }

    public void setIeltsScore(String ieltsScore) {
        this.ieltsScore = ieltsScore;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getListneing() {
        return listneing;
    }

    public void setListneing(String listneing) {
        this.listneing = listneing;
    }

    public String getSpeaking() {
        return speaking;
    }

    public void setSpeaking(String speaking) {
        this.speaking = speaking;
    }

    public String getWriting() {
        return writing;
    }

    public void setWriting(String writing) {
        this.writing = writing;
    }


}
