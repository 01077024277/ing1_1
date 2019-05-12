package com.example.pc.ing1_;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.List;

public class Cluster_Item implements ClusterItem {

    Store store;

    public Cluster_Item(Store store) {
        this.store = store;
    }



    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public LatLng getPosition() {
            return new LatLng(Double.parseDouble(store.getLat()), Double.parseDouble(store.getLon()));

    }
}