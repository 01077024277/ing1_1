package com.example.pc.ing1_.Menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.Store;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cluster_recycle_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Store> stores;
    Context context;

    public Cluster_recycle_adapter(ArrayList<Store> stores, Context context) {
        this.stores = stores;
        this.context = context;
    }
    public class ViewHolder_Cluster extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name ;

        public ViewHolder_Cluster(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.view);
            name=itemView.findViewById(R.id.Store_name);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custominfowindow,viewGroup,false);

        ViewHolder_Cluster viewHolder=new ViewHolder_Cluster(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Glide.with(context).load(stores.get(i).getStore_img()).into(((ViewHolder_Cluster)viewHolder).imageView);
        ((ViewHolder_Cluster)viewHolder).name.setText(stores.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return stores.size();
    }
}
