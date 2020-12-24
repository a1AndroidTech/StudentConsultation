package com.a1techandroid.studentconsultant.Models;

public class ChatUSer {
    String userName;
    String userId;
    String type;

    public ChatUSer(){

    }
    public ChatUSer(String userName, String userId, String type) {
        this.userName = userName;
        this.userId = userId;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
