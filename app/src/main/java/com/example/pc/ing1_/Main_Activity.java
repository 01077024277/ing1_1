package com.example.pc.ing1_;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pc.ing1_.Login.Login_Activity;
import com.example.pc.ing1_.Menu.BlankFragment;
import com.example.pc.ing1_.Menu.Main_frag;
import com.example.pc.ing1_.Sign.Sign_1_agree_Activity;
import com.example.pc.ing1_.Sign.Sign_4_profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
RetrofitExService http;
    View header;
     String id,social;
    ImageView imageView;
    Button sign_button,profile_change;
    User login_user;
    DrawerLayout drawer;
    TextView name;
    Bitmap bmp;
    MenuItem menu_logout;
    Menu menu;
    SharedPreferences sf;
    SharedPreferences.Editor sd;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Retrofit retrofit=new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build();
        http=retrofit.create(RetrofitExService.class);

        sf = getSharedPreferences("login",MODE_PRIVATE);
        sd=sf.edit();
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

        init();
        name=header.findViewById(R.id.name);
        imageView =header.findViewById(R.id.profile);
        sign_button=header.findViewById(R.id.sign_button);
        profile_change=header.findViewById(R.id.profile_change);

        fragmentTransaction.replace(R.id.frag,new Main_frag());
        fragmentTransaction.commit();

//        final Intent intent=getIntent();
//        id=intent.getStringExtra("id");
//        social=intent.getStringExtra("social");
        if(!sf.getString("id","").equals("")){
           id=sf.getString("id","");
           if(!sf.getString("social","").equals("")){
               social=sf.getString("social",null);
               Social_Login(social,id,1);
           }else{
               Login(1);
           }
            menu_logout.setVisible(true);

        }else{
            menu_logout.setVisible(false);
        }

        menu.findItem(R.id.nav_camera).setChecked(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setClipToOutline(true);
        }

//        Login(1);

        sign_button.setVisibility(View.VISIBLE);
        profile_change.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id!=null){
                    if(!login_user.getProfile().equals("")) {
                        if(social!=null){
                            Intent intent1 = new Intent(getApplicationContext(), Profile_View.class);
                            intent1.putExtra("id", id);
                            intent1.putExtra("social",social);

                            startActivity(intent1);
                        }else {
                            Intent intent1 = new Intent(getApplicationContext(), Profile_View.class);
                            intent1.putExtra("id", id);

                            startActivity(intent1);
                        }
                    }
                }else {

                }
            }
        });


        sign_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login_Activity.class);
//                Intent intent = new Intent(getApplicationContext(),Sign_4_profile.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivityForResult(intent,100);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                finish();
            }
        });
        profile_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Sign_4_profile.class);
                intent.putExtra("id",id);
                //소셜로그인이라면
                if(social!=null) {
                    intent.putExtra("nname", id);
                    intent.putExtra("social",social);
                }
                intent.putExtra("change",1);
                intent.putExtra("name",login_user.getName());
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                intent.putExtra("image",bitmap);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("dddddd","pre");

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("dddddd","cre");
        getMenuInflater().inflate(R.menu.main_, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragmentTransaction=fragmentManager.beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d("dddddd","sele");
        if (id == R.id.nav_camera) {
            fragmentTransaction.replace(R.id.frag,new Main_frag());
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            fragmentTransaction.replace(R.id.frag,new BlankFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else  if(id==R.id.nav_logout){
            sign_button.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            profile_change.setVisibility(View.GONE);
            item.setVisible(false);
            sd.putString("id","");
            sd.putString("social","");
            sd.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void  init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        menu=navigationView.getMenu();
        menu_logout=menu.findItem(R.id.nav_logout);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
    }

    public void Login(int i){
        //로그인이 되었다면
        final int result;
        result=i;
        if(id!=null){
            sign_button.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            profile_change.setVisibility(View.VISIBLE);
            Call<User> user = http.userinfo(id);
            user.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    login_user = response.body();
                    name.setText(login_user.getName());
                    Log.d("프로필",login_user.getProfile());
                    //프로필이 없으면 기본 이미지
                    if (login_user.getProfile().equals("")) {
                        Glide.with(getApplicationContext()).load(R.drawable.user).into(imageView);

                    }
                    //있으면 프로필 이미지
                    else {
                        if(result==1) {
                            http.getProfile(RetrofitExService.url + "/profile/" + id + ".jpg").enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    bmp = BitmapFactory.decodeStream(response.body().byteStream());

                                    // 변환된 이미지 사용
                                    Glide.with(getApplicationContext()).load(bmp).into(imageView);
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        }else{
            sign_button.setVisibility(View.VISIBLE);
            profile_change.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("에러","소셜로그인0");
        Log.e("에러","q   "+requestCode+"s   "+resultCode);
//        Log.e("에러",data.getStringExtra("id"));
        if(resultCode==RESULT_OK){
                if(requestCode==100) {
                    menu_logout.setVisible(true);
                    Log.e("에러","소셜로그인1");
                    sd.putString("id",data.getStringExtra("id"));
                    Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                    id=data.getStringExtra("id");
                    if(data.getStringExtra("change")==null){
                        //프로필 이미지가 변경O or 첫 로그인
                        Log.e("에러","체인지 null ");

                        if(data.getStringExtra("social")==null) {
                            //일반 로그인
                            Login(1);
                            Log.e("에러","소셜 널");
                        }
                        else if(data.getStringExtra("social").equals("naver")){
                            //소셜 로그인
                            Log.e("에러","체인지 널 소셜네이버");
                            social="naver";
                            sd.putString("social",social);
                            Social_Login("naver",id,1);

                        }else if(data.getStringExtra("social").equals("kakao")){
                            Log.e("에러","체인지 널 소셜카카오");
                            social="kakao";
                            sd.putString("social",social);
                            Social_Login("kakao",id,1);
                        }
                    }else{
                        //프로필이미지 변경  X
                        if(data.getStringExtra("social")==null) {
                            Log.e("에러","체인지 널 ㄴㄴ 소셜 널");
                            //일반 로그인
                            Login(0);
                        }else if(data.getStringExtra("social").equals("naver")){
                            Log.e("에러","체인지 널 ㄴㄴ 네이버");
                            social="naver";
                            sd.putString("social",social);
                            Social_Login("naver",id,0);
                        }else if(data.getStringExtra("social").equals("kakao")){
                            Log.e("에러","체인지 널 ㄴㄴ 카카오");

                            social="kakao";
                            sd.putString("social",social);
                            Social_Login("kakao",id,0);
                        }
                    }



                }
                sd.commit();
            }


    }


    public void Social_Login(String social, final String uid, int i){
        Log.e("에러","소셜로그인2");
        if(social.equals("naver")){
            final int result =i;
            http.naver_login(uid).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    sign_button.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    profile_change.setVisibility(View.VISIBLE);
                    Call<User> user = http.naver_login(id);
                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            login_user = response.body();
                            name.setText(login_user.getName());
                            Log.d("프로필",login_user.getUid());
                            //프로필이 없으면 기본 이미지
                            if (login_user.getProfile().equals("")) {
                                Glide.with(getApplicationContext()).load(R.drawable.user).into(imageView);

                            }
                            //있으면 프로필 이미지
                            else {
                                if(result==1) {
                                    http.getProfile(RetrofitExService.url + "/profile/naver_" + id + ".jpg").enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            bmp = BitmapFactory.decodeStream(response.body().byteStream());

                                            // 변환된 이미지 사용
                                            Glide.with(getApplicationContext()).load(bmp).into(imageView);
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }else if (social.equals("kakao")){
            final int result =i;
            http.kakao_login(uid).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    sign_button.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    profile_change.setVisibility(View.VISIBLE);
                    Call<User> user = http.naver_login(id);
                    login_user = response.body();
                    name.setText(login_user.getName());
                    Log.d("프로필",login_user.getUid());
                    //프로필이 없으면 기본 이미지
                    if (login_user.getProfile().equals("")) {
                        Glide.with(getApplicationContext()).load(R.drawable.user).into(imageView);

                    }
                    //있으면 프로필 이미지
                    else {
                        if(result==1) {
                            http.getProfile(RetrofitExService.url + "/profile/kakao_" + id + ".jpg").enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    bmp = BitmapFactory.decodeStream(response.body().byteStream());

                                    // 변환된 이미지 사용
                                    Glide.with(getApplicationContext()).load(bmp).into(imageView);
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
