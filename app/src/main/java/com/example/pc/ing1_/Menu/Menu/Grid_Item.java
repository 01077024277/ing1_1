package com.example.pc.ing1_.Menu.Menu;

public class Grid_Item {
    String day;
    //요일 나타냄
    int day_week;
    //년 월 일 yyyy-mm-dd
    String fomat;

    public Grid_Item(String day, int day_week, String fomat) {
        this.day = day;
        this.day_week = day_week;
        this.fomat = fomat;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDay_week() {
        return day_week;
    }

    public void setDay_week(int day_week) {
        this.day_week = day_week;
    }

    public String getFomat() {
        return fomat;
    }

    public void setFomat(String fomat) {
        this.fomat = fomat;
    }
}
