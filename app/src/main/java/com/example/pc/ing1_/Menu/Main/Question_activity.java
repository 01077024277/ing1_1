package com.example.pc.ing1_.Menu.Main;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.pc.ing1_.R;
import com.example.pc.ing1_.aaa.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;


public class Question_activity extends AppCompatActivity {

    TextView title;
    Button image,send;
    EditText editText;
    int store_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        title=findViewById(R.id.title);
        image=findViewById(R.id.image);
        send=findViewById(R.id.send);
        editText=findViewById(R.id.content);
        Intent intent=getIntent();
        store_no= Integer.parseInt(intent.getStringExtra("store_id"));
        title.setText(intent.getStringExtra("store_name"));

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TedImagePicker.with(getApplicationContext())
                        .startMultiImage(new OnMultiSelectedListener() {
                            @Override
                            public void onSelected(@NotNull List<? extends Uri> uriList) {
//                                showMultiImage(uriList);
                            }
                        });

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
