package com.example.pc.ing1_.Menu.Main;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Store_info_Activity extends Activity {
    private static final String TAG = "Store_info_Activity";
    Store store;
    RetrofitExService http;
    ArrayList<Review_Item> review_items;
    ArrayList<Menu>menu_item ;
    ImageView store_img;
    TextView store_name,realtime,menu,menu_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_info_activity);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        int width = (int) (display.getWidth() * 0.9); //Display 사이즈의 70%

        int height = (int) (display.getHeight() * 0.9);  //Display 사이즈의 90%

        getWindow().getAttributes().width = width;

        getWindow().getAttributes().height = height;


        Intent intent = getIntent();
        review_items=new ArrayList<>();
        menu_item=new ArrayList<>();
        store = (Store) intent.getSerializableExtra("store");
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);

        store_img=findViewById(R.id.store_img);
        store_name=findViewById(R.id.store_name);
        menu=findViewById(R.id.menu);
        menu_add=findViewById(R.id.menu_add);
        store_name.setText(store.getName());
        realtime=findViewById(R.id.realtime);
        Glide.with(getApplicationContext()).load(store.getStore_img()).centerCrop().into(store_img);

        if(store.getStore_img().equals("")){
            ConstraintLayout.LayoutParams layoutParams=(ConstraintLayout.LayoutParams) store_name.getLayoutParams();
            layoutParams.topMargin=-100;
            store_name.setLayoutParams(layoutParams);
        }

        realtime.setText("여는날 : "+store.getOpen_day()+" 닫는날 : "+store.getClose_day()+" 여는 시간 "+store.getTime());


        http.store_info(store.getNo()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object=response.body();

                JsonArray array=object.getAsJsonArray("rating");
                JsonArray array1=object.getAsJsonArray("menu");
                for(int i=0;i<array1.size();i++){
                    JsonObject jsonObject=array1.get(i).getAsJsonObject();
                    Gson gson= new Gson();
                    menu_item.add(gson.fromJson(jsonObject,Menu.class));
                }
                for(int i=0;i<array.size();i++){
                    JsonObject jsonObject=array.get(i).getAsJsonObject();
                    Gson gson=new Gson();
                    Review_Item review_item =gson.fromJson(jsonObject,Review_Item.class);
                    review_items.add(review_item);
                }
                for(int i=0;i<review_items.size();i++){
                    Log.d(TAG, "onResponse: "+review_items.get(i).getContent());
                }
                for(int i=0;i<menu_item.size();i++){
                    Log.d(TAG, "onResponse: "+menu_item.get(i).getItem());
                    if(i<=4) {
                        menu.append(menu_item.get(i).getItem() + " " + menu_item.get(i).getPrice() + "\n");
                    }
                }
                if(menu_item.size()>5){
                    menu_add.setVisibility(View.VISIBLE);
                    menu_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(menu_add.getText().toString().equals("더보기")) {
                                for (int i = 5; i < menu_item.size(); i++) {
                                    menu.append(menu_item.get(i).getItem() + " " + menu_item.get(i).getPrice() + "\n");
                                }
                                menu_add.setText("접기");
                            }
                            else{
                                menu.setText("");
                                for(int i=0;i<=4;i++){
                                    menu.append(menu_item.get(i).getItem() + " " + menu_item.get(i).getPrice() + "\n");
                                }
                                menu_add.setText("더보기");
                            }
                        }
                    });

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });



    }
}

class Menu {
    String item,price;

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
