package com.example.pc.ing1_;

public class User{
    int no;
    String phone;
    String social;
    String uid;
    String id;
    String password;
    String name;
    String profile;
    String height;
    String weight;

    public User(int no, String phone, String social, String uid, String id, String password, String name, String profile, String height, String weight) {
        this.no = no;
        this.phone = phone;
        this.social = social;
        this.uid = uid;
        this.id = id;
        this.password = password;
        this.name = name;
        this.profile = profile;
        this.height = height;
        this.weight = weight;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
