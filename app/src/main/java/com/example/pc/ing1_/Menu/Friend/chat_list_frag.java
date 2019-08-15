package com.example.pc.ing1_.Menu.Friend;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class chat_list_frag extends Fragment {
    Socket_Service socket_service;
    ServiceConnection connection;
    SharedPreferences sf;
    RetrofitExService http;
    RecyclerView recyclerView;
    Chat_list_Adapter chat_list_adapter;
    ArrayList<Room_Item>room_items;
    Socket_Service.CallBack_ callBack_;
    int my;
    public chat_list_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.chat_list_farg, container, false);
        recyclerView=v.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chat_list_adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("실행","채팅목록");
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        sf = getActivity().getSharedPreferences("login",MODE_PRIVATE);
        my= Integer.parseInt(sf.getString("no",""));

        room_items=new ArrayList<>();
        chat_list_adapter=new Chat_list_Adapter(getContext(),room_items);
        chat_list_adapter.Room_AdapterClick(new Chat_list_Adapter.Room_ClickListener() {
            @Override
            public void Room(View v, int i) {
                if(room_items.get(i).isSingle()==true){
                    Intent intent = new Intent(getContext(),Chat_Activity.class);
                    intent.putExtra("user_no",room_items.get(i).user_no+"");
                    intent.putExtra("single",true);
                    intent.putExtra("room_no",room_items.get(i).room_no+"");

                    startActivityForResult(intent,100);
                }
            }
        });



         callBack_= new Socket_Service.CallBack_() {
            @Override
            public void Call(final String data) {
                Log.d("바인드data",data);
                Handler handler=new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            boolean single=jsonObject.getBoolean("single");
                            if(single == true) {
                                Log.d("바인드s", single + "");
                                String from = jsonObject.getString("from");
                                Log.d("바인드f", from);
                                String message = jsonObject.getString("message");
                                Log.d("바인드m", message);
                                String time = jsonObject.getString("time");
                                Log.d("바인드t", time);
                                if (room_items.size() != 0){
                                    for (int i = 0; i < room_items.size(); i++) {
                                        Log.d("바인드ss", room_items.size() + "");

                                        if (from.equals(room_items.get(i).getUser_no())) {
                                            Log.d("바인드jj", "조건");

                                            room_items.get(i).setMessage(message);
                                            room_items.get(i).setTime(time);
                                            Collections.sort(room_items, new Comparator<Room_Item>() {
                                                @Override
                                                public int compare(Room_Item o1, Room_Item o2) {
                                                    return o2.getTime().compareTo(o1.getTime());
                                                }
                                            });
                                            chat_list_adapter.notifyDataSetChanged();
//                                    Toast.makeText(getActivity(), "수정", Toast.LENGTH_SHORT).show();
                                            break;

                                        }
                                        if (i == room_items.size() - 1) {
                                            String room_no,from_user,user_image,user_name,user_no;
                                            room_no=jsonObject.getString("room_no");
                                            from_user=jsonObject.getString("from_user");
//                                            message=jsonObject.getString("message");
                                            user_image=jsonObject.getString("user_image");
                                            user_name=jsonObject.getString("user_name");
                                            user_no=jsonObject.getString("from_user");

                                           Room_Item room_item=new Room_Item(true,Integer.parseInt(room_no),Integer.parseInt(from_user),message,user_image,user_name,time,user_no);
                                           room_items.add(room_item);
                                            Log.d("바인드s1s", room_items.size() + "");

                                            Collections.sort(room_items, new Comparator<Room_Item>() {
                                                @Override
                                                public int compare(Room_Item o1, Room_Item o2) {
                                                    return o2.getTime().compareTo(o1.getTime());
                                                }
                                            });
                                            chat_list_adapter.notifyDataSetChanged();
                                            break;

                                        }
                                    }
//
                            }else{
                                    String room_no,from_user,user_image,user_name,user_no;
                                    room_no=jsonObject.getString("room_no");
                                    from_user=jsonObject.getString("from_user");
//                                            message=jsonObject.getString("message");
                                    user_image=jsonObject.getString("user_image");
                                    user_name=jsonObject.getString("user_name");
                                    user_no=jsonObject.getString("from_user");

                                    Room_Item room_item=new Room_Item(true,Integer.parseInt(room_no),Integer.parseInt(from_user),message,user_image,user_name,time,user_no);
                                    room_items.add(room_item);
                                    Collections.sort(room_items, new Comparator<Room_Item>() {
                                        @Override
                                        public int compare(Room_Item o1, Room_Item o2) {
                                            return o2.getTime().compareTo(o1.getTime());
                                        }
                                    });
                                    chat_list_adapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            Log.d("바인",String.valueOf(e));
                        }
                    }
                },0);


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
                Log.d("바인드123", String.valueOf(name));
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("no",my+"");
        http.room_items(hashMap).enqueue(new Callback<ArrayList<Room_Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Room_Item>> call, Response<ArrayList<Room_Item>> response) {
                ArrayList<Room_Item> roomItem =response.body();
                //처음 액티비티 접속시 전부 불러옴
                if(room_items.size()==0){
                    for(int i=0;i<roomItem.size();i++){
                        room_items.add(roomItem.get(i));

                    }
                }else{
                    //백그라운드에 있다 포그라운드로 올라올떄 수정된사항만 불러옴
                    for(int i=0;i<roomItem.size();i++){
                        Log.d("왜 i",roomItem.get(i).getUser_no());
                        Log.d("왜 i",roomItem.get(i).isSingle()+"");

                        for(int j=0;j<room_items.size();j++){
                            Log.d("왜 j",room_items.get(j).getUser_no());
                            Log.d("왜 j",room_items.get(j).isSingle()+"");
                            if(roomItem.get(i).isSingle()==room_items.get(j).isSingle()){
                                if(roomItem.get(i).getUser_no().equals(room_items.get(j).getUser_no())){
                                    room_items.get(j).setTime(roomItem.get(i).getTime());
                                    room_items.get(j).setMessage(roomItem.get(i).getMessage());
                                    Log.d("리스트","rrr");
                                    break;
                                }
                                if(j==room_items.size()-1){
                                    Log.d("리스트","add");
                                    room_items.add(roomItem.get(i));

                                }
                            }
                        }
                    }

                }
                Collections.sort(room_items, new Comparator<Room_Item>() {
                    @Override
                    public int compare(Room_Item o1, Room_Item o2) {
                        return o2.getTime().compareTo(o1.getTime());
                    }
                });
                chat_list_adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<Room_Item>> call, Throwable t) {

            }
        });



        Intent serview = new Intent(getContext(),Socket_Service.class);
        Socket_Service.nnn=0;
        getActivity().bindService(serview,connection,Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        Socket_Service.nnn=-1;
        getActivity().unbindService(connection);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK){
            if(requestCode==100){
                for(int i=0;i<room_items.size();i++){
                    if(room_items.get(i).getUser_no().equals(data.getStringExtra("result"))){
                        room_items.remove(i);
                    }
                }
                Collections.sort(room_items, new Comparator<Room_Item>() {
                    @Override
                    public int compare(Room_Item o1, Room_Item o2) {
                        return o2.getTime().compareTo(o1.getTime());
                    }
                });
                chat_list_adapter.notifyDataSetChanged();

            }
        }
    }
}
