package com.example.pc.ing1_.Menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.Cluster_Item;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.Store;

import java.util.ArrayList;
import java.util.List;

public class Store_recycle_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Store> store_item;
    Context context;
    public class  ViewHolder_ extends RecyclerView.ViewHolder {
        TextView textView,rating_point,new_address,old_address;
        ImageView imageView;
        RatingBar ratingBar;
        public ViewHolder_(@NonNull View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.img);
            this.textView=itemView.findViewById(R.id.name);
            this.ratingBar=itemView.findViewById(R.id.rating);
            this.rating_point=itemView.findViewById(R.id.rating_point);
            this.new_address=itemView.findViewById(R.id.new_address);
            this.old_address=itemView.findViewById(R.id.old_address);

        }
    }
    public Store_recycle_Adapter(List<Store> store_item, Context context) {
        this.store_item = store_item;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_recycle_item,viewGroup,false);

        ViewHolder_ viewHolder_=new ViewHolder_(view);

        return viewHolder_;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ViewHolder_)viewHolder).textView.setText(store_item.get(i).getName());
        Glide.with(context).load(store_item.get(i).getStore_img()).override(150,150).centerCrop().into(((ViewHolder_) viewHolder).imageView);
        ((ViewHolder_) viewHolder).old_address.setText(store_item.get(i).getOld_address());
        ((ViewHolder_) viewHolder).new_address.setText(store_item.get(i).getNew_address());

        int num = 0;
        int point=0;
        for(int j=0;j<store_item.get(i).getReview_item().size();j++){
            point = point+store_item.get(i).getReview_item().get(j).getPoint();
            num=num+5;
        }

        double  total = (Double.parseDouble(String.valueOf(point))/Double.parseDouble(String.valueOf(num)))*5;
        total=(Math.round(total*10)/10.0);
        ((ViewHolder_) viewHolder).ratingBar.setRating((float) total);
        ((ViewHolder_) viewHolder).rating_point.setText(""+total);




    }

    @Override
    public int getItemCount() {
        return store_item.size();

    }
}
