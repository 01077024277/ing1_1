package com.example.pc.ing1_.Sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.Login.Hash;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_3_id_Activity extends AppCompatActivity {
    EditText id,pass,passcheck;
    Button idcheck,sign;
    Pattern id_pattern,pass_pattern;
    TextView id_alarm,pass_text,pass_check;
    RetrofitExService http;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_3_id);

        id_pattern=Pattern.compile("^([a-zA-Z]{1})(?=.*[0-9])(?=.*[A-Za-z])[a-zA-Z0-9]{4,11}$");
        pass_pattern=Pattern.compile("^(?=.*[0-9])(?=.*[A-Za-z])[a-zA-Z0-9]{4,11}$");

//        id_pattern=Pattern.compile("^[a-zA-Z]{1}[a-zA-Z0-9]{4,11}$");

        Retrofit retrofit=new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build();
        http=retrofit.create(RetrofitExService.class);

        pass_text=findViewById(R.id.textView7);
        idcheck=findViewById(R.id.idcheck);
        id=findViewById(R.id.id);
        id_alarm=findViewById(R.id.textView5);
        pass=findViewById(R.id.pass);
        passcheck=findViewById(R.id.pass2);
        id.getBackground().clearColorFilter();
        pass.getBackground().clearColorFilter();
        passcheck.getBackground().clearColorFilter();
        pass_check=findViewById(R.id.textView8);
        sign=findViewById(R.id.button2);
        final Intent intent=getIntent();
        phone=intent.getStringExtra("phone");
//        phone="010770242771";


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

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    Matcher matcher=pass_pattern.matcher(pass.getText().toString());
                    if(!matcher.find()){
                        pass_text.setText("비밀번호는 영문+숫자 조합으로 5~12자");
                        pass_text.setTextColor(Color.parseColor("#ff0000"));

                    }else{
                        pass_text.setText("가입 가능한 비밀번호");
                        pass_text.setTextColor(Color.parseColor("#00ff00"));
                        if(!passcheck.getText().toString().equals("")){
                            if (pass.getText().toString().equals(passcheck.getText().toString())) {
                                pass_check.setText("비밀번호 일치");
                                pass_check.setTextColor(Color.parseColor("#00ff00"));
                            }
                            else{
                                pass_check.setText("비밀번호 일치하지 않음");
                                pass_check.setTextColor(Color.parseColor("#ff0000"));
                            }
                        }
                    }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("칼라", String.valueOf(pass_text.getTextColors().getDefaultColor()));
                if(pass_text.getTextColors().getDefaultColor()==0xff00ff00) {


                    if (pass.getText().toString().equals(passcheck.getText().toString())) {
                        pass_check.setText("비밀번호 일치");
                        pass_check.setTextColor(Color.parseColor("#00ff00"));
                    }
                    else{
                        pass_check.setText("비밀번호 일치하지 않음");
                        pass_check.setTextColor(Color.parseColor("#ff0000"));
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_alarm.getTextColors().getDefaultColor()!=0xff00ff00){
                    Toast.makeText(getApplicationContext(),"아이디를 확인해주세요",Toast.LENGTH_SHORT).show();
                }else if (pass_text.getTextColors().getDefaultColor()!=0xff00ff00 || pass_check.getTextColors().getDefaultColor()!=0xff00ff00){
                    Toast.makeText(getApplicationContext(),"비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }else{
                    final ProgressDialog progressDialog=new ProgressDialog(Sign_3_id_Activity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("잠시만 기다려주세요");
                    progressDialog.show();
                    String password = Hash.getSHA512(pass.getText().toString());
                    final String id_=id.getText().toString();
                    HashMap<String,String> user=new HashMap<>();
                    user.put("id",id_);
                    user.put("password",password);
                    user.put("phone",phone);
                    Log.d("리턴",id_+" "+password+ " "+phone);
                    http.sign(user).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressDialog.dismiss();
                            String result = null;
                            try {
                                result = response.body().string();
                                Log.d("리턴값",result);

                            } catch (IOException e) {
                                Log.d("리턴값","ㅂㅈㄷㅂㅈㄷ");
                                e.printStackTrace();
                            }
                            if(result.equals("0")){
                                Toast.makeText(getApplicationContext(),"가입완료",Toast.LENGTH_SHORT).show();
                                Intent intent1=new Intent(getApplicationContext(),Sign_4_profile.class);
                                intent1.putExtra("id",id_);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(),"가입불가",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });


                }
            }
        });

    }



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
