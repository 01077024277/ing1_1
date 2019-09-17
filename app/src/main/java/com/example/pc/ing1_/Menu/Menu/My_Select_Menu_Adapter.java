package com.example.pc.ing1_.Menu.Menu;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.ing1_.R;

import java.util.ArrayList;

public class My_Select_Menu_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Food_info> food_infos;
    Context context;
    public ClikeItem clikeItem;
    public interface  ClikeItem{
        public void removeItem(View v ,int i);
    }
    public void SetOnitemClick(ClikeItem item){
        this.clikeItem=item;
    }
    public My_Select_Menu_Adapter(ArrayList<Food_info> arrayList, Context context){
        this.food_infos=arrayList;
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.search,viewGroup,false);
        ViewHoldre viewHolder_select=new ViewHoldre(v);
        return viewHolder_select;    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        ((ViewHoldre)viewHolder).food_name.setText(food_infos.get(i).getName());

        ((ViewHoldre)viewHolder).exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clikeItem.removeItem(v,i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return food_infos.size();
    }
    public class ViewHoldre extends RecyclerView.ViewHolder{

        TextView food_name,food_info,exit;
        public ViewHoldre(@NonNull View itemView) {
            super(itemView);
            food_name=itemView.findViewById(R.id.food_name);
            food_info=itemView.findViewById(R.id.food_info);
            exit=itemView.findViewById(R.id.exit);
        }
    }
}
