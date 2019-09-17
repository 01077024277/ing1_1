package com.example.pc.ing1_;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.Login.Login_Activity;
import com.example.pc.ing1_.Menu.BlankFragment;
import com.example.pc.ing1_.Menu.Friend.Main_Friend_Acitivity;
import com.example.pc.ing1_.Menu.Friend.Socket_Service;
import com.example.pc.ing1_.Menu.Main.Main_frag;

import com.example.pc.ing1_.Menu.Menu.Menu_Activity2;
import com.example.pc.ing1_.Sign.Sign_4_profile;

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
    MenuItem menu_logout,select;
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
        sign_button.setVisibility(View.VISIBLE);
        profile_change.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
//        final Intent intent=getIntent();
//        id=intent.getStringExtra("id");
//        social=intent.getStringExtra("social");

        if(!sf.getString("id","").equals("")){
           id=sf.getString("id","");
           if(!sf.getString("social","").equals("")){
               Log.d("소셜ㄹㄹㄹ",sf.getString("social",""));

               social=sf.getString("social",null);
               Social_Login(social,id,1);
           }else{
               Login(1);
           }
            menu_logout.setVisible(true);

        }else{
            menu_logout.setVisible(false);
        }
        Toast.makeText(getApplicationContext(),sf.getString("phone",""),Toast.LENGTH_SHORT).show();
        menu.findItem(R.id.nav_camera).setChecked(true);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setClipToOutline(true);
        }

//        Login(1);



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
            Log.d("dddddd","seeee1");
            return true;
        }
        Log.d("dddddd","seeee2");
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
            select=item;
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            fragmentTransaction.replace(R.id.frag,new BlankFragment());
            fragmentTransaction.commit();
            select=item;

        } else if (id == R.id.nav_slideshow) {
//            Intent intent =new Intent(getApplicationContext(),Menu_Activity.class);
            if("".equals(sf.getString("phone",""))){
                Toast.makeText(getApplicationContext(),"로그인을 해주세요",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent =new Intent(getApplicationContext(),Menu_Activity2.class);
                startActivity(intent);

            }

        } else if (id == R.id.nav_friend) {
            Intent intent =new Intent(getApplicationContext(), Main_Friend_Acitivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {

        }else  if(id==R.id.nav_logout){
            sign_button.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            profile_change.setVisibility(View.GONE);
//            item.setChecked(false);
            item.setVisible(false);
//            sd.putString("id","");
//            sd.putString("social","");
//            sd.putString("height","");
//            sd.putString("weight","");
//            sd.putString("gender","");
//            sd.putString("age","");
//            sd.putString("no","");
//            sd.putString("phone","");
            sd.clear();
            social=null;
            sd.commit();
            sf = getSharedPreferences("login",MODE_PRIVATE);
            Intent intent = new Intent(
                    getApplicationContext(),//현재제어권자
                    Socket_Service.class); // 이동할 컴포넌트
            stopService(intent);


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
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            int qwe=0;
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {
                Log.d("qweqwe","ccc"+i);
                if(i==2){
                    if(select==null){
                        menu.findItem(R.id.nav_camera).setChecked(true);
                    }else{
                        select.setChecked(true);
                    }
                }

            }
        });

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
Log.d("서비스","로그인");
        if(id!=null){
            Log.d("왜",id);
            sign_button.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            profile_change.setVisibility(View.VISIBLE);
            Call<User> user = http.userinfo(id);
            user.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    login_user = response.body();
                    sd.putString("phone",login_user.getPhone());
                    sd.putString("height",login_user.getHeight());
                    sd.putString("weight",login_user.getWeight());
                    sd.putString("no", String.valueOf(login_user.getNo()));
                    sd.putString("id",id);
                    sd.putString("profile",login_user.getProfile());
                    sd.putString("nick",login_user.getName());
                    sd.putString("social",null);
                    sd.commit();
//                    Socket_Service socket_service=new Socket_Service(String.valueOf(login_user.getNo()));
                    if(isServiceRunning()==false) {
                        Intent service = new Intent(getApplicationContext(), Socket_Service.class);
                        service.putExtra("no", String.valueOf(login_user.getNo()));

                        startService(service);

                    }
                    name.setText(login_user.getName());
                    Log.d("프로필",login_user.getProfile());
                    //프로필이 없으면 기본 이미지
                    if(login_user==null){
                        Log.d("왜","null");
                    }
                    if(login_user.getProfile()=="null"){
                        Log.d("왜","null");
                    }
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
            Log.d("왜2",id);

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
                    menu_logout.setChecked(false);
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
                    final Call<User> user = http.naver_login(id);
                    user.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            login_user = response.body();
                            name.setText(login_user.getName());
                            sd.putString("phone",login_user.getPhone());
                            sd.putString("height",login_user.getHeight());
                            sd.putString("weight",login_user.getWeight());
                            sd.putString("no", String.valueOf(login_user.getNo()));
                            sd.putString("profile",login_user.getProfile());
                            sd.putString("nick",login_user.getName());
                            sd.commit();
                            if(isServiceRunning()==false) {
                                Intent service = new Intent(getApplicationContext(), Socket_Service.class);
                                service.putExtra("no", String.valueOf(login_user.getNo()));

                                startService(service);

                            }
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
                    sd.putString("phone",login_user.getPhone());
                    sd.putString("height",login_user.getHeight());
                    sd.putString("weight",login_user.getWeight());
                    sd.putString("no", String.valueOf(login_user.getNo()));
                    sd.putString("profile",login_user.getProfile());
                    sd.putString("nick",login_user.getName());
                    sd.commit();
                    if(isServiceRunning()==false) {
                        Intent service = new Intent(getApplicationContext(), Socket_Service.class);
                        service.putExtra("no", String.valueOf(login_user.getNo()));

                        startService(service);

                    }
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
    public boolean isServiceRunning()
    {
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (Socket_Service.class.getName().equals(service.service.getClassName()))
                return true;
        }
        return false;
    }
}
