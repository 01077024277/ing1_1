package com.example.pc.ing1_.Menu.Friend;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;

import java.util.ArrayList;

public class Chat_list_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Room_Item> room_items;
    public interface Room_ClickListener{
        void Room(View v ,int i);
    }
    public Room_ClickListener room_clickListener;
    public void Room_AdapterClick(Room_ClickListener room_clickListener){
        this.room_clickListener=room_clickListener;
    }
    Chat_list_Adapter(Context context,ArrayList<Room_Item> room_items){
        this.context=context;
        this.room_items=room_items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.single_room_item,viewGroup,false);
        Chat_list_ViewHolder chat_list_viewHolder=new Chat_list_ViewHolder(v);

        return chat_list_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Chat_list_ViewHolder)viewHolder).imageView.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Chat_list_ViewHolder)viewHolder).imageView.setClipToOutline(true);
        }
        ((Chat_list_ViewHolder)viewHolder).title.setText(room_items.get(i).getUser_name());
        ((Chat_list_ViewHolder)viewHolder).message.setText(room_items.get(i).getMessage());
        ((Chat_list_ViewHolder)viewHolder).time.setText(room_items.get(i).getTime());
        ((Chat_list_ViewHolder)viewHolder).imageView.setVisibility(View.VISIBLE);
        Glide.with(context).load("http://54.180.168.210/profile/"+room_items.get(i).getUser_image()).thumbnail(0.2f).centerCrop().into(((Chat_list_ViewHolder)viewHolder).imageView);
        ((Chat_list_ViewHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(room_clickListener!=null) {
                    room_clickListener.Room(v, i);
                }
            }
        });
//        ((Chat_list_ViewHolder)viewHolder).
//        ((Chat_list_ViewHolder)viewHolder).
//        ((Chat_list_ViewHolder)viewHolder).
//        ((Chat_list_ViewHolder)viewHolder).

    }

    @Override
    public int getItemCount() {
        return room_items.size();
    }
    public class Chat_list_ViewHolder extends RecyclerView.ViewHolder{

        TextView title,message,time,read;
        ImageView imageView;
        public Chat_list_ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            message=itemView.findViewById(R.id.message);
            time=itemView.findViewById(R.id.time);
            read=itemView.findViewById(R.id.read);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
