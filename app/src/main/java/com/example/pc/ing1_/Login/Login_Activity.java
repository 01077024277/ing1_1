package com.example.pc.ing1_.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.Main_Activity;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.Sign.Sign_1_agree_Activity;
import com.example.pc.ing1_.Sign.Sign_2_sms_Activity;
import com.example.pc.ing1_.Sign.Sign_3_id_Activity;
import com.example.pc.ing1_.Sign.Sign_4_profile;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_Activity extends AppCompatActivity {

    RetrofitExService http;
    Button login,sign;
    EditText id, pass;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();


    }

    @Override
    protected void onResume() {
        super.onResume();


        //로그인 버튼 클릭
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //아이디 비밀번호 둘다 공백일경우
                if (pass.getText().toString().equals("") && id.getText().toString().equals("")) {
                    id.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
                    pass.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
                    textView.setText("아이디와 비밀번호를 입력해주세요");

                } else if (id.getText().toString().equals("")) {
                    id.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
                    textView.setText("아이디를 입력해주세요");
                } else if (pass.getText().toString().equals("")) {
                    pass.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_anim));
                    textView.setText("비밀번호를 입력해주세요");
                } else if (!pass.getText().toString().equals("") && !id.getText().toString().equals("")) {
                    HashMap<String, String> info = new HashMap<>();
                    String password = Hash.getSHA512(pass.getText().toString());
                    //에러 날경우
                    if (password.equals("0")) {
                        Toast.makeText(getApplicationContext(), "서버 오류입니다 다시 로그인 해주세요", Toast.LENGTH_SHORT).show();

                    } else {
                        info.put("id", id.getText().toString());
                        info.put("pass", password);

                        final Call<ResponseBody> id_ = http.login(info);
                        id_.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
//                                Log.d("값",response.body().string());
                                    String result = response.body().string();


                                    if (result.toString().equals("0")) {
                                        textView.setText("아이디 혹은 비밀번호가 맞지 않습니다");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_SHORT).show();
                                        Intent intent= new Intent();
                                        intent.putExtra("id",id.getText().toString());
                                        setResult(RESULT_OK,intent);
                                        finish();
                                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });


    }

    public void init() {
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);


        login = findViewById(R.id.button);
        id = findViewById(R.id.id);
        pass = findViewById(R.id.pass);
        textView = findViewById(R.id.fail);
        sign=findViewById(R.id.sign);


        //포커스 이동시 알람 삭제
        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    textView.setText("");
                }
            }
        });
        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    textView.setText("");
                }
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Sign_1_agree_Activity.class);
//                Intent intent = new Intent(getApplicationContext(),Sign_4_profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();



            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent=new Intent(getApplicationContext(),Main_Activity.class);
//        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }
}