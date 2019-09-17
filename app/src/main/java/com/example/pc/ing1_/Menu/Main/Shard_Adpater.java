package com.example.pc.ing1_.Menu.Main;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Shard_Adpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int lastCheckedRB =-1;
    ArrayList<Shared_item_class> shared_item_classes;
    Context context;
    String my_nick,my_profile;

    public Shard_Adpater(ArrayList<Shared_item_class> shared_item_classes, Context context,String my_nick,String my_profile) {
        this.shared_item_classes = shared_item_classes;
        this.context = context;
        this.my_nick=my_nick;
        this.my_profile=my_profile;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v =LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_item,parent,false);
        Shard_Viewholder shard_viewholder=new Shard_Viewholder(v);
        return shard_viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Shard_Viewholder)holder).imageView.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Shard_Viewholder)holder).imageView.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Shard_Viewholder)holder).image2_1.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Shard_Viewholder)holder).image2_1.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Shard_Viewholder)holder).image2_2.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Shard_Viewholder)holder).image2_2.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Shard_Viewholder)holder).image3_1.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Shard_Viewholder)holder).image3_1.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Shard_Viewholder)holder).image3_2.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Shard_Viewholder)holder).image3_2.setClipToOutline(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ((Shard_Viewholder)holder).image3_3.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Shard_Viewholder)holder).image3_3.setClipToOutline(true);
        }
        if(shared_item_classes.get(position).isSingle()==true){
            ((Shard_Viewholder) holder).imageView.setVisibility(View.VISIBLE);
            ((Shard_Viewholder) holder).image2_1.setVisibility(View.GONE);
            ((Shard_Viewholder) holder).image2_2.setVisibility(View.GONE);
            ((Shard_Viewholder) holder).image3_1.setVisibility(View.GONE);
            ((Shard_Viewholder) holder).image3_2.setVisibility(View.GONE);
            ((Shard_Viewholder) holder).image3_3.setVisibility(View.GONE);
            Glide.with(context).load("http://54.180.168.210/profile/"+shared_item_classes.get(position).getImage()).thumbnail(0.3f).into(((Shard_Viewholder)holder).imageView);
            ((Shard_Viewholder)holder).title.setText(shared_item_classes.get(position).getTitle());
//            ((Shard_Viewholder)holder).time.setText(shared_item_classes.get(position).getTime());
            ((Shard_Viewholder)holder).message.setText(shared_item_classes.get(position).getMessage());



        }else{




            String title = shared_item_classes.get(position).getTitle().replaceFirst(my_nick, "");
            try {


                if (title.substring(0, 1).equals(",")) {
                    title = title.replaceFirst(",", "");
                }
                if (title.substring(title.length() - 1).equals(",")) {
                    title = title.substring(0, title.length() - 1);
                }
                title = title.replace(",,", ",");
                ((Shard_Viewholder) holder).title.setText(title);
            }catch (StringIndexOutOfBoundsException e){
                ((Shard_Viewholder) holder).title.setText("대화상대 없음");

            }
            ((Shard_Viewholder) holder).message.setText(shared_item_classes.get(position).getMessage());
//            ((Shard_Viewholder) holder).time.setText(shared_item_classes.get(position).getTime());






            int num= shared_item_classes.get(position).getImage().split(",").length;
            String images = shared_item_classes.get(position).getImage().replace(my_profile+",","").replace(","+my_profile,"");

            if (num == 1) {
                ((Shard_Viewholder) holder).imageView.setVisibility(View.VISIBLE);
                ((Shard_Viewholder) holder).image2_1.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image2_2.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image3_1.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image3_2.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image3_3.setVisibility(View.GONE);
                for (int j=0;j<images.split(",").length;j++){
                    Glide.with(context).load(R.drawable.ic_user_1).thumbnail(0.1f).centerCrop().into(((Shard_Viewholder)holder).imageView);

                }
            } else if (num == 2) {
                ((Shard_Viewholder) holder).imageView.setVisibility(View.VISIBLE);
                ((Shard_Viewholder) holder).image2_1.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image2_2.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image3_1.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image3_2.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image3_3.setVisibility(View.GONE);

                for (int j=0;j<images.split(",").length;j++){
                    if(j==0){
                        Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Shard_Viewholder)holder).imageView);
                    }
                }

            } else if (num == 3) {
                ((Shard_Viewholder) holder).imageView.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image2_1.setVisibility(View.VISIBLE);
                ((Shard_Viewholder) holder).image2_2.setVisibility(View.VISIBLE);
                ((Shard_Viewholder) holder).image3_1.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image3_2.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image3_3.setVisibility(View.GONE);
                for (int j=0;j<images.split(",").length;j++){
                    if(j==0){
                        Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Shard_Viewholder)holder).image2_1);
                    }else if (j==1){
                        Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Shard_Viewholder)holder).image2_2);

                    }

                }
            } else {
                ((Shard_Viewholder) holder).imageView.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image2_1.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image2_2.setVisibility(View.GONE);
                ((Shard_Viewholder) holder).image3_1.setVisibility(View.VISIBLE);
                ((Shard_Viewholder) holder).image3_2.setVisibility(View.VISIBLE);
                ((Shard_Viewholder) holder).image3_3.setVisibility(View.VISIBLE);
                for (int j=0;j<images.split(",").length;j++){
                    if(j==0){
                        Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Shard_Viewholder)holder).image3_1);
                    }else if (j==1){

                        Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Shard_Viewholder)holder).image3_2);

                    }else if(j==2){
                        Glide.with(context).load("http://54.180.168.210/profile/"+images.split(",")[j]).thumbnail(0.1f).centerCrop().into(((Shard_Viewholder)holder).image3_3);

                    }
                }
            }
        }



        ((Shard_Viewholder)holder).radioButton.setChecked(lastCheckedRB==position);
        
        
        
    }
    public int SCheck(){
        return lastCheckedRB;
    }

    @Override
    public int getItemCount() {
        return shared_item_classes.size();
    }

    class Shard_Viewholder extends RecyclerView.ViewHolder{
        TextView title, message,  read;
        RadioButton radioButton;
        ImageView imageView, image2_1, image2_2, image3_1, image3_2, image3_3;
        public Shard_Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
            read = itemView.findViewById(R.id.read);
            radioButton=itemView.findViewById(R.id.radio);
            imageView = itemView.findViewById(R.id.image);
            image2_1 = itemView.findViewById(R.id.image2_1);
            image2_2 = itemView.findViewById(R.id.image2_2);
            image3_1 = itemView.findViewById(R.id.image3_1);
            image3_2 = itemView.findViewById(R.id.image3_2);
            image3_3 = itemView.findViewById(R.id.image3_3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedRB=getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastCheckedRB=getAdapterPosition();
                    notifyDataSetChanged();
                }
            });

        }
    }
}
