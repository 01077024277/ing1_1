package com.example.pc.ing1_.Menu.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.Store;

import java.util.List;

public class Store_recycle_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Store> store_item;
    Context context;
    private ItemClickListener listener;
    public interface ItemClickListener{
        void onItemClicke(View view,int position);
    }
    public void setOnClickListener(ItemClickListener listener){
        this.listener=listener;
    }
    public class  ViewHolder_ extends RecyclerView.ViewHolder {
        TextView textView,rating_point,new_address,review_count;
        ImageView imageView;
        RatingBar ratingBar;
        public ViewHolder_(@NonNull View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.img);
            this.textView=itemView.findViewById(R.id.name);
            this.ratingBar=itemView.findViewById(R.id.rating);
            this.rating_point=itemView.findViewById(R.id.rating_point);
            this.new_address=itemView.findViewById(R.id.new_address);
            this.review_count=itemView.findViewById(R.id.review_count);


        }
//        public ViewHolder_(@NonNull View itemView,int i){
//            super(itemView);
//            this.imageView=itemView.findViewById(R.id.view);
////            this.imageView.setImageResource(R.drawable.user);
//
//        }
    }
    public Store_recycle_Adapter(List<Store> store_item, Context context) {
        this.store_item = store_item;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        if(i==R.layout.store_recycle_item) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.store_recycle_item, viewGroup, false);

            ViewHolder_ viewHolder_ = new ViewHolder_(view);

            return viewHolder_;
//        }else{
//            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cluster_recycle, viewGroup, false);
//
//            ViewHolder_ viewHolder_ = new ViewHolder_(view,1);
//
//            return viewHolder_;
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//        if (i == store_item.size()) {
//            Toast.makeText(context, "Button Clicked", Toast.LENGTH_LONG).show();
//            ((ViewHolder_) viewHolder).imageView.setImageResource(R.drawable.user);
//            ((ViewHolder_) viewHolder).imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "Button Clicked", Toast.LENGTH_LONG).show();
//
//                }
//            });
//        } else {
            final int position = i;
            if (store_item.get(i).getName().length() >= 14) {
                ((ViewHolder_) viewHolder).textView.setText(store_item.get(i).getName());
                ((ViewHolder_) viewHolder).textView.setTextSize(18f);
            } else {
                ((ViewHolder_) viewHolder).textView.setText(store_item.get(i).getName());
                ((ViewHolder_) viewHolder).textView.setTextSize(30f);
            }
            if (!store_item.get(i).getStore_img().equals("")) {
                Glide.with(context).load(store_item.get(i).getStore_img()).error(R.drawable.no_image).override(150, 150).centerCrop().into(((ViewHolder_) viewHolder).imageView);

            } else {
                Glide.with(context).load(R.drawable.no_image).error(R.drawable.no_image).override(150, 150).centerCrop().into(((ViewHolder_) viewHolder).imageView);

            }

            ((ViewHolder_) viewHolder).new_address.setText(store_item.get(i).getNew_address());

            int num = 0;
            int point = 0;

            point = store_item.get(i).getPoint();
            num = store_item.get(i).getReview() * 5;


            double total = (Double.parseDouble(String.valueOf(point)) / Double.parseDouble(String.valueOf(num))) * 5;
            total = (Math.round(total * 10) / 10.0);
            ((ViewHolder_) viewHolder).ratingBar.setRating((float) total);
            ((ViewHolder_) viewHolder).rating_point.setText("" + total);
            ((ViewHolder_) viewHolder).review_count.setText(store_item.get(i).getReview() + " 건");

            ((ViewHolder_) viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClicke(v, position);
                    }

                }
            });


//        }
    }

    @Override
    public int getItemCount() {
//        return store_item.size()+1;
        return store_item.size();

    }

//    @Override
//    public int getItemViewType(int position) {
//        return (position == store_item.size()) ? R.layout.cluster_recycle : R.layout.store_recycle_item;
//    }
}
