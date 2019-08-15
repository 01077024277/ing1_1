package com.example.pc.ing1_.Menu.Main;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.Store;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Directions_Activity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    LatLng start, end;
    Store store;
    RetrofitExService naver_http,tmap_http;
    ArrayList<ArrayList<LatLng>> listArrayList;

    JSONObject jsonObject0, jsonObject1;
    ArrayList<JsonObject> dumy;
    ArrayList<JsonObject> road,road_bmw;
    ArrayList<JSONObject> map_obj;
    ArrayList<JSONArray> map_path;
    ArrayList<ArrayList<JSONArray>> map_walk;
    JsonArray path;
    HashMap<Integer,Integer> path_color;
    int a=0;
    HashMap<String, String> hashMap;
    Polyline line;
    Directions_Adapter directions_adapter;
    Directions_bmw_Adapter directions_bmw_adapter;
    RecyclerView recyclerView,recyclerView_bmw;
    ODsayService oDsayService;
    OnResultCallbackListener onResultCallbackListener,onResultCallbackListener_path;
    Button car_butotn,bmw_button;

    int category=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directions_acitivity);
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        start = new LatLng(intent.getDoubleExtra("lat", 0), intent.getDoubleExtra("lon", 0));
        store = (Store) intent.getSerializableExtra("store");
//        end=new LatLng(Double.parseDouble(store.getLat()),Double.parseDouble(store.getLon()));
        end = new LatLng(Float.parseFloat(store.getLat()), Float.parseFloat(store.getLon()));
        listArrayList = new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView_bmw=findViewById(R.id.recyclerView_bmw);
        dumy=new ArrayList<>();
        car_butotn=findViewById(R.id.car);
        bmw_button=findViewById(R.id.bmw);
        map_obj=new ArrayList<>();
        map_path=new ArrayList<>();
        map_walk= new ArrayList<>();
        recyclerView_bmw.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        oDsayService=ODsayService.init(Directions_Activity.this,"Xql7sCwhZC7w5d0ha9Z/l1LvTVz4e1CQkE7l3nxAA3k");
        oDsayService.setReadTimeout(5000);
        oDsayService.setConnectionTimeout(5000);

        directions_bmw_adapter=new Directions_bmw_Adapter(map_obj);
        recyclerView_bmw.setAdapter(directions_bmw_adapter);

        car_butotn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category == 1) {
                    car_butotn.setBackgroundColor(Color.parseColor("#000000"));
                    car_butotn.setTextColor(Color.parseColor("#ffffff"));
                    bmw_button.setBackgroundColor(Color.parseColor("#d8d8d8"));
                    bmw_button.setTextColor(Color.parseColor("#000000"));
                    category=0;
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView_bmw.setVisibility(View.GONE);
                    recyclerView.scrollToPosition(0);
                    map.clear();
                    path_polyline(road.get(0));

                    Directions_Adapter.check=0;
                    directions_adapter.notifyDataSetChanged();
                }
            }
        });
        bmw_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category == 0) {
                    car_butotn.setBackgroundColor(Color.parseColor("#d8d8d8"));
                    car_butotn.setTextColor(Color.parseColor("#000000"));
                    bmw_button.setBackgroundColor(Color.parseColor("#000000"));
                    bmw_button.setTextColor(Color.parseColor("#ffffff"));
                    category=1;

                    recyclerView.setVisibility(View.GONE);
                    recyclerView_bmw.setVisibility(View.VISIBLE);
                    recyclerView_bmw.scrollToPosition(0);
                    map.clear();
                    MarkerOptions markerOptions=new MarkerOptions();
                    markerOptions.position(new LatLng(start.latitude,start.longitude));
                    map.addMarker(markerOptions);
                    markerOptions.position(new LatLng(end.latitude,end.longitude));
                    map.addMarker(markerOptions);
                    Log.d("제슨",""+start.longitude+" "+start.latitude+" / "+end.longitude+" "+end.latitude);

                    Directions_bmw_Adapter.check=0;
                    directions_bmw_adapter.notifyDataSetChanged();
                    bmw_path(map_path.get(0),0);









                }
            }
        });

        directions_bmw_adapter.setOnClickListener_Path_bmw(new Directions_bmw_Adapter.PathClickListener_bmw() {
            @Override
            public void onItemClicke_Path_bmw(View view, int position) {
                bmw_path(map_path.get(position),position);
                directions_bmw_adapter.notifyDataSetChanged();



            }
        });

    }

    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        //자가용 경로 // 처음 액티비티 켜졌을때
        init();


        // camera 좌쵸를 서울역 근처로 옮겨 봅니다.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                start, 17f)   // 위도, 경도
        );

        // 구글지도(지구) 에서의 zoom 레벨은 1~23 까지 가능합니다.
        // 여러가지 zoom 레벨은 직접 테스트해보세요
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
//        MarkerOptions markerOptions=new MarkerOptions();
//        markerOptions.position(new LatLng(37.555744,126.970431));
//        map.addMarker(markerOptions);
//        googleMap.animateCamera(zoom);   // moveCamera 는 바로 변경하지만,
        // animateCamera() 는 근거리에선 부드럽게 변경합니다

        // marker 표시
        // market 의 위치, 타이틀, 짧은설명 추가 가능.

        //도보

//        tmap_http= new Retrofit.Builder().baseUrl("https://apis.openapi.sk.com").addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
//        HashMap<String,String> aa=new HashMap<>();
//
//        tmap_http.tmap(aa).enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                Log.d("티맵",response.body().toString());
//                ArrayList<LatLng> a = new ArrayList<>();
//            JsonObject jsonObject=response.body();
//            JsonArray jsonElements= jsonObject.getAsJsonArray("features");
//            for(int i=0;i<jsonElements.size();i++){
//               String value= jsonElements.get(i).getAsJsonObject().getAsJsonObject("geometry").get("coordinates").toString().replace("],","/").replace("[","").replace("]","");
//               for(int j=0;j<value.split("/").length;j++){
//                   a.add(new LatLng(Double.parseDouble(value.split("/")[j].split(",")[1]),Double.parseDouble(value.split("/")[j].split(",")[0])));
//               }
//
//
//            }
//                 final int PATTERN_DASH_LENGTH_PX = 20;
//                  final int PATTERN_GAP_LENGTH_PX = 20;
//                  final PatternItem DOT = new Dot();
//                  final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
//                  final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
//                  final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);
//
//                PolylineOptions polylineOptions=new PolylineOptions().pattern(PATTERN_POLYGON_ALPHA);
//                polylineOptions.addAll(a);
//                map.addPolyline(polylineOptions);
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//
//            }
//        });








    }

    public void bmw_path(JSONArray jsonObject,int index){
        if(line!=null) {

            line.remove();
            map.clear();

        }
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(new LatLng(start.latitude,start.longitude));
        map.addMarker(markerOptions);
        markerOptions.position(new LatLng(end.latitude,end.longitude));
        map.addMarker(markerOptions);
        JSONArray jsonArray= jsonObject;
        try {

        for(int i=0;i<jsonArray.length();i++) {
            JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("section").getJSONObject(0).getJSONArray("graphPos");
            int classs = jsonArray.getJSONObject(i).getInt("class");
            int type = jsonArray.getJSONObject(i).getInt("type");
            int path_c = 0;
            for (int j = 0; j < jsonArray1.length() - 1; j++) {
                            double x,y,x_1,y_1;
                            if(i!=jsonArray1.length()-2){
                                 x=jsonArray1.getJSONObject(j).getDouble("x");
                                 y=jsonArray1.getJSONObject(j).getDouble("y");
                                 x_1=jsonArray1.getJSONObject(j+1).getDouble("x");
                                 y_1=jsonArray1.getJSONObject(j+1).getDouble("y");
//                                 arrayList.add(new LatLng(y,x));
                            }
                            else{
                                JSONArray jsonArray2=jsonArray.getJSONObject(i+1).getJSONArray("section").getJSONObject(0).getJSONArray("graphPos");


                                x=jsonArray1.getJSONObject(j).getDouble("x");
                                y=jsonArray1.getJSONObject(j).getDouble("y");
                                 x_1=jsonArray2.getJSONObject(0).getDouble("x");
                                 y_1=jsonArray2.getJSONObject(0).getDouble("y");
                            }
//
//
                            Log.d("지하철1",""+jsonArray1.getJSONObject(j).getDouble("y")+" "+jsonArray1.getJSONObject(j).getDouble("x"));
                            Log.d("지하철2",""+jsonArray1.getJSONObject(j+1).getDouble("y")+" "+jsonArray1.getJSONObject(j+1).getDouble("x"));
                            Log.d("지하철3",""+jsonArray1.getJSONObject(j).toString());
                            Log.d("지하철4",""+jsonArray1.getJSONObject(j+1).toString());
//                            Log.d("지하철1",""+x);
//                            Log.d("지하철2",""+y);
//                            Log.d("지하철3",""+x_1);
//                            Log.d("지하철4",""+y_1);
                            Log.d("클래스",""+classs);

                            if(classs==1){
                                path_c=Color.parseColor("#00bfff");

                            }
                            else if(classs==2){
                                switch (type) {
                                    case 1:
                                        path_c=Color.parseColor("#0d3692");
                                        break;
                                    case 2:
                                        path_c=Color.parseColor("#33a23d");
                                        break;
                                    case 3:
                                        path_c=Color.parseColor("#fe5b10");
                                        break;
                                    case 4:
                                        path_c=Color.parseColor("#32a1c8");
                                        break;
                                    case 5:
                                        path_c=Color.parseColor("#8b50a4");
                                        break;
                                    case 6:
                                        path_c=Color.parseColor("#c55c1d");
                                        break;
                                    case 7:
                                        path_c=Color.parseColor("#54640d");
                                        break;
                                    case 8:
                                        path_c=Color.parseColor("#f51361");
                                        break;
                                    case 9:
                                        path_c=Color.parseColor("#aa9872");
                                        break;
                                    case 100:
                                        path_c=Color.parseColor("#ffb300");
                                        break;
                                }

                            }else{
                                path_c=Color.parseColor("#753778");

                            }

                                line = map.addPolyline(
                                        new PolylineOptions().add(
                                                new LatLng(y,x),
                                                new LatLng(y_1,x_1)
                                        ).color(path_c).geodesic(true));

                            }

            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        try {
            JSONArray walk_path = map_obj.get(index).getJSONArray("subPath");
            for(int j=0;j<walk_path.length();j++){
                if(walk_path.getJSONObject(j).getInt("trafficType")==3 && walk_path.getJSONObject(j).getInt("distance")>=50) {
                    double startY,startX,endY,endX;
                    if (j == 0) {
                        startX=start.longitude;
                        startY=start.latitude;
                        endX=walk_path.getJSONObject(j + 1).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getDouble("x") ;
                        endY=walk_path.getJSONObject(j + 1).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getDouble("y");



                    }else if (j==walk_path.length()-1){
                        Log.d("걸음3", walk_path.getJSONObject(j -1).getDouble("endX")+"");

                        endX=end.longitude;
                        endY=end.latitude;
                        startX=walk_path.getJSONObject(j -1).getDouble("endX");
                        startY=walk_path.getJSONObject(j -1).getDouble("endY");


                    }else{
                        Log.d("걸음2", walk_path.getJSONObject(j + 1).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getDouble("x") + "");
                        startX=walk_path.getJSONObject(j -1).getDouble("endX");
                        startY=walk_path.getJSONObject(j -1).getDouble("endY");
                        endX=walk_path.getJSONObject(j + 1).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getDouble("x") ;
                        endY=walk_path.getJSONObject(j + 1).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getDouble("y");

                    }






                    Log.d("티맵",startX+" "+startY+" "+endX+" "+endY);



                    tmap_http= new Retrofit.Builder().baseUrl("https://apis.openapi.sk.com").addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
//                                    version=1&startX={startX}&startY={startY}&endX={endX}endY={endY}&startName=%EC%B6%9C%EB%B0%9C&endName=%EB%B3%B8%EC%82%AC
                    HashMap <String,String> tt = new HashMap<>();
                    tt.put("version","1");
                    tt.put("startX",startX+"");
                    tt.put("startY",startY+"");
                    tt.put("endX",endX+"");
                    tt.put("endY",endY+"");
                    tt.put("startName","%EC%B6%9C%EB%B0%9C");
                    tt.put("endName","%EB%B3%B8%EC%82%AC");
                    Log.d("티맵",startX+" "+startY+" "+endX+" "+endY);

                    tmap_http.tmap(tt).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("티맵",response.toString());
                            ArrayList<LatLng> a = new ArrayList<>();
                            JsonObject jsonObject=response.body();
                            JsonArray jsonElements= jsonObject.getAsJsonArray("features");
                            for(int i=0;i<jsonElements.size();i++){
                                String value= jsonElements.get(i).getAsJsonObject().getAsJsonObject("geometry").get("coordinates").toString().replace("],","/").replace("[","").replace("]","");
                                for(int j=0;j<value.split("/").length;j++){
                                    a.add(new LatLng(Double.parseDouble(value.split("/")[j].split(",")[1]),Double.parseDouble(value.split("/")[j].split(",")[0])));
                                }


                            }
                            final int PATTERN_DASH_LENGTH_PX = 20;
                            final int PATTERN_GAP_LENGTH_PX = 20;
                            final PatternItem DOT = new Dot();
                            final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
                            final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
                            final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

                            PolylineOptions polylineOptions=new PolylineOptions().pattern(PATTERN_POLYGON_ALPHA);
                            polylineOptions.addAll(a);
                            map.addPolyline(polylineOptions);
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
////
//







                }
            }


        }catch (JSONException e) {

        }










    }


    public void init() {

        onResultCallbackListener_path=new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                try {
                    ArrayList<LatLng> arrayList=new ArrayList<>();
                    JSONArray jsonArray=oDsayData.getJson().getJSONObject("result").getJSONArray("lane");
                    map_path.add(jsonArray);

                    for(int i=0;i<jsonArray.length();i++){
                        JSONArray jsonArray1=jsonArray.getJSONObject(i).getJSONArray("section").getJSONObject(0).getJSONArray("graphPos");

//                        int classs=jsonArray.getJSONObject(i).getInt("class");
//                        int type=jsonArray.getJSONObject(i).getInt("type");
//                        int path_c=0;
//                        Log.d("지하철",jsonArray1.toString());
//                        Log.d("지하철",jsonArray1.getJSONObject(0).toString());
//                        Log.d("지하철",jsonArray1.getJSONObject(1).toString());
//
//                        Log.d("지하철",jsonArray1.length()+"");
//
//                        for(int j=0;j<jsonArray1.length()-1;j++){
//                            double x,y,x_1,y_1;
////                            if(i!=jsonArray1.length()-2){
//                                 x=jsonArray1.getJSONObject(j).getDouble("x");
//                                 y=jsonArray1.getJSONObject(j).getDouble("y");
//                                 x_1=jsonArray1.getJSONObject(j+1).getDouble("x");
//                                 y_1=jsonArray1.getJSONObject(j+1).getDouble("y");
////                                 arrayList.add(new LatLng(y,x));
////                            }else{
////                                JSONArray jsonArray2=jsonArray.getJSONObject(i+1).getJSONArray("section").getJSONObject(0).getJSONArray("graphPos");
////
////
////                                x=jsonArray1.getJSONObject(j).getDouble("x");
////                                y=jsonArray1.getJSONObject(j).getDouble("y");
////                                 x_1=jsonArray2.getJSONObject(0).getDouble("x");
////                                 y_1=jsonArray2.getJSONObject(0).getDouble("y");
////                            }
//
//
////                            Log.d("지하철1",""+jsonArray1.getJSONObject(j).getDouble("y")+" "+jsonArray1.getJSONObject(j).getDouble("x"));
////                            Log.d("지하철2",""+jsonArray1.getJSONObject(j+1).getDouble("y")+" "+jsonArray1.getJSONObject(j+1).getDouble("x"));
////                            Log.d("지하철3",""+jsonArray1.getJSONObject(j).toString());
////                            Log.d("지하철4",""+jsonArray1.getJSONObject(j+1).toString());
//                            Log.d("지하철1",""+x);
//                            Log.d("지하철2",""+y);
//                            Log.d("지하철3",""+x_1);
//                            Log.d("지하철4",""+y_1);
//                            if(classs==2){
//                                switch (type) {
//                                    case 1:
//                                        path_c=Color.parseColor("#0d3692");
//                                        break;
//                                    case 2:
//                                        path_c=Color.parseColor("#33a23d");
//                                        break;
//                                    case 3:
//                                        path_c=Color.parseColor("#fe5b10");
//                                        break;
//                                    case 4:
//                                        path_c=Color.parseColor("#32a1c8");
//                                        break;
//                                    case 5:
//                                        path_c=Color.parseColor("#8b50a4");
//                                        break;
//                                    case 6:
//                                        path_c=Color.parseColor("#c55c1d");
//                                        break;
//                                    case 7:
//                                        path_c=Color.parseColor("#54640d");
//                                        break;
//                                    case 8:
//                                        path_c=Color.parseColor("#f51361");
//                                        break;
//                                    case 9:
//                                        path_c=Color.parseColor("#aa9872");
//                                        break;
//                                    case 100:
//                                        path_c=Color.parseColor("#ffb300");
//                                        break;
//                                }
//                            }else{
//                                path_c=Color.parseColor("#753778");
//
//                            }
//
//                                line = map.addPolyline(
//                                        new PolylineOptions().add(
//                                                new LatLng(y,x),
//                                                new LatLng(y_1,x_1)
//                                        ).color(path_c).geodesic(true));
//
//                            }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("지하철",String.valueOf(e));
                }
            }

            @Override
            public void onError(int i, String s, API api) {

            }
        };



        onResultCallbackListener=new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {

                try {
//                    Log.d("지하철",oDsayData.getJson().toString());

                    JSONArray jsonArray=oDsayData.getJson().getJSONObject("result").getJSONArray("path");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject sub_path=jsonArray.getJSONObject(i);
//                        JSONArray sub_path=jsonArray.getJSONObject(i).getJSONArray("subPath");

//                        Log.d("서브패치",sub_path.toString());
//                        for(int j =0 ;j<sub_path.length();j++){

//                                JSONObject sub_path_array= sub_path.getJSONObject(j);
//                                MarkerOptions markerOptions=new MarkerOptions();
//                                if(sub_path_array.getInt("trafficType")==1 || sub_path_array.getInt("trafficType")==2 ){
//                                    Log.d("서브패치", sub_path_array.toString());
//                                    markerOptions.position(new LatLng(sub_path_array.getDouble("startY"),sub_path_array.getDouble("startX"))).title("승차");
//                                    map.addMarker(markerOptions);
//                                    markerOptions.position(new LatLng(sub_path_array.getDouble("endY"),sub_path_array.getDouble("endX"))).title("하차");
//                                    map.addMarker(markerOptions);
//                                }else{
//                                    Log.d("서브패치123", sub_path.get(j).toString());
//
//                                }
                            map_obj.add(sub_path);
//                        JSONArray walk_path=sub_path.getJSONArray("subPath");
//                        for(int j=0;j<walk_path.length();j++){
//                            if(walk_path.getJSONObject(j).getInt("trafficType")==3 && walk_path.getJSONObject(j).getInt("distance")>=100) {
//                                double startY,startX,endY,endX;
//                                if (j == 0) {
//
//
//
//
//
//                                }else if (j==walk_path.length()-1){
//                                    Log.d("걸음3", walk_path.getJSONObject(j -1).getDouble("endX")+"");
//
//                                    endX=end.longitude;
//                                    endY=end.latitude;
//                                    startX=walk_path.getJSONObject(j -1).getDouble("endX");
//                                    startY=walk_path.getJSONObject(j -1).getDouble("endY");
//
//
//                                }else{
//                                    Log.d("걸음2", walk_path.getJSONObject(j + 1).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getDouble("x") + "");
//                                    startX=walk_path.getJSONObject(j -1).getDouble("endX");
//                                    startY=walk_path.getJSONObject(j -1).getDouble("endY");
//                                    endX=walk_path.getJSONObject(j + 1).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getDouble("x") ;
//                                    endY=walk_path.getJSONObject(j + 1).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).getDouble("y");
//
//                                }
//
//
//
//
//
//
//                                Log.d("티맵",startX+" "+startY+" "+endX+" "+endY);
//
//
//
//                                tmap_http= new Retrofit.Builder().baseUrl("https://apis.openapi.sk.com").addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
////                                    version=1&startX={startX}&startY={startY}&endX={endX}endY={endY}&startName=%EC%B6%9C%EB%B0%9C&endName=%EB%B3%B8%EC%82%AC
//                                HashMap <String,String> tt = new HashMap<>();
//                                tt.put("version","1");
//                                tt.put("startX",startX+"");
//                                tt.put("startY",startY+"");
//                                tt.put("endX",endX+"");
//                                tt.put("endY",endY+"");
//                                tt.put("startName","%EC%B6%9C%EB%B0%9C");
//                                tt.put("endName","%EB%B3%B8%EC%82%AC");
//                                Log.d("티맵",startX+" "+startY+" "+endX+" "+endY);
//
//                                tmap_http.tmap(tt).enqueue(new Callback<JsonObject>() {
//                                    @Override
//                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                                        Log.d("티맵",response.toString());
//                                        ArrayList<LatLng> a = new ArrayList<>();
//                                        JsonObject jsonObject=response.body();
//                                        JsonArray jsonElements= jsonObject.getAsJsonArray("features");
//                                        for(int i=0;i<jsonElements.size();i++){
//                                            String value= jsonElements.get(i).getAsJsonObject().getAsJsonObject("geometry").get("coordinates").toString().replace("],","/").replace("[","").replace("]","");
//                                            for(int j=0;j<value.split("/").length;j++){
//                                                a.add(new LatLng(Double.parseDouble(value.split("/")[j].split(",")[1]),Double.parseDouble(value.split("/")[j].split(",")[0])));
//                                            }
//
//
//                                        }
//                                        final int PATTERN_DASH_LENGTH_PX = 20;
//                                        final int PATTERN_GAP_LENGTH_PX = 20;
//                                        final PatternItem DOT = new Dot();
//                                        final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
//                                        final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
//                                        final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);
//
//                                        PolylineOptions polylineOptions=new PolylineOptions().pattern(PATTERN_POLYGON_ALPHA);
//                                        polylineOptions.addAll(a);
//                                        map.addPolyline(polylineOptions);
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<JsonObject> call, Throwable t) {
//
//                                    }
//                                });
//////
////
//
//
//
//
//
//
//
//                            }
//                        }


//                        }

                    directions_bmw_adapter.notifyDataSetChanged();

                        String mapObj = jsonArray.getJSONObject(i).getJSONObject("info").getString("mapObj").toString();
                            oDsayService.requestLoadLane("0:0@" + mapObj, onResultCallbackListener_path);
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(int i, String s, API api) {

            }
        };
        oDsayService.requestSearchPubTransPath(""+start.longitude, ""+start.latitude, ""+end.longitude, ""+end.latitude, "0", "0", "0", onResultCallbackListener);


        final ProgressDialog progressDialog=new ProgressDialog(Directions_Activity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("로딩중입니다..");
        progressDialog.show();
        naver_http = new Retrofit.Builder().baseUrl("https://naveropenapi.apigw.ntruss.com").addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
         hashMap = new HashMap<>();
        hashMap.put("start", start.longitude + "," + start.latitude);
        hashMap.put("goal", end.longitude + "," + end.latitude);
        hashMap.put("option", "trafast:tracomfort");
        naver_http.qwqw(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string1 = null;
                JsonObject result = null;
               road=new ArrayList<>();
                ArrayList<LatLng> fastlatlng=new ArrayList<>();

                try {
                    string1 = response.body().string().toString();

                    JsonParser parser = new JsonParser();
                    Object object = parser.parse(string1);
                    result = (JsonObject) object;
                    dumy.add((JsonObject) result.getAsJsonObject("route").getAsJsonArray("trafast").get(0));
                    dumy.add((JsonObject) result.getAsJsonObject("route").getAsJsonArray("tracomfort").get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                path_polyline(dumy.get(0));


//                        PolylineOptions polylineOptions=new PolylineOptions();
//                    polylineOptions.color(Color.BLACK);
//                    polylineOptions.addAll(fastlatlng);
//                    map.addPolyline(polylineOptions);

                hashMap.put("option", "traoptimal:traavoidtoll:traavoidcaronly");

                naver_http.qwqw(hashMap).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        String string2 = null;
                        JsonObject result2 = null;

                        try {
                            string2 = response.body().string().toString();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        JsonParser parser = new JsonParser();
                        Object object = parser.parse(string2);
                        result2 = (JsonObject) object;
                        dumy.add((JsonObject) result2.getAsJsonObject("route").getAsJsonArray("traoptimal").get(0));
                        dumy.add((JsonObject) result2.getAsJsonObject("route").getAsJsonArray("traavoidtoll").get(0));
                        dumy.add((JsonObject) result2.getAsJsonObject("route").getAsJsonArray("traavoidcaronly").get(0));

//                Toast.makeText(getApplicationContext(),dumy.size()+"",Toast.LENGTH_SHORT).show();

                        for(int i=0;i<dumy.size();i++){
                            if(!road.contains(dumy.get(i))){
                                road.add(dumy.get(i));
                            }
                        }
                        directions_adapter=new Directions_Adapter(road);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(directions_adapter);
                        for(int i=0;i<road.size();i++){
                            Log.d("중복",road.get(i).toString());
                        }
                        directions_adapter.setOnClickListener_Path(new Directions_Adapter.PathClickListener() {
                            @Override
                            public void onItemClicke_Path(View view, int position) {
                                path_polyline(road.get(position));
                                directions_adapter.notifyDataSetChanged();
                            }
                        });
//                            Toast.makeText(getApplicationContext(),road.size()+"",Toast.LENGTH_SHORT).show();
                        bmw_button.setVisibility(View.VISIBLE);
                        car_butotn.setVisibility(View.VISIBLE);
                        car_butotn.setBackgroundColor(Color.parseColor("#000000"));
                        car_butotn.setTextColor(Color.parseColor("#ffffff"));
                        bmw_button.setBackgroundColor(Color.parseColor("#d8d8d8"));
                        bmw_button.setTextColor(Color.parseColor("#000000"));

                        progressDialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

//        oDsayService.requestSearchPubTransPath(""+start.longitude, ""+start.latitude, ""+end.longitude, ""+end.latitude, "0", "0", "0", onResultCallbackListener);

//        oDsayService.requestSearchPubTransPath("126.970954", "37.484709", "127.047473", "37.547132", "0", "0", "0", onResultCallbackListener);


    }

public  void path_polyline(JsonObject jsonObject){
        if(line!=null) {

            line.remove();
            map.clear();

        }
    MarkerOptions markerOptions=new MarkerOptions();
    markerOptions.position(new LatLng(start.latitude,start.longitude));
    map.addMarker(markerOptions);
    markerOptions.position(new LatLng(end.latitude,end.longitude));
    map.addMarker(markerOptions);
    path=null;
    path_color = new HashMap<>();
    path=jsonObject.getAsJsonArray("section");
    for (int i=0;i<path.size();i++){
        int start = Integer.parseInt(path.get(i).getAsJsonObject().get("pointIndex").toString());
        int end = Integer.parseInt(path.get(i).getAsJsonObject().get("pointCount").toString())+start;
        int color=Integer.parseInt(path.get(i).getAsJsonObject().get("congestion").toString());
        for(int j=start ; j<end;j++){
            path_color.put(j,color);
        }
    }

    path=jsonObject.getAsJsonArray("path");
    for ( int i=0;i<path.size()-1;i++){
//        Log.d("패치", String.valueOf(path.get(i).getAsJsonArray().get(0)));
//        fastlatlng.add(new LatLng(Double.parseDouble(path.get(i).getAsJsonArray().get(1).toString()),Double.parseDouble(path.get(i).getAsJsonArray().get(0).toString())));
        if(path_color.get(i)==null){
             line = map.addPolyline(
                    new PolylineOptions().add(
                            new LatLng(Double.parseDouble(path.get(i).getAsJsonArray().get(1).toString()),Double.parseDouble(path.get(i).getAsJsonArray().get(0).toString())),
                            new LatLng(Double.parseDouble(path.get(i+1).getAsJsonArray().get(1).toString()),Double.parseDouble(path.get(i+1).getAsJsonArray().get(0).toString()))
                    ).color(Color.GREEN).geodesic(true)
            );
        }else if (path_color.get(i)==3){
             line = map.addPolyline(
                    new PolylineOptions().add(
                            new LatLng(Double.parseDouble(path.get(i).getAsJsonArray().get(1).toString()),Double.parseDouble(path.get(i).getAsJsonArray().get(0).toString())),
                            new LatLng(Double.parseDouble(path.get(i+1).getAsJsonArray().get(1).toString()),Double.parseDouble(path.get(i+1).getAsJsonArray().get(0).toString()))
                    ).color(Color.RED).geodesic(true)
            );
        }else if (path_color.get(i)==2){
             line = map.addPolyline(
                    new PolylineOptions().add(
                            new LatLng(Double.parseDouble(path.get(i).getAsJsonArray().get(1).toString()),Double.parseDouble(path.get(i).getAsJsonArray().get(0).toString())),
                            new LatLng(Double.parseDouble(path.get(i+1).getAsJsonArray().get(1).toString()),Double.parseDouble(path.get(i+1).getAsJsonArray().get(0).toString()))
                    ).color(Color.YELLOW).geodesic(true)
            );
        }else{
             line = map.addPolyline(
                    new PolylineOptions().add(
                            new LatLng(Double.parseDouble(path.get(i).getAsJsonArray().get(1).toString()),Double.parseDouble(path.get(i).getAsJsonArray().get(0).toString())),
                            new LatLng(Double.parseDouble(path.get(i+1).getAsJsonArray().get(1).toString()),Double.parseDouble(path.get(i+1).getAsJsonArray().get(0).toString()))
                    ).color(Color.GREEN).geodesic(true)
            );
        }

    }
}
}
