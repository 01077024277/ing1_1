package com.example.pc.ing1_.Menu.Menu;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Food_Info_Activity extends AppCompatActivity {
    String Food_name;
    RetrofitExService http;
    List<Food_info> food_infos;
    TextView textView;
    Food_info one_item;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food__info_activity);
        textView = findViewById(R.id.title);
        recyclerView = findViewById(R.id.recycle);

        Intent intent = getIntent();
        Food_name = intent.getStringExtra("food_name");
        Toast.makeText(getApplicationContext(),""+Food_name,Toast.LENGTH_SHORT).show();
        textView.setText(Food_name);
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);

        http.food_one(Food_name).enqueue(new Callback<List<Food_info>>() {
            @Override
            public void onResponse(Call<List<Food_info>> call, Response<List<Food_info>> response) {
                food_infos = response.body();
                for (int i = 0; i < food_infos.size(); i++) {
                    if (food_infos.get(i).getNum() != 1) {
                        if (food_infos.get(i).getSize().toString().equals("g")) {
                            int num = food_infos.get(i).getNum();
                            one_item = new Food_info(food_infos.get(i).getName(), food_infos.get(i).getSize(), 1
                                    , food_infos.get(i).getCal() / num, food_infos.get(i).getCarb() / num, food_infos.get(i).getProtein() / num, food_infos.get(i).getFat() / num
                                    , food_infos.get(i).getChol() / num, food_infos.get(i).getFiber() / num, food_infos.get(i).getSalt() / num, food_infos.get(i).getPotass() / num);
                            Log.d("칼탄단지", "" + one_item.getName());
                            Log.d("칼탄단지", "" + one_item.getNum());
                            Log.d("칼탄단지", "" + one_item.getSize());
                            Log.d("칼탄단지", "" + one_item.getCal());
                            Log.d("칼탄단지", "" + one_item.getCarb());
                            Log.d("칼탄단지", "" + one_item.getProtein());
                            Log.d("칼탄단지", "" + one_item.getFat());
                            break;


                        }
                        if (food_infos.get(i).getSize().toString().equals("ml")) {
                            int num = food_infos.get(i).getNum();
                            one_item = new Food_info(food_infos.get(i).getName(), food_infos.get(i).getSize(), 1
                                    , food_infos.get(i).getCal() / num, food_infos.get(i).getCarb() / num, food_infos.get(i).getProtein() / num, food_infos.get(i).getFat() / num
                                    , food_infos.get(i).getChol() / num, food_infos.get(i).getFiber() / num, food_infos.get(i).getSalt() / num, food_infos.get(i).getPotass() / num);

                            Log.d("칼탄단지", "" + one_item.getName());
                            Log.d("칼탄단지", "" + one_item.getNum());
                            Log.d("칼탄단지", "" + one_item.getSize());
                            Log.d("칼탄단지", "" + one_item.getCal());
                            Log.d("칼탄단지", "" + one_item.getCarb());
                            Log.d("칼탄단지", "" + one_item.getProtein());
                            Log.d("칼탄단지", "" + one_item.getFat());
                            break;


                        }

                    }
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);

                adapter = new RecyclerAdapter();

                adapter.ButtonClick(new RecyclerAdapter.Itemclick() {
                    @Override
                    public void onClick(View v, int Position, String title, int size, double cal, double carb, double protein, double fat, double chol, double fiber, double salt, double protass) {
                        Log.d("타이틀",title);
                        Log.d("size",size+"");
                        Log.d("칼",cal+"");
                        Log.d("탄",carb+"");
                        Log.d("단",protein+"");
                        Log.d("지",fat+"");
                        Log.d("콜레",chol+"");
                        Log.d("식이",fiber+"");
                        Log.d("나트륨",salt+"");
                        Log.d("칼륨",protass+"");
                        Food_input food_input= Food_input.food_input;
                        food_input.finish();
                        Intent intent =new Intent();
                        intent.putExtra("이름","이름");
                        setResult(RESULT_OK,intent);
                        finish();


                    }
                });

                recyclerView.setAdapter(adapter);
                if(one_item!=null) {
                    adapter.addItem(one_item);
                }
                for(int i=0;i<food_infos.size();i++){
                    adapter.addItem(food_infos.get(i));
                }

                adapter.notifyDataSetChanged();
                PieChart pieChart;
                pieChart = (PieChart)findViewById(R.id.piechart);

                pieChart.setUsePercentValues(true);
                pieChart.getDescription().setEnabled(false);
                pieChart.setExtraOffsets(5,10,5,5);

                pieChart.setDragDecelerationFrictionCoef(0.95f);

                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(61f);
                pieChart.setNoDataText("클릭해주세요");
                pieChart.invalidate();

                ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

                yValues.add(new PieEntry((float) food_infos.get(0).getCarb(),"탄수화물"));
                yValues.add(new PieEntry((float) food_infos.get(0).getProtein(),"단백질"));
                yValues.add(new PieEntry((float) food_infos.get(0).getFat(),"지방"));


                Description description = new Description();
                description.setText(""); //라벨

                pieChart.setDescription(description);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setTransparentCircleRadius(30f);
                pieChart.setHoleRadius(30f);
                pieChart.setUsePercentValues(true);
                pieChart.setEntryLabelColor(Color.BLACK);

                PieDataSet dataSet = new PieDataSet(yValues,"");
                dataSet.setSliceSpace(3f);
                dataSet.setValueTextSize(45f);
                dataSet.setSelectionShift(5f);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

                PieData data = new PieData((dataSet));
                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(15f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);
                if(food_infos.get(0).getCarb()==0&&food_infos.get(0).getProtein()==0&&food_infos.get(0).getFat()==0){
                    pieChart.setVisibility(View.GONE);
                }



            }

            @Override
            public void onFailure(Call<List<Food_info>> call, Throwable t) {
                Log.d("ewq", String.valueOf(t));

            }
        });





        // adapter의 값이 변경되었다는 것을 알려줍니다.



    }
}

