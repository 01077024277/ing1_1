package com.example.pc.ing1_;

import java.io.Serializable;
import java.util.ArrayList;

public class Review_Item implements Serializable {
    String content, nick,profile;
    ArrayList<String> img;
    int point;
    boolean daum_user;

    public Review_Item(String content, String nick, String profile, ArrayList<String> img, int point, boolean daum_user) {
        this.content = content;
        this.nick = nick;
        this.profile = profile;
        this.img = img;
        this.point = point;
        this.daum_user = daum_user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean isDaum_user() {
        return daum_user;
    }

    public void setDaum_user(boolean daum_user) {
        this.daum_user = daum_user;
    }
}
