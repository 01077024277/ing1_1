package com.example.pc.ing1_.Menu.Friend;

import android.graphics.Bitmap;

public class Chat_user {
    String no;
    String nick;
    Bitmap bitmap;

    public Chat_user(String no, String nick, Bitmap bitmap) {
        this.no = no;
        this.nick = nick;
        this.bitmap = bitmap;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
