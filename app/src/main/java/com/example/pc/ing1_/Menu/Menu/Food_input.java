package com.example.pc.ing1_.Menu.Menu;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pc.ing1_.Login.Hash;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;

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

public class Food_input extends AppCompatActivity {
    public static Food_input food_input;
    RetrofitExService http;
    String value;
    RecyclerView recyclerView;
    List<Food_list_item> food_list_items;
    Food_input_Adapter food_input_adapter;
    static int max;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_input);
        food_input=Food_input.this;
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);

        Intent intent=getIntent();
        value=intent.getStringExtra("value");
        Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();

        recyclerView=findViewById(R.id.recycle1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        food_list_items=new ArrayList<>();
        Log.d("물병","food_math"+value);
        http.food_match_max(value).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    max= Integer.parseInt(response.body().string().toString());
                    Toast.makeText(getApplicationContext(),max+"",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                food_match(food_list_items.size());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



//        food_input_adapter=new Food_input_Adapter(getApplicationContext(),food_list_items);
//        recyclerView.setAdapter(food_input_adapter);


    }

    public void food_match(final int page) {
        int size = value.replace(" ", "").length();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("value", value);
        hashMap.put("size", "" + size);
        hashMap.put("page", "" + page);
        http.food_match(hashMap).enqueue(new Callback<List<Food_list_item>>() {
            @Override
            public void onResponse(Call<List<Food_list_item>> call, Response<List<Food_list_item>> response) {
                List<Food_list_item> food_list_items1=response.body();
//                Log.d("물병","food_math"+food_list_items1.get(0).getName());
                for(int i=0;i<food_list_items1.size();i++){
                    Food_list_item food_list_item= food_list_items1.get(i);
                    food_list_items.add(food_list_item);
                }
                if(page==0) {
                    food_input_adapter = new Food_input_Adapter(getApplicationContext(), food_list_items);
                    recyclerView.setAdapter(food_input_adapter);
                    food_input_adapter.setItemClicke(new Food_input_Adapter.Food_List_Click() {
                        @Override
                        public void Next_click(View v, int position) {
//                            food_match(food_list_items.size());
                            Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                            food_match(food_list_items.size());
                        }

                        @Override
                        public void item_click(View v, int position) {
                            Intent intent=new Intent(getApplicationContext(),Food_Info_Activity.class);
                            intent.putExtra("food_name",food_list_items.get(position).getName());

//                            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            startActivityForResult(intent,200);

                        }
                    });

                }else{
                    food_input_adapter.notifyDataSetChanged();
                }
//                food_input_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Food_list_item>> call, Throwable t) {
                Log.d("qweqwe", String.valueOf(t));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if(resultCode==RESULT_OK){
           if(requestCode==200) {
               Intent intent = new Intent();
               intent.putExtra("이름", data.getSerializableExtra("이름"));
               setResult(RESULT_OK,intent);
               finish();
           }
       }
    }
}
