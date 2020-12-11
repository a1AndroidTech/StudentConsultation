package com.a1techandroid.studentconsultant.Models;

public class UserModel {

        private String name;
        private String phone;
        private String email;
        private String password;
        private String user_id;
        private int user_type;
        private String profileStatus;


        public UserModel(String name, String phone, String email, String password, int user_type,String profileStatus) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.password = password;
            this.user_type = user_type;
            this.profileStatus = profileStatus;
        }

        public UserModel(){

        }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }


    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }
}
