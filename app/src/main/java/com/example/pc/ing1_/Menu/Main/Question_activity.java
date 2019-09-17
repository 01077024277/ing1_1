package com.example.pc.ing1_.Menu.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.example.pc.ing1_.Login.Hash;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class Question_activity extends AppCompatActivity {

    TextView title;
    Button image,send;
    EditText editText;
    int store_no;
    RecyclerView recyclerView;
    ArrayList<String> image_path;
    Question_Adapter question_adapter;
    RetrofitExService http;
    SharedPreferences sf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        title=findViewById(R.id.title);
        image=findViewById(R.id.image);
        send=findViewById(R.id.send);
        editText=findViewById(R.id.content);
        sf = getSharedPreferences("login",MODE_PRIVATE);
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
        Intent intent=getIntent();
        store_no= Integer.parseInt(intent.getStringExtra("store_id"));
        title.setText(intent.getStringExtra("store_name"));
        recyclerView=findViewById(R.id.recyclerView);
        image_path=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayout.HORIZONTAL,false));
        question_adapter=new Question_Adapter(image_path,getApplicationContext());
        recyclerView.setAdapter(question_adapter);
        question_adapter.setImage_Delete(new Question_Adapter.ImageClickLinstener() {
            @Override
            public void Delete_image(View v, int position) {
                image_path.remove(position);
                question_adapter.notifyDataSetChanged();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(10-image_path.size()==0){
                    Toast.makeText(getApplicationContext(),"이미지 최대 10장",Toast.LENGTH_SHORT).show();
                }else{
                    ImagePicker.create(Question_activity.this).limit(10-image_path.size()).showCamera(false)
                            .start();
                }


            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MultipartBody.Part> multi=new ArrayList<>();
                for(int i=0;i<image_path.size();i++){
                    File file=new File(image_path.get(i));
                    RequestBody requestBody= RequestBody.create(MediaType.parse("image/*"),file);
                    MultipartBody.Part body= MultipartBody.Part.createFormData("uploaded_fild[]",file.getName(),requestBody);
                    multi.add(body);

                }
                HashMap<String,RequestBody> hashMap=new HashMap<>();
                RequestBody r1 = RequestBody.create(MediaType.parse("text/plain"),sf.getString("no","")+"");
                RequestBody r2 = RequestBody.create(MediaType.parse("text/plain"),store_no+"");
                RequestBody r3 = RequestBody.create(MediaType.parse("text/plain"),editText.getText().toString());

                hashMap.put("user",r1);
                hashMap.put("store_id",r2);
                hashMap.put("content",r3);

                http.fead_upload(multi,hashMap).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.d("반환값",response.body().string().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Log.d("반환값",String.valueOf(t));

                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            for(int i=0;i<images.size();i++){
                image_path.add(images.get(i).getPath());
            }
            question_adapter.notifyDataSetChanged();
            // or get a single image only
//            Image image = ImagePicker.getFirstImageOrNull(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
