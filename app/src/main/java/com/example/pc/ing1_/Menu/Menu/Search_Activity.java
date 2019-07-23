package com.example.pc.ing1_.Menu.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.google.gson.Gson;

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

public class Search_Activity extends AppCompatActivity {
    RetrofitExService http;

    ArrayList<Food_info2> food_infos, back_infos, recommed_menu;
    FoodDataBase foodDataBase;
    SQLiteDatabase db;
    List<String> autolist, searchlist, blankremove;
    RecyclerView recyclerView, recyclerView2, recyclerView3;
    Food_List_Adapter food_list_adapter;
    Food_Select_Adapter food_select_adapter;
    Recommend_Total_Adapter recommend_total_adapter;
    EditText search;
    Button save;
    Button category_all,category_1,category_2,category_3,category_4,category_5;
    SharedPreferences sf;
    String day, value, phone, select;
    double cal, carb, protein, fat;
    int total_size;

    int recommend,category;
    double my_cal;
    double my_carb;
    double my_protein;
    double my_fat;
    RoundCornerProgressBar progressBar_carb, progressBar_fat, progressBar_pro, progressBar_cal;
    TextView textview_carb,textview_fat,textview_pro,textview_cal;

    double height,weight,age,ccal;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        sf = getSharedPreferences("login", MODE_PRIVATE);
        food_infos = new ArrayList<>();
        back_infos = new ArrayList<>();
        autolist = new ArrayList<>();
        searchlist = new ArrayList<>();
        blankremove = new ArrayList<>();
        Intent intent = getIntent();

        back_infos = (ArrayList<Food_info2>) intent.getSerializableExtra("food_infos");
        for (int i = 0; i < back_infos.size(); i++) {
            food_infos.add(back_infos.get(i));
        }

        category=0;
        category_all=findViewById(R.id.button_all);
        category_1=findViewById(R.id.button_1);
        category_2=findViewById(R.id.button_2);
        category_3=findViewById(R.id.button_3);
        category_4=findViewById(R.id.button_4);
        category_5=findViewById(R.id.button_5);
        category_all.setBackgroundColor(Color.parseColor("#000000"));
        category_all.setTextColor(Color.parseColor("#ffffff"));

        category_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category!=0){
                    category=0;
                    init();
                }else{
                }
                category_all.setBackgroundColor(Color.parseColor("#000000"));
                category_all.setTextColor(Color.parseColor("#ffffff"));
                category_1.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_1.setTextColor(Color.parseColor("#000000"));
                category_2.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_2.setTextColor(Color.parseColor("#000000"));
                category_3.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_3.setTextColor(Color.parseColor("#000000"));
                category_4.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_4.setTextColor(Color.parseColor("#000000"));
                category_5.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_5.setTextColor(Color.parseColor("#000000"));




            }
        });
        category_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category!=1){
                    category=1;
                    init();
                }else{
                }
                category_1.setBackgroundColor(Color.parseColor("#000000"));
                category_1.setTextColor(Color.parseColor("#ffffff"));
                category_all.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_all.setTextColor(Color.parseColor("#000000"));
                category_2.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_2.setTextColor(Color.parseColor("#000000"));
                category_3.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_3.setTextColor(Color.parseColor("#000000"));
                category_4.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_4.setTextColor(Color.parseColor("#000000"));
                category_5.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_5.setTextColor(Color.parseColor("#000000"));
            }
        });
        category_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category!=2){
                    category=2;
                    init();
                }else{
                }                category_2.setBackgroundColor(Color.parseColor("#000000"));
                category_2.setTextColor(Color.parseColor("#ffffff"));
                category_1.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_1.setTextColor(Color.parseColor("#000000"));
                category_all.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_all.setTextColor(Color.parseColor("#000000"));
                category_3.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_3.setTextColor(Color.parseColor("#000000"));
                category_4.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_4.setTextColor(Color.parseColor("#000000"));
                category_5.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_5.setTextColor(Color.parseColor("#000000"));
            }
        });
        category_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category!=3){
                    category=3;
                    init();
                }else{
                }
                category_3.setBackgroundColor(Color.parseColor("#000000"));
                category_3.setTextColor(Color.parseColor("#ffffff"));
                category_1.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_1.setTextColor(Color.parseColor("#000000"));
                category_2.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_2.setTextColor(Color.parseColor("#000000"));
                category_all.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_all.setTextColor(Color.parseColor("#000000"));
                category_4.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_4.setTextColor(Color.parseColor("#000000"));
                category_5.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_5.setTextColor(Color.parseColor("#000000"));
            }
        });
        category_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category!=4){
                    category=4;
                    init();
                }else{
                }
                category_4.setBackgroundColor(Color.parseColor("#000000"));
                category_4.setTextColor(Color.parseColor("#ffffff"));
                category_1.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_1.setTextColor(Color.parseColor("#000000"));
                category_2.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_2.setTextColor(Color.parseColor("#000000"));
                category_3.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_3.setTextColor(Color.parseColor("#000000"));
                category_all.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_all.setTextColor(Color.parseColor("#000000"));
                category_5.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_5.setTextColor(Color.parseColor("#000000"));
            }
        });
        category_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category!=5){
                    category=5;
                    init();
                }else{
                }
                category_5.setBackgroundColor(Color.parseColor("#000000"));
                category_5.setTextColor(Color.parseColor("#ffffff"));
                category_1.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_1.setTextColor(Color.parseColor("#000000"));
                category_2.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_2.setTextColor(Color.parseColor("#000000"));
                category_3.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_3.setTextColor(Color.parseColor("#000000"));
                category_4.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_4.setTextColor(Color.parseColor("#000000"));
                category_all.setBackgroundColor(Color.parseColor("#d8d8d8"));
                category_all.setTextColor(Color.parseColor("#000000"));
            }
        });


        progressBar_carb = findViewById(R.id.progress_carb);
        progressBar_fat = findViewById(R.id.progress_fat);
        progressBar_pro = findViewById(R.id.progress_pro);
        progressBar_cal = findViewById(R.id.progress_cal);

        textview_carb=findViewById(R.id.textView_carb);
        textview_fat=findViewById(R.id.textView_fat);
        textview_pro=findViewById(R.id.textView_pro);
        textview_cal=findViewById(R.id.textView_cal);
//        colors = ['#ff9999','#66b3ff','#99ff99','#ffcc99']
        progressBar_carb.setProgressColor(Color.parseColor("#00ff99"));
        progressBar_carb.setMax(100);
        progressBar_fat.setProgressColor(Color.parseColor("#ff9900"));
        progressBar_fat.setMax(100);
        progressBar_pro.setProgressColor(Color.parseColor("#ff0099"));
        progressBar_pro.setMax(100);
        progressBar_cal.setProgressColor(Color.parseColor("#00b3ff"));
        progressBar_cal.setMax(100);
//        progressBar_carb.setProgress(10);
//        progressBar_fat.setProgress(40);
//        progressBar_pro.setProgress(70);
//        progressBar_cal.setProgress(120);
        day = intent.getStringExtra("day");
        value = intent.getStringExtra("value");
        phone = intent.getStringExtra("phone");
        height= Double.parseDouble(intent.getStringExtra("height"));
        weight= Double.parseDouble(intent.getStringExtra("weight"));
        age= Double.parseDouble(intent.getStringExtra("age"));
        gender=intent.getStringExtra("gender");
        Log.d("제이슨",phone);
        select = intent.getStringExtra("select");
        recommend = (intent.getIntExtra("recommend", 99));
        foodDataBase = new FoodDataBase(getApplicationContext(), "food", null, 14);
        db = foodDataBase.getWritableDatabase();
        save = findViewById(R.id.save);

        if(gender.equals("남")){
            ccal = (66.48 + (13.75 * (int)weight) + (5 * (int)height) - (6.76 * (int)age))*1.4;

        }else{
            ccal = (655.1 + (9.56 * (int)weight) + (1.85 * (int)height) - (4.68 * (int)age))*1.4;

        }
        if(value.equals("아침")){

            if(select.equals("체중 증가")){
                ccal=ccal+500;

            }else if (select.equals("체중 감소")){
                ccal=ccal-500;
            }
            ccal=ccal/10000*3333;


        }else if (value.equals("점심")){
            if(select.equals("체중 증가")){
                ccal=ccal+500;

            }else if (select.equals("체중 감소")){
                ccal=ccal-500;

            }
            ccal=ccal/10000*4333;


        }else{
            if(select.equals("체중 증가")){
                ccal=ccal+500;

            }else if (select.equals("체중 감소")){
                ccal=ccal-500;

            }
            ccal=ccal/10000*2333;

        }
        //저장 버튼을 눌렀을 경우
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 아이템이 없을경우
                if (food_infos.size() == 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Search_Activity.this);
                    dialog.setTitle("경고");
                    dialog.setMessage("선택된 음식이 없습니다 저장 하시겠습니까 ?");
                    dialog.setNegativeButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final Intent intent = new Intent();
                            intent.putExtra("value", food_infos);

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("phone", sf.getString("phone", ""));
                                jsonObject.put("day", day);
                                jsonObject.put("time", value);
                                jsonObject.put("remove", "o");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            http.schedule(jsonObject).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });


                        }
                    });
                    dialog.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Search_Activity.this);
                    dialog.setTitle("");
                    dialog.setMessage("저장 하시겠습니까 ?");
                    dialog.setNegativeButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent intent = new Intent();
                            intent.putExtra("value", food_infos);
                            JSONArray jsonArray = new JSONArray();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("phone", sf.getString("phone", ""));
                                jsonObject.put("day", day);
                                jsonObject.put("time", value);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            for (int i = 0; i < food_infos.size(); i++) {
                                Log.d("qaqaqaqa", "이름" + food_infos.get(i).getFood_info().getName() + "");
                                Log.d("qaqaqaqa", "num = 횟수 or g" + food_infos.get(i).getNum() + "");
                                Log.d("qaqaqaqa", "G = ml and g XX 무게" + food_infos.get(i).getG());
                                Log.d("qaqaqaqa", "탄" + food_infos.get(i).getFood_info().getCarb());
                                Log.d("qaqaqaqa", "단" + food_infos.get(i).getFood_info().getProtein());
                                Log.d("qaqaqaqa", "지" + food_infos.get(i).getFood_info().getFat());
                                Log.d("qaqaqaqa", "콜" + food_infos.get(i).getFood_info().getChol());
                                Log.d("qaqaqaqa", "식이" + food_infos.get(i).getFood_info().getFiber());
                                Log.d("qaqaqaqa", "나트" + food_infos.get(i).getFood_info().getSalt());
                                Log.d("qaqaqaqa", "칼" + food_infos.get(i).getFood_info().getPotass());
                                JSONObject jsonObject1 = new JSONObject();
                                try {
                                    jsonObject1.put("food_name", food_infos.get(i).getFood_info().getName());
                                    jsonObject1.put("Num", food_infos.get(i).getNum());
                                    jsonObject1.put("g", food_infos.get(i).getG());
                                    jsonObject1.put("size", food_infos.get(i).getFood_info().getNum() + food_infos.get(i).getFood_info().getSize());

                                    jsonArray.put(jsonObject1);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                            try {
                                jsonObject.put("value", jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            http.schedule(jsonObject).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });

                        }
                    });
                    dialog.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
            }
        });
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);


        final Button button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView2 = findViewById(R.id.recycl2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView3 = findViewById(R.id.total_menu);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        db.beginTransaction();
        Cursor c = db.rawQuery("select * from food ", null);
        while (c.moveToNext()) {

            autolist.add(c.getString(0));
            blankremove.add(c.getString(0).replace(" ", "").toLowerCase());


        }
        db.setTransactionSuccessful();
        db.endTransaction();


        Log.d("리스트", autolist.size() + "");
        food_list_adapter = new Food_List_Adapter(getApplicationContext(), searchlist);
        Log.d("리스트", food_list_adapter.getItemCount() + "");
        food_select_adapter = new Food_Select_Adapter(getApplicationContext(), food_infos,ccal,select);
        food_select_adapter.CClick(new Food_Select_Adapter.itemClick() {
            @Override
            public void click(View v, int position) {
                Toast.makeText(getApplicationContext(), food_infos.get(position).getFood_info().getName() + "수정하기", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Food_Info_Activity.class);
                intent.putExtra("food_name", food_infos.get(position).getFood_info().getName());
                intent.putExtra("size", food_infos.get(position));
                startActivityForResult(intent, position);
            }

            @Override
            public void Dclick(View v, int position) {
                Toast.makeText(getApplicationContext(), food_infos.get(position).getFood_info().getName() + "삭제", Toast.LENGTH_SHORT).show();
                food_infos.remove(position);
                food_select_adapter.notifyDataSetChanged();
                init();
            }
        });
        recyclerView2.setAdapter(food_select_adapter);


        recyclerView.setAdapter(food_list_adapter);


        food_list_adapter.SetItemClick_j(new Food_List_Adapter.ItemClickeListener() {
            @Override
            public void onClick(View v, int position) {
//                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Food_input.class);
                intent.putExtra("value", searchlist.get(position));
                startActivityForResult(intent, 100);
                searchlist.clear();
                search.setText("");
                food_list_adapter.notifyDataSetChanged();

            }
        });

        search = findViewById(R.id.edit1);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return true;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("텍스트1", s + "");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("텍스트2", s + "");
                searchlist.clear();


                if (s.toString().replace(" ", "").length() != 0) {
                    Food_List_Adapter.textcolor = s.toString().replace(" ", "").toLowerCase();
                    for (int i = 0; i < blankremove.size(); i++) {
//                    for (int i = 0; i < page; i++) {
                        if (blankremove.get(i).contains(s.toString().replace(" ", "").toLowerCase())) {
                            searchlist.add(autolist.get(i));

                        }

                    }
                }

                food_list_adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("텍스트3", s + "");


            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("리스트", autolist.size() + "");
        Log.d("리스트", food_list_adapter.getItemCount() + "");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("안나옴",""+requestCode+"    "+resultCode);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Food_info2 food_info = (Food_info2) data.getSerializableExtra("이름");
                Log.d("페이즈", food_info.getFood_info().getName());
                Log.d("페이지", food_info.getFood_info().getName());
                Log.d("페이지", food_info.getFood_info().getName());

                food_infos.add(food_info);
                food_select_adapter.notifyDataSetChanged();
                init();
            }
        } else {
            if (resultCode == Activity.RESULT_OK) {
                Food_info2 food_info = (Food_info2) data.getSerializableExtra("이름");
//                Log.d("페이즈",food_info.getFood_info().getName());
//                Log.d("페이지",food_info.getFood_info().getName());
//                Log.d("페이지",food_info.getFood_info().getName());
                food_infos.set(requestCode, food_info);
                food_select_adapter.notifyDataSetChanged();
                init();
            }
        }
    }


    public void init() {
        recommed_menu = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        my_cal = 0;
        my_carb = 0;
        my_protein = 0;
        my_fat = 0;
        for (int i = 0; i < food_infos.size(); i++) {
            int num = food_infos.get(i).getNum();
            my_cal = my_cal + Math.round((num * food_infos.get(i).getFood_info().getCal()) * 100) / 100.0;
            my_carb = my_carb + Math.round((num * food_infos.get(i).getFood_info().getCarb()) * 100) / 100.0;
            my_protein = my_protein + Math.round((num * food_infos.get(i).getFood_info().getProtein()) * 100) / 100.0;
            my_fat = my_fat + Math.round((num * food_infos.get(i).getFood_info().getFat()) * 100) / 100.0;

        }
//        Log.d("서버통신", String.valueOf(my_cal));
//        Log.d("서버통신", String.valueOf(my_carb));
//        Log.d("서버통신", String.valueOf(my_protein));
//        Log.d("서버통신", String.valueOf(my_fat));
//
        hashMap.put("cal", String.valueOf(my_cal));
        hashMap.put("carb", String.valueOf(my_carb));
        hashMap.put("protein", String.valueOf(my_protein));
        hashMap.put("fat", String.valueOf(my_fat));
        hashMap.put("category",String.valueOf(category));
        hashMap.put("page", String.valueOf(recommed_menu.size()));
        hashMap.put("phone", phone);
        hashMap.put("day", day);
        hashMap.put("time", value);
        hashMap.put("select", select);
        hashMap.put("recommend", String.valueOf(recommend));
        http.ex(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    Log.d("포문", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (String.valueOf(jsonObject.getInt("total_size")).equals("")) {
                            total_size = 0;

                        } else {
                            total_size = jsonObject.getInt("total_size");

                        }

                        cal = jsonObject.getDouble("cal");
                        carb = jsonObject.getDouble("carb");
                        protein = jsonObject.getDouble("protein");
                        fat = jsonObject.getDouble("fat");

                        progressBar_cal.setProgress((float) (my_cal / cal * 100));
                        progressBar_carb.setProgress((float) (my_carb / carb * 100));
                        progressBar_pro.setProgress((float) (my_protein / protein * 100));
                        progressBar_fat.setProgress((float) (my_fat / fat * 100));
                        textview_carb.setText(Math.round((float) (my_carb / carb * 100)*100)/100+"%");
                        textview_pro.setText(Math.round((float) (my_protein / protein * 100)*100)/100+"%");
                        textview_fat.setText(Math.round((float) (my_fat / fat * 100)*100)/100+"%");
                        textview_cal.setText(Math.round((float) (my_cal / cal * 100)*100)/100+"%");
                        JSONArray jsonArray = (jsonObject.getJSONArray("item"));
                        Log.d("포문", jsonArray.toString());
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Food_info info = (gson.fromJson(jsonArray.get(i).toString(), Food_info.class));
                            recommed_menu.add(new Food_info2(1, jsonArray.getJSONObject(i).getDouble("g"), info));
                        }
//                        for (int i = 0; i < food_infos.size(); i++) {
//                            Log.d("포문", food_infos.get(i).getName());
//                        }
//                        Toast.makeText(getApplicationContext(), "asd", Toast.LENGTH_SHORT).show();
                        recommend_total_adapter = new Recommend_Total_Adapter(recommed_menu, cal, carb, protein, fat, total_size);
                        recommend_total_adapter.setItemClick(new Recommend_Total_Adapter.ItemClick() {
                            @Override
                            public void onClick(View v, int i) {

                                food_infos.add(recommed_menu.get(i));
                                food_select_adapter.notifyDataSetChanged();
                                init();


                            }

                            @Override
                            public void addClick(View v, int i) {

                                additem();
                            }
                        });

//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//                        total_menu.setLayoutManager(linearLayoutManager);
                        recyclerView3.setAdapter(recommend_total_adapter);


                    } catch (JSONException e) {
                        Log.d("포문", String.valueOf(e));

                    }


                } catch (IOException e) {
                    Log.d("포문", String.valueOf(e));

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
Log.d("포문", String.valueOf(t));
            }
        });
    }


    //더보기를 눌렀을 경우 additem 메소스

    public void additem() {
        HashMap<String, String> hashMap = new HashMap<>();
        double my_cal = 0;
        double my_carb = 0;
        double my_protein = 0;
        double my_fat = 0;
        for (int i = 0; i < food_infos.size(); i++) {
            int num = food_infos.get(i).getNum();
            my_cal = my_cal + Math.round((num * food_infos.get(i).getFood_info().getCal()) * 100) / 100.0;
            my_carb = my_carb + Math.round((num * food_infos.get(i).getFood_info().getCarb()) * 100) / 100.0;
            my_protein = my_protein + Math.round((num * food_infos.get(i).getFood_info().getProtein()) * 100) / 100.0;
            my_fat = my_fat + Math.round((num * food_infos.get(i).getFood_info().getFat()) * 100) / 100.0;

        }
//        Log.d("서버통신", String.valueOf(my_cal));
//        Log.d("서버통신", String.valueOf(my_carb));
//        Log.d("서버통신", String.valueOf(my_protein));
//        Log.d("서버통신", String.valueOf(my_fat));
//
        hashMap.put("cal", String.valueOf(my_cal));
        hashMap.put("carb", String.valueOf(my_carb));
        hashMap.put("protein", String.valueOf(my_protein));
        hashMap.put("fat", String.valueOf(my_fat));
        hashMap.put("page", String.valueOf(recommed_menu.size()));
        hashMap.put("phone", phone);
        hashMap.put("day", day);
        hashMap.put("time", value);
        hashMap.put("select", select);
        hashMap.put("category",String.valueOf(category));
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
                        Food_info info = (gson.fromJson(jsonArray.get(i).toString(), Food_info.class));
                        recommed_menu.add(new Food_info2(1, jsonArray.getJSONObject(i).getDouble("g"), info));
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
