package com.example.pc.ing1_.Menu.Friend.multi_chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.Menu.Friend.Chat_Activity;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Friend_invite_activity extends Activity {
    RetrofitExService http;
    int my;
    ArrayList<User> users;
    RecyclerView recyclerView;
    Button save;
    Friend_invite_Adapter friend_adapter;
    TextView none;
    ArrayList<User> inusers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_invite_activity);
//
//        int width = (int) (display.getWidth() * 0.7); //Display 사이즈의 70%
//
//        int height = (int) (display.getHeight() * 0.6);  //Display 사이즈의 90%
//
//        getWindow().getAttributes().width = width;
//
//        getWindow().getAttributes().height = height;


        Intent intent=getIntent();
        my=intent.getIntExtra("no",0);
        inusers= (ArrayList<User>) intent.getSerializableExtra("users");
        recyclerView=findViewById(R.id.recyclerView);
        none=findViewById(R.id.none);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        users=new ArrayList<>();
        friend_adapter=new Friend_invite_Adapter(getApplicationContext(),users,inusers);
        recyclerView.setAdapter(friend_adapter);
        save=findViewById(R.id.save);
        if(inusers==null){
            Log.d("inuser","널");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //친구가 없다면 종료
                if(users.size()==0){
                    finish();
                }
                //있다면 처리
                else{
                    ArrayList<User> u=friend_adapter.checkuser();
                    if(u.size()==0){
                        Toast.makeText(getApplicationContext(),"초대할 사용자를 선택해주세요",Toast.LENGTH_SHORT).show();

                    }else if ( u.size()==1){
                        Toast.makeText(getApplicationContext(),"선택된 사용자 한명",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),Chat_Activity.class);
                        intent.putExtra("user_no",u.get(0).getNo()+"");
                        startActivity(intent);
                        finish();

                    }else{
//                        for(int i=0;i<u.size();i++){
//                            Log.d("사용자",u.get(i).getName());
//
//                        }
                        Intent intent=new Intent(getApplicationContext(),Multi_Chat_Activity.class);
                        Collections.sort(u, new Comparator<User>() {
                            @Override
                            public int compare(User o1, User o2) {
                                return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                            }
                        });
                        intent.putExtra("users",u);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });

        }else{
            Log.d("inuser","널아님");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(users.size()==0){
                        finish();
                    }
                    //있다면 처리
                    else{
                        ArrayList<User> u=friend_adapter.checkuser();
                        if(u.size()==0){
                            Toast.makeText(getApplicationContext(),"초대할 사용자를 선택해주세요",Toast.LENGTH_SHORT).show();

                        }
//                        else if ( u.size()==1){
//                            Toast.makeText(getApplicationContext(),"선택된 사용자 한명",Toast.LENGTH_SHORT).show();
//                            Intent intent=new Intent();
//                            intent.putExtra("user",u);
//                            setResult(RESULT_OK,intent);
////                            finish();
//
//                        }
                        else{
//                        for(int i=0;i<u.size();i++){
//                            Log.d("사용자",u.get(i).getName());
//
//                        }
                            Intent intent=new Intent();
                            Collections.sort(u, new Comparator<User>() {
                                @Override
                                public int compare(User o1, User o2) {
                                    return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                                }
                            });
                            intent.putExtra("users",u);
                            setResult(RESULT_OK,intent);
                            finish();
                        }

                    }
                }
            });
        }


        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        http.firend_list(my+"").enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                ArrayList<User> user=response.body();
                for(int i=0;i<user.size();i++){
                    users.add(user.get(i));
                }

                //친구가 없다면
                if(users.size()==0){
                    recyclerView.setVisibility(View.GONE);
                    none.setVisibility(View.VISIBLE);
                }
                //친구가 있다면
                else{
                    friend_adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }
}
