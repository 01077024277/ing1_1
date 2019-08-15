package com.example.pc.ing1_.Menu.Friend;

public class Message_model {
    int no;
    String message;
    String image;
    String time;
    String sys_message;
    int nno;

    public Message_model(int no, String message, String image, String time, String sys_message, int nno) {
        this.no = no;
        this.message = message;
        this.image = image;
        this.time = time;
        this.sys_message = sys_message;
        this.nno = nno;
    }

    public String getSys_message() {

        return sys_message;
    }

    public void setSys_message(String sys_message) {
        this.sys_message = sys_message;
    }

    public int getNno() {
        return nno;
    }

    public void setNno(int nno) {
        this.nno = nno;
    }

    public Message_model(int no, String message, String image, String time) {
        this.no = no;
        this.message = message;
        this.image = image;
        this.time = time;
    }

    public Message_model(int no, String message, String image, String time, String sys_message) {
        this.no = no;
        this.message = message;
        this.image = image;
        this.time = time;
        this.sys_message = sys_message;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
