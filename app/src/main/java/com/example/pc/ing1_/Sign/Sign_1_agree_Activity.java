package com.example.pc.ing1_.Sign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.pc.ing1_.Login.Login_Activity;
import com.example.pc.ing1_.R;

public class Sign_1_agree_Activity extends AppCompatActivity {

    View view;

    FragmentManager fm;
    FragmentTransaction tran;
    CheckBox c1,c2,c3,c4;
    TextView next;
    public static Activity sign_1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_1_agree);
        sign_1=Sign_1_agree_Activity.this;
            // Inflate the layout for this fragment


            next=findViewById(R.id.textView3);

            c1=findViewById(R.id.checkBox);
            c4=findViewById(R.id.checkBox4);
            c3=findViewById(R.id.checkBox3);
            c2=findViewById(R.id.checkBox2);
            c1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (c1.isChecked()){
                        c2.setChecked(true);
                        c3.setChecked(true);
                        c4.setChecked(true);
                        next.setAlpha(1.0F);

                    }else{
                        c2.setChecked(false);
                        c3.setChecked(false);
                        c4.setChecked(false);
                        next.setAlpha(0.1F);
                    }
                }
            });
            c2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check();
                }
            });
            c3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check();

                }
            });
            c4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check();

                }
            });

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(c1.isChecked()&&c2.isChecked()&&c3.isChecked()&&c4.isChecked()) {

                        //인증 다됐을경우
                        Intent intent=new Intent(getApplicationContext(),Sign_2_sms_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                    }else{
                    }
                }
            });







    }

    public void check(){
        if(c2.isChecked()&&c3.isChecked()&&c4.isChecked()){
            c1.setChecked(true);
            next.setAlpha(1.0F);
        }else{
            c1.setChecked(false);
            next.setAlpha(0.1F);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        check();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();
    }
}






