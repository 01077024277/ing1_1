package com.example.pc.ing1_.Menu.Menu;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.Menu.Main.Store_recommend_Activity;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Schedule_Activity extends AppCompatActivity {
    RetrofitExService http;
    String day,phone;
    TextView title;
    TextView textView1,textView2,textView3,total1,total2,total3;
    LinearLayout linear1,linear2,linear3;
    Button button1,button2,button3;
    SharedPreferences sf;
    ArrayList<Food_info2> foodInfo1,foodInfo2,foodInfo3;
    String hight,weight,age,gender;
    Button store_recommed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);
        final Intent intent=getIntent();
        textView1=findViewById(R.id.text1);
        textView2=findViewById(R.id.text2);
        textView3=findViewById(R.id.text3);
        foodInfo1=new ArrayList<>();
        foodInfo2=new ArrayList<>();
        foodInfo3=new ArrayList<>();
        total1=findViewById(R.id.total1);
        total2=findViewById(R.id.total2);
        total3=findViewById(R.id.total3);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        store_recommed=findViewById(R.id.store_recommend);
        day=intent.getStringExtra("day");
        hight=intent.getStringExtra("height");
        weight=intent.getStringExtra("weight");
        age=intent.getStringExtra("age");
        gender=intent.getStringExtra("gender");
        sf = getSharedPreferences("login",MODE_PRIVATE);
        phone=sf.getString("phone","");
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        final HashMap<String,String> data=new HashMap<>();
        data.put("day",day);
        data.put("phone",sf.getString("phone",""));
        http.schedule_list(data).enqueue(new Callback<ArrayList<Food_info2>>() {
            @Override
            public void onResponse(Call<ArrayList<Food_info2>> call, Response<ArrayList<Food_info2>> response) {
                ArrayList<Food_info2> food_info2s=response.body();
                for(int i=0;i<food_info2s.size();i++){
                    if(food_info2s.get(i).getTime().equals("아침")){
                        Toast.makeText(getApplicationContext(),"아침",Toast.LENGTH_SHORT).show();
                        foodInfo1.add(food_info2s.get(i));
                        textView1.append(food_info2s.get(i).getFood_info().getName()+"\n");

                    }else if (food_info2s.get(i).getTime().equals("점심")){
                        Toast.makeText(getApplicationContext(),"점심",Toast.LENGTH_SHORT).show();
                        foodInfo2.add(food_info2s.get(i));
                        textView2.append(food_info2s.get(i).getFood_info().getName()+"\n");

                    }else if (food_info2s.get(i).getTime().equals("저녁")){
                        Toast.makeText(getApplicationContext(),"저녁",Toast.LENGTH_SHORT).show();
                        foodInfo3.add(food_info2s.get(i));
                        textView3.append(food_info2s.get(i).getFood_info().getName()+"\n");

                    }
                }
                double tot1 = 0;
                double tot2=0;
                double tot3 =0;
                for(int i=0;i<foodInfo1.size();i++){
                    tot1= tot1+foodInfo1.get(i).getNum()*foodInfo1.get(i).getFood_info().getCal();
                }
                for(int i=0;i<foodInfo2.size();i++){
                    tot2= tot2+foodInfo2.get(i).getNum()*foodInfo2.get(i).getFood_info().getCal();
                }
                for(int i=0;i<foodInfo3.size();i++){
                    tot3= tot3+foodInfo3.get(i).getNum()*foodInfo3.get(i).getFood_info().getCal();
                }
                total1.setText(Math.round(tot1*100)/100.0+" kcal");
                total2.setText(Math.round(tot2*100)/100.0+" kcal");
                total3.setText(Math.round(tot3*100)/100.0+" kcal");

            }


            @Override
            public void onFailure(Call<ArrayList<Food_info2>> call, Throwable t) {

            }
        });







        title=findViewById(R.id.title);
        title.setText(day.split("-")[1]+"월"+day.split("-")[2]+"일 식단");
        linear1=findViewById(R.id.linear1);
        linear2=findViewById(R.id.linear2);
        linear3=findViewById(R.id.linear3);

        linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),Search_Activity.class);
//                intent.putExtra("day",day);
//                intent.putExtra("value","아침");
//                intent.putExtra("food_infos",foodInfo1);
//                startActivityForResult(intent,1);
                dialog_choice("아침");

            }
        });
        linear2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),Search_Activity.class);
//                intent.putExtra("day",day);
//                intent.putExtra("value","점심");
//                intent.putExtra("food_infos",foodInfo2);
//                startActivityForResult(intent,2);
                dialog_choice("점심");

            }
        });
        linear3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),Search_Activity.class);
//                intent.putExtra("day",day);
//                intent.putExtra("value","저녁");
//                intent.putExtra("food_infos",foodInfo3);

                dialog_choice("저녁");

//                startActivityForResult(intent,3);

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(getApplicationContext(),Recommend_select_Activity.class);
                                intent1.putExtra("phone",phone);
                intent1.putExtra("day",day);
                intent1.putExtra("value","아침");
                intent1.putExtra("select","체중 유지");
                intent1.putExtra("height",hight);
                intent1.putExtra("weight",weight);
                intent1.putExtra("age",age);
                intent1.putExtra("gender",gender);
                startActivityForResult(intent1,1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),Recommend_select_Activity.class);
                intent1.putExtra("phone",phone);
                intent1.putExtra("day",day);
                intent1.putExtra("value","점심");
                intent1.putExtra("select","체중 유지");
                intent1.putExtra("height",hight);
                intent1.putExtra("weight",weight);
                intent1.putExtra("age",age);
                intent1.putExtra("gender",gender);
                startActivityForResult(intent1,2);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),Recommend_select_Activity.class);
                intent1.putExtra("phone",phone);
                intent1.putExtra("day",day);
                intent1.putExtra("value","저녁");
                intent1.putExtra("select","체중 유지");
                intent1.putExtra("height",hight);
                intent1.putExtra("weight",weight);
                intent1.putExtra("age",age);
                intent1.putExtra("gender",gender);
                startActivityForResult(intent1,3);

            }
        });
        store_recommed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView1.getText().toString().equals("")&&textView2.getText().toString().equals("")&&textView3.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"입력된 음식이 없습니다",Toast.LENGTH_SHORT).show();
                }else{
                    final ArrayList<String> item=new ArrayList<>();
                    if(!textView1.getText().toString().equals("")){
                        item.add("아침");
                    }
                    if(!textView2.getText().toString().equals("")){
                        item.add("점심");

                    }
                    if(!textView3.getText().toString().equals("")){
                        item.add("저녁");

                    }
                    final String [] items = new String[item.size()];
                    for(int i=0;i<item.size();i++){
                        items[i]=item.get(i);
                    }
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Schedule_Activity.this);

                    dialog.setTitle("선택")
                            .setItems(items, new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Toast.makeText(getApplicationContext(),
                                            items[which], Toast.LENGTH_LONG).show();
                                    Intent intent1=new Intent(getApplicationContext(),Store_recommend_Activity.class);
                                    intent1.putExtra("day",day);
                                    intent1.putExtra("phone",phone);
                                    intent1.putExtra("select",items[which]);
                                    startActivity(intent1);
                                }
                            })
                            .show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        double carb,protein,fat,cal;
        carb=0;
        protein=0;
        fat=0;
        cal=0;
        for(int i=0;i<foodInfo1.size();i++){
            carb=carb+foodInfo1.get(i).getNum()*foodInfo1.get(i).getFood_info().getCarb();
            protein=protein+foodInfo1.get(i).getNum()*foodInfo1.get(i).getFood_info().getProtein();
            fat=fat+foodInfo1.get(i).getNum()*foodInfo1.get(i).getFood_info().getFat();
            cal=cal+foodInfo1.get(i).getNum()*foodInfo1.get(i).getFood_info().getCal();

        }

        for(int i=0;i<foodInfo2.size();i++){
            carb=carb+foodInfo2.get(i).getNum()*foodInfo2.get(i).getFood_info().getCarb();
            protein=protein+foodInfo2.get(i).getNum()*foodInfo2.get(i).getFood_info().getProtein();
            fat=fat+foodInfo2.get(i).getNum()*foodInfo2.get(i).getFood_info().getFat();
            cal=cal+foodInfo2.get(i).getNum()*foodInfo2.get(i).getFood_info().getCal();

        }

        for(int i=0;i<foodInfo3.size();i++){
            carb=carb+foodInfo3.get(i).getNum()*foodInfo3.get(i).getFood_info().getCarb();
            protein=protein+foodInfo3.get(i).getNum()*foodInfo3.get(i).getFood_info().getProtein();
            fat=fat+foodInfo3.get(i).getNum()*foodInfo3.get(i).getFood_info().getFat();
            cal=cal+foodInfo3.get(i).getNum()*foodInfo3.get(i).getFood_info().getCal();

        }
        Intent intent=new Intent();
        intent.putExtra("result",day+" "+cal+" "+carb+" "+protein+" "+fat+" "+(foodInfo1.size()+foodInfo2.size()+foodInfo3.size()));
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK) {
            if (requestCode == 1) {
                Toast.makeText(getApplicationContext(),"아침",Toast.LENGTH_SHORT).show();
                foodInfo1 = (ArrayList<Food_info2>) data.getSerializableExtra("value");
                if(foodInfo1.size()==0){
                    textView1.setText("");
                }else{
                    textView1.setText("");
                    for(int i=0;i<foodInfo1.size();i++){
                        textView1.append(foodInfo1.get(i).getFood_info().getName()+"\n");
                    }
                }
                double tot1 = 0;

                for(int i=0;i<foodInfo1.size();i++){
                    tot1= tot1+foodInfo1.get(i).getNum()*foodInfo1.get(i).getFood_info().getCal();
                }

                total1.setText(Math.round(tot1*100)/100.0+" kcal");


            } else if (requestCode == 2) {
                Toast.makeText(getApplicationContext(),"점심",Toast.LENGTH_SHORT).show();
                foodInfo2 = (ArrayList<Food_info2>) data.getSerializableExtra("value");
                if(foodInfo2.size()==0){
                    textView2.setText("");
                }else{
                    textView2.setText("");
                    for(int i=0;i<foodInfo2.size();i++){
                        textView2.append(foodInfo2.get(i).getFood_info().getName()+"\n");
                    }
                }

                double tot2=0;

                for(int i=0;i<foodInfo2.size();i++){
                    tot2= tot2+foodInfo2.get(i).getNum()*foodInfo2.get(i).getFood_info().getCal();
                }


                total2.setText(Math.round(tot2*100)/100.0+" kcal");


            } else if (requestCode == 3) {
                Toast.makeText(getApplicationContext(),"저녁",Toast.LENGTH_SHORT).show();
                foodInfo3= (ArrayList<Food_info2>) data.getSerializableExtra("value");
                if(foodInfo3.size()==0){
                    textView3.setText("");
                }else{
                    textView3.setText("");
                    for(int i=0;i<foodInfo3.size();i++){
                        textView3.append(foodInfo3.get(i).getFood_info().getName()+"\n");
                    }
                }

                double tot3 =0;

                for(int i=0;i<foodInfo3.size();i++){
                    tot3= tot3+foodInfo3.get(i).getNum()*foodInfo3.get(i).getFood_info().getCal();
                }

                total3.setText(Math.round(tot3*100)/100.0+" kcal");

            }
        }
    }

    public Dialog dialog_choice(final String string){
        final String[] item={"체중 증가","체중 유지","체중 감소"};
        final int[] i = new int[1];
        AlertDialog.Builder builder=new AlertDialog.Builder(Schedule_Activity.this);

        builder.setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i[0] =which;
            }
        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),item[i[0]],Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                if(string.equals("아침")&& !textView1.getText().toString().equals("")){
                    Recommend(1,item[i[0]],string);
                }else if(string.equals("점심")&&!textView2.getText().toString().equals("")){
                    Recommend(1,item[i[0]],string);

                }else if (string.equals("저녁")&&!textView3.getText().toString().equals("")){
                    Recommend(1,item[i[0]],string);

                }else{
                    Recommend(1,item[i[0]],string);

                }
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


      return   builder.show();
    }
//    public Dialog dialog_Method(final String select, final String string){
//        final String[] item={"현재 식단 유지하고 추천받기","새로 추천받기"};
//        final int[] i = new int[1];
//        AlertDialog.Builder builder=new AlertDialog.Builder(Schedule_Activity.this);
//        builder.setTitle("식단관리");
//        builder.setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                i[0] =which;
//            }
//        }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),string+" "+item[i[0]],Toast.LENGTH_SHORT).show();
//                Recommend(i[0],select,string);
//
//
//            }
//        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        return builder.show();
//    }


    //식단을 추천 받을때 입력된 식단을 유지하고 추천받을것인지 새로 추천 받을 것인지
    public void Recommend (int i,String select,String string){

        Intent intent=new Intent(getApplicationContext(),Search_Activity.class);
        intent.putExtra("phone",phone);
        intent.putExtra("day",day);
        int result=0;
        if(string.equals("아침")){
            intent.putExtra("value",string);
            intent.putExtra("food_infos",foodInfo1);
            intent.putExtra("height",hight);
            intent.putExtra("weight",weight);
            intent.putExtra("age",age);
            intent.putExtra("gender",gender);
            result=1;
        }else if (string.equals("점심")){
            intent.putExtra("value",string);
            intent.putExtra("food_infos",foodInfo2);
            result=2;
            intent.putExtra("height",hight);
            intent.putExtra("weight",weight);
            intent.putExtra("age",age);
            intent.putExtra("gender",gender);
        }
        else{
            intent.putExtra("value",string);
            intent.putExtra("food_infos",foodInfo3);
            intent.putExtra("height",hight);
            intent.putExtra("weight",weight);
            intent.putExtra("age",age);
            intent.putExtra("gender",gender);
            result=3;
        }
        intent.putExtra("recommend",i);
        intent.putExtra("select",select);
        startActivityForResult(intent,result);

    }
}
