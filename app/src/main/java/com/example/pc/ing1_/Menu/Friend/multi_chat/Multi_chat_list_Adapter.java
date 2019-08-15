package com.example.pc.ing1_.Menu.Friend.multi_chat;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pc.ing1_.R;

import java.util.ArrayList;

public class Multi_chat_list_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Multi_Room_Item> multi_room_items;
    String my_nick;
    String my_profile;

    public interface Multi_chat_list_{
        void MultiClick_(View v,int i);
    }
    Multi_chat_list_ multi_chat_list_;
    public void Multi_Click(Multi_chat_list_ multi_chat_list_){
        this.multi_chat_list_=multi_chat_list_;
    }

    public Multi_chat_list_Adapter(Context context, ArrayList<Multi_Room_Item> multi_room_items, String my_nick, String my_profile) {
        this.context = context;
        this.multi_room_items = multi_room_items;
        this.my_nick = my_nick;
        this.my_profile = my_profile;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.room_item, viewGroup, false);
        Multi_chat_list_Viewholder multi_chat_list_viewholder = new Multi_chat_list_Viewholder(v);
        return multi_chat_list_viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        String title = multi_room_items.get(i).getTitle().replaceFirst(my_nick, "");
        try {


            if (title.substring(0, 1).equals(",")) {
                title = title.replaceFirst(",", "");
            }
            if (title.substring(title.length() - 1).equals(",")) {
                title = title.substring(0, title.length() - 1);
            }
            title = title.replace(",,", ",");
            ((Multi_chat_list_Viewholder) viewHolder).title.setText(title);
        }catch (StringIndexOutOfBoundsException e){
            ((Multi_chat_list_Viewholder) viewHolder).title.setText("대화상대 없음");

        }
        ((Multi_chat_list_Viewholder) viewHolder).message.setText(multi_room_items.get(i).getMessage());
        ((Multi_chat_list_Viewholder) viewHolder).time.setText(multi_room_items.get(i).getTime());





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Multi_chat_list_Viewholder)viewHolder).imageView.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Multi_chat_list_Viewholder)viewHolder).imageView.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Multi_chat_list_Viewholder)viewHolder).image2_1.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Multi_chat_list_Viewholder)viewHolder).image2_1.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Multi_chat_list_Viewholder)viewHolder).image2_2.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Multi_chat_list_Viewholder)viewHolder).image2_2.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Multi_chat_list_Viewholder)viewHolder).image3_1.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Multi_chat_list_Viewholder)viewHolder).image3_1.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Multi_chat_list_Viewholder)viewHolder).image3_2.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Multi_chat_list_Viewholder)viewHolder).image3_2.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Multi_chat_list_Viewholder)viewHolder).image3_3.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Multi_chat_list_Viewholder)viewHolder).image3_3.setClipToOutline(true);
        }
//
//
//
//
//
//
//






        int num= multi_room_items.get(i).getImages().split(",").length;
        String images = multi_room_items.get(i).getImages().replace(my_profile+",","").replace(","+my_profile,"");

        if (num == 1) {
            ((Multi_chat_list_Viewholder) viewHolder).imageView.setVisibility(View.VISIBLE);
            ((Multi_chat_list_Viewholder) viewHolder).image2_1.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image2_2.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_1.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_2.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_3.setVisibility(View.GONE);
            for (int j=0;j<images.split(",").length;j++){
                Glide.with(context).load(R.drawable.ic_user_1).thumbnail(0.1f).centerCrop().into(((Multi_chat_list_Viewholder)viewHolder).imageView);

            }
        } else if (num == 2) {
            ((Multi_chat_list_Viewholder) viewHolder).imageView.setVisibility(View.VISIBLE);
            ((Multi_chat_list_Viewholder) viewHolder).image2_1.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image2_2.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_1.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_2.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_3.setVisibility(View.GONE);

            for (int j=0;j<images.split(",").length;j++){
                if(j==0){
                    Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Multi_chat_list_Viewholder)viewHolder).imageView);
                }
            }

        } else if (num == 3) {
            ((Multi_chat_list_Viewholder) viewHolder).imageView.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image2_1.setVisibility(View.VISIBLE);
            ((Multi_chat_list_Viewholder) viewHolder).image2_2.setVisibility(View.VISIBLE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_1.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_2.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_3.setVisibility(View.GONE);
            for (int j=0;j<images.split(",").length;j++){
                if(j==0){
                 Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Multi_chat_list_Viewholder)viewHolder).image2_1);
                }else if (j==1){
                    Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Multi_chat_list_Viewholder)viewHolder).image2_2);

                }

            }
        } else {
            ((Multi_chat_list_Viewholder) viewHolder).imageView.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image2_1.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image2_2.setVisibility(View.GONE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_1.setVisibility(View.VISIBLE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_2.setVisibility(View.VISIBLE);
            ((Multi_chat_list_Viewholder) viewHolder).image3_3.setVisibility(View.VISIBLE);
            for (int j=0;j<images.split(",").length;j++){
                if(j==0){
                    Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Multi_chat_list_Viewholder)viewHolder).image3_1);
                }else if (j==1){

                    Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Multi_chat_list_Viewholder)viewHolder).image3_2);

                }else if(j==2){
                    Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Multi_chat_list_Viewholder)viewHolder).image3_3);

                }
            }
        }
        ((Multi_chat_list_Viewholder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multi_chat_list_.MultiClick_(v,i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return multi_room_items.size();
    }

    public class Multi_chat_list_Viewholder extends RecyclerView.ViewHolder {

        TextView title, message, time, read;
        ImageView imageView, image2_1, image2_2, image3_1, image3_2, image3_3;

        public Multi_chat_list_Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            read = itemView.findViewById(R.id.read);
            imageView = itemView.findViewById(R.id.image);
            image2_1 = itemView.findViewById(R.id.image2_1);
            image2_2 = itemView.findViewById(R.id.image2_2);
            image3_1 = itemView.findViewById(R.id.image3_1);
            image3_2 = itemView.findViewById(R.id.image3_2);
            image3_3 = itemView.findViewById(R.id.image3_3);
        }
    }
}
