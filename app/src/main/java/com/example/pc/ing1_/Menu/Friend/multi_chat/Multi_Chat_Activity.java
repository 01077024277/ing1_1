package com.example.pc.ing1_.Menu.Friend.multi_chat;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pc.ing1_.Menu.Friend.Message_model;
import com.example.pc.ing1_.Menu.Friend.Socket_Service;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.User;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Multi_Chat_Activity extends AppCompatActivity {
    ServiceConnection connection;
    Socket_Service socket_service;
    Socket_Service.MCallback mCallback;
    ArrayList<User> users;
    ArrayList<User>inusers;
    EditText edit;
    Button send,exit,invite;
    String room_no, tt;
    SharedPreferences sf;
    int my;
    String my_nick, my_profile;
    RetrofitExService http;
    String title;
    TextView textView;
    String profiles;
    RecyclerView recyclerView;
    Multi_Char_Adapter multi_char_adapter;
    ArrayList<Message_model> message_models;
    int check;
    ProgressDialog progressDialog;
    String az;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_chat_activity);
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        textView = findViewById(R.id.textView);
        exit=findViewById(R.id.exit);
        invite=findViewById(R.id.inite);
        edit = findViewById(R.id.edit);
        send = findViewById(R.id.send);
        progressDialog=new ProgressDialog(Multi_Chat_Activity.this);
        check=0;
        recyclerView = findViewById(R.id.recyclerView);
        sf = getSharedPreferences("login", MODE_PRIVATE);
        my = Integer.parseInt(sf.getString("no", ""));
        my_nick = sf.getString("nick", "");
        my_profile = sf.getString("profile", "");
        profiles = sf.getString("profile", "");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        title = sf.getString("nick", "");
        message_models = new ArrayList<>();
        final Intent intent = getIntent();
        room_no = (intent.getStringExtra("room_no"));
        users=new ArrayList<>();
        inusers=new ArrayList<>();
        if (room_no == null) {


            ArrayList<User> uu= (ArrayList<User>) intent.getSerializableExtra("users");

            for(int i=0;i< uu.size();i++){
                users.add(uu.get(i));
                inusers.add(uu.get(i));

            }
            users.add(new User(my, profiles, my_nick));
            inusers.add(new User(my, profiles, my_nick));
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                }
            });
            Collections.sort(inusers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                }
            });
        }


















        if (room_no == null) {
            //처음 채팅방 만들때 룸 번호가 없음

            tt = "";

//            users.add(new User(my, profiles, my_nick));
//            inusers.add(new User(my, profiles, my_nick));

            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                }
            });
            Collections.sort(inusers, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                }
            });
            for (int i = 0; i < users.size(); i++) {
                Toast.makeText(getApplicationContext(), "" + users.get(i).getName(), Toast.LENGTH_SHORT).show();
                final int finalI = i;
                final int finalI1 = i;
                Glide.with(getApplicationContext()).asBitmap().load("http://54.180.168.210/profile/" + users.get(i).getProfile()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        users.get(finalI).setBitmap(resource);
                        inusers.get(finalI).setBitmap(resource);
                        if (finalI1 == users.size() - 1) {
                            multi_char_adapter = new Multi_Char_Adapter(getApplicationContext(), users, my, message_models);
                            Log.d("메세지선언no", my + "");

                            recyclerView.setAdapter(multi_char_adapter);

//                            Collections.sort(users, new Comparator<User>() {
//                                @Override
//                                public int compare(User o1, User o2) {
//                                    return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
//                                }
//                            });
                            multi_char_adapter.notifyDataSetChanged();
                        }


                    }
                });

                if (i == 0) {
                    tt = users.get(i).getName();
                    Log.d("로그1", tt);
                } else {
                    tt = tt + "," + users.get(i).getName();
                    Log.d("로그1", tt);
                }
                title = title + "," + users.get(i).getName();

            }
            if(tt.replace(my_nick+",","").length()>10){
                textView.setText(tt.replace(my_nick + ",", "").substring(0,11)+"...");

            }else{
                textView.setText(tt.replace(my_nick + ",", ""));

            }

        } else {
            //룸 넘버로 http통신해서 가져오기
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("불러오는중...");
            progressDialog.show();
            message_models = new ArrayList<>();
            tt = "";
            HashMap <String,String> hashMap=new HashMap<>();
            hashMap.put("room_no",room_no+"");
            hashMap.put("no",my+"");
            http.mul_chat(hashMap).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("제이늣ㄴ", room_no + "");
//                    users = new ArrayList<>();
//                    inusers = new ArrayList<>();
                    multi_char_adapter = new Multi_Char_Adapter(getApplicationContext(), users, my, message_models);
                    Log.d("메세지선언no", my + "");
                    recyclerView.setAdapter(multi_char_adapter);
                    Log.d("제이늣ㄴ", response.body().toString());
                    JsonObject jsonObject123 = response.body();
//                    JSONParser jsonParser=new JsonParser();
//                    Object o=jsonParser.parse(jsonObject123.toString());
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(String.valueOf(jsonObject123));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("제이늣ㄴ", jsonObject.toString());
                    try {
                        final JSONArray userarray = jsonObject.getJSONArray("users");
                        JSONArray messagearray = jsonObject.getJSONArray("message");
                        final JSONArray inseararray=jsonObject.getJSONArray("inuser");
                        Log.d("애초에",userarray.toString());
                        Log.d("애초에",inseararray.toString());

                        for (int i = 0; i < userarray.length(); i++) {
                            final JSONObject jj = userarray.getJSONObject(i);
                            final int finalI = i;
                            Log.d("애초에",finalI+"");

                            Glide.with(getApplicationContext()).asBitmap().load("http://54.180.168.210/profile/" + jj.getString("profile")).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    User user = null;
                                    try {

                                        user = new User(Integer.parseInt(jj.getString("no")), jj.getString("phone"), jj.getString("social"), jj.getString("uid"), jj.getString("id"), jj.getString("password"), jj.getString("name"), jj.getString("profile"), resource);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    users.add(user);
                                    Log.d("ㅇㅇㅇㅇ",user.getName());
//                                    for(int j=0;j<inseararray.length();j++){
//
//
//                                        try {
//                                            final JSONObject injj = inseararray.getJSONObject(j);
//                                            Log.d("user값","전체"+user.getNo());
//                                            Log.d("user값","인유저"+injj.getString("no"));
//                                            if(user.getNo()==Integer.parseInt(injj.getString("no"))){
//                                                inusers.add(user);
//                                                Log.d("나는",user.getName());
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
                                    multi_char_adapter.notifyDataSetChanged();

                                    if (users.size() == userarray.length() ) {
                                        for(int j=0;j<inseararray.length();j++) {
                                            for (int k = 0; k < users.size(); k++) {

                                                try {
                                                    final JSONObject injj = inseararray.getJSONObject(j);
                                                    Log.d("user값", "전체" + users.get(k).getNo());
                                                    Log.d("user값", "인유저" + injj.getString("no"));
                                                    if (users.get(k).getNo() == Integer.parseInt(injj.getString("no"))) {
                                                        inusers.add(users.get(k));
                                                        Log.d("나는", users.get(k).getName());
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        multi_char_adapter.notifyDataSetChanged();

                                        Collections.sort(users, new Comparator<User>() {
                                            @Override
                                            public int compare(User o1, User o2) {
                                                return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                                            }
                                        });
                                        Collections.sort(inusers, new Comparator<User>() {
                                            @Override
                                            public int compare(User o1, User o2) {
                                                return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                                            }
                                        });
                                        for(int i=0;i<users.size();i++){
                                            Log.d("유저스",users.get(i).getName());
                                        }
                                        for(int i=0;i<inusers.size();i++){
                                            Log.d("인유저스",inusers.get(i).getName());
                                        }
                                        for(int j=0;j<inusers.size();j++){
                                            if (j == 0) {
                                                tt = inusers.get(j).getName();
                                            } else {
                                                tt = tt + "," + inusers.get(j).getName();

                                            }
                                        }
                                        Log.d("제목은",tt);
                                        if(tt.replace(my_nick + ",", "").replace(","+my_nick , "").replace(my_nick,"").length()>10){
                                            textView.setText(tt.replace(my_nick + ",", "").replace(","+my_nick , "").replace(my_nick,"").toString().substring(0,11)+"...");

                                        }else{
                                            textView.setText(tt.replace(my_nick + ",", "").replace(","+my_nick , "").replace(my_nick,""));
                                            if(tt.replace(my_nick + ",", "").replace(","+my_nick , "").replace(my_nick,"").equals("")){
                                                textView.setText("대화상대 없음");
                                                Log.d("제목은","없음");

                                            }

                                        }


//                                            if(titt.length()>12){
//                                                textView.setText(titt.substring(0,13)+"...");
//
//                                            }else{
//                                                textView.setText(titt);
//                                            }


//                                        textView.setText(tt.replace( ","+my_nick , ""));
//                                        textView.setText(tt.replace(my_nick , ""));
                                        progressDialog.dismiss();

                                    }

//                                    Collections.sort(users, new Comparator<User>() {
//                                        @Override
//                                        public int compare(User o1, User o2) {
//                                            return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
//                                        }
//                                    });

                                    multi_char_adapter.notifyDataSetChanged();
                                }
                            });

                        }

                        for (int i = 0; i < messagearray.length(); i++) {
                            JSONObject jj = messagearray.getJSONObject(i);
                            Message_model message_model = new Message_model(Integer.parseInt(jj.getString("no")), jj.getString("message"), jj.getString("image"), jj.getString("time"), jj.getString("sys_message"));
                            message_models.add((message_model));
                        }


                        multi_char_adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(message_models.size() - 1);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }
        recyclerView.scrollToPosition(message_models.size() - 1);
































        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),Friend_invite_activity.class);
                intent1.putExtra("no",my);
                ArrayList<User> uuu=new ArrayList<>();
                for(int i=0;i<inusers.size();i++){

                    uuu.add(new User(inusers.get(i).getNo()));
//                    uuu.get(i).setBitmap(null);
                }
                intent1.putExtra("users",uuu);
                startActivityForResult(intent1,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
//            textView.setText(title.replace(my_nick + ",", ""));
//
//        } else {
//            //룸 넘버로 http통신해서 가져오기
//        }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit.getText().toString().equals("")) {
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("single", false);
                        jsonObject.put("from_nick", my_nick);
                        jsonObject.put("from_profile", my_profile);
                        jsonObject.put("sys_message", "");

                        String us = "^" + my + "^";
                        String pp = profiles;
                        for (int i = 0; i < inusers.size(); i++) {
                            if (inusers.get(i).getNo() != my) {
                                us = us + "^" + inusers.get(i).getNo() + "^";
                            }
                            if (i == 0) {
                                pp = inusers.get(i).getProfile();
                                Log.d("로그", pp);

                            } else {
                                pp = pp + "," + inusers.get(i).getProfile();
                                Log.d("로그", pp);
                            }


                        }
                        jsonObject.put("users", us);
                        jsonObject.put("profile", pp);
                        if (room_no == null) {
                            jsonObject.put("room_no", "null");
                            jsonObject.put("message", edit.getText().toString());
                            jsonObject.put("from", my + "");
                            jsonObject.put("image", "");

                            jsonObject.put("names", tt);
                            Log.d("룸", jsonObject.toString());
                            final String finalUs = us;
                            Log.d("룸", us);
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
                            message_models.add(new Message_model(my, "", "", df.format(date), "in^"+us));
                            multi_char_adapter.notifyDataSetChanged();

                        } else {
                            //방번호를 알고있을때 방 넘버를 함께 보내줌
                            jsonObject.put("room_no", room_no);
                            jsonObject.put("message", edit.getText().toString());
                            jsonObject.put("from", my + "");
                            jsonObject.put("image", "");
                            jsonObject.put("names", tt);
                            Log.d("룸", jsonObject.toString());
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Socket_Service.out.println(jsonObject);

                                }
                            });
                            thread.start();
                        }
                        TimeZone tz;
                        Date date = new Date();
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        tz = TimeZone.getTimeZone("Asia/Seoul");
                        df.setTimeZone(tz);
                        message_models.add(new Message_model(my, edit.getText().toString(), "", df.format(date), ""));
                        Log.d("메세지no", my + "");
                        multi_char_adapter.notifyItemChanged(message_models.size()-1);
                        recyclerView.scrollToPosition(message_models.size() - 1);

                        edit.setText("");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        mCallback = new Socket_Service.MCallback() {
            @Override
            public void MCall(String data) {
                Log.d("룸넘버0", data);

                if (room_no == null) {
                    room_no = data;
                    Socket_Service.nnn = Integer.parseInt(room_no);
                    Log.d("룸넘버", room_no);
                } else {
                    try {
                        final JSONObject jsonObject = new JSONObject(data);
                        if(!jsonObject.getString("sys_message").equals("")) {
                            if (jsonObject.getString("sys_message").equals("out")) {
                                for (int i = 0; i < inusers.size(); i++) {
                                    if (jsonObject.getString("from").equals(inusers.get(i).getNo() + "")) {
                                        inusers.remove(i);

                                    }
                                }
                                for(int i=0;i<inusers.size();i++){
                                    if(i==0){
                                        tt=inusers.get(i).getName();
                                    }else{
                                        tt=tt+","+inusers.get(i).getName();
                                    }
                                }
                            }else if (jsonObject.getString("sys_message").contains("in")){
                                String in=jsonObject.getString("sys_message").replace("^^",",").replace("^","").replace("in","");
                                for(int q=1;q<in.split(",").length;q++){
                                    String ur=jsonObject.getString("users").replace("^^",",").replace("^","");
                                    for(int b=0;b<ur.split(",").length;b++){
                                        if(in.split(",")[q].equals(ur.split(",")[b])){
                                            String pp=jsonObject.getString("profile");
                                            String nn=jsonObject.getString("names");
                                            User as=new User(Integer.parseInt(in.split(",")[q]),pp.split(",")[b],nn.split(",")[b]);
                                            inusers.add(as);
                                        }
                                    }
                                }
                                Collections.sort(users, new Comparator<User>() {
                                    @Override
                                    public int compare(User o1, User o2) {
                                        return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                                    }
                                });
                                Collections.sort(inusers, new Comparator<User>() {
                                    @Override
                                    public int compare(User o1, User o2) {
                                        return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                                    }
                                });

                            }
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                            String titt = tt.replace(my_nick + ",", "").replace("," + my_nick, "").replace(my_nick, "");
                                            if (titt.equals("")) {
                                                textView.setText("대화상대 없음");

                                            } else {
                                                if(titt.length()>10){
                                                    textView.setText(titt.substring(0,11)+"...");

                                                }else{
                                                    textView.setText(titt);
                                                }

                                            }

                                    }
                                }, 0);

                        }
                        Message_model message_model = new Message_model(Integer.parseInt(jsonObject.getString("from")), jsonObject.getString("message"), jsonObject.getString("image"), jsonObject.getString("time"),jsonObject.getString("sys_message"));
                        message_models.add(message_model);
                        Handler handler=new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                multi_char_adapter.notifyItemChanged(message_models.size()-1);
                                recyclerView.scrollToPosition(message_models.size() - 1);

                            }
                        },0);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                else{
//                    Message_model message_model=new Message_model()
//                }
            }
        };
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Socket_Service.BindServiceBinder binder = (Socket_Service.BindServiceBinder) service;
                socket_service = binder.getService();
                socket_service.mregisterCallback(mCallback);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("단체", String.valueOf(name));

            }
        };

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (room_no == null) {
                    finish();
                } else {
                    for(int i=0;i<inusers.size();i++){
                        if(inusers.get(i).getNo()==my){
                            inusers.remove(i);
                        }
                    }
                    try {
                        final JSONObject jsonObject=new JSONObject();
                        String us="";
                        String pp="";
                        tt="";
                        for (int i = 0; i < inusers.size(); i++) {

                                us = us + "^" + inusers.get(i).getNo() + "^";

                            if (i == 0) {
                                pp = inusers.get(i).getProfile();
                                tt=inusers.get(i).getName();
                                Log.d("로그", pp);

                            } else {
                                pp = pp + "," + inusers.get(i).getProfile();
                                tt=tt+","+inusers.get(i).getName();
                                Log.d("로그", pp);
                            }


                        }
                        jsonObject.put("single",false);
                        jsonObject.put("users", us);
                        jsonObject.put("profile", pp);
                        jsonObject.put("room_no", room_no);
                        jsonObject.put("sys_message","out");
                        jsonObject.put("message", "");
                        jsonObject.put("from", my + "");
                        jsonObject.put("image", "");

                        jsonObject.put("names", tt);
                        Log.d("룸", jsonObject.toString());
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Socket_Service.out.println(jsonObject);

                            }
                        });
                        thread.start();
                    }catch (JSONException e){

                    }

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("no", my + "");
                    hashMap.put("room_no", room_no + "");
                    http.mul_exit(hashMap).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Intent intent = new Intent();
                            intent.putExtra("room_no", room_no + "");
                            setResult(RESULT_OK, intent);
                            finish();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();




        Intent serview = new Intent(getApplicationContext(), Socket_Service.class);
        if (room_no != null) {
            Socket_Service.nnn = Integer.parseInt(room_no);
        } else {
            Socket_Service.nnn = 0;
            Log.d("룸넘버", "nnn==0");

        }
        bindService(serview, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Socket_Service.nnn = -1;
        unbindService(connection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK){
            if(requestCode==1){
                az="";
                    final ArrayList<User> u = (ArrayList<User>) data.getSerializableExtra("users");
                    for (int i = 0; i < u.size(); i++) {
                        final int finalI = i;
                        Glide.with(getApplicationContext()).asBitmap().load("http://54.180.168.210/profile/"+u.get(i).getProfile()).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                u.get(finalI).setBitmap(resource);
                                inusers.add(u.get(finalI));
                                users.add(u.get(finalI));
                                az=az+"^"+u.get(finalI).getNo()+"^";
                                Log.d("azaz",az);
                                if(finalI==u.size()-1){
                                    Collections.sort(users, new Comparator<User>() {
                                        @Override
                                        public int compare(User o1, User o2) {
                                            return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                                        }
                                    });
                                    Collections.sort(inusers, new Comparator<User>() {
                                        @Override
                                        public int compare(User o1, User o2) {
                                            return String.valueOf(o1.getNo()).compareTo(String.valueOf(o2.getNo()));
                                        }
                                    });
//                                    Handler handler=new Handler(Looper.getMainLooper());
//                                    handler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            multi_char_adapter.notifyDataSetChanged();
//
//                                        }
//                                    },0);
                                    tt="";
                                    for(int k=0;k<inusers.size();k++){
                                        if(k==0){
                                            tt=inusers.get(k).getName();
                                        }else{
                                            tt=tt+","+inusers.get(k).getName();
                                        }
                                    }
                                    if(tt.replace(my_nick + ",", "").replace(","+my_nick , "").replace(my_nick,"").length()>10){
                                        textView.setText(tt.replace(my_nick + ",", "").replace(","+my_nick , "").replace(my_nick,"").toString().substring(0,11)+"...");

                                    }else{
                                        textView.setText(tt.replace(my_nick + ",", "").replace(","+my_nick , "").replace(my_nick,""));

                                    }

                                    try {
                                        final JSONObject jsonObject=new JSONObject();
                                        String us="";
                                        String pp="";
                                        tt="";
                                        for (int i = 0; i < inusers.size(); i++) {

                                            us = us + "^" + inusers.get(i).getNo() + "^";
                                            Log.d("로그", us);

                                            if (i == 0) {
                                                pp = inusers.get(i).getProfile();
                                                tt=inusers.get(i).getName();
                                                Log.d("로그", pp);

                                            } else {
                                                pp = pp + "," + inusers.get(i).getProfile();
                                                tt=tt+","+inusers.get(i).getName();
                                                Log.d("로그", pp);
                                            }


                                        }
                                        jsonObject.put("single",false);
                                        jsonObject.put("users", us);
                                        jsonObject.put("profile", pp);
                                        jsonObject.put("room_no", room_no);
                                        jsonObject.put("sys_message","in^"+az);
                                        jsonObject.put("message", "");
                                        jsonObject.put("from", my + "");
                                        jsonObject.put("image", "");

                                        jsonObject.put("names", tt);
                                        Log.d("룸", jsonObject.toString());
                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Socket_Service.out.println(jsonObject);

                                            }
                                        });
                                        thread.start();
                                        TimeZone tz;
                                        final Date date = new Date();
                                        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        tz = TimeZone.getTimeZone("Asia/Seoul");
                                        df.setTimeZone(tz);
                                        Handler handler=new Handler(Looper.getMainLooper());
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                message_models.add(new Message_model(my, "", "", df.format(date), "in^"+az));
                                                multi_char_adapter.notifyDataSetChanged();
                                                recyclerView.scrollToPosition(message_models.size() - 1);

                                            }
                                        },0);

                                    }catch (JSONException e){

                                    }
                                }
                            }
                        });

                    }


            }
        }
    }
}
