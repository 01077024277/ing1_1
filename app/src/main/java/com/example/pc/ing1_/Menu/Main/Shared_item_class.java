package com.example.pc.ing1_.Menu.Main;

public class Shared_item_class {
    boolean single;
    String title;
    String message;
    String room_no;
    String time;
    String image;
    String names;

    public Shared_item_class(boolean single, String title, String message, String room_no, String time, String image, String names) {
        this.single = single;
        this.title = title;
        this.message = message;
        this.room_no = room_no;
        this.time = time;
        this.image = image;
        this.names = names;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Shared_item_class(boolean single, String title, String message, String room_no, String time, String image) {
        this.single = single;
        this.title = title;
        this.message = message;
        this.room_no = room_no;
        this.time = time;
        this.image = image;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
