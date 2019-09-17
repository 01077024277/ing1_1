package com.example.pc.ing1_.Menu.Main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pc.ing1_.R;

import java.util.ArrayList;

public class Image_Total_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    Image_total_Adapter image_total_adapter;
    ArrayList<String>img;
    ArrayList<Bitmap> bmp;
    ViewPager viewPager;
    ImgViewPagerAdapter imgViewPagerAdapter;
    int start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image__total_activity);
        Intent intent = getIntent();
        img = intent.getStringArrayListExtra("img");
//        Collections.reverse(img);
        ArrayList<String> content=intent.getStringArrayListExtra("content");
//        Collections.reverse(content);
        viewPager=findViewById(R.id.viewpager);


        Log.d("이미지 사이즈",img.size()+"");
        for(int i=0;i<img.size();i++){
            Log.d("이미지 사이즈",""+img.get(i).toString());
        }




         imgViewPagerAdapter=new ImgViewPagerAdapter(getApplicationContext(),img,content,bmp);
//         Log.d("qweewq",img.get(0).toString());
//        Log.d("qweewq",img.get(1).toString());
//        Log.d("qweewq",img.get(2).toString());
        viewPager.setAdapter(imgViewPagerAdapter);
        imgViewPagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(intent.getIntExtra("num",0));
        start=intent.getIntExtra("num",0);
        Toast.makeText(getApplicationContext(),""+imgViewPagerAdapter.getCount(),Toast.LENGTH_SHORT).show();



          recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayout.HORIZONTAL,false));
         image_total_adapter= new Image_total_Adapter(getApplicationContext(),img);
         image_total_adapter.num=intent.getIntExtra("num",0);



         image_total_adapter.setOnClickListener(new Image_total_Adapter.ItemClickListener() {
             @Override
             public void onItemClicke(View view, int position) {
                 viewPager.setCurrentItem(position);
                 image_total_adapter.num=position;
                 image_total_adapter.notifyDataSetChanged();
             }
         });
        recyclerView.setAdapter(image_total_adapter);



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int end;
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                end=i;
                if(end<start){
                    recyclerView.scrollToPosition(i-2);
                }else{
                    recyclerView.scrollToPosition(i+2);

                }
                start=i;
                image_total_adapter.num=i;
                image_total_adapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}
