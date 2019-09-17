package com.example.pc.ing1_.Menu.Friend.multi_chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.Menu.Friend.Message_model;
import com.example.pc.ing1_.Menu.Main.Store_info_Activity;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.Store;
import com.example.pc.ing1_.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Multi_Char_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<User> users;
    int myuser;
    ArrayList<Message_model> message_models;
    RetrofitExService http;

    Multi_Char_Adapter(Context context, ArrayList<User> users , int my , ArrayList<Message_model> message_models){
        this.context=context;
        this.users=users;
        this.myuser=my;
        this.message_models=message_models;
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.chat_item,viewGroup,false);
        Mult_Chat_ViewHolder mult_chat_viewHolder=new Mult_Chat_ViewHolder(v);
        return mult_chat_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        String time=message_models.get(i).getTime();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date to = transFormat.parse(time);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm a",Locale.KOREAN);
            time=simpleDateFormat.format(to);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Mult_Chat_ViewHolder) viewHolder).profile.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Mult_Chat_ViewHolder) viewHolder).profile.setClipToOutline(true);
        }

//        Log.d("메세지클래스",message_models.get(i).getNo()+"");
//        Log.d("메세지my",myuser+"");

        if(message_models.get(i).getMessage().equals("")&&message_models.get(i).getImage().equals("")){
            ((Mult_Chat_ViewHolder)viewHolder).profile.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).nick.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).my_message.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).my_time.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).other_time.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).othermessage.setVisibility(View.GONE);
            if(message_models.get(i).getSys_message().equals("out")){
                for(int j=0;j<users.size();j++){
                    if(message_models.get(i).getNo()==users.get(j).getNo()){
                        ((Mult_Chat_ViewHolder) viewHolder).sys_message.setText(users.get(j).getName()+"님이 나가셨습니다");

                    }
                }

            }else if (message_models.get(i).getSys_message().contains("in")){
                String replace=message_models.get(i).getSys_message().replace("in","").replace("^"+message_models.get(i).getNo()+"^","").replace("^^",",").replace("^","");
                String uus="";
                Log.d("참여자",replace);

                for(int j=1;j<replace.split(",").length;j++){
                    for(int k=0;k<users.size();k++){
                        Log.d("참여자",uus);
                        if(j==1) {
                            if (replace.split(",")[j].equals(users.get(k).getNo() + "")) {
                                uus = users.get(k).getName();
                                break;
                            }
                        }else{
                            if (replace.split(",")[j].equals(users.get(k).getNo() + "")) {
                                uus = uus+","+users.get(k).getName();
                                break;
                            }
                        }
                    }
                }
                for(int j=0;j<users.size();j++){
                    if(message_models.get(i).getNo()==users.get(j).getNo()){
                        Log.d("참여자123",uus);
                        ((Mult_Chat_ViewHolder) viewHolder).sys_message.setText(users.get(j).getName()+"님이 "+uus+"님을 초대하셨습니다");
                        break;
                    }
                }

            }
            ((Mult_Chat_ViewHolder)viewHolder).sys_message.setVisibility(View.VISIBLE);




        }
        else if(message_models.get(i).getNo()==myuser){
            if(message_models.get(i).getImage().equals("")) {
                //내가 보냈고 이미지 공유가 아니라면
                ((Mult_Chat_ViewHolder) viewHolder).othermessage.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder) viewHolder).profile.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder) viewHolder).nick.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder) viewHolder).my_message.setVisibility(View.VISIBLE);
                ((Mult_Chat_ViewHolder) viewHolder).my_time.setText(time.split(" ")[2] + " " + time.split(" ")[1]);
                ((Mult_Chat_ViewHolder) viewHolder).my_message.setText(message_models.get(i).getMessage());
                ((Mult_Chat_ViewHolder) viewHolder).con.setMinHeight(100);
                ((Mult_Chat_ViewHolder) viewHolder).my_time.setVisibility(View.VISIBLE);
                ((Mult_Chat_ViewHolder) viewHolder).other_time.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder) viewHolder).sys_message.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).my_shared_text.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).my_shared.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).my_shared_time.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).other_shared.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).other_shared_text.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).other_shared_time.setVisibility(View.GONE);
            }else{
             //내가 보냈고 이미지 공유
                String finalTime = time;
                http.shared_store(message_models.get(i).getImage()).enqueue(new Callback<Store>() {
                    @Override
                    public void onResponse(Call<Store> call, Response<Store> response) {
                        Store store=response.body();
                        ((Mult_Chat_ViewHolder)viewHolder).othermessage.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).profile.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).nick.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).my_message.setVisibility(View.GONE);
//                            ((Chat_ViewHolder)viewHolder).my_time.setText(finalTime.split(" ")[2]+" "+ finalTime.split(" ")[1]);
                        ((Mult_Chat_ViewHolder)viewHolder).con.setMinHeight(100);
                        ((Mult_Chat_ViewHolder)viewHolder).my_time.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).other_time.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).my_shared_text.setVisibility(View.VISIBLE);
                        ((Mult_Chat_ViewHolder)viewHolder).my_shared.setVisibility(View.VISIBLE);
                        ((Mult_Chat_ViewHolder)viewHolder).my_shared_time.setVisibility(View.VISIBLE);
                        ((Mult_Chat_ViewHolder)viewHolder).my_shared_time.setText(finalTime.split(" ")[2]+" "+ finalTime.split(" ")[1]);
                        ((Mult_Chat_ViewHolder)viewHolder).other_shared.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).other_shared_text.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).other_shared_time.setVisibility(View.GONE);
                        if(store.getStore_img().equals("")){
                            Glide.with(context).load(R.drawable.no_image).centerCrop().into(((Mult_Chat_ViewHolder)viewHolder).my_shared);
                        }else{
                            Glide.with(context).load(store.getStore_img()).thumbnail(0.2f).centerCrop().into(((Mult_Chat_ViewHolder)viewHolder).my_shared);

                        }
                        ((Mult_Chat_ViewHolder)viewHolder).my_shared_text.setText(store.getName());
                        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//                            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        double user_lat=37.486471;
                        double user_lon=126.971432;

//        user_lat = location.getLatitude();
//        user_lon = location.getLongitude();
                        ((Mult_Chat_ViewHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(context,Store_info_Activity.class);
                                intent.putExtra("lat",user_lat);
                                intent.putExtra("lon",user_lon);
                                intent.putExtra("store",store);
                                context.startActivity(intent);
                            }
                        });



                    }

                    @Override
                    public void onFailure(Call<Store> call, Throwable t) {

                    }
                });
            }
        }else{
            for(int j=0;j<users.size();j++){
                if(message_models.get(i).getNo()==users.get(j).getNo()){
                    ((Mult_Chat_ViewHolder) viewHolder).othermessage.setText(message_models.get(i).getMessage());
                    ((Mult_Chat_ViewHolder) viewHolder).nick.setText("" + users.get(j).getName());
                    ((Mult_Chat_ViewHolder) viewHolder).profile.setImageBitmap(users.get(j).getBitmap());
                }
            }


            if(message_models.get(i).getImage().equals("")){
                //다른사람이 보내고 이미지공유가 아님
            ((Mult_Chat_ViewHolder)viewHolder).other_time.setText(time.split(" ")[2]+" "+time.split(" ")[1]);
            ((Mult_Chat_ViewHolder)viewHolder).othermessage.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).profile.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).nick.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).my_message.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).my_time.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).sys_message.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).other_time.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).con.setMinHeight(130);
                ((Mult_Chat_ViewHolder)viewHolder).my_shared_text.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).my_shared.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).my_shared_time.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).other_shared.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).other_shared_text.setVisibility(View.GONE);
                ((Mult_Chat_ViewHolder)viewHolder).other_shared_time.setVisibility(View.GONE);

            }else{
             //다른사람이 보냈고 이미지 공유
                String finalTime1 = time;
                http.shared_store(message_models.get(i).getImage()).enqueue(new Callback<Store>() {
                    @Override
                    public void onResponse(Call<Store> call, Response<Store> response) {
                        Store store=response.body();

                        ((Mult_Chat_ViewHolder) viewHolder).other_shared_time.setText(finalTime1.split(" ")[2] + " " + finalTime1.split(" ")[1]);
                        ((Mult_Chat_ViewHolder) viewHolder).othermessage.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder) viewHolder).profile.setVisibility(View.VISIBLE);
                        ((Mult_Chat_ViewHolder) viewHolder).nick.setVisibility(View.VISIBLE);
                        ((Mult_Chat_ViewHolder) viewHolder).my_message.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder) viewHolder).my_time.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder) viewHolder).other_time.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder) viewHolder).con.setMinHeight(130);
                        ((Mult_Chat_ViewHolder)viewHolder).my_shared_text.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).my_shared.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).my_shared_time.setVisibility(View.GONE);
                        ((Mult_Chat_ViewHolder)viewHolder).other_shared.setVisibility(View.VISIBLE);
                        ((Mult_Chat_ViewHolder)viewHolder).other_shared_text.setVisibility(View.VISIBLE);
                        ((Mult_Chat_ViewHolder)viewHolder).other_shared_time.setVisibility(View.VISIBLE);
                        if(store.getStore_img().equals("")){
                            Glide.with(context).load(R.drawable.no_image).centerCrop().into(((Mult_Chat_ViewHolder)viewHolder).other_shared);
                        }else{
                            Glide.with(context).load(store.getStore_img()).thumbnail(0.2f).centerCrop().into(((Mult_Chat_ViewHolder)viewHolder).other_shared);

                        }
                        ((Mult_Chat_ViewHolder)viewHolder).other_shared_text.setText(store.getName());
                        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//                            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        double user_lat=37.486471;
                        double user_lon=126.971432;

//        user_lat = location.getLatitude();
//        user_lon = location.getLongitude();
                        ((Mult_Chat_ViewHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(context,Store_info_Activity.class);
                                intent.putExtra("lat",user_lat);
                                intent.putExtra("lon",user_lon);
                                intent.putExtra("store",store);
                                context.startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Store> call, Throwable t) {

                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return message_models.size();
    }

    class Mult_Chat_ViewHolder extends RecyclerView.ViewHolder{
        ImageView profile,my_shared,other_shared;
        TextView nick,othermessage,my_message,my_time,other_time,sys_message,my_shared_text,my_shared_time,other_shared_text,other_shared_time;
        ConstraintLayout con;
        public Mult_Chat_ViewHolder(@NonNull View itemView) {
            super(itemView);
            sys_message=itemView.findViewById(R.id.sys_message);
            con=itemView.findViewById(R.id.con);
            profile=itemView.findViewById(R.id.profile);
            nick=itemView.findViewById(R.id.nick);
            othermessage=itemView.findViewById(R.id.other_message);
            my_time=itemView.findViewById(R.id.my_time);
            other_time=itemView.findViewById(R.id.other_time);
            my_message=itemView.findViewById(R.id.my_message);
            my_shared=itemView.findViewById(R.id.my_shared);
            my_shared_text=itemView.findViewById(R.id.my_shared_text);
            my_shared_time=itemView.findViewById(R.id.my_shared_time);
            other_shared=itemView.findViewById(R.id.other_shared);
            other_shared_text=itemView.findViewById(R.id.other_shared_text);
            other_shared_time=itemView.findViewById(R.id.other_shared_time);
        }
    }
}
