package com.example.pc.ing1_.Menu.Friend;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.User;

import java.util.ArrayList;

public class Friend_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<User> users;
    public ProfileCilckListner profileCilckListner;
    public void Friend_AdapterClick (ProfileCilckListner profileCilckListner){
        this.profileCilckListner=profileCilckListner;
    }
    public interface ProfileCilckListner{
        void Pclick(View v,int i);
    }

    Friend_Adapter(Context context, ArrayList<User> users){
        this.context=context;
        this.users=users;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.friend_item,viewGroup,false);
        Firend_list_ViewHolder viewHolder_select=new Firend_list_ViewHolder(v);
        return viewHolder_select;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Firend_list_ViewHolder)viewHolder).imageView.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Firend_list_ViewHolder)viewHolder).imageView.setClipToOutline(true);
        }
        if(users.get(i).getProfile().equals("")){

        }else {
            Glide.with(context).load("http://54.180.168.210/profile/"+users.get(i).getProfile()).thumbnail(0.3f).centerCrop().into(((Firend_list_ViewHolder)viewHolder).imageView);
//
//            ((Firend_list_ViewHolder) viewHolder).imageView.setImageBitmap(users.get(i).getBitmap());
        }
        ((Firend_list_ViewHolder)viewHolder).textView.setText(users.get(i).getName());

        ((Firend_list_ViewHolder)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileCilckListner!=null) {
                    profileCilckListner.Pclick(v, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class Firend_list_ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public Firend_list_ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.text);
        }
    }
}
