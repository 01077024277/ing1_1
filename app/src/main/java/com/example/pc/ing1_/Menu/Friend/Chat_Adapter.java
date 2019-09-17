package com.example.pc.ing1_.Menu.Friend;

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

public class Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Message_model> message_models;
    RetrofitExService http;
    User other_user;
    Chat_Adapter(Context context,ArrayList<Message_model> message_models,User user){
        this.context=context;
        this.message_models=message_models;
        this.other_user=user;
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.chat_item,viewGroup,false);
        Chat_ViewHolder chat_viewHolder=new Chat_ViewHolder(v);
        return chat_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(other_user!=null) {
            Log.d("상대방d",String.valueOf(i));
            String time=message_models.get(i).getTime();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date to = transFormat.parse(time);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm a",Locale.KOREAN);
                time=simpleDateFormat.format(to);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (message_models.get(i).getNo() == other_user.getNo()) {
                Log.d("상대방t",String.valueOf(i));

                ((Chat_ViewHolder) viewHolder).othermessage.setText(message_models.get(i).getMessage());
                ((Chat_ViewHolder) viewHolder).nick.setText("" + other_user.getName());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ((Chat_ViewHolder) viewHolder).profile.setBackground(new ShapeDrawable(new OvalShape()));
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((Chat_ViewHolder) viewHolder).profile.setClipToOutline(true);
                }
                if(message_models.get(i).getMessage().equals("음식점 공유")&&!message_models.get(i).getImage().equals("")){
                    String finalTime1 = time;
                    http.shared_store(message_models.get(i).getImage()).enqueue(new Callback<Store>() {
                        @Override
                        public void onResponse(Call<Store> call, Response<Store> response) {
                            Store store=response.body();

                            ((Chat_ViewHolder) viewHolder).other_shared_time.setText(finalTime1.split(" ")[2] + " " + finalTime1.split(" ")[1]);
                            ((Chat_ViewHolder) viewHolder).profile.setImageBitmap(other_user.getBitmap());
                            ((Chat_ViewHolder) viewHolder).othermessage.setVisibility(View.GONE);
                            ((Chat_ViewHolder) viewHolder).profile.setVisibility(View.VISIBLE);
                            ((Chat_ViewHolder) viewHolder).nick.setVisibility(View.GONE);
                            ((Chat_ViewHolder) viewHolder).my_message.setVisibility(View.GONE);
                            ((Chat_ViewHolder) viewHolder).my_time.setVisibility(View.GONE);
                            ((Chat_ViewHolder) viewHolder).other_time.setVisibility(View.GONE);
                            ((Chat_ViewHolder) viewHolder).con.setMinHeight(130);
                            ((Chat_ViewHolder)viewHolder).my_shared_text.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).my_shared.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).my_shared_time.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).other_shared.setVisibility(View.VISIBLE);
                            ((Chat_ViewHolder)viewHolder).other_shared_text.setVisibility(View.VISIBLE);
                            ((Chat_ViewHolder)viewHolder).other_shared_time.setVisibility(View.VISIBLE);
                            if(store.getStore_img().equals("")){
                                Glide.with(context).load(R.drawable.no_image).centerCrop().into(((Chat_ViewHolder)viewHolder).other_shared);
                            }else{
                                Glide.with(context).load(store.getStore_img()).thumbnail(0.2f).centerCrop().into(((Chat_ViewHolder)viewHolder).other_shared);

                            }
                            ((Chat_ViewHolder)viewHolder).other_shared_text.setText(store.getName());
                            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//                            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            double user_lat=37.486471;
                            double user_lon=126.971432;

//        user_lat = location.getLatitude();
//        user_lon = location.getLongitude();
                            ((Chat_ViewHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
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
                else {
                    ((Chat_ViewHolder) viewHolder).other_time.setText(time.split(" ")[2] + " " + time.split(" ")[1]);
                    ((Chat_ViewHolder) viewHolder).profile.setImageBitmap(other_user.getBitmap());
                    ((Chat_ViewHolder) viewHolder).othermessage.setVisibility(View.VISIBLE);
                    ((Chat_ViewHolder) viewHolder).profile.setVisibility(View.VISIBLE);
                    ((Chat_ViewHolder) viewHolder).nick.setVisibility(View.VISIBLE);
                    ((Chat_ViewHolder) viewHolder).my_message.setVisibility(View.GONE);
                    ((Chat_ViewHolder) viewHolder).my_time.setVisibility(View.GONE);
                    ((Chat_ViewHolder) viewHolder).other_time.setVisibility(View.VISIBLE);
                    ((Chat_ViewHolder) viewHolder).con.setMinHeight(130);
                    ((Chat_ViewHolder)viewHolder).my_shared_text.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).my_shared.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).my_shared_time.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).other_shared.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).other_shared_text.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).other_shared_time.setVisibility(View.GONE);
                }
            }else{
                if(message_models.get(i).getMessage().equals("음식점 공유")&&!message_models.get(i).getImage().equals("")){

                    String finalTime = time;
                    http.shared_store(message_models.get(i).getImage()).enqueue(new Callback<Store>() {
                        @Override
                        public void onResponse(Call<Store> call, Response<Store> response) {
                            Store store=response.body();
                            ((Chat_ViewHolder)viewHolder).othermessage.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).profile.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).nick.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).my_message.setVisibility(View.GONE);
//                            ((Chat_ViewHolder)viewHolder).my_time.setText(finalTime.split(" ")[2]+" "+ finalTime.split(" ")[1]);
                            ((Chat_ViewHolder)viewHolder).con.setMinHeight(100);
                            ((Chat_ViewHolder)viewHolder).my_time.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).other_time.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).my_shared_text.setVisibility(View.VISIBLE);
                            ((Chat_ViewHolder)viewHolder).my_shared.setVisibility(View.VISIBLE);
                            ((Chat_ViewHolder)viewHolder).my_shared_time.setVisibility(View.VISIBLE);
                            ((Chat_ViewHolder)viewHolder).my_shared_time.setText(finalTime.split(" ")[2]+" "+ finalTime.split(" ")[1]);
                            ((Chat_ViewHolder)viewHolder).other_shared.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).other_shared_text.setVisibility(View.GONE);
                            ((Chat_ViewHolder)viewHolder).other_shared_time.setVisibility(View.GONE);
                            if(store.getStore_img().equals("")){
                                Glide.with(context).load(R.drawable.no_image).centerCrop().into(((Chat_ViewHolder)viewHolder).my_shared);
                            }else{
                                Glide.with(context).load(store.getStore_img()).thumbnail(0.2f).centerCrop().into(((Chat_ViewHolder)viewHolder).my_shared);

                            }
                            ((Chat_ViewHolder)viewHolder).my_shared_text.setText(store.getName());
                            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//                            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            double user_lat=37.486471;
                            double user_lon=126.971432;

//        user_lat = location.getLatitude();
//        user_lon = location.getLongitude();
                            ((Chat_ViewHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
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
                }else{

                ((Chat_ViewHolder)viewHolder).othermessage.setVisibility(View.GONE);
                ((Chat_ViewHolder)viewHolder).profile.setVisibility(View.GONE);
                ((Chat_ViewHolder)viewHolder).nick.setVisibility(View.GONE);
                ((Chat_ViewHolder)viewHolder).my_message.setVisibility(View.VISIBLE);
                ((Chat_ViewHolder)viewHolder).my_time.setText(time.split(" ")[2]+" "+time.split(" ")[1]);
                ((Chat_ViewHolder)viewHolder).my_message.setText(message_models.get(i).getMessage());
                ((Chat_ViewHolder)viewHolder).con.setMinHeight(100);
                ((Chat_ViewHolder)viewHolder).my_time.setVisibility(View.VISIBLE);
                ((Chat_ViewHolder)viewHolder).other_time.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).my_shared_text.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).my_shared.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).my_shared_time.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).other_shared.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).other_shared_text.setVisibility(View.GONE);
                    ((Chat_ViewHolder)viewHolder).other_shared_time.setVisibility(View.GONE);


                }

            }
        }
    }
    @Override
    public int getItemCount() {
        return message_models.size();
    }

    public class  Chat_ViewHolder extends RecyclerView.ViewHolder{
        ImageView profile,my_shared,other_shared;
        TextView nick,othermessage,my_message,my_time,other_time,my_shared_text,my_shared_time,other_shared_text,other_shared_time;
        ConstraintLayout con;
        public Chat_ViewHolder(@NonNull View itemView) {
            super(itemView);
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

    @Override
    public long getItemId(int position) {
        return message_models.get(position).getNno();
    }
}
