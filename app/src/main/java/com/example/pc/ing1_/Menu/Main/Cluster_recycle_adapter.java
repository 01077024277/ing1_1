package com.example.pc.ing1_.Menu.Main;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.Store;

import java.util.ArrayList;

public class Cluster_recycle_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Store> stores;
    Context context;
    private ItemClickListener itemClickListener;
    public interface ItemClickListener{
        void onItemClicke(View view,int position);
    }
    public void setOnClickListener(ItemClickListener listener){
        this.itemClickListener=listener;
    }

    public Cluster_recycle_adapter(ArrayList<Store> stores, Context context) {
        this.stores = stores;
        this.context = context;
    }
    public class ViewHolder_Cluster extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name ,store_address;

        public ViewHolder_Cluster(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.view);
            store_address=itemView.findViewById(R.id.Store_address);
//            imageView.getLayoutParams().width=120;
//            imageView.getLayoutParams().height=120;
//            imageView.requestLayout();
            name=itemView.findViewById(R.id.Store_name);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cluster_recycle,viewGroup,false);

        ViewHolder_Cluster viewHolder=new ViewHolder_Cluster(view);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final int position=i;
        if(!stores.get(i).getStore_img().equals("")){
            Glide.with(context).load(stores.get(i).getStore_img()).thumbnail(0.2f).override(80,80).into(((ViewHolder_Cluster)viewHolder).imageView);

        }
        else {
            Glide.with(context).load(R.drawable.no_image).thumbnail(0.2f).override(80,80).into(((ViewHolder_Cluster)viewHolder).imageView);

        }
        if(stores.get(i).getName().length()>=15){
            ((ViewHolder_Cluster)viewHolder).name.setText(stores.get(i).getName());
            ((ViewHolder_Cluster)viewHolder).name.setTextSize(14f);

        }else{
            ((ViewHolder_Cluster)viewHolder).name.setText(stores.get(i).getName());

        }
        ((ViewHolder_Cluster)viewHolder).store_address.setText(stores.get(i).getNew_address());
        ((ViewHolder_Cluster)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.onItemClicke(v,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return stores.size();
    }


}
