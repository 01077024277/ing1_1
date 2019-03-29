package com.example.pc.ing1_.Sign;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sing_3_userinfo_Activity extends AppCompatActivity {
    EditText id,pass,passcheck;
    Button idcheck;
    Pattern id_pattern;
    TextView id_alarm;
    RetrofitExService http;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_3_userinfo);

        id_pattern=Pattern.compile("^([a-zA-Z]{1})(?=.*[0-9])[a-zA-Z]{1}[a-zA-Z0-9]{4,11}$");
//        id_pattern=Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9]{4,11}$");

        Retrofit retrofit=new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build();
        http=retrofit.create(RetrofitExService.class);


        idcheck=findViewById(R.id.idcheck);
        id=findViewById(R.id.id);
        id_alarm=findViewById(R.id.textView5);

        idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matcher matcher=id_pattern.matcher(id.getText().toString());
                if(matcher.find()){

                    http.id_check(id.getText().toString()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String id_ck=response.body().string();
                                if (id_ck.equals("0")) {
                                    Toast.makeText(getApplicationContext(),"가입가능",Toast.LENGTH_SHORT).show();
                                    id_alarm.setTextColor(Color.parseColor("#00ff00"));
                                    id_alarm.setText("가입 가능한 아이디입니다");

                                }else{
                                    Toast.makeText(getApplicationContext(),"가입불가능",Toast.LENGTH_SHORT).show();
                                    id_alarm.setTextColor(Color.parseColor("#ff0000"));
                                    id_alarm.setText("중복된 아이디입니다");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                }else{
                    id_alarm.setTextColor(Color.parseColor("#ff0000"));

                }

            }
        });

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                id_alarm.setText("아이디는 영문+숫자 조합으로 5~12자");
                id_alarm.setTextColor(Color.parseColor("#808080"));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    //id 정규식 "^[a-zA-Z]{1}[a-zA-Z0-9]{4,11}$"

    //뒤로가기 버튼시 인증화면으로 이동  애니메이션
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),Sign_2_sms_Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        finish();

    }
}
