package com.example.pc.ing1_.Menu.Friend.multi_chat;

public class Multi_Room_Item {


    boolean single;
    String room_no;
    String time;
    String message;
    String title;
    String images;

//    public Multi_Room_Item(boolean single, String room_no, String time, String message, String title) {
//        this.single = single;
//        this.room_no = room_no;
//        this.time = time;
//        this.message = message;
//        this.title = title;
//    }


    public Multi_Room_Item(boolean single, String room_no, String time, String message, String title, String images) {
        this.single = single;
        this.room_no = room_no;
        this.time = time;
        this.message = message;
        this.title = title;
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
