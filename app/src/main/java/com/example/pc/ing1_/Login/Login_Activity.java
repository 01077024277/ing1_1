package com.example.pc.ing1_.Login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
import com.example.pc.ing1_.User;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private String TAG = "Login_Activity";


    private SessionCallback callback;

    public static OAuthLogin mOAuthLoginModule;
    OAuthLoginButton mOAuthLoginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        init();
        setNaver();
        kakaoData();




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
        mOAuthLoginButton=findViewById(R.id.button_naverlogin);

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
        mOAuthLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOAuthLoginModule.startOauthLoginActivity(Login_Activity.this,mOAuthLoginHandler);

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

















//네이버

    private void setNaver() {
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(this, "_CheAPReFMsAcot1I99f", "ExZ90kX4oB", "clientName");

        mOAuthLoginButton = findViewById(R.id.button_naverlogin);

        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
//        mOAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler);
    }
    public OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {

//토큰 가져오기
                new RequestApiTask().execute();

            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(getApplicationContext()).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(getApplicationContext());
                Toast.makeText(getApplicationContext(), "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };


//로그인 정보 가져오기

    private class RequestApiTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {//작업이 실행되기 전에 먼저 실행.

        }

        @Override
        protected String doInBackground(Void... params) {//네트워크에 연결하는 과정이 있으므로 다른 스레드에서 실행되어야 한다.
            String url = "https://openapi.naver.com/v1/nid/me";
            String at = mOAuthLoginModule.getAccessToken(getApplicationContext());
            return mOAuthLoginModule.requestApi(getApplicationContext(), at, url);//url, 토큰을 넘겨서 값을 받아온다.json 타입으로 받아진다.
        }

        protected void onPostExecute(String content) {//doInBackground 에서 리턴된 값이 여기로 들어온다.
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject response = jsonObject.getJSONObject("response");
                final String uid=response.getString("id");
                final String name=response.getString("name");
                Log.d("제이슨", String.valueOf(jsonObject));

                http.naver_login(uid).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User result = response.body();

                        if(!result.getUid().equals("")){
                            //가입 되어있음
                            //메인으로 이동 로그인


                            Intent intent= new Intent();
                            intent.putExtra("id",uid);
                            intent.putExtra("social","naver");
                            intent.putExtra("name",name);
                            setResult(RESULT_OK,intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        }else {
                            //미가입
                            Intent intent = new Intent(getApplicationContext(),Sign_1_agree_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            intent.putExtra("id",uid);
                            intent.putExtra("social","naver");
                            intent.putExtra("name",name);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("에[러",t.getMessage());

                    }
                });

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }














    //카카오톡
    private void kakaoData(){
        //로그아웃

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);

/** 토큰 만료시 갱신을 시켜준다**/
        if(Session.getCurrentSession().isOpenable()){
            Session.getCurrentSession().checkAndImplicitOpen();
        }

        Log.e(TAG, "토큰큰 : " + Session.getCurrentSession().getTokenInfo().getAccessToken());
        Log.e(TAG, "토큰큰 리프레쉬토큰 : " + Session.getCurrentSession().getTokenInfo().getRefreshToken());
        Log.e(TAG, "토큰큰 파이어데이트 : " + Session.getCurrentSession().getTokenInfo().getRemainingExpireTime());
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.e(TAG, "카카오 로그인 성공 ");
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Log.e(TAG, "exception : " + exception);
            }
        }
    }

    /** 사용자에 대한 정보를 가져온다 **/
    private void requestMe() {

        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                super.onFailure(errorResult);
                Log.e(TAG, "requestMe onFailure message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onFailureForUiThread(ErrorResult errorResult) {
                super.onFailureForUiThread(errorResult);
                Log.e(TAG, "requestMe onFailureForUiThread message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "requestMe onSessionClosed message : " + errorResult.getErrorMessage());
            }

            @Override
            public void onSuccess(final MeV2Response result) {
                Log.e(TAG, "requestMe onSuccess message : " +  result.getNickname()+"    "+result.getId());


                http.kakao_login(String.valueOf(result.getId())).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        User user=response.body();
                        //해당 카카오아이디가 없다면
                        //가입으로가기
                        if(user.getUid().equals("")){
                            Intent intent = new Intent(getApplicationContext(),Sign_1_agree_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            intent.putExtra("id",""+result.getId());
                            intent.putExtra("social","kakao");
                            intent.putExtra("name",result.getNickname());
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }else{
                            //있다면 로그인하기
                            Intent intent= new Intent();
                            intent.putExtra("id",""+result.getId());
                            intent.putExtra("social","kakao");
                            intent.putExtra("name",user.getName());
                            setResult(RESULT_OK,intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                            Toast.makeText(getApplicationContext(),"카카오 가입",Toast.LENGTH_SHORT).show();
                            Log.e("에러",result.getId()+"  456  "+user.getName());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });



            }

        });
    }

    /** 앱연결 해제 **/
    private void onClickLogout() {

        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "카카오 로그아웃 onSessionClosed");
            }

            @Override
            public void onNotSignedUp() {
                Log.e(TAG, "카카오 로그아웃 onNotSignedUp");
            }

            @Override
            public void onSuccess(Long result) {
                Log.e(TAG, "카카오 로그아웃 onSuccess");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            Log.d("결과값","ㅂㅈㄷㅂㅈㄷ");

            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }
}