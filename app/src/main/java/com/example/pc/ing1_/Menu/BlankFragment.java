package com.example.pc.ing1_.Menu;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.Store_info;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    boolean a;
    RetrofitExService http;
    View view;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_blank,container,false);

        MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) view.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 중심점 변경 - 예제 좌표는 서울 남산
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.54892296550104, 126.99089033876304), true);

        // 줌 레벨 변경
        mapView.setZoomLevel(4, true);


        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.4834775,126.9748299);
        Log.d("123123",MARKER_POINT.getMapPointGeoCoord().longitude+"/"+MARKER_POINT.getMapPointGeoCoord().latitude);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);

        Button button=view.findViewById(R.id.dlrj);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        http.store("qwe").enqueue(new Callback<List<Store_info>>() {

                            @Override
                            public void onResponse(Call<List<Store_info>> call, Response<List<Store_info>> response) {
                                List<Store_info> store_infos = new ArrayList<>();
//                Log.d("wqewqe",response.body().toString());

                                store_infos = response.body();
                                for (int i = 0; i < store_infos.size(); i++) {
                                    MapPoint MARKER_POINT = MapPoint.mapPointWithWCONGCoord(Double.parseDouble(store_infos.get(i).getX()), Double.parseDouble(store_infos.get(i).getY()));
                                    Log.d("asd", store_infos.get(i).getName() + " " + MARKER_POINT.getMapPointGeoCoord().latitude + " " + MARKER_POINT.getMapPointGeoCoord().longitude);
                                    HashMap <String,String> hashMap=new HashMap<>();

                                        hashMap.put("name", store_infos.get(i).getName());
                                        hashMap.put("address", store_infos.get(i).getAddress());
                                        hashMap.put("x", store_infos.get(i).getX());
                                        hashMap.put("y", store_infos.get(i).getY());
                                        hashMap.put("lat", "" + MARKER_POINT.getMapPointGeoCoord().latitude);
                                        hashMap.put("lon", "" + MARKER_POINT.getMapPointGeoCoord().longitude);

                                    http.re(hashMap).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Log.d("ㅂㅈㄷㅁㄴㅇ", String.valueOf(t));
                                        }
                                    });

                                }



                            }

                            @Override
                            public void onFailure(Call<List<Store_info>> call, Throwable t) {
                                Log.d("ㅂㅈㄷㅁㄴㅇ", String.valueOf(t));

                            }
                        });
                    }


        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);


    }

    @Override
    public void onResume() {
        super.onResume();
        //마커 찍기

    }
}
