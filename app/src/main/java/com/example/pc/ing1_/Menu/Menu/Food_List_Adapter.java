package com.example.pc.ing1_.Menu.Menu;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.ing1_.R;

import java.util.List;

public class Food_List_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static String textcolor;
    List<String> food_list;
    Context context;
    int start;
    int end;
    SpannableString spannableString;
    private ItemClickeListener itemclick;
    public interface ItemClickeListener{
        public void onClick(View v,int position);
    }
    public void SetItemClick_j(ItemClickeListener itemclick){
        this.itemclick=itemclick;
    }

    public Food_List_Adapter(Context context,List<String> food_list) {
        this.food_list=food_list;
        this.context=context;


    }
    public class  ViewHolder_Food extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder_Food(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.food_name);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_list,viewGroup,false);
        ViewHolder_Food viewHolder_food=new ViewHolder_Food(v);
        return viewHolder_food;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
         start= food_list.get(i).toString().toLowerCase().indexOf(textcolor);
         end=start+textcolor.length();
        Log.d("김치list",food_list.get(i).toLowerCase());
        Log.d("김치text",textcolor);
        Log.d("김치",start+"   "+end);
        if(start>=0) {
            spannableString = new SpannableString(food_list.get(i));
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6702")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ((ViewHolder_Food)viewHolder).textView.setText(food_list.get(i));
            ((ViewHolder_Food) viewHolder).textView.setText(spannableString);
        }
        ((ViewHolder_Food)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemclick!=null){
                    itemclick.onClick(v,i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }
}
