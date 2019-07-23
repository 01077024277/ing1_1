package com.example.pc.ing1_.Menu.Menu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.pc.ing1_.R;

public class Recommend_select_Activity extends AppCompatActivity {

    private ViewPager viewPager ;
    private Recommend_random_adapter pagerAdapter ;
    String phone,day,value;
    Button refresh,save;
    int now;
    String hight,weight,gender,age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recommend_select_activity);
        Intent intent=getIntent();
        phone=intent.getStringExtra("phone");
        day=intent.getStringExtra("day");
        value=intent.getStringExtra("value");
        hight=intent.getStringExtra("height");
        weight=intent.getStringExtra("weight");
        age=intent.getStringExtra("age");
        gender=intent.getStringExtra("gender");
        refresh=findViewById(R.id.refresh);
        save=findViewById(R.id.save);
        viewPager = (ViewPager) findViewById(R.id.viewPager) ;
        pagerAdapter = new Recommend_random_adapter(Recommend_select_Activity.this,phone,value,day) ;
        viewPager.setAdapter(pagerAdapter) ;
        viewPager.setOffscreenPageLimit(10);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);




        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final ProgressDialog asyncDialog = new ProgressDialog(
//                        Recommend_select_Activity.this);
//                asyncDialog.show();
                pagerAdapter = new Recommend_random_adapter(Recommend_select_Activity.this,phone,value,day) ;
                viewPager.setAdapter(pagerAdapter) ;
//                pagerAdapter.notifyDataSetChanged();
//                viewPager.setCurrentItem(0);
//                asyncDialog.dismiss();
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                now=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),Search_Activity.class);
                intent1.putExtra("food_infos",Recommend_random_adapter.hashMap.get(""+now));
                intent1.putExtra("phone",phone);
                intent1.putExtra("day",day);
                intent1.putExtra("value",value);
                intent1.putExtra("recommend",1);
                intent1.putExtra("select","체중 유지");
                intent1.putExtra("height",hight);
                intent1.putExtra("weight",weight);
                intent1.putExtra("age",age);
                intent1.putExtra("gender",gender);
                intent1.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent1);
                finish();

            }
        });

    }
}
