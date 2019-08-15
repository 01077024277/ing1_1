package com.example.pc.ing1_.Menu.Friend;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;

public class Room_Item {
    boolean single;
    int room_no;
    int from_user;
    String message,user_image,user_name,time,user_no;

    public Room_Item(boolean single, int room_no, int from_user, String message, String user_image, String user_name, String time, String user_no) {
        this.single = single;
        this.room_no = room_no;
        this.from_user = from_user;
        this.message = message;
        this.user_image = user_image;
        this.user_name = user_name;
        this.time = time;
        this.user_no = user_no;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public int getRoom_no() {
        return room_no;
    }

    public void setRoom_no(int room_no) {
        this.room_no = room_no;
    }

    public int getFrom_user() {
        return from_user;
    }

    public void setFrom_user(int from_user) {
        this.from_user = from_user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
