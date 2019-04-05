package com.example.pc.ing1_;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile_View extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile__view);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String social = intent.getStringExtra("social");
        if(social==null){
            social="";
        }else{
            social=social+"_";
        }
        RetrofitExService http;
        Retrofit retrofit=new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build();
        http=retrofit.create(RetrofitExService.class);
        imageView = findViewById(R.id.image);

        http.getProfile(RetrofitExService.url + "/profile/"+social+id+".jpg").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());

                    // 변환된 이미지 사용
                    Glide.with(getApplicationContext()).load(bmp).into(imageView);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
