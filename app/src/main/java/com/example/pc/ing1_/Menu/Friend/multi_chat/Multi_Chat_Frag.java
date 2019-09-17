package com.example.pc.ing1_.Menu.Friend.multi_chat;


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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pc.ing1_.Menu.Friend.Socket_Service;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Multi_Chat_Frag extends Fragment {
    ServiceConnection connection;
    Button new_chat_btn;
    SharedPreferences sf;
    RetrofitExService http;
    RecyclerView recyclerView;
    int my;
    String my_nick;
    String my_profile;
    ArrayList<Multi_Room_Item> multi_room_items;
    Multi_chat_list_Adapter multi_chat_list_adapter;
    Socket_Service socket_service;
    Socket_Service.MCallback mCallback;

    public Multi_Chat_Frag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sf = getActivity().getSharedPreferences("login",MODE_PRIVATE);
        my= Integer.parseInt(sf.getString("no",""));
        my_nick=sf.getString("nick","");
        my_profile=sf.getString("profile","");
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        multi_room_items=new ArrayList<>();


        mCallback=new Socket_Service.MCallback() {
            @Override
            public void MCall(String data) {
                try {
                    final JSONObject jsonObject=new JSONObject(data);
                    final String room_no = jsonObject.getString("room_no");
                    final String time = jsonObject.getString("time");
                    final String message = jsonObject.getString("message");
                    final String title=jsonObject.getString("names");
                    final String images =  jsonObject.getString("profile");
                    final  String sys_message = jsonObject.getString("sys_message");
                    Log.d("단체",jsonObject.toString());
                    Log.d("단체",images);
                    Handler handler=new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(multi_room_items.size()==0&&!sys_message.contains("in")&&!sys_message.equals("out")){
                                Multi_Room_Item multi_room_item=new Multi_Room_Item(false,room_no,time,message,title,images);
                                multi_room_items.add(multi_room_item);
                                Log.d("첫번쨰","1");
                            }else{
                                for(int i=0;i<multi_room_items.size();i++){
                                    if(multi_room_items.get(i).getRoom_no().equals(room_no)){
                                        if(message.equals("")&&sys_message.equals("out")){
                                            try {
                                                Toast.makeText(getContext(),jsonObject.getString("from")+"나감",Toast.LENGTH_SHORT).show();
                                                Multi_Room_Item multi_room_item = new Multi_Room_Item(false, room_no, multi_room_items.get(i).getTime(), multi_room_items.get(i).getMessage(), title, images);
                                                multi_room_items.set(i, multi_room_item);
                                                Log.d("첫번쨰","2");
                                                break;
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }else if (message.equals("")&&sys_message.contains("in")){
                                            try {
                                                Toast.makeText(getContext(),jsonObject.getString("sys_message")+"들어옴",Toast.LENGTH_SHORT).show();
                                                Multi_Room_Item multi_room_item = new Multi_Room_Item(false, room_no, multi_room_items.get(i).getTime(), multi_room_items.get(i).getMessage(), title, images);
                                                multi_room_items.set(i, multi_room_item);
                                                Log.d("첫번쨰","3");
                                                break;
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                            else if (!message.equals("")){
                                            Multi_Room_Item multi_room_item = new Multi_Room_Item(false, room_no, time, message, title, images);
                                            multi_room_items.set(i, multi_room_item);
                                            Log.d("첫번쨰","4");
                                            break;
                                        }
                                    }
                                    if(i==multi_room_items.size()-1&&!sys_message.contains("in")){
                                        Multi_Room_Item multi_room_item=new Multi_Room_Item(false,room_no,time,message,title,images);
                                        multi_room_items.add(multi_room_item);
                                        Log.d("첫번쨰","5");
                                        break;
                                    }
                                }
                            }
                            Collections.sort(multi_room_items, new Comparator<Multi_Room_Item>() {
                                @Override
                                public int compare(Multi_Room_Item o1, Multi_Room_Item o2) {
                                    return o2.getTime().compareTo(o1.getTime());
                                }
                            });
                            multi_chat_list_adapter.notifyDataSetChanged();

                        }
                    },0);
                } catch (JSONException e) {
                    Log.d("단체",String.valueOf(e));
                }


            }
        };

        connection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Socket_Service.BindServiceBinder binder = (Socket_Service.BindServiceBinder) service;
                socket_service=binder.getService();
                socket_service.mregisterCallback(mCallback);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("단체",String.valueOf(name));

            }
        };

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.multi_chat_fragment, container, false);
        recyclerView=v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        multi_chat_list_adapter=new Multi_chat_list_Adapter(getContext(),multi_room_items,my_nick,my_profile);

        multi_chat_list_adapter.Multi_Click(new Multi_chat_list_Adapter.Multi_chat_list_() {
            @Override
            public void MultiClick_(View v, int i) {

                Intent intent=new Intent(getContext(),Multi_Chat_Activity.class);
                intent.putExtra("room_no",multi_room_items.get(i).getRoom_no()+"");
                startActivityForResult(intent,100);
            }
        });



        recyclerView.setAdapter(multi_chat_list_adapter);
        new_chat_btn=v.findViewById(R.id.new_chat);
        //새로운채팅 버튼 클릭시 //친구목록을 보여줌
        new_chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Friend_invite_activity.class);
                intent.putExtra("no",my);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        http.mul_list(my+"").enqueue(new Callback<ArrayList<Multi_Room_Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Multi_Room_Item>> call, Response<ArrayList<Multi_Room_Item>> response) {
                ArrayList<Multi_Room_Item> room_items=response.body();
                if(multi_room_items.size()==0) {
                    for (int i = 0; i < room_items.size(); i++) {
                        multi_room_items.add(room_items.get(i));
                    }
                }else{
                    for(int i=0;i<room_items.size();i++){
                        for(int j=0;j<multi_room_items.size();j++){
                            if(room_items.get(i).getRoom_no().equals(multi_room_items.get(j).getRoom_no())){
                                multi_room_items.set(j,room_items.get(i));
                                break;
                            }
                            if(j==multi_room_items.size()-1){
                                multi_room_items.add(room_items.get(i));
                                break;
                            }
                        }
                    }

                }
                Collections.sort(multi_room_items, new Comparator<Multi_Room_Item>() {
                    @Override
                    public int compare(Multi_Room_Item o1, Multi_Room_Item o2) {
                        return o2.getTime().compareTo(o1.getTime());
                    }
                });
                multi_chat_list_adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Multi_Room_Item>> call, Throwable t) {

            }
        });

        Intent serview = new Intent(getContext(),Socket_Service.class);
        Socket_Service.nnn=0;
        getActivity().bindService(serview,connection,Context.BIND_AUTO_CREATE);
        Log.d("resume","resume");
        Log.d("resume","resume123");

    }
    @Override
    public void onPause() {
        super.onPause();
        Socket_Service.nnn=-1;
        getActivity().unbindService(connection);
        Log.d("resume","pause");
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Socket_Service.nnn=-1;
//        getActivity().unbindService(connection);
//        Log.d("resume","pause");
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Activity.RESULT_OK){
            if(requestCode==100){
                try {
                    int no = Integer.parseInt(data.getStringExtra("room_no"));
                    for(int i=0;i<multi_room_items.size();i++){
                        if(multi_room_items.get(i).getRoom_no().equals(no+"")){
                            multi_room_items.remove(i);
                            multi_chat_list_adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }catch (NullPointerException e){

                }

                multi_chat_list_adapter.notifyDataSetChanged();

            }
        }
    }
}
