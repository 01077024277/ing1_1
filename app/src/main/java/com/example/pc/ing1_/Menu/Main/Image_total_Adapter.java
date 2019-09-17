package com.example.pc.ing1_.Menu.Main;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;

import java.util.ArrayList;

public class Image_total_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<String> imglist;
    static int num=1;
    private Image_total_Adapter.ItemClickListener itemClickListener;
    public interface ItemClickListener{
        void onItemClicke(View view,int position);
    }
    public void setOnClickListener(Image_total_Adapter.ItemClickListener listener){
        this.itemClickListener=listener;
    }

    public class ViewHolder_ extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder_(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.textView12);
        }
    }
    public Image_total_Adapter (){

    }
    public Image_total_Adapter (Context context, ArrayList<String> arrayList){
        this.context=context;
        this.imglist=arrayList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_total_recyclerview, viewGroup, false);
        ViewHolder_ viewHolder_= new ViewHolder_(view);
        return viewHolder_;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        Glide.with(context).load(imglist.get(i)).centerCrop().into(((ViewHolder_)viewHolder).imageView);
        if(num==i){
            Log.d("어댑터",""+num);
            ((ViewHolder_)viewHolder).textView.setBackgroundColor(Color.parseColor("#00ff00"));
        }else{
            ((ViewHolder_)viewHolder).textView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        ((ViewHolder_)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.onItemClicke(v,i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return imglist.size();
    }
}
