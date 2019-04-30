package com.example.pc.ing1_;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Store  implements Serializable {
    String name,store_img;
    String lat,lon;
    List<Review_Item> review_item;
    String old_address,new_address,tel;

    public Store(String name, String store_img, String lat, String lon, List<Review_Item> review_item, String old_address, String new_address, String tel) {
        this.name = name;
        this.store_img = store_img;
        this.lat = lat;
        this.lon = lon;
        this.review_item = review_item;
        this.old_address = old_address;
        this.new_address = new_address;
        this.tel = tel;
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

    public List<Review_Item> getReview_item() {
        return review_item;
    }

    public void setReview_item(List<Review_Item> review_item) {
        this.review_item = review_item;
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
