package com.example.pc.ing1_.Menu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.pc.ing1_.Cluster_Item;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.Review_Item;
import com.example.pc.ing1_.Store;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

/**
 * A simple {@link Fragment} subclass.
 */

public class Main_frag extends Fragment implements OnMapReadyCallback {
    boolean one;
    EditText editText;
    Button button;
    View view;
    MapView google_map = null;
    private static final String TAG = "Main_frag";
    List<Store> store;
    GoogleMap googleMap;
    long start, end;
    RetrofitExService http;
    double user_lon, user_lat;
    private String htmlPageUrl = "https://Store.naver.com/restaurants/list?entry=pll&filterId=r09590&page=";
    Location location;
    Cluster_Item view_item;
    Cluster<Cluster_Item>view_cluster;
    ClusterManager<Cluster_Item> clusterManager;
    ImageView imageView;
    HashMap<String,Bitmap> bitmapList;

    RecyclerView recyclerView;
    Store_recycle_Adapter recycle_adapter;
    public Main_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_frag, container, false);
        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.button);
        recyclerView=view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        google_map = (MapView) view.findViewById(R.id.google_map);
        google_map.onCreate(savedInstanceState);
        google_map.getMapAsync(this);



        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("search", editText.getText().toString());
                hashMap.put("km", "1");
                hashMap.put("lat", String.valueOf(user_lat));
                hashMap.put("lon", String.valueOf(user_lon));
                Log.d("현재위치", user_lat + "/" + user_lon);
                http.search_store(hashMap).enqueue(new Callback<List<Store>>() {
                    @Override
                    public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
//                        Log.d("음식점", response.body().toString());
                         store = response.body();
//                            Log.d("음식점",store.getName());

                        clusterManager.clearItems();
                        bitmapList=new HashMap<>();

//                        clusterManager.setOnClusterItemInfoWindowClickListener((ClusterManager.OnClusterItemInfoWindowClickListener<Cluster_Item>) getContext());
                        for (int i = 0; i < store.size(); i++) {
                            Log.d("음식점 이름", i + "        " + store.get(i).getName() + "     " + store.get(i).getStore_img() + "   " + store.get(i).getLat() + "   " + store.get(i).getLon());
                            List<Review_Item> review_item = store.get(i).getReview_item();
                            for (int j = 0; j < review_item.size(); j++) {
                                Log.d("음식점 리뷰", review_item.get(j).getNick() + "  " + review_item.get(j).getContent());
                            }

                            final int finalI = i;
                            Glide.with(getContext()).asBitmap().load(store.get(i).getStore_img()).override(150,150).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    bitmapList.put(store.get(finalI).getName(),resource);
                                    Toast.makeText(getContext(),bitmapList.size()+"",Toast.LENGTH_SHORT).show();
                                }
                            });

                            clusterManager.addItem(new Cluster_Item(store.get(i)));



                        }
                        clusterManager.cluster();
                        recycle_adapter=new Store_recycle_Adapter(store,getActivity());
                        recyclerView.setAdapter(recycle_adapter);




//                        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(new CustomInfoWindowGoogleMap(getContext()));

                    }

                    @Override
                    public void onFailure(Call<List<Store>> call, Throwable t) {
                        Log.d("음식점", String.valueOf(t));

                    }
                });

//


//
            }

        });

        Log.d("선언", "creview");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        one = true;
        Log.d("선언", "cre");


        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

    }


    @Override
    public void onResume() {
        google_map.onResume();
        super.onResume();
        Log.d("선언", "re");


    }

    @Override
    public void onDestroy() {
        google_map.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        google_map.onLowMemory();
        super.onLowMemory();
        Log.d("선언", "low");
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d("선언", "on");
        MapsInitializer.initialize(this.getActivity());

        this.googleMap = googleMap;
        clusterManager = new ClusterManager<>(getActivity(), this.googleMap);
// Updates the location and zoom of the MapView


        user_lat = location.getLatitude();
        user_lon = location.getLongitude();

        this.googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(37.4834775,126.9748299))
                .position(new LatLng(user_lat, user_lon))
                .title("현재위치")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        CircleOptions circle500m = new CircleOptions().center(new LatLng(user_lat, user_lon))
                .radius(500).strokeWidth(0f).fillColor(Color.parseColor("#500000ff"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(user_lat, user_lon), 16);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(37.4834775,126.9748299), 15);
        this.googleMap.addCircle(circle500m);
        this.googleMap.animateCamera(cameraUpdate);
        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(user_lat, user_lon, 1);
            Toast.makeText(getContext(), "" + addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        clusterManager.setRenderer(new MakerRander(getContext(), googleMap, clusterManager));
//        googleMap.setOnInfoWindowClickListener(clusterManager);
//        clusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(new CustomInfoWindowGoogleMap(getContext()));
        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(new CustomInfoWindowGoogleMap(getContext()));
        googleMap.setOnMarkerClickListener(clusterManager);
        googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager());
        googleMap.setOnCameraChangeListener(clusterManager);


        //        clusterManager.setOnClusterItemInfoWindowClickListener((ClusterManager.OnClusterItemInfoWindowClickListener<Cluster_Item>) this);
        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Cluster_Item>() {
            @Override
            public boolean onClusterItemClick(Cluster_Item cluster_item) {
                Log.d("로그","매니저 클릭 리스너");
                view_item = cluster_item;
                return false;
            }
        });
       clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Cluster_Item>() {
           @Override
           public boolean onClusterClick(Cluster<Cluster_Item> cluster) {
               Intent intent = new Intent(getActivity(),Cluster_Activity.class);
              ArrayList<Store> stores= new ArrayList<>();
               for(Cluster_Item item : cluster.getItems()){
                   stores.add(item.getStore());

               }
               intent.putExtra("store", stores);
               startActivityForResult(intent,100);
               return false;
           }
       });




    }


    class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {
        private Context context;
        private View view;


        public CustomInfoWindowGoogleMap(Context context) {
            this.context = context;

        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;

        }

        @Override
        public View getInfoContents(Marker marker) {
            Log.d("로그","겟인포윈도우");

                view = ((Activity) getActivity()).getLayoutInflater()
                        .inflate(R.layout.custominfowindow, null);
                TextView name = view.findViewById(R.id.Store_name);
                TextView address = view.findViewById(R.id.Store_address);
                imageView = view.findViewById(R.id.view);

                imageView.setImageBitmap(bitmapList.get(view_item.getStore().getName()));


                name.setText(view_item.getStore().getName());
                int num = 0;
                int point = 0;
                for (int i = 0; i < view_item.getStore().getReview_item().size(); i++) {
                    point = point + view_item.getStore().getReview_item().get(i).getPoint();
                    num = num + 5;
                }

                double total = (Double.parseDouble(String.valueOf(point)) / Double.parseDouble(String.valueOf(num))) * 5;


                address.setText("" + point + " " + num + " " + total + "    " + (Math.round(total * 10) / 10.0));

                return view;


        }
    }


}






