package com.example.pc.ing1_.Sign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.example.pc.ing1_.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_2_sms_Activity extends AppCompatActivity {
    BroadcastReceiver mReceiver;
    View view;
    EditText phone;
    static EditText sms;
    Button auth;
    TextView fail,sms_title;
    boolean authCheck;
    RetrofitExService http ;
    Retrofit retrofit;
    String auth_num;
    Button next;
    String phone_;

    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_2_sms);

        retrofit=new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build();
        http=retrofit.create(RetrofitExService.class);





        //리시버 등록
        SmsReceiver();
        phone=findViewById(R.id.phone);
        auth=findViewById(R.id.auth);
        fail=findViewById(R.id.fail);
        sms=findViewById(R.id.sms);


        sms_title=findViewById(R.id.title2);
        next=findViewById(R.id.next);

        int color = Color.parseColor("#00fa55");
        sms.getBackground().setColorFilter(color,PorterDuff.Mode.SRC_ATOP);
        phone.getBackground().setColorFilter(color,PorterDuff.Mode.SRC_ATOP);
        //인증버튼 누르기
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //번호가 올바르다면 시작번호 010,011,019 등등
                if(authCheck==true){
                    auth.setVisibility(View.GONE);
                    sms.setVisibility(View.VISIBLE);
                    sms_title.setVisibility(View.VISIBLE);
                    phone_=phone.getText().toString();

                    Random random =new Random();
//                    Log.d("랜덤값",random.nextInt(10000));
                    //랜덤값 전달
                    auth_num = String.format("%06d", random.nextInt(1000000));
                    HashMap<String,String> auth = new HashMap<>();
                    auth.put("phone",phone.getText().toString());
                    auth.put("auth",auth_num);
                    //sms메시지 보내기
                    http.smsauth(auth).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                }else{
                    //번호 안맞을때
                    Toast.makeText(getApplicationContext(),"인증번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });



        //텍스트뷰 기본 여백 지우기
        phone.setIncludeFontPadding(false);
        //핸드폰번호 입력 이벤트
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                StringBuffer input = new StringBuffer(s.toString());
                Log.d("qweqwe",""+phone.getText().toString().length());

                //번호 입력이벤트 번호 길이가 11일때
                if(phone.getText().toString().length()==11){
                    //앞자리 3개 저장
                    String num=input.substring(0,3);
                    // 앞자리가 핸드폰번호가 맞을때만
                    if(num.equals("010")||num.equals("011")||num.equals("016")||num.equals("019")){
                        Log.d("qweqwe","합");
                        authCheck=true;
                        //참일때 버튼 백그라운드 알파값 조걸
                        auth.setBackgroundColor(Color.rgb(9,225,244));
                        auth.setAlpha(1.0F);
                        fail.setVisibility(View.INVISIBLE);
                        phone.getBackground().clearColorFilter();
                        int color = Color.parseColor("#00fa55");
                        phone.getBackground().setColorFilter(color,PorterDuff.Mode.SRC_ATOP);
                    }
                    else{
                        authCheck=false;
                        auth.setBackgroundResource(R.drawable.border_black);
                        auth.setAlpha(0.4F);
                        fail.setVisibility(View.VISIBLE);
                        phone.getBackground().clearColorFilter();


                    }
                }else{
                    authCheck=false;
                    auth.setBackgroundResource(R.drawable.border_black);
                    auth.setAlpha(0.4F);
                    fail.setVisibility(View.INVISIBLE);
                    phone.getBackground().clearColorFilter();
                    int color = Color.parseColor("#00fa55");
                    phone.getBackground().setColorFilter(color,PorterDuff.Mode.SRC_ATOP);



                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //인증번호 입력 이벤트
        sms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //인증번호가 맞다면 알파값
                if(s.length()==6&&s.toString().equals(auth_num)){
                    next.setVisibility(View.VISIBLE);
                    next.setBackgroundColor(Color.rgb(9,225,244));
                    next.setAlpha(1.0F);
                }
                else{

                    next.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //다음 버튼 누르기
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("qweqwe","클릭");
                Log.d("qweqwe",phone.getText().toString());
                //디비에 있는 회원인지 아닌지 판단
                final Call<User> user =http.phone_check(phone.getText().toString());
                user.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user1=response.body();
                        if(user1.getPhone()!=null){
                            Toast.makeText(getApplicationContext(),"가입불가능",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            phone.getBackground().clearColorFilter();
                            Toast.makeText(getApplicationContext(),"가입가능",Toast.LENGTH_SHORT).show();





                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("생명주기","onDestroy");
        getApplicationContext().unregisterReceiver(mReceiver);

    }

















    //sms 리시버
    private  void SmsReceiver(){
        IntentFilter intentFilter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        mReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("1q1q","onReceived() 호출됨");

                Bundle bundle = intent.getExtras();
                SmsMessage[] messages = parseSmsMessage(bundle);

                if(messages != null){
                    String sender = messages[0].getDisplayOriginatingAddress();
                    Log.d("1q1q",sender);

                    String contents = messages[0].getMessageBody();
                    Log.d("1q1q", contents);

                    Date receivedDate = new Date(messages[0].getTimestampMillis());
                    Log.d("1q1q",receivedDate.toString());
                    if(sender.equals("01077024277")){
                        sms.setText(contents.split("인증번호는 ")[1]);
                        //sms 인증번호를 파싱했다면 수정 불가능 읽기전용
                        sms.setInputType(InputType.TYPE_NULL);
                        phone.setText(phone_);
                        phone.setInputType(InputType.TYPE_NULL);
                    }

                }

            }
        };
        getApplicationContext().registerReceiver(mReceiver,intentFilter);
    }


    //내용 파싱
    private SmsMessage[] parseSmsMessage(Bundle bundle){
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0; i<objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }

        return messages;
    }


}
