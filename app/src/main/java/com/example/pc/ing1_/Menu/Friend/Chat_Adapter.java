package com.example.pc.ing1_.Menu.Friend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Chat_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<Message_model> message_models;
    User other_user;
    Chat_Adapter(Context context,ArrayList<Message_model> message_models,User user){
        this.context=context;
        this.message_models=message_models;
        this.other_user=user;

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
                ((Chat_ViewHolder)viewHolder).other_time.setText(time.split(" ")[2]+" "+time.split(" ")[1]);
                ((Chat_ViewHolder) viewHolder).profile.setImageBitmap(other_user.getBitmap());
                ((Chat_ViewHolder)viewHolder).othermessage.setVisibility(View.VISIBLE);
                ((Chat_ViewHolder)viewHolder).profile.setVisibility(View.VISIBLE);
                ((Chat_ViewHolder)viewHolder).nick.setVisibility(View.VISIBLE);
                ((Chat_ViewHolder)viewHolder).my_message.setVisibility(View.GONE);
                ((Chat_ViewHolder)viewHolder).my_time.setVisibility(View.GONE);
                ((Chat_ViewHolder)viewHolder).other_time.setVisibility(View.VISIBLE);
                ((Chat_ViewHolder)viewHolder).con.setMinHeight(130);

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


            }
        }
    }
    @Override
    public int getItemCount() {
        return message_models.size();
    }

    public class  Chat_ViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView nick,othermessage,my_message,my_time,other_time;
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
        }
    }
}
