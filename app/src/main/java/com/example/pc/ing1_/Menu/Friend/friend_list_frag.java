package com.example.pc.ing1_.Menu.Friend;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.Menu.Main.Store_recommend_Activity;
import com.example.pc.ing1_.Menu.Menu.Schedule_Activity;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class friend_list_frag extends Fragment {

    View view;
    Friend_Adapter friend_adapter;
    RecyclerView friend_recycler;
    Button friend_search_button;
    ArrayList<User> users;
    RetrofitExService http;
    SharedPreferences sf;
    public friend_list_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.friend_list_frag, container, false);
        friend_search_button=view.findViewById(R.id.friend_search);
        friend_recycler=view.findViewById(R.id.friend_list);
        friend_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        friend_recycler.setAdapter(friend_adapter);



        friend_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                final String[] items = {"ID로 찾기","핸드폰번호로 찾기"};
                dialog.setTitle("선택")
                        .setItems(items, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                Intent intent1=new Intent(getContext(),Friend_search_activity.class);
                                if(items[which].equals("ID로 찾기")){
                                    intent1.putExtra("select","id");

                                }else{
                                    intent1.putExtra("select","phone");

                                }
                                intent1.putExtra("users",users);
                                startActivityForResult(intent1,100);
                            }
                        })
                        .show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("실행","친구목록");
        users=new ArrayList<>();
        friend_adapter=new Friend_Adapter(getContext(),users);
        friend_adapter.Friend_AdapterClick(new Friend_Adapter.ProfileCilckListner() {
            @Override
            public void Pclick(View v, int i) {
                Intent intent = new Intent(getContext(),Chat_Activity.class);
                intent.putExtra("user_no",users.get(i).getNo()+"");
                intent.putExtra("single",true);

                startActivity(intent);
            }
        });
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        sf = getActivity().getSharedPreferences("login",MODE_PRIVATE);
        http.firend_list(sf.getString("no","-1")).enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                ArrayList<User>userArrayList=response.body();
                if(userArrayList!=null) {
                    for (int i = 0; i < userArrayList.size(); i++) {

                        users.add(userArrayList.get(i));



                    }
                }
                friend_adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100){
            if(resultCode==Activity.RESULT_OK){
                User user = (User) data.getSerializableExtra("user");
                users.add(user);
                Toast.makeText(getContext(),user.getName()+"",Toast.LENGTH_SHORT).show();
                friend_adapter.notifyDataSetChanged();
            }
        }
    }
}
