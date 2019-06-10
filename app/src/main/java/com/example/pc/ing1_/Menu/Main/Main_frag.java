package com.example.pc.ing1_.Menu.Main;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pc.ing1_.Cluster_Item;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.Review_Item;
import com.example.pc.ing1_.Store;
import com.example.pc.ing1_.aaa.MainActivity;
import com.example.pc.ing1_.aaa.MapWrapperLayout;
import com.example.pc.ing1_.aaa.OnInfoWindowElemTouchListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import com.liaoinstan.springview.widget.SpringView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

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
    Cluster<Cluster_Item> view_cluster;
    ClusterManager<Cluster_Item> clusterManager;
    ImageView imageView;
    HashMap<String, Bitmap> bitmapList;
    ArrayList<Marker> on_marker;
    RecyclerView recyclerView;
    LatLng cluster_lan;
    RadioGroup radioGroup;
    RadioButton review_count, star_rating;
    Marker map_click_marker;
    Store_recycle_Adapter recycle_adapter;
//    SwipyRefreshLayout swipe;
    SpringView swipe;
    int max;


//    MapWrapperLayout mapWrapperLayout;
//    MapFragment mapFragment;
//    private ViewGroup infoWindow;
//    private TextView infoTitle;
//    private TextView infoSnippet;
//    private Button infoButton;
//    private OnInfoWindowElemTouchListener infoButtonListener;
    public Main_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_frag, container, false);
        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.button);
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        google_map = (MapView) view.findViewById(R.id.google_map);
        google_map.onCreate(savedInstanceState);
        google_map.getMapAsync(this);
        radioGroup = view.findViewById(R.id.radio_group);
        radioGroup.setVisibility(View.GONE);
        review_count = view.findViewById(R.id.review_count);
        star_rating = view.findViewById(R.id.star_rating);
        review_count.setChecked(true);

        swipe=view.findViewById(R.id.swipe);
        swipe.setType(SpringView.Type.FOLLOW);
        swipe.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(),"ff",Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.onFinishFreshAndLoad();
                    }
                },3000);
            }

            @Override
            public void onLoadmore() {
                Toast.makeText(getContext(),"ll",Toast.LENGTH_SHORT).show();
                Store_search(1,store.size());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },1000);
            }
        });




        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmapList = new HashMap<>();
                review_count.setChecked(true);
                Store_search(0,0);

                clusterManager.clearItems();

                swipe.setFooter(new DefaultFooter(getContext(),R.drawable.progress_small));

//                swipe.setFooter(null);


//
            }

        });
//        star_rating.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Store_search(1,0);
//                if()
//
//            }
//        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                bitmapList = new HashMap<>();
                Store_search(0,0);
                clusterManager.clearItems();
            }
        });

//        review_count.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Store_search(0,0);
//
//
//
//            }
//        });

        Log.d("선언", "creview");





//        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.info__window, null);

//        this.infoTitle = (TextView) infoWindow.findViewById(R.id.title);
//        this.infoSnippet = (TextView) infoWindow.findViewById(R.id.snippet);
//        this.infoButton = (Button) infoWindow.findViewById(R.id.button);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
//        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,getContext()) {
//            @Override
//            protected void onClickConfirmed(View v, Marker marker) {
//                 Here we can perform some action triggered after clicking the button
//                Toast.makeText(getContext(), marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
//            }
//        };
//        this.infoButton.setOnTouchListener(infoButtonListener);






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







        store= new ArrayList<>();
        recycle_adapter = new Store_recycle_Adapter(store, getActivity());
        recycle_adapter.setOnClickListener(new Store_recycle_Adapter.ItemClickListener() {
            @Override
            public void onItemClicke(View view, int position) {
                if(map_click_marker!=null){
                    map_click_marker.remove();
                }
                //하단 리사이클러뷰 클릭 이벤트
                Toast.makeText(getContext(), position + " " + store.get(position).getName(), Toast.LENGTH_SHORT).show();
                view_item = new Cluster_Item(store.get(position));
                LatLng latLng = new LatLng(Double.parseDouble(view_item.getStore().getLat()), Double.parseDouble(view_item.getStore().getLon()));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 19);

                googleMap.animateCamera(cameraUpdate);
//                                clusterManager.setRenderer(renderer);
//                                Marker marker=renderer.getMarker(view_item);
//                                marker.showInfoWindow();

                cluster_lan = latLng;
                for (Marker marker1 : clusterManager.getMarkerCollection().getMarkers()) {
                    if (marker1.getPosition().equals(latLng)) {
                        Log.d(TAG, "onItemClicke: " + marker1.getPosition());
                        marker1.showInfoWindow();
//                                        cluster_lan=null;
                    }

                }


            }
        });







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
    public void onMapReady(final GoogleMap googleMa) {
        Log.d("선언", "on");
        MapsInitializer.initialize(this.getActivity());

        this.googleMap = googleMa;
        clusterManager = new ClusterManager<>(getActivity(), this.googleMap);
// Updates the location and zoom of the MapView


//        user_lat = location.getLatitude();
//        user_lon = location.getLongitude();
        user_lat=37.486471;
        user_lon=126.971432;
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
//            Toast.makeText(getContext(), "" + addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }


//        clusterManager.setRenderer(new MakerRander(getContext(), googleMap, clusterManager));
//        googleMap.setOnInfoWindowClickListener(clusterManager);
//        clusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(new CustomInfoWindowGoogleMap(getContext()));
        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(new CustomInfoWindowGoogleMap(getContext()));
        clusterManager.setRenderer(new MakerRander(getContext(), this.googleMap, clusterManager));
        this.googleMap.setOnMarkerClickListener(clusterManager);
        this.googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager());
        this.googleMap.setOnCameraChangeListener(clusterManager);


        //        clusterManager.setOnClusterItemInfoWindowClickListener((ClusterManager.OnClusterItemInfoWindowClickListener<Cluster_Item>) this);
        //클러스터 아이템 //마커 클릭 이벤트
        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<Cluster_Item>() {
            @Override
            public boolean onClusterItemClick(Cluster_Item cluster_item) {
                Log.d("로그", "매니저 클릭 리스너");
                view_item = cluster_item;
                return false;
            }
        });
        //클러스터 클릭 이벤트
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Cluster_Item>() {
            @Override
            public boolean onClusterClick(Cluster<Cluster_Item> cluster) {

                Intent intent = new Intent(getActivity(), Cluster_Activity.class);
                ArrayList<Store> stores = new ArrayList<>();
                for (Cluster_Item item : cluster.getItems()) {
                    stores.add(item.getStore());

                }

                intent.putExtra("store", stores);
                startActivityForResult(intent, 100);
                return false;
            }
        });
        this.googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(!marker.equals(map_click_marker)) {
                    if(map_click_marker!=null){
                        map_click_marker.remove();
                    }
                    for (int i = 0; i < store.size(); i++) {
                        if (marker.getPosition().equals(new LatLng(Double.parseDouble(store.get(i).getLat()), Double.parseDouble(store.get(i).getLon())))) {
                            Intent intent = new Intent(getContext(), Store_info_Activity.class);
                            intent.putExtra("store", store.get(i));
                            startActivity(intent);
                        }

                    }
                }else{
                    Toast.makeText(getActivity(),"qweqwe",Toast.LENGTH_SHORT).show();
                }
            }
        });
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                MarkerOptions markerOptions=new MarkerOptions();
//                clusterManager.addItem(new Cluster_Item(new Store(0,String.valueOf(latLng.latitude),String.valueOf(latLng.longitude))));
//                clusterManager.cluster();
                if(map_click_marker!=null){
                    map_click_marker.remove();
                }
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.location);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
                map_click_marker=googleMap.addMarker(new MarkerOptions()
                        .title("현재위치변경/정보수정요청")
                        .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                map_click_marker.showInfoWindow();


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
            if(map_click_marker!=null){
                map_click_marker.remove();
            }

                Log.d("로그", "겟인포윈도우");
                view = ((Activity) getActivity()).getLayoutInflater()
                        .inflate(R.layout.custominfowindow, null);

                TextView name = view.findViewById(R.id.Store_name);
                TextView address = view.findViewById(R.id.Store_address);
                imageView = view.findViewById(R.id.view);
                if (bitmapList.get(view_item.getStore().getName()) == null) {
                    imageView.setImageResource(R.drawable.no_image);

                } else {
                    imageView.setImageBitmap(bitmapList.get(view_item.getStore().getName()));

                }

                if(view_item.getStore().getName().length()>=15){
                    name.setTextSize(14f);
                }else{

                }
                name.setText(view_item.getStore().getName());
                address.setText(view_item.getStore().getNew_address());
                int num = 0;
                int point = 0;

                return view;


        }




        @Override
        public View getInfoContents(Marker marker) {
//            if(marker.equals(map_click_marker)){
//                Toast.makeText(getActivity(),"qweqwe",Toast.LENGTH_SHORT).show();
//            }else {
////                return null;
//            }
return null;
        }
        
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                Store store = (Store) data.getSerializableExtra("result");
//            Toast.makeText(getContext(),userList.size()+"",Toast.LENGTH_SHORT).show();
                view_item = new Cluster_Item(store);

                Log.d(TAG, "onItemClicke: for " + view_item.getStore().getName());
                Log.d(TAG, "onItemClicke: for " + view_item.getStore().getStore_img());
                Log.d(TAG, "onItemClicke: for " + view_item.getStore().getNew_address());
                Log.d(TAG, "onItemClicke: for " + view_item.getStore().getOld_address());
//            for(int i = 0 ; i<userList.size();i++){
//                Log.d(TAG, "onActivityResult: "+userList.get(i).getPosition());
//
//                Log.d(TAG, "onActivityResult: 123123"+latLng);
//
//                if(userList.get(i).getPosition().equals(latLng)){
//                    Log.d(TAG, "onActivityResult: 성공성공");
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
//                    userList.get(i).showInfoWindow();
//                }
//            }
                LatLng latLng = new LatLng(Double.parseDouble(store.getLat()), Double.parseDouble(store.getLon()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                cluster_lan = latLng;

            }
        }

    }

    public class MakerRander extends DefaultClusterRenderer<Cluster_Item> {
        GoogleMap googleMap;
        Context context;
        ClusterManager<Cluster_Item> clusterManager;

        public MakerRander(Context context, GoogleMap map, ClusterManager<Cluster_Item> clusterManager) {
            super(context, map, clusterManager);
            googleMap = map;
            this.context = context;
            this.clusterManager = clusterManager;


        }

        @Override
        protected void onBeforeClusterItemRendered(Cluster_Item item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);
            Log.d("클러스터", "onClusterItemRendered: " + "클러스터가 마커로 나뉠때");


        }

        @Override
        protected void onBeforeClusterRendered(Cluster<Cluster_Item> cluster, MarkerOptions markerOptions) {
            super.onBeforeClusterRendered(cluster, markerOptions);
            Log.d("클러스터", "onClusterItemRendered: " + "비포클러스터");

        }

        @Override
        protected void onClusterRendered(Cluster<Cluster_Item> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);
            Log.d("클러스터", "onClusterItemRendered: " + "클러스터렌더");
        }

        @Override
        protected void onClusterItemRendered(Cluster_Item clusterItem, Marker marker) {
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(),18));
            Log.d("클러스터", "onClusterItemRendered: " + "123123" + marker.getPosition());
            Log.d("클러스터", "onClusterItemRendered: 123123" + clusterItem.getStore().getName());
//            marker.hideInfoWindow();
            Log.d("클러스터", "onClusterItemRendered: " + "조건" + cluster_lan);
            LatLng latLng = new LatLng(Double.parseDouble(clusterItem.getStore().getLat()), Double.parseDouble(clusterItem.getStore().getLon()));
            if ((latLng).equals(cluster_lan)) {
                Log.d("클러스터", "onClusterItemRendered: " + "조건 성공");
                marker.showInfoWindow();
                cluster_lan = null;
            }


            super.onClusterItemRendered(clusterItem, marker);

        }

        @Override
        public Marker getMarker(Cluster_Item clusterItem) {
            return super.getMarker(clusterItem);
        }

        @Override
        public Marker getMarker(Cluster<Cluster_Item> cluster) {
            return super.getMarker(cluster);
        }

        @Override
        public Cluster_Item getClusterItem(Marker marker) {
            return super.getClusterItem(marker);
        }

        @Override
        public Cluster<Cluster_Item> getCluster(Marker marker) {
            return super.getCluster(marker);
        }


    }


    public void Store_search(int a, final int size) {

        final HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("search", editText.getText().toString());
        hashMap.put("km", "0.5");
        hashMap.put("size",String.valueOf(size));
        hashMap.put("lat", String.valueOf(user_lat));
        hashMap.put("lon", String.valueOf(user_lon));
        Log.d("현재위치", user_lat + "/" + user_lon);
        if(star_rating.isChecked()){
            hashMap.put("cate", "1");
        }else {
            hashMap.put("cate", "0");
        }
        if(a==0){
            store.clear();
            swipe.setEnableFooter(true);
            http.search_size(hashMap).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        max= Integer.parseInt(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

        http.search_store(hashMap).enqueue(new Callback<List<Store>>() {
            @Override
            public void onResponse(Call<List<Store>> call, final Response<List<Store>> response) {
//                        Log.d("음식점", response.body().toString());
//                store = response.body();
                final List<Store> s = response.body();

//                            Log.d("음식점",store.getName());

//                clusterManager.clearItems();


//                        clusterManager.setOnClusterItemInfoWindowClickListener((ClusterManager.OnClusterItemInfoWindowClickListener<Cluster_Item>) getContext());
                for (int i = 0; i < s.size(); i++) {
                    store.add(s.get(i));
                    Log.d("음식점 이름", i + "        " + s.get(i).getName() + "     " + s.get(i).getStore_img() + "   " + s.get(i).getLat() + "   " + s.get(i).getLon());


                    final int finalI = i;
                    Glide.with(getContext()).asBitmap().load(s.get(i).getStore_img()).override(150, 150).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            bitmapList.put(s.get(finalI).getName(), resource);
//                                    Toast.makeText(getContext(),bitmapList.size()+"",Toast.LENGTH_SHORT).show();
                        }
                    });

//                            Marker marker=googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(store.get(i).getLat()),Double.parseDouble(store.get(i).getLon()))));
                    clusterManager.addItem(new Cluster_Item(s.get(i)));


                }


                clusterManager.cluster();
                if(size==0){
                    recyclerView.setAdapter(recycle_adapter);

                }else{
                    recycle_adapter.notifyDataSetChanged();
                }
                radioGroup.setVisibility(View.VISIBLE);
                swipe.onFinishFreshAndLoad();
//                recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//                    @Override
//                    public void onScrollStateChanged(@NonNull RecyclerView recyclerVie, int newState) {
//                        if(!recyclerVie.canScrollVertically(1)){
//                            ViewGroup.LayoutParams layoutParams=recyclerView.getLayoutParams();
//                            layoutParams.height=recyclerView.getHeight()-50;
//                            recyclerView.setLayoutParams(layoutParams);
//                            Toast.makeText(getContext(),recyclerView.getHeight()+"",Toast.LENGTH_SHORT).show();
//
//
//                        }
//                    }
//                });
                if(store.size()==max){
                    swipe.setEnableFooter(false);
                }
            }

            @Override
            public void onFailure(Call<List<Store>> call, Throwable t) {
                Log.d("음식점", String.valueOf(t));

            }
        });

//
    }
}







