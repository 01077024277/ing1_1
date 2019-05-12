package com.example.pc.ing1_;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Store  implements Serializable {
    int no,review,point;
    String name,store_img,category1,category2,category3,category4,category5;
    String lat,lon,content,time,open_day,close_day;

    String old_address,new_address,tel;

    public Store(int no, int review, int point, String name, String store_img, String category1, String category2, String category3, String category4, String category5, String lat, String lon, String content, String time, String open_day, String close_day, String old_address, String new_address, String tel) {
        this.no = no;
        this.review = review;
        this.point = point;
        this.name = name;
        this.store_img = store_img;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.category4 = category4;
        this.category5 = category5;
        this.lat = lat;
        this.lon = lon;
        this.content = content;
        this.time = time;
        this.open_day = open_day;
        this.close_day = close_day;
        this.old_address = old_address;
        this.new_address = new_address;
        this.tel = tel;
    }
    public Store(int no,String lat,String lon){
        this.no = no;
        this.lat = lat;
        this.lon = lon;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore_img() {
        return store_img;
    }

    public void setStore_img(String store_img) {
        this.store_img = store_img;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getCategory3() {
        return category3;
    }

    public void setCategory3(String category3) {
        this.category3 = category3;
    }

    public String getCategory4() {
        return category4;
    }

    public void setCategory4(String category4) {
        this.category4 = category4;
    }

    public String getCategory5() {
        return category5;
    }

    public void setCategory5(String category5) {
        this.category5 = category5;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOpen_day() {
        return open_day;
    }

    public void setOpen_day(String open_day) {
        this.open_day = open_day;
    }

    public String getClose_day() {
        return close_day;
    }

    public void setClose_day(String close_day) {
        this.close_day = close_day;
    }

    public String getOld_address() {
        return old_address;
    }

    public void setOld_address(String old_address) {
        this.old_address = old_address;
    }

    public String getNew_address() {
        return new_address;
    }

    public void setNew_address(String new_address) {
        this.new_address = new_address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
