package com.example.pc.ing1_;

import java.io.Serializable;

public class Review_Item implements Serializable {
    String content,img,nick;
    int point;
    boolean daum_user;

    public Review_Item(String content, String img, String nick, int point, boolean daum_user) {
        this.content = content;
        this.img = img;
        this.nick = nick;
        this.point = point;
        this.daum_user = daum_user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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
