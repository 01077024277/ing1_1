package com.example.pc.ing1_.Menu.Main;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.R;

import java.util.ArrayList;

public class ImgViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> imglist, contentlist;
    private ArrayList<Bitmap> bmp;

    public ImgViewPagerAdapter(Context context, ArrayList<String> img, ArrayList<String> contentlist, ArrayList<Bitmap> bmp) {
        this.context = context;
        this.imglist = img;
        this.contentlist = contentlist;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.img_pager, container, false);
        ImageView imageView = view.findViewById(R.id.img);
        TextView textView = view.findViewById(R.id.content);
        Glide.with(context).load(imglist.get(position)).centerCrop().into(imageView);
//        Log.d("qweqwe", "" + imglist.get(position) + "   " + contentlist.get(position).toString());

        if(contentlist!=null) {
            textView.setText(contentlist.get(position).toString());
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return imglist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (View) o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
