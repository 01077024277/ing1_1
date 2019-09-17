package com.example.pc.ing1_.Menu.Menu;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pc.ing1_.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Calendar_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Grid_Item> arrayList;
    private LayoutInflater inflater;
    Calendar calendar;
    SimpleDateFormat format1;
    HashMap<String,String > hashMap;
    int formatted;
    public Calendar_Adapter(Context context,ArrayList<Grid_Item> arrayList,HashMap<String,String> hashMap){

        this.context=context;
        this.arrayList=arrayList;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        calendar= Calendar.getInstance();
        format1 = new SimpleDateFormat("yyyy-MM-dd");
        formatted = Integer.parseInt(format1.format(calendar.getTime()).replace("-",""));
        this.hashMap=hashMap;
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
        TextView carb=convertView.findViewById(R.id.carb);
        TextView carb_info=convertView.findViewById(R.id.carb_info);
        TextView protein=convertView.findViewById(R.id.protein);
        TextView protein_info=convertView.findViewById(R.id.protein_info);
        TextView fat=convertView.findViewById(R.id.fat);
        TextView fat_info=convertView.findViewById(R.id.fat_info);
        TextView cal=convertView.findViewById(R.id.cal);
        TextView cal_info=convertView.findViewById(R.id.cal_info);

//        calendar.add(Calendar.DATE,1);


        //날짜 맞추기용 널 == 이미지 숨김
        if(arrayList.get(position)==null){
            textView.setText("");
            imageView.setVisibility(View.INVISIBLE);
        }
//        else if(formatted<Integer.parseInt(arrayList.get(position).getFomat().replace("-",""))){
//            imageView.setVisibility(View.INVISIBLE);
//            textView.setText(arrayList.get(position).day + "");
//            if (arrayList.get(position).day_week==1) {
//                textView.setTextColor(Color.parseColor("#ff0000"));
//            } else if (arrayList.get(position).day_week==7) {
//                textView.setTextColor(Color.parseColor("#0000ff"));
//            }
//            if (position%7==0) {
//                textView.setTextColor(Color.parseColor("#ff0000"));
//            } else if (position%7==6) {
//                textView.setTextColor(Color.parseColor("#0000ff"));
//            }
//        }
        else {
            if (arrayList.get(position).day.equals("일")||arrayList.get(position).day_week==1) {
                textView.setTextColor(Color.parseColor("#ff0000"));
            } else if (arrayList.get(position).day.equals("토")||arrayList.get(position).day_week==7) {
                textView.setTextColor(Color.parseColor("#0000ff"));
            }else{
                textView.setTextColor(Color.parseColor("#000000"));
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
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
            }else {
                if(hashMap.get(arrayList.get(position).getFomat())!=null){
                    imageView.setVisibility(View.GONE);
                    carb.setVisibility(View.VISIBLE);
                    carb_info.setVisibility(View.VISIBLE);
                    protein.setVisibility(View.VISIBLE);
                    protein_info.setVisibility(View.VISIBLE);
                    fat.setVisibility(View.VISIBLE);
                    fat_info.setVisibility(View.VISIBLE);
                    cal.setVisibility(View.VISIBLE);
                    cal_info.setVisibility(View.VISIBLE);
                    String value = hashMap.get(arrayList.get(position).getFomat());
                    carb_info.setTextColor(Color.parseColor("#00ff99"));
                    fat_info.setTextColor(Color.parseColor("#ff9900"));
                    protein_info.setTextColor(Color.parseColor("#ff0099"));
                    cal_info.setTextColor(Color.parseColor("#00b3ff"));
                    cal_info.setText(" "+(int)(Double.parseDouble(value.split(" ")[0].toString()))+" %");
                    carb_info.setText(" "+(int)(Double.parseDouble(value.split(" ")[1].toString()))+" %");
                    protein_info.setText(" "+(int)(Double.parseDouble(value.split(" ")[2]))+" %");
                    fat_info.setText(" "+(int)(Double.parseDouble(value.split(" ")[3]))+" %");


                }else{
                    imageView.setVisibility(View.VISIBLE);
                    carb.setVisibility(View.GONE);
                    carb_info.setVisibility(View.GONE);
                    protein.setVisibility(View.GONE);
                    protein_info.setVisibility(View.GONE);
                    fat.setVisibility(View.GONE);
                    fat_info.setVisibility(View.GONE);
                    cal.setVisibility(View.GONE);
                    cal_info.setVisibility(View.GONE);
                }

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

        }
//        else if (formatted<Integer.parseInt(arrayList.get(position).getFomat().replace("-",""))){
//            return false;
//        }
        else{
            return true;
        }
    }

}
