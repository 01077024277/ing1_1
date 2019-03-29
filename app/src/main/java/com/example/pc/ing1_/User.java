package com.example.pc.ing1_;

public class User{
    int no;
    String phone;
    String social;
    String uid;
    String email;
    String password;
    String name;

    public User(int no, String phone, String social, String uid, String email, String password, String name) {
        this.no = no;
        this.phone = phone;
        this.social = social;
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
    }


    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
