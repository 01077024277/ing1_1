package com.example.pc.ing1_.Menu.Menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recommend_Activity extends AppCompatActivity {

    String phone, day, time, select;
    int recommend;
    RetrofitExService http;
    double cal, carb, protein, fat;
    RecyclerView total_menu, select_menu;
    Recommend_Total_Adapter recommend_total_adapter;
    My_Select_Menu_Adapter my_select_menu;
    int total_size;
    ArrayList<Food_info> food_infos, select_infos;
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_activity);
        Intent intent1 = getIntent();
        Toast.makeText(getApplicationContext(), intent1.getIntExtra("recommend", 99) + "", Toast.LENGTH_SHORT).show();
//        Log.d("recommend",intent1.getIntExtra("recommend",99)+"");
//        Log.d("recommend",intent1.getStringExtra("phone"));
//        Log.d("recommend",intent1.getStringExtra("day"));
//        Log.d("recommend",intent1.getStringExtra("time"));

        phone = intent1.getStringExtra("phone");
        day = intent1.getStringExtra("day");
        Log.d("결과", day);
        time = intent1.getStringExtra("time");
        select = intent1.getStringExtra("select");
        //0일시 미리 입력된 식단 포함해서 식단 짜기
        //1일시 초기화 시킨후 식단 짜기
        recommend = intent1.getIntExtra("recommend", 99);

        total_menu = findViewById(R.id.total_menu);
        select_menu = findViewById(R.id.select_menu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        select_menu.setLayoutManager(linearLayoutManager);



        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        food_infos = new ArrayList<>();
        select_infos = new ArrayList<>();
        hashMap = new HashMap<>();

        my_select_menu=new My_Select_Menu_Adapter(select_infos,getApplicationContext());
        my_select_menu.SetOnitemClick(new My_Select_Menu_Adapter.ClikeItem() {
            @Override
            public void removeItem(View v, int i) {

                for(int j=0; j<food_infos.size();j++){
                    if(select_infos.get(i)==food_infos.get(j)){

                        recommend_total_adapter.notifyDataSetChanged();
                    }
                }
                select_infos.remove(i);
                my_select_menu.notifyDataSetChanged();
            }
        });
        select_menu.setAdapter(my_select_menu);
        init();

    }


    //처음 액티비티 실행시 네트워크 통신
    public void init() {
        hashMap.put("page", "0");
        hashMap.put("phone", phone);
        hashMap.put("day", day);
        hashMap.put("time", time);
        hashMap.put("select", select);
        hashMap.put("recommend", String.valueOf(recommend));
        http.ex(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        total_size = jsonObject.getInt("total_size");
                        cal = jsonObject.getDouble("cal");
                        carb = jsonObject.getDouble("carb");
                        protein = jsonObject.getDouble("protein");
                        fat = jsonObject.getDouble("fat");
                        JSONArray jsonArray = (jsonObject.getJSONArray("item"));
                        Log.d("포문", jsonArray.toString());
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            food_infos.add(gson.fromJson(jsonArray.get(i).toString(), Food_info.class));
                        }
                        for (int i = 0; i < food_infos.size(); i++) {
                            Log.d("포문", food_infos.get(i).getName());
                        }
                        Toast.makeText(getApplicationContext(), "asd", Toast.LENGTH_SHORT).show();
//                        recommend_total_adapter = new Recommend_Total_Adapter(food_infos, cal, carb, protein, fat, total_size);
                        recommend_total_adapter.setItemClick(new Recommend_Total_Adapter.ItemClick() {
                            @Override
                            public void onClick(View v, int i) {


                                    select_infos.add(food_infos.get(i));
                                    my_select_menu.notifyDataSetChanged();
//                                    food_infos.get(i).setCheck(true);


                            }

                            @Override
                            public void addClick(View v, int i) {

                                additem();
                            }
                        });

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                        total_menu.setLayoutManager(linearLayoutManager);
                        total_menu.setAdapter(recommend_total_adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //더보기를 눌렀을 경우 additem 메소스

    public void additem() {

        hashMap.put("page", String.valueOf(food_infos.size()));
        hashMap.put("phone", phone);
        hashMap.put("day", day);
        hashMap.put("time", time);
        hashMap.put("select", select);
        hashMap.put("recommend", String.valueOf(recommend));
        http.ex(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    JSONObject jsonObject = new JSONObject(result);

                    JSONArray jsonArray = (jsonObject.getJSONArray("item"));
                    Log.d("포문", jsonArray.toString());
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        food_infos.add(gson.fromJson(jsonArray.get(i).toString(), Food_info.class));
                    }
                    recommend_total_adapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
