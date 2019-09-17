package com.example.pc.ing1_.Menu.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pc.ing1_.Menu.Friend.Socket_Service;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Shared_Lisat_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    RetrofitExService http;
    int my_no,store_no;
    ArrayList<Shared_item_class> shared_item_classes;
    Shard_Adpater shard_adpater;
    SharedPreferences sf;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared__lisat_activity);
        button=findViewById(R.id.shared);
        sf = getSharedPreferences("login",MODE_PRIVATE);
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        recyclerView=findViewById(R.id.recyclerView);
        shared_item_classes=new ArrayList<>();
        shard_adpater=new Shard_Adpater(shared_item_classes,getApplicationContext(),sf.getString("nick",""),sf.getString("profile",""));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(shard_adpater);
        Intent intent=getIntent();
        my_no= Integer.parseInt(intent.getStringExtra("user_no"));
        store_no= Integer.parseInt(intent.getStringExtra("store_no"));
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("no",my_no+"");
        hashMap.put("store_no",store_no+"");
        http.shared(hashMap).enqueue(new Callback<ArrayList<Shared_item_class>>() {
            @Override
            public void onResponse(Call<ArrayList<Shared_item_class>> call, Response<ArrayList<Shared_item_class>> response) {
                ArrayList<Shared_item_class> shared_item_classsss=response.body();
                for(int i=0;i<shared_item_classsss.size();i++){
                    shared_item_classes.add(shared_item_classsss.get(i));
                }
                shard_adpater.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Shared_item_class>> call, Throwable t) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=shard_adpater.SCheck();
                if(num==-1){
                    Toast.makeText(getApplicationContext(),"선택",Toast.LENGTH_SHORT).show();
                }else{
                   if(shared_item_classes.get(num).isSingle()==true){
                       //1:!대화
                       JSONObject jsonObject=new JSONObject();
                       try {
                           jsonObject.put("single",true);
                           jsonObject.put("from",my_no+"");
                           jsonObject.put("to",shared_item_classes.get(num).getRoom_no());
                           jsonObject.put("user_image",shared_item_classes.get(num).getImage());
                           jsonObject.put("user_name",shared_item_classes.get(num).getTitle());
                           jsonObject.put("message","음식점 공유");
                           jsonObject.put("image",store_no+"");
                           jsonObject.put("sys_message","");
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       Thread thread=new Thread(new Runnable() {
                           @Override
                           public void run() {
                               Socket_Service.out.println(jsonObject);
                           }
                       });
                       thread.start();
                   }else{
                       //멀티채팅
                       JSONObject jsonObject=new JSONObject();
                       try {

                               jsonObject.put("single",false);
                           jsonObject.put("image",store_no+"");
                           jsonObject.put("from_nick",sf.getString("nick",""));
                           jsonObject.put("names",shared_item_classes.get(num).getTitle());
                           jsonObject.put("profile",shared_item_classes.get(num).getImage());
                           jsonObject.put("room_no",shared_item_classes.get(num).getRoom_no());
                           jsonObject.put("from",my_no+"");
                           jsonObject.put("from_profile",sf.getString("profile",""));
                           jsonObject.put("message","음식점 공유");
                           jsonObject.put("users",shared_item_classes.get(num).getNames());
                           jsonObject.put("sys_message","");

                       }catch (JSONException e){

                       }
                       Thread thread=new Thread(new Runnable() {
                           @Override
                           public void run() {
                               Socket_Service.out.println(jsonObject);
                           }
                       });
                       thread.start();
                   }
                   finish();
                }
            }
        });

    }

}
