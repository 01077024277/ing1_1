package com.example.pc.ing1_;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.Collection;
import java.util.Set;

public class MakerRander extends DefaultClusterRenderer<Cluster_Item> {
    GoogleMap googleMap;
    Context context;

    public MakerRander(Context context, GoogleMap map, ClusterManager<Cluster_Item> clusterManager) {
        super(context, map, clusterManager);
    googleMap=map;
    this.context=context;
    }



    @Override
    protected void onBeforeClusterItemRendered(Cluster_Item item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);

//        markerOptions.title(item.getName() + " "+item.getReview_item().size());

    }

    @Override
    public Marker getMarker(Cluster<Cluster_Item> cluster) {
        return super.getMarker(cluster);
    }

    @Override
    public Marker getMarker(Cluster_Item clusterItem) {
        return super.getMarker(clusterItem);
    }



    @Override
    protected void onClusterItemRendered(Cluster_Item clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
    }
}
