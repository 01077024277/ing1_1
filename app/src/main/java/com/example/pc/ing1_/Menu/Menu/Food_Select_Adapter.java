package com.example.pc.ing1_.Menu.Menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.ing1_.R;

import java.util.ArrayList;

public class Food_Select_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Food_info2> food_infos;
    Context context;
    public itemClick Click;
    double ccal;
    double carbp,proteinp,fatp;
    public interface itemClick{
        public void click(View v , int position);
        public void Dclick(View v,int position);
    }
    public void CClick(itemClick itemClick){
        this.Click=itemClick;
    }
    public class  ViewHolder_select extends RecyclerView.ViewHolder{
        TextView name,exit,size,cal,carb,protein,fat;
        public ViewHolder_select(@NonNull View itemView) {
            super(itemView);
            size=itemView.findViewById(R.id.food_info);
            name=itemView.findViewById(R.id.food_name);
            carb=itemView.findViewById(R.id.carb);
            protein=itemView.findViewById(R.id.protein);
            fat=itemView.findViewById(R.id.fat);
            exit=itemView.findViewById(R.id.exit);
            cal=itemView.findViewById(R.id.cal);
        }
    }
    public Food_Select_Adapter(Context context, ArrayList<Food_info2> food_infos,double ccal,String value){
        this.context=context;
        this.food_infos=food_infos;
        this.ccal =ccal;
        if(value.equals("체중 증가")){
            carbp=(ccal/10)*4/4;
            proteinp=(ccal/10)*4/4;
            fatp=(ccal/10)*2/9;
        }else if (value.equals("체중 감소")){
            carbp=(ccal/10)*3/4;
            proteinp=(ccal/10)*4/4;
            fatp=(ccal/10)*3/9;
        }else{
            carbp=(ccal/10)*5/4;
            proteinp=(ccal/10)*3/4;
            fatp=(ccal/10)*2/9;
        }


    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(context).inflate(R.layout.select_item,viewGroup,false);
        ViewHolder_select viewHolder_select=new ViewHolder_select(v);
        return viewHolder_select;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ((ViewHolder_select)viewHolder).name.setText(food_infos.get(i).getFood_info().getName());
        String size1=food_infos.get(i).getFood_info().getNum()+food_infos.get(i).getFood_info().getSize();
        if(size1.equals("1회")){

        }else {
            size1 = size1.replace("(", "").replace(")", "").replace("1회", "");
        }
        if(food_infos.get(i).getFood_info().getSize().equals("g")&&food_infos.get(i).getFood_info().getNum()==1){
            ((ViewHolder_select)viewHolder).size.setText(food_infos.get(i).getFood_info().getNum()*food_infos.get(i).getNum()+"g");
//            ((ViewHolder_select)viewHolder).info.setText(Math.round(food_infos.get(i).getFood_info().getCal()*food_infos.get(i).getNum()*100)/100.0+"kcal");
//            ((ViewHolder_select)viewHolder).info.setText("탄 "+(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getCarb())/carbp*100+" 단" +(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getProtein())/proteinp*100+" 지"+(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getFat())/fatp*100);

            //소수점 자리 수정하기

        }
        else if(food_infos.get(i).getG()==0){
            ((ViewHolder_select)viewHolder).size.setText(food_infos.get(i).getNum()+" 회  " +"(1회 = "+size1+")");
//            ((ViewHolder_select)viewHolder).info.setText((food_infos.get(i).getFood_info().getCal()*food_infos.get(i).getNum())+"kcal");
//            ((ViewHolder_select)viewHolder).info.setText("탄 "+(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getCarb())/carbp*100+" 단" +(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getProtein())/proteinp*100+" 지"+(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getFat())/fatp*100);

        }else{
            ((ViewHolder_select)viewHolder).size.setText(food_infos.get(i).getNum()+" 회  " +"약 "+Math.round(food_infos.get(i).getG()*food_infos.get(i).getNum()*100)/100.0+"g"+"  (1회 = "+size1+") ");
//            ((ViewHolder_select)viewHolder).info.setText((food_infos.get(i).getFood_info().getCal()*food_infos.get(i).getNum())+"kcal");
//            ((ViewHolder_select)viewHolder).info.setText("탄 "+(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getCarb())/carbp*100+" 단" +(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getProtein())/proteinp*100+" 지"+(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getFat())/fatp*100);

        }
        ((ViewHolder_select)viewHolder).carb.setText((int)Math.floor(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getCarb()/carbp*100)+" %");
        ((ViewHolder_select)viewHolder).protein.setText((int)Math.floor(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getProtein()/proteinp*100)+" %");
        ((ViewHolder_select)viewHolder).fat.setText((int)Math.floor(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getFat()/fatp*100)+" %");
        ((ViewHolder_select)viewHolder).cal.setText((int)Math.floor(food_infos.get(i).getNum()*food_infos.get(i).getFood_info().getCal()/ccal*100)+" %");

        Log.d("이게뭐지",food_infos.get(i).getNum()+" "+food_infos.get(i).getFood_info().getCarb()+" "+carbp);
        ((ViewHolder_select)viewHolder).exit.setText("X");
        ((ViewHolder_select)viewHolder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Click!=null){
                    Click.click(v,i);
                }
            }
        });
        ((ViewHolder_select)viewHolder).exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Click!=null){
                    Click.Dclick(v,i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return food_infos.size();
    }
}
