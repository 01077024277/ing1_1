package com.example.pc.ing1_.Menu.Friend.multi_chat;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.Menu.Friend.Message_model;
import com.example.pc.ing1_.R;
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

    Multi_Char_Adapter(Context context, ArrayList<User> users , int my , ArrayList<Message_model> message_models){
        this.context=context;
        this.users=users;
        this.myuser=my;
        this.message_models=message_models;
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

        if(message_models.get(i).getMessage().equals("")){
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
            ((Mult_Chat_ViewHolder)viewHolder).othermessage.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).profile.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).nick.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).my_message.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).my_time.setText(time.split(" ")[2]+" "+time.split(" ")[1]);
            ((Mult_Chat_ViewHolder)viewHolder).my_message.setText(message_models.get(i).getMessage());
            ((Mult_Chat_ViewHolder)viewHolder).con.setMinHeight(100);
            ((Mult_Chat_ViewHolder)viewHolder).my_time.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).other_time.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).sys_message.setVisibility(View.GONE);

        }else{
            ((Mult_Chat_ViewHolder)viewHolder).other_time.setText(time.split(" ")[2]+" "+time.split(" ")[1]);
            ((Mult_Chat_ViewHolder)viewHolder).othermessage.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).profile.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).nick.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).my_message.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).my_time.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).sys_message.setVisibility(View.GONE);
            ((Mult_Chat_ViewHolder)viewHolder).other_time.setVisibility(View.VISIBLE);
            ((Mult_Chat_ViewHolder)viewHolder).con.setMinHeight(130);
            for(int j=0;j<users.size();j++){
                if(message_models.get(i).getNo()==users.get(j).getNo()){
                    ((Mult_Chat_ViewHolder) viewHolder).othermessage.setText(message_models.get(i).getMessage());
                    ((Mult_Chat_ViewHolder) viewHolder).nick.setText("" + users.get(j).getName());
                    ((Mult_Chat_ViewHolder) viewHolder).profile.setImageBitmap(users.get(j).getBitmap());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return message_models.size();
    }

    class Mult_Chat_ViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView nick,othermessage,my_message,my_time,other_time,sys_message;
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
        }
    }
}
