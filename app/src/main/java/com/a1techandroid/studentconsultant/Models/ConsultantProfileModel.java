package com.a1techandroid.studentconsultant.Models;

public class ConsultantProfileModel {

    String email;
    String name;
    String phone;
    String numberOfYear;
    String description;
    String markeeting;
    String orgSkill;
    String markeetSearch;
    String communSkill;
    String pManagment;
    String status;

    public ConsultantProfileModel(){

    }

    public ConsultantProfileModel(String email, String name, String phone, String numberOfYear, String description, String markeeting, String orgSkill, String markeetSearch, String communSkill, String pManagment, String status) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.numberOfYear = numberOfYear;
        this.description = description;
        this.markeeting = markeeting;
        this.orgSkill = orgSkill;
        this.markeetSearch = markeetSearch;
        this.communSkill = communSkill;
        this.pManagment = pManagment;
        this.status = status;
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

    public String getNumberOfYear() {
        return numberOfYear;
    }

    public void setNumberOfYear(String numberOfYear) {
        this.numberOfYear = numberOfYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMarkeeting() {
        return markeeting;
    }

    public void setMarkeeting(String markeeting) {
        this.markeeting = markeeting;
    }

    public String getOrgSkill() {
        return orgSkill;
    }

    public void setOrgSkill(String orgSkill) {
        this.orgSkill = orgSkill;
    }

    public String getMarkeetSearch() {
        return markeetSearch;
    }

    public void setMarkeetSearch(String markeetSearch) {
        this.markeetSearch = markeetSearch;
    }

    public String getCommunSkill() {
        return communSkill;
    }

    public void setCommunSkill(String communSkill) {
        this.communSkill = communSkill;
    }

    public String getpManagment() {
        return pManagment;
    }

    public void setpManagment(String pManagment) {
        this.pManagment = pManagment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
