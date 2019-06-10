package com.example.pc.ing1_.Menu.Menu;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Calendar_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Grid_Item> arrayList;
    private LayoutInflater inflater;
    Calendar calendar;
    SimpleDateFormat format1;
    int formatted;
    public Calendar_Adapter(Context context,ArrayList<Grid_Item> arrayList){

        this.context=context;
        this.arrayList=arrayList;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        calendar= Calendar.getInstance();
        format1 = new SimpleDateFormat("yyyy-MM-dd");
        formatted = Integer.parseInt(format1.format(calendar.getTime()).replace("-",""));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.calendar_item, parent, false);
        }
        TextView textView=convertView.findViewById(R.id.day);
        ImageView imageView=convertView.findViewById(R.id.plus);

//        calendar.add(Calendar.DATE,1);


        if(arrayList.get(position)==null){
            textView.setText("");
            imageView.setVisibility(View.INVISIBLE);
        }else if(formatted<Integer.parseInt(arrayList.get(position).getFomat().replace("-",""))){
            imageView.setVisibility(View.INVISIBLE);
            textView.setText(arrayList.get(position).day + "");
            if (arrayList.get(position).day_week==1) {
                textView.setTextColor(Color.parseColor("#ff0000"));
            } else if (arrayList.get(position).day_week==7) {
                textView.setTextColor(Color.parseColor("#0000ff"));
            }
            if (position%7==0) {
                textView.setTextColor(Color.parseColor("#ff0000"));
            } else if (position%7==6) {
                textView.setTextColor(Color.parseColor("#0000ff"));
            }
        }
        else {
            if (arrayList.get(position).day.equals("일")||arrayList.get(position).day_week==1) {
                textView.setTextColor(Color.parseColor("#ff0000"));
            } else if (arrayList.get(position).day.equals("토")||arrayList.get(position).day_week==7) {
                textView.setTextColor(Color.parseColor("#0000ff"));
            }
            //요일 표현(사이즈)
            if(arrayList.get(position).day.equals("월")||arrayList.get(position).day.equals("화")||arrayList.get(position).day.equals("수")||
            arrayList.get(position).day.equals("목")||arrayList.get(position).day.equals("금")||arrayList.get(position).day.equals("토")||
                    arrayList.get(position).day.equals("일")){
                imageView.setVisibility(View.GONE);
                RelativeLayout rr = convertView.findViewById(R.id.rr);
                ViewGroup.LayoutParams params=rr.getLayoutParams();
                params.height=70;
                rr.setLayoutParams(params);
                rr.setGravity(Gravity.CENTER_VERTICAL);
            }
            //오늘 일자에 백그라운드 색 표시
            if(formatted==Integer.parseInt(arrayList.get(position).getFomat().replace("-",""))){
                RelativeLayout rr = convertView.findViewById(R.id.rr);
                rr.setBackgroundResource(R.drawable.border_d8d8d8);
            }

            textView.setText(arrayList.get(position).day + "");
        }
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {

        if(arrayList.get(position)==null||arrayList.get(position).day.equals("월")||arrayList.get(position).day.equals("화")||arrayList.get(position).day.equals("수")||
                arrayList.get(position).day.equals("목")||arrayList.get(position).day.equals("금")||arrayList.get(position).day.equals("토")||
                arrayList.get(position).day.equals("일")){
            return false;

        }else if (formatted<Integer.parseInt(arrayList.get(position).getFomat().replace("-",""))){
            return false;
        }
        else{
            return true;
        }
    }

}
