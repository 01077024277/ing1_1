package com.example.pc.ing1_.Menu.Main;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.Review_Item;
import com.example.pc.ing1_.Store;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Store_info_Activity extends AppCompatActivity {
    private static final String TAG = "Store_info_Activity";
    Store store;
    RetrofitExService http;
    ArrayList<Review_Item> review_items;
    ArrayList<Menu> menu_item;
    ImageView store_img,total1,total2,total3;
    TextView store_name, realtime, menu, menu_add;
    HashMap<String, String> open;
    HashMap<String, String> close;
    PopupWindow popupWindow;
    ConstraintLayout call,friend,road,info,img_total_view;
    TextView review_point;
    RatingBar review_rating;
    EditText review;
    ArrayList <String> img_total,img_review;
    RecyclerView review_recycle;
    Reivew_Adapter reivew_adapter;
    double user_lat,user_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_info_activity);
//        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//
//        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 70%
//
//        int height = (int) (display.getHeight() * 0.9);  //Display 사이즈의 90%
//
//        getWindow().getAttributes().width = width;
//
//        getWindow().getAttributes().height = height;

        review_recycle=findViewById(R.id.review_recycle);
        review_recycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));








        Intent intent = getIntent();
        user_lat=intent.getDoubleExtra("lat",0);
        user_lon=intent.getDoubleExtra("lon",0);
        review_items = new ArrayList<>();
        menu_item = new ArrayList<>();
        store = (Store) intent.getSerializableExtra("store");
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);

        store_img = findViewById(R.id.store_img);
        store_name = findViewById(R.id.store_name);
        menu = findViewById(R.id.menu);
        menu_add = findViewById(R.id.menu_add);
        store_name.setText(store.getName());
        ImageView help = findViewById(R.id.help);
        ImageView call_img=findViewById(R.id.call_img);
        review=findViewById(R.id.review);
        review_rating=findViewById(R.id.review_rating);
        review_point=findViewById(R.id.rating_point);
        img_total_view=findViewById(R.id.const1);
        total1=findViewById(R.id.total_img1);
        total2=findViewById(R.id.total_img2);
        total3=findViewById(R.id.total_img3);




        http.store_info(store.getNo()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                Log.d("리뷰아이템",object.toString());
                JsonArray array = object.getAsJsonArray("rating");
                JsonArray array1 = object.getAsJsonArray("menu");
                for (int i = 0; i < array1.size(); i++) {
                    JsonObject jsonObject = array1.get(i).getAsJsonObject();
                    Gson gson = new Gson();
                    menu_item.add(gson.fromJson(jsonObject, Menu.class));
                }
                for (int i = 0; i < array.size(); i++) {
                    JsonObject jsonObject = array.get(i).getAsJsonObject();
                    Gson gson = new Gson();
                    Review_Item review_item = gson.fromJson(jsonObject, Review_Item.class);
                    review_items.add(review_item);
                }


                reivew_adapter=new Reivew_Adapter(getApplicationContext(),review_items);
                reivew_adapter.setOnclikListener(new Reivew_Adapter.ItemClickeListener() {
                    @Override
                    public void onItemclicke(View v, int position) {
                        Intent intent =new Intent(getApplicationContext(),Image_Total_Activity.class);
                        ArrayList <String> re =new ArrayList<>();
                        for (int i=0 ;i<review_items.get(position).getImg().size();i++){
                            re.add(review_items.get(position).getImg().get(i));
                        }
//                        ArrayList<String >co = new ArrayList<>();

                        intent.putExtra("img",re);
//                        intent.putExtra("content",img_review);
                        intent.putExtra("num",0);
                        startActivity(intent);
                    }
                });
                review_recycle.setAdapter(reivew_adapter);




                for (int i = 0; i < review_items.size(); i++) {
//                    Log.d(TAG, "onResponse: " + review_items.get(i).getContent() + "   " + review_items.get(i).getImg().get(0).toString());
                }
                for (int i = 0; i < menu_item.size(); i++) {
                    Log.d(TAG, "onResponse: " + menu_item.get(i).getItem());
                    if (i <= 4) {
                        menu.append(menu_item.get(i).getItem() + " " + menu_item.get(i).getPrice() + "\n");
                    }
                }
                if (menu_item.size() > 5) {
                    menu_add.setVisibility(View.VISIBLE);
                    menu_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (menu_add.getText().toString().equals("더보기")) {
                                for (int i = 5; i < menu_item.size(); i++) {
                                    menu.append(menu_item.get(i).getItem() + " " + menu_item.get(i).getPrice() + "\n");
                                }
                                menu_add.setText("접기");
                            } else {
                                menu.setText("");
                                for (int i = 0; i <= 4; i++) {
                                    menu.append(menu_item.get(i).getItem() + " " + menu_item.get(i).getPrice() + "\n");
                                }
                                menu_add.setText("더보기");
                            }
                        }
                    });

                }
                img_total=new ArrayList<>();
                img_review=new ArrayList<>();
                for(int i=0;i<review_items.size();i++){
                    for(int j=0;j<review_items.get(i).getImg().size();j++){
                        if(review_items.get(i).getImg().get(j).equals("")){

                        }
                        else{
                            img_total.add(review_items.get(i).getImg().get(j));
                            img_review.add(review_items.get(i).getContent());
                        }
                    }
                }
                if(img_total.size()==0){
                    img_total_view.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getApplicationContext(),""+img_total.size(),Toast.LENGTH_SHORT).show();
                }
//                Collections.reverse(img_total);
//                Collections.reverse(img_review);

                    for(int i=0;i<img_total.size();i++){
                        if(i==0){
                            Glide.with(getApplicationContext()).load(img_total.get(i)).centerCrop().thumbnail(0.1f).into(total1);
                            total1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(),""+total1.getResources(),Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(getApplicationContext(),Image_Total_Activity.class);
                                    intent.putExtra("img",img_total);
                                    intent.putExtra("content",img_review);
                                    intent.putExtra("num",0);
                                    startActivity(intent);
                                }
                            });
                        }if (i==1){
                            Glide.with(getApplicationContext()).load(img_total.get(i)).centerCrop().thumbnail(0.1f).into(total2);
                            total2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(),""+total2.getResources(),Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(getApplicationContext(),Image_Total_Activity.class);
                                    intent.putExtra("img",img_total);
                                    intent.putExtra("content",img_review);
                                    intent.putExtra("num",1);
                                    startActivity(intent);

                                }
                            });

                        }if(i==2){
                            Glide.with(getApplicationContext()).load(img_total.get(i)).centerCrop().thumbnail(0.1f).into(total3);
                            total3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(),""+total3.getResources(),Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(getApplicationContext(),Image_Total_Activity.class);
                                    intent.putExtra("img",img_total);
                                    intent.putExtra("content",img_review);
                                    intent.putExtra("num",2);
                                    startActivity(intent);

                                }
                            });

                        }

                    }
                    if(img_total.size()==1){
                    total2.setVisibility(View.GONE);
                    total3.setVisibility(View.GONE);
                    }else if (img_total.size()==2){
                    total3.setVisibility(View.GONE);

                    }
                    if(img_total.size()>3){
                    TextView img_front = findViewById(R.id.img_front);
                    img_front.setVisibility(View.VISIBLE);
                    img_front.setText("+"+(img_total.size()-3));
                    img_front.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(),"더보기",Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(getApplicationContext(),Image_Total_Activity.class);
                            intent.putExtra("img",img_total);
                            intent.putExtra("content",img_review);
                            intent.putExtra("num",2);
                            startActivity(intent);

                        }
                    });
                    }



            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("음식점",String.valueOf(t));
            }
        });





        review_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                review_point.setText(String.valueOf(rating).substring(0,1)+"점");
            }
        });

        review.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SharedPreferences sf;
                sf=getSharedPreferences("login",MODE_PRIVATE);
                if (sf.getString("id", "").equals("")) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {

                        Toast.makeText(getApplicationContext(), "로그인을 해주세요", Toast.LENGTH_SHORT).show();
                        review.setText("");
                        review.clearFocus();
                        return true;
                    }
                    else{
                    return false;
                }
                }else{

                    return false;
                }
            }
        });
//        review.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (review.hasFocus()) {
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
//                    switch (event.getAction() & MotionEvent.ACTION_MASK){
//                        case MotionEvent.ACTION_SCROLL:
//                            v.getParent().requestDisallowInterceptTouchEvent(false);
//                            return true;
//                    }
//                }
//                return false;
//            }
//        });


        if(store.getTel().equals("")){
            call_img.setImageResource(R.drawable.phone_callx);
        }else{
            call_img.setImageResource(R.drawable.phone_call);

        }

        call=findViewById(R.id.call);
        friend=findViewById(R.id.friend);

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sf;
                sf=getSharedPreferences("login",MODE_PRIVATE);
                Intent intent1=new Intent(getApplicationContext(),Shared_Lisat_Activity.class);
                intent1.putExtra("user_no",sf.getString("no",""));
                intent1.putExtra("store_no",store.getNo()+"");
                startActivity(intent1);
            }
        });


        road=findViewById(R.id.road);
        info=findViewById(R.id.infoadd);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),Question_activity.class);
                intent.putExtra("store_id",store.getNo()+"");
                intent.putExtra("store_name",store.getName()+"");
                startActivity(intent);
            }
        });


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(store.getTel().equals("")){
                    Toast.makeText(getApplicationContext(),"등록된 전화번호가 없습니다",Toast.LENGTH_SHORT).show();
                }else{

                    String tel = "tel:"+store.getTel();
                    Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse(tel));
                    startActivity(intent);
                }
            }
        });


        //길찾기를 눌었을떄 처리

        road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),Directions_Activity.class);
                intent1.putExtra("lat",user_lat);
                intent1.putExtra("lon",user_lon);
                intent1.putExtra("store",store);
                startActivity(intent1);

            }
        });




        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow=new PopupWindow(v);
                LayoutInflater inflater= (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view= inflater.inflate(R.layout.open_pop,null);
                TextView textView=view.findViewById(R.id.pop);
                if(store.getOpen_day().equals("")){
                    textView.setText("영업시간\n정보 없음\n");
                }else {
                    for (int i = 0; i < store.getOpen_day().split("\\.").length; i++) {
                        if (i == 0) {
                            textView.setText("영업시간\n");
                            textView.append(store.getOpen_day().split("\\.")[i]);
                            textView.append("   " + store.getTime().split("\\.")[i] + "\n");

                        } else {
                            textView.append(store.getOpen_day().split("\\.")[i]);
                            textView.append("   " + store.getTime().split("\\.")[i] + "\n");

                        }

                    }
                }
                if(store.getClose_day().equals("")){
                    if(store.getOpen_day().contains("매일")){
                        textView.append("휴무일\n없음");
                    }else{
                        textView.append("휴무일\n정보 없음");
                    }
                }else{
                    for(int i=0;i<store.getClose_day().split("\\.").length;i++){
                        if(i==0){
                            textView.append("휴무일\n");
                            textView.append(store.getClose_day().split("\\.")[i]+"\n");
                        }else {
                            textView.append(store.getClose_day().split("\\.")[i]+"\n");
                        }
                    }
                }
                popupWindow.setContentView(view);
                popupWindow.setWindowLayoutMode(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setOutsideTouchable(true);
                if(store.getStore_img().equals("")){
//                    popupWindow.showAtLocation(view,Gravity.,250,680);

                }else{
//                    popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,250,830);

                }
                popupWindow.showAsDropDown(v,-30,-70);
            }
        });

        realtime = findViewById(R.id.realtime);
        Glide.with(getApplicationContext()).load(store.getStore_img()).centerCrop().into(store_img);

        if (store.getStore_img().equals("")) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) store_name.getLayoutParams();
            layoutParams.topMargin = -100;
            store_name.setLayoutParams(layoutParams);
        }


        open = new HashMap<>();
        close = new HashMap<>();
        Today(store.getOpen_day(), store.getClose_day(), store.getTime());




    }

    public void Today(String o, String c, String t) {
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String week = String.valueOf(cal.get(Calendar.WEEK_OF_MONTH));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minite = cal.get(Calendar.MINUTE);
        String a = null;
        String b= null;
        if(String.valueOf(hour).length()==1){
            a="0"+String.valueOf(hour);
        }else{
            a=String.valueOf(hour);
        }
        if(String.valueOf(minite).length()==1){
            b="0"+String.valueOf(minite);
        }else{
            b=String.valueOf(minite);
        }
        String now = a+b;
//
        String[] op = o.split("\\.");
        String[] clo = c.split("\\.");
        String[] ti = t.split("\\.");

        if (!c.equals((""))) {
            for (int i = 0; i < clo.length; i++) {
                clo[i] = clo[i].replace("월요일", "2");
                clo[i] = clo[i].replace("화요일", "3");
                clo[i] = clo[i].replace("수요일", "4");
                clo[i] = clo[i].replace("목요일", "5");
                clo[i] = clo[i].replace("금요일", "6");
                clo[i] = clo[i].replace("토요일", "7");
                clo[i] = clo[i].replace("일요일", "1");
                clo[i] = clo[i].replace("월", "2");
                clo[i] = clo[i].replace("화", "3");
                clo[i] = clo[i].replace("수", "4");
                clo[i] = clo[i].replace("목", "5");
                clo[i] = clo[i].replace("금", "6");
                clo[i] = clo[i].replace("토", "7");
                clo[i] = clo[i].replace("일", "1");


                int fi = -1;
                for (int j = 0; j < clo[i].split(",").length; j++) {
                    Log.d("qweqweqwe", "j==" + j);
                    //,로 나눈 배열중 요일이 포함되어 있지 않을때 ex) 첫째,둘째,셋째 화요일
                    if (!clo[i].split(",")[j].contains("1") && !clo[i].split(",")[j].contains("2") && !clo[i].split(",")[j].contains("3") && !clo[i].split(",")[j].contains("4") && !clo[i].split(",")[j].contains("5") && !clo[i].split(",")[j].contains("6") && !clo[i].split(",")[j].contains("7")) {
                        if (j == 0) {
                            Log.d("qweqweqwe", "j==0");
                            fi = j;
                        } else {
                            if (clo[i].split(",")[j - 1].contains("1") || clo[i].split(",")[j - 1].contains("2") || clo[i].split(",")[j - 1].contains("3") || clo[i].split(",")[j - 1].contains("4") || clo[i].split(",")[j - 1].contains("5") || clo[i].split(",")[j - 1].contains("6") || clo[i].split(",")[j - 1].contains("7")) {
                                fi = j;
                                Log.d("qweqweqwe", "요일");
                            }

                            Log.d("qweqweqwe", "ㅂㅈㄷ" + clo[i].split(",")[j]);
                        }
                    } else {
                        Log.d("qweqweqwe", "ㅇㅇㅇㅇ");
                        if (fi != -1) {
                            //,로 나눈 배열중 요일이 포함되어 있지 않을때 ex) 첫째,둘째,셋째 화요일
                            for (int q = 1; q < 8; q++) {
                                if (clo[i].split(",")[j].contains(q + "")) {
                                    Log.d("qweqweqwe", q + "포함");
                                    if (close.get("" + q) == null) {
                                        close.put("" + q, clo[i].split(",")[j].replace("" + q, "").replace(" ", ""));
                                    }

                                    for (int w = fi; w < j; w++) {
                                        Log.d("qweqweqwe", "더하기" + w);
//                                        clo[i].split(",")[w] = clo[i].split(",")[w] + " " + q;
//                                        clo[i].split(",")[w]+="Qweqwe";
                                        close.put("" + q, close.get("" + q) + "," + clo[i].split(",")[w]);
                                        Log.d("qweqweqwe", "" + clo[i].split(",")[w]);
                                    }
                                }
                            }
                            fi = -1;
                        } else {
                            //요일만 적혀있을때
                            Log.d("qweqweqwe", "여기 ?");
//                            if (clo[i].split(",")[j].contains("째")) {
//                                for (int q = 1; q < 8; q++) {
//                                    if (clo[i].split(",")[j].contains(q + "")) {
//                                        if (close.get("" + q) == null) {
//
//                                            close.put("" + q, );
//                                        }
//                                    }
//                                }
//
//                            } else {
                                close.put("" + clo[i].split(",")[j], "0");
//                            }
                        }

                    }
                }


                for (int u = 1; u < 8; u++) {
                    try {
                        Log.d("qweqweqwe모음" + u, close.get("" + u));
                    } catch (NullPointerException e) {
                        Log.d("qweqweqwe에러", "" + u);
                    }
                }
//                Log.d("qweqweqwe모음", close.get("둘째 7"));


            }

        }
        int real = 0;
        String week__ = null;
        if(week.equals("1")){
          week__="첫";
        }
        if(week.equals("2")){
            week__="둘";
        }
        if(week.equals("3")){
            week__="셋";
        }
        if(week.equals("4")){
            week__="넷";
        }

        try {
            if (close.get("" + dayOfWeek).contains(week__) || close.get("" + dayOfWeek).contains("0")) {
                real = -1;
                Log.d("qweqweqwe","리얼-1");
            }
        } catch (NullPointerException e) {
            real = 0;
            Log.d("qweqweqwe","리얼0");
        }

        if (!o.equals("")) {
            for (int i = 0; i < op.length; i++) {
                op[i] = op[i].replace("매일", "1,2,3,4,5,6,7");
                op[i] = op[i].replace("일요일", "1");
                op[i] = op[i].replace("월요일", "2");
                op[i] = op[i].replace("화요일", "3");
                op[i] = op[i].replace("수요일", "4");
                op[i] = op[i].replace("목요일", "5");
                op[i] = op[i].replace("금요일", "6");
                op[i] = op[i].replace("토요일", "7");

                op[i] = op[i].replace("일", "1");
                op[i] = op[i].replace("월", "2");
                op[i] = op[i].replace("화", "3");
                op[i] = op[i].replace("수", "4");
                op[i] = op[i].replace("목", "5");
                op[i] = op[i].replace("금", "6");
                op[i] = op[i].replace("토", "7");

                Log.d("qweqwe", op[i]);
                int size = op[i].split(",").length;
                for (int j = 0; j < size; j++) {
                    if (op[i].split(",")[j].contains("~")) {
                        int start = Integer.parseInt(op[i].split(",")[j].split("~")[0]);
                        int end = Integer.parseInt(op[i].split(",")[j].split("~")[1]);
                        for (int k = start; k <= end; k++) {
                            if(ti[i].split(" ~ ")[1].charAt(0)=='0'){
                                open.put(""+k,ti[i].split(" ~ ")[0]+" ~ "+(Integer.parseInt(ti[i].split(" ~ ")[1].split(":")[0])+24)+":"+ti[i].split(" ~ ")[1].split(":")[1]);
                            }else{
                                open.put("" + k, ti[i]);
                            }
                        }

                    } else {
                        if (ti[i].split(" ~ ")[1].charAt(0) == '0') {
                            open.put(op[i].split(",")[j], ti[i].split(" ~ ")[0] + " ~ " + (Integer.parseInt(ti[i].split(" ~ ")[1].split(":")[0]) + 24) + ":" + ti[i].split(" ~ ")[1].split(":")[1]);
                        } else {
                            open.put(op[i].split(",")[j], ti[i]);
                        }
                    }
                }

            }
            for (int i = 0; i <= 7; i++) {
                try {
                    Log.d("영업" + i, open.get("" + i));
                } catch (NullPointerException e) {

                }
            }
            try {
                Log.d("영업시간", "" + Integer.parseInt(open.get(dayOfWeek + "").split(" ~ ")[0].replace(":", "")));
                Log.d("영업시간", now);
                Log.d("영업시간", "" + Integer.parseInt(open.get(dayOfWeek + "").split(" ~ ")[1].replace(":", "")));


                if (Integer.parseInt(open.get(dayOfWeek + "").split(" ~ ")[0].replace(":", "")) <= Integer.parseInt(now)
                        && Integer.parseInt(now) < Integer.parseInt(open.get(dayOfWeek + "").split(" ~ ")[1].replace(":", "")) && real == 0) {

                    realtime.setText("영업중");
                    realtime.setTextColor(Color.parseColor("#00ff00"));
                } else {
                    Log.d("qweqweqwe","영업아님");
                    realtime.setText("영업중 아님");
                    realtime.setTextColor(Color.parseColor("#ff0000"));
                }
            } catch (NullPointerException e) {
                Log.d("qweqweqwe","널");
                realtime.setText("영업중 아님");
                realtime.setTextColor(Color.parseColor("#ff0000"));
            }catch (NumberFormatException e) {
                Log.d("qweqweqwe","넘버");
                if (real == -1) {
                    realtime.setText("영업중 아님");
                    realtime.setTextColor(Color.parseColor("#ff0000"));
                } else {
                    realtime.setText("확인 불가");
                    realtime.setTextColor(Color.parseColor("#ff0000"));
                }
            }

        } else {
            if(real==-1){
                realtime.setText("영업중 아님");
                realtime.setTextColor(Color.parseColor("#ff0000"));
            }else {
                realtime.setText("확인 불가 ");
                realtime.setTextColor(Color.parseColor("#ff0000"));
            }
        }

    }
}

class Menu {
    String item, price;

    public Menu(String item, String price) {
        this.item = item;
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
