package com.example.pc.ing1_.Menu.Friend.multi_chat;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.User;

import java.util.ArrayList;

public class Friend_invite_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<User> users;
    ArrayList<User> checkUser;
    ArrayList<User> inusers;
    Friend_invite_Adapter(Context context,ArrayList<User> users,ArrayList<User> inusers){
        this.context=context;
        this.users=users;
        checkUser=new ArrayList<>();
        this.inusers=inusers;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.invite_item,viewGroup,false);
        Invite_ViewHolder invite_viewHolder=new Invite_ViewHolder(v);
        return  invite_viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Invite_ViewHolder)viewHolder).imageView.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Invite_ViewHolder)viewHolder).imageView.setClipToOutline(true);
        }
        if(inusers!=null){
            for(int j=0;j<inusers.size();j++){
                if(users.get(i).getNo()==inusers.get(j).getNo()){
                    ((Invite_ViewHolder)viewHolder).checkBox.setEnabled(false);
                }
            }
        }
        ((Invite_ViewHolder)viewHolder).textView.setText(users.get(i).getName());
        Glide.with(context).load("http://54.180.168.210/profile/"+users.get(i).getProfile()).thumbnail(0.1f).centerCrop().into(((Invite_ViewHolder)viewHolder).imageView);
        ((Invite_ViewHolder)viewHolder).checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Invite_ViewHolder)viewHolder).checkBox.isChecked()){
                    checkUser.add(users.get(i));
                }else{
                    for(int j=0;j<checkUser.size();j++){
                        if(checkUser.get(j).getNo()==users.get(i).getNo()){
                            checkUser.remove(j);
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public ArrayList<User> checkuser(){
        return checkUser;
    }

    public class Invite_ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        CheckBox checkBox;
        public Invite_ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.name);
            checkBox=itemView.findViewById(R.id.check);

        }
    }
}
