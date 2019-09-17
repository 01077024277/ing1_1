package com.example.pc.ing1_.Menu.Menu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.ing1_.R;

import java.util.ArrayList;

public class Recommend_Total_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    int Foot=3;
    ArrayList<Food_info2> food_infos;
    double cal,carb,protein,fat;
    int total_size;
    public ItemClick itemClick;
    public interface ItemClick{
        public void onClick(View v ,int i);
        public void addClick(View v,int i);
    }
    public void setItemClick(ItemClick itemClick){
        this.itemClick=itemClick;
    }
    public Recommend_Total_Adapter(ArrayList<Food_info2> food_infos,double cal,double carb,double protein,double fat,int total_size) {
        this.food_infos=food_infos;
        this.cal=cal;
        this.carb=carb;
        this.protein=protein;
        this.fat=fat;
        this.total_size=total_size;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i!=3) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recommend_total_item, viewGroup, false);
            ViewHolder1 viewHolder1 = new ViewHolder1(view);
            return viewHolder1;

        }else{
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recommend_total_item, viewGroup, false);
            ViewHolder_F viewHolder1 = new ViewHolder_F(view);
            return viewHolder1;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        if(viewHolder instanceof ViewHolder_F){
            ((ViewHolder_F)viewHolder).info.setVisibility(View.GONE);

            ((ViewHolder_F)viewHolder).name.setVisibility(View.VISIBLE);
            ((ViewHolder_F)viewHolder).name.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
            ((ViewHolder_F)viewHolder).name.setTextSize(20f);
            ((ViewHolder_F)viewHolder).name.setText("더보기");

            ((ViewHolder_F)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.addClick(v,i);
                }
            });


        }else if (food_infos.size()!=0){

            ((ViewHolder1)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        itemClick.onClick(v,i);


                }
            });

            ((ViewHolder1)viewHolder).name.setText(food_infos.get(i).getFood_info().getName());
            ((ViewHolder1)viewHolder).info.setText(food_infos.get(i).getFood_info().getCal()+"");
            ((ViewHolder1)viewHolder).name.setVisibility(View.VISIBLE);
            ((ViewHolder1)viewHolder).info.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public int getItemViewType(int position) {
        if(food_infos.size()!=total_size && position==food_infos.size()){
            return 3;
        }else{
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        if(total_size>food_infos.size()) {
            return food_infos.size() + 1;
        }else{
            return food_infos.size();
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView name,info;

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.food_name);
            info=itemView.findViewById(R.id.food_info);

        }
    }
    class ViewHolder_F extends  RecyclerView.ViewHolder{
        TextView name,info;

        public ViewHolder_F(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.food_name);
            info=itemView.findViewById(R.id.food_info);

        }
    }
}
