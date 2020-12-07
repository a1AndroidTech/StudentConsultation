package com.a1techandroid.studentconsultant.Models;

public class AttachmentModel {

    String passImage, idImage, sscImage, hsscImage, baImage, maImage;

    public AttachmentModel(String passImage, String idImage, String sscImage, String hsscImage, String baImage, String maImage) {
        this.passImage = passImage;
        this.idImage = idImage;
        this.sscImage = sscImage;
        this.hsscImage = hsscImage;
        this.baImage = baImage;
        this.maImage = maImage;
    }

    public String getPassImage() {
        return passImage;
    }

    public void setPassImage(String passImage) {
        this.passImage = passImage;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getSscImage() {
        return sscImage;
    }

    public void setSscImage(String sscImage) {
        this.sscImage = sscImage;
    }

    public String getHsscImage() {
        return hsscImage;
    }

    public void setHsscImage(String hsscImage) {
        this.hsscImage = hsscImage;
    }

    public String getBaImage() {
        return baImage;
    }

    public void setBaImage(String baImage) {
        this.baImage = baImage;
    }

    public String getMaImage() {
        return maImage;
    }

    public void setMaImage(String maImage) {
        this.maImage = maImage;
    }
}
