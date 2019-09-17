package com.example.pc.ing1_.Menu.Friend;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.User;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Friend_search_activity extends Activity {

    TextView title,name,fail,check_msg;
    EditText search;
    Button button,save;
    RetrofitExService http;
    ImageView img;
    SharedPreferences sf;
    User user;
    ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_search_activity);
        img=findViewById(R.id.image);
        save=findViewById(R.id.save);
        button=findViewById(R.id.button);
        name=findViewById(R.id.name);
        fail=findViewById(R.id.fail);
        title=findViewById(R.id.title);
        search=findViewById(R.id.search);
        check_msg=findViewById(R.id.check);
        sf = getSharedPreferences("login",MODE_PRIVATE);
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            img.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img.setClipToOutline(true);
        }
        Intent intent =getIntent();
        final String select =intent.getStringExtra("select");
        users= (ArrayList<User>) intent.getSerializableExtra("users");
        if(select.equals("id")){
            title.setText("ID로 친구찾기");

        }else{
            title.setText("연락처로 친구찾기");
            search.setInputType(InputType.TYPE_CLASS_NUMBER);

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap <String ,String > key=new HashMap<>();
                key.put("select",select);
                key.put("value",search.getText().toString());
                http.friend_search(key).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                         user = response.body();
                        if (user==null) {
                            Log.d("우저", "없");
                            fail.setVisibility(View.VISIBLE);
                            img.setVisibility(View.GONE);
                            save.setVisibility(View.GONE);
                            name.setVisibility(View.GONE);
                            check_msg.setVisibility(View.GONE);
                            fail.setText("없음");

                        } else {

                            if(user.getNo()==Integer.parseInt(sf.getString("no","-1"))){
                                fail.setVisibility(View.VISIBLE);
                                img.setVisibility(View.GONE);
                                save.setVisibility(View.GONE);
                                name.setVisibility(View.GONE);
                                fail.setText("본인은 친구로 등록 불가함");
                            }else{
                                int check=0;
                                if(users!=null) {
                                    for (int i = 0; i < users.size(); i++) {
                                        if (users.get(i).getNo() == user.getNo()) {
                                            check = 1;
                                        }
                                    }
                                }
                                Log.d("우저", user.toString());
                                if(check==0){

                                    save.setVisibility(View.VISIBLE);
                                    check_msg.setVisibility(View.GONE);
                                }else{
                                    check_msg.setVisibility(View.VISIBLE);
                                    check_msg.setText("이미 등록된 친구");
                                    save.setVisibility(View.GONE);

                                }
                                img.setVisibility(View.VISIBLE);
                                name.setVisibility(View.VISIBLE);
                                fail.setVisibility(View.GONE);
                                if(user.getProfile().equals("")){
                                    img.setImageResource(R.drawable.ic_user_1);
                                }else {
                                    Glide.with(getApplicationContext()).load("http://54.180.168.210/profile/"+user.getProfile()).thumbnail(0.3f).centerCrop().into(img);
                                }
                                name.setText(user.getName());
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap <String,String> hashMap=new HashMap<>();
                hashMap.put("my",sf.getString("no","-1"));
                hashMap.put("other",user.getNo()+"");
                http.firend_add(hashMap).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent intent =new Intent();
                        intent.putExtra("user",user);
                        setResult(RESULT_OK,intent);
                        finish();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
//                        Intent intent =new Intent();
//                        intent.putExtra("user",user);
//                        setResult(RESULT_OK,intent);
//                finish();

            }
        });


    }
}
