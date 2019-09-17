package com.example.pc.ing1_.Menu.Friend;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import androidx.recyclerview.widget.SimpleItemAnimator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Chat_Activity extends AppCompatActivity {

    EditText editText;
    TextView title;
    Button button;
    User user;
    RecyclerView recyclerView;
    Chat_Adapter chat_adapter;
    ArrayList<Message_model> message_models;
    Socket_Service.CallBack_ callBack_;
    Thread thread;
    Socket_Service socket_service;
    ServiceConnection connection;
    boolean single;
    SharedPreferences sf;
    int my,other_user,check;
    RetrofitExService http;
    Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        editText = findViewById(R.id.edit);
        title = findViewById(R.id.textView);
        button = findViewById(R.id.send);
        exit=findViewById(R.id.exit);
        final Intent intent=getIntent();
        single=intent.getBooleanExtra("single",true);
        recyclerView=findViewById(R.id.recyclerView);
        message_models=new ArrayList<>();
        check=0;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        other_user= Integer.parseInt(intent.getStringExtra("user_no"));
        sf = getSharedPreferences("login",MODE_PRIVATE);
        my= Integer.parseInt(sf.getString("no",""));
        Log.d("상대방1",other_user+"");

        http.user_class(other_user+"").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user=response.body();
                title.setText(user.getName()+"");
                Glide.with(getApplicationContext()).asBitmap().load("http://54.180.168.210/profile/"+user.getProfile()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        user.setBitmap(resource);
                        chat_adapter=new Chat_Adapter(getApplicationContext(),message_models,user);
//                        chat_adapter.setHasStableIds(true);
                        recyclerView.setAdapter(chat_adapter);
                        Log.d("상대방d",String.valueOf(user.getNo()));
                        HashMap hashMap=new HashMap();
                        hashMap.put("no",my+"");
                        hashMap.put("other",other_user+"");
                        http.chat_list(hashMap).enqueue(new Callback<ArrayList<Message_model>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Message_model>> call, Response<ArrayList<Message_model>> response) {
                                ArrayList<Message_model> message_model=response.body();
                                for(int i=0;i<message_model.size();i++){
                                    Log.d("상대방",message_model.get(i).getMessage());
                                    Log.d("상대방",message_model.get(i).getTime());
                                    Log.d("상대방",message_model.get(i).getNo()+"");
                                    message_models.add(message_model.get(i));

                                }

                                chat_adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onFailure(Call<ArrayList<Message_model>> call, Throwable t) {

                            }
                        });


                    }
                });


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("상대방d",String.valueOf(t));
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("my",my+"");
                hashMap.put("other",other_user+"");
                http.room_exit(hashMap).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent intent1=new Intent();
                        intent1.putExtra("result",other_user+"");
                        setResult(RESULT_OK,intent1);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().toString().equals("")) {
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("single", single);
                        jsonObject.put("from", my);
                        jsonObject.put("to", user.getNo());
                        String room_no = intent.getStringExtra("room_no");


                        if (room_no != null) {
                            Log.d("바인드", "널이아닐때 이거");
                            jsonObject.put("room_no", room_no + "");
                            jsonObject.put("from_user", my + "");
                            jsonObject.put("user_image", sf.getString("profile", ""));
                            jsonObject.put("user_name", sf.getString("nick", ""));

                        }
                        jsonObject.put("message", editText.getText().toString());
                        jsonObject.put("image", "");
                        jsonObject.put("sys_message", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("제제", jsonObject.toString());
//                final String input = editText.getText().toString();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Socket_Service.out.println(jsonObject);

                        }
                    });
                    thread.start();
                    TimeZone tz;
                    Date date = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    tz = TimeZone.getTimeZone("Asia/Seoul");
                    df.setTimeZone(tz);
                    Message_model ma = new Message_model(my, editText.getText().toString(), "", df.format(date));
                    Log.d("보낸시간", df.format(date) + "");
                    message_models.add(ma);

                    chat_adapter.notifyItemChanged(message_models.size()-1);
                    recyclerView.scrollToPosition(message_models.size() - 1);
                    editText.setText("");
                }
            }
        });
        callBack_=new Socket_Service.CallBack_() {
            @Override
            public void Call(String data) {
Log.d("처음",data);
                try {
                    JSONObject jsonObject=new JSONObject(data);
                    final int no = jsonObject.getInt("from");
                    final String message = jsonObject.getString("message");
                    final String image=jsonObject.getString("image");
                    final String time = jsonObject.getString("time");
                    final Message_model mm = new Message_model(no,message,image,time);
                    Handler handler=new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            message_models.add(mm);

                            chat_adapter.notifyItemChanged(message_models.size()-1);
                            recyclerView.scrollToPosition(message_models.size()-1);
                        }
                    },0);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Socket_Service.BindServiceBinder binder = (Socket_Service.BindServiceBinder) service;
                socket_service=binder.getService();
                socket_service.registerCallback(callBack_);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("바인드", String.valueOf(name));
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        Socket_Service.nnn=other_user;
        Intent serview = new Intent(getApplicationContext(),Socket_Service.class);
        bindService(serview,connection,Context.BIND_AUTO_CREATE);
        if(check==1) {


            HashMap hashMap = new HashMap();
            hashMap.put("no", my + "");
            hashMap.put("other", other_user + "");
            http.chat_list(hashMap).enqueue(new Callback<ArrayList<Message_model>>() {
                @Override
                public void onResponse(Call<ArrayList<Message_model>> call, Response<ArrayList<Message_model>> response) {
                    ArrayList<Message_model> message_model = response.body();
                    for (int i = 0; i < message_model.size(); i++) {
                        for (int j = 0; j < message_models.size(); j++) {
                            if (message_model.get(i).getTime().equals(message_models.get(j).getTime())) {
                                break;
                            }
                            if (j == message_models.size() - 1) {
                                message_models.add(message_model.get(i));
                                break;
                            }
                        }

                    }

                    chat_adapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<ArrayList<Message_model>> call, Throwable t) {

                }
            });


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Socket_Service.nnn=-1;
        unbindService(connection);
        check=1;

    }
}