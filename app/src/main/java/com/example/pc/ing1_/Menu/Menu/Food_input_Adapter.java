package com.example.pc.ing1_.Menu.Menu;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.ing1_.R;

import java.util.List;

public class Food_input_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Food_list_item> food_list_items;
    int FOOTER=1;
    int item=0;
    int a=0;


    public  Food_List_Click food_list_click;
    public interface Food_List_Click{
        public void Next_click(View v ,int position);
        public void item_click(View v ,int position);
    }
    public void setItemClicke(Food_List_Click food_list_click){
        this.food_list_click=food_list_click;
    }


    public Food_input_Adapter(Context context, List<Food_list_item> food_list_items){
        this.context=context;
        this.food_list_items=food_list_items;
        FOOTER=food_list_items.size()+1;
        Log.d("리사이클러뷰","생성");

        Log.d("qweqwe",""+food_list_items.size());
    }
    public class Food_input_ViewHoler extends RecyclerView.ViewHolder {
        TextView food_name,food_info,info;
        public Food_input_ViewHoler(@NonNull View itemView) {
            super(itemView);
            Log.d( "qweqweqwe: ","qqqqqq");
            food_name=itemView.findViewById(R.id.food_name);
            food_info=itemView.findViewById(R.id.food_info);
            info=itemView.findViewById(R.id.info);
            Log.d("리사이클러뷰","홀더");

        }
    }

    public class Food_input_ViewHoler_F extends RecyclerView.ViewHolder {
        TextView food_name,food_info,info;
        public Food_input_ViewHoler_F(@NonNull View itemView) {
            super(itemView);
            Log.d( "qweqweqwe: ","qqqqqq");
            food_name=itemView.findViewById(R.id.food_name);
            food_info=itemView.findViewById(R.id.food_info);
            info=itemView.findViewById(R.id.info);
            Log.d("리사이클러뷰","푸터");

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("리사이클러뷰","온크리");

        if(i==FOOTER){
            View v = LayoutInflater.from(context).inflate(R.layout.food_list_lnfo,viewGroup,false);
            Food_input_ViewHoler_F viewHolder_food=new Food_input_ViewHoler_F(v);
            return viewHolder_food;
        }else {
            View v = LayoutInflater.from(context).inflate(R.layout.food_list_lnfo, viewGroup, false);
            Food_input_ViewHoler viewHolder_food = new Food_input_ViewHoler(v);
            return viewHolder_food;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final int pos=i;
        Log.d("리사이클러뷰","뷰홀더");

        if (viewHolder instanceof Food_input_ViewHoler_F) {
            ((Food_input_ViewHoler_F)viewHolder).food_name.setVisibility(View.GONE);
            ((Food_input_ViewHoler_F)viewHolder).info.setVisibility(View.GONE);
            ((Food_input_ViewHoler_F)viewHolder).food_info.setVisibility(View.VISIBLE);

            ((Food_input_ViewHoler_F)viewHolder).food_info.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
            ((Food_input_ViewHoler_F)viewHolder).food_info.setTextSize(20f);
            ((Food_input_ViewHoler_F)viewHolder).food_info.setText("더보기");
            ((Food_input_ViewHoler_F)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    food_list_click.Next_click(v,pos);
                }
            });
        } else {
            ((Food_input_ViewHoler)viewHolder).food_name.setVisibility(View.VISIBLE);
            ((Food_input_ViewHoler)viewHolder).info.setVisibility(View.VISIBLE);
            ((Food_input_ViewHoler)viewHolder).food_info.setVisibility(View.VISIBLE);
            ((Food_input_ViewHoler)viewHolder).food_name.setText(food_list_items.get(i).getName());
            ((Food_input_ViewHoler)viewHolder).food_info.setText(food_list_items.get(i).getNum()+""+food_list_items.get(i).getSize()+"당  "+food_list_items.get(i).getKcal()+" kcal");
            ((Food_input_ViewHoler)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    food_list_click.item_click(v,pos);
                }
            });
        }
    }


//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
////        if(i==food_list_items.size()+1){
////            ((Food_input_ViewHoler) viewHolder).textView.setText("더보기");
////
////        }else {
//        Log.d("qweqweqwe",food_list_items.get(i).getName());
//            ((Food_input_ViewHoler) viewHolder).textView.setText(food_list_items.get(i).getName());
////        }
//    }

    @Override
    public int getItemViewType(int position) {
        Log.d("리사이클러뷰","겟뷰타입");

        if(position==food_list_items.size()){

            return FOOTER;
        }
            return item;

    }

    @Override
    public int getItemCount() {
        if(Food_input.max==food_list_items.size()){
            return food_list_items.size() ;
        }
            else{
            return food_list_items.size() + 1;
        }
        }


}
