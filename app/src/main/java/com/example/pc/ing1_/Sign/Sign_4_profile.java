package com.example.pc.ing1_.Sign;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pc.ing1_.Login.Hash;
import com.example.pc.ing1_.Main_Activity;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.transform.Result;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_4_profile extends AppCompatActivity {
    private static final int PICK_FROM_ALBUM = 1;
    RetrofitExService http;
    ImageView profile;
    Button skip,succ;
    String id;
    EditText name;
    String imagePath,name_,social;
    File tmpfile;
    boolean change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_4_profile);
        profile = findViewById(R.id.profile);
        skip = findViewById(R.id.skip);
        name=findViewById(R.id.name);
        succ=findViewById(R.id.succ);
        change=false;

        final Intent intent=getIntent();
        id=intent.getStringExtra("id");
        String nick = intent.getStringExtra("name");
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();

        name_=intent.getStringExtra("nname");
        social=intent.getStringExtra("social");
        if(intent.getIntExtra("change",0)==1){
            skip.setVisibility(View.GONE);
        }






//        Log.d("name은",nick);
//        id="qwe123";
        if(nick==null) {
            //첫가입
            Log.d("Sign_4","실행");
            name.setText(id);
            Sign_1_agree_Activity sign_1_agree_activity= (Sign_1_agree_Activity) Sign_1_agree_Activity.sign_1;
            sign_1_agree_activity.finish();
        }
        else{
            //가입후 프로필 수정
            name.setText(nick);
            Bitmap bitmap=intent.getParcelableExtra("image");

            profile.setImageBitmap(bitmap);
        }
        Retrofit retrofit=new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build();
        http=retrofit.create(RetrofitExService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            profile.setBackground(new ShapeDrawable(new OvalShape()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            profile.setClipToOutline(true);
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FROM_ALBUM);


            }
        });


        succ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(Sign_4_profile.this);
                if (name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (name_==null) {
//                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                    progressDialog.setMessage("잠시만 기다려주세요");
//                    progressDialog.show();


                try {

                    Log.d("리", imagePath.toString());
                    File file = new File(tmpfile.getAbsolutePath());
                    Log.d("리", "Filename " + file.getName());
                    //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                    RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                    RequestBody id_send = RequestBody.create(MediaType.parse("text/plain"), id);
                    RequestBody nick = RequestBody.create(MediaType.parse("text/plain"),name.getText().toString());
                    HashMap<String, RequestBody> data = new HashMap<>();
                    data.put("name", filename);
                    data.put("id", id_send);
                    data.put("nick",nick);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);




                    Call<ResponseBody> uplade = http.upload(fileToUpload, data);
                    uplade.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                            progressDialog.dismiss();
                            try {
                                if (response.body().string().equals("1")) {
                                    //이미지 업로드 성공
                                    Toast.makeText(getApplicationContext(), "성공", Toast.LENGTH_SHORT).show();
                                    Intent intent1= new Intent();
                                    intent1.putExtra("id",id);


                                    setResult(RESULT_OK,intent1);
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                                } else {
                                    Toast.makeText(getApplicationContext(), "잠시후 다시 시도해주세요", Toast.LENGTH_SHORT).show();

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "잠시후 111다시 시도해주세요", Toast.LENGTH_SHORT).show();


                        }
                    });


                } catch (NullPointerException e) {
                    Log.d("리", "널");
                    //사진을 선택하지않음
                    HashMap hashMap=new HashMap();
                    hashMap.put("nick",name.getText().toString());
                    hashMap.put("id",id);
                    http.nick(hashMap).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                            progressDialog.dismiss();
                            Intent intent1= new Intent();
                            intent1.putExtra("id",id);
                            intent1.putExtra("change",String.valueOf(change));
                            setResult(RESULT_OK,intent1);
                            finish();
                            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
//                Intent intent1=new Intent(getApplicationContext(),Main_Activity.class);

            }
            //네이버로그인 회원가입일때
            else if (social.equals("naver")){
                    try{
                        Log.d("리", imagePath.toString());
                        File file = new File(tmpfile.getAbsolutePath());
                        Log.d("리", "Filename " + file.getName());
                        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                        RequestBody id_send = RequestBody.create(MediaType.parse("text/plain"), name_);
                        RequestBody nick = RequestBody.create(MediaType.parse("text/plain"),name.getText().toString());
                        HashMap<String, RequestBody> data = new HashMap<>();
                        data.put("name", filename);
                        data.put("id", id_send);
                        data.put("nick",nick);
                        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                        Call<ResponseBody> upload = http.naver_upload(fileToUpload, data);
                        upload.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String result = response.body().string();
                                    if(result.equals("1")){
                                        Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                                        Intent intent1= new Intent();
                                        intent1.putExtra("id",name_);
                                        intent1.putExtra("social","naver");


                                        setResult(RESULT_OK,intent1);
                                        finish();
                                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                                    }else{
                                        Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

                    }catch (NullPointerException e){
                        Log.d("리", "널");
                        //사진을 선택하지않음
                        HashMap hashMap=new HashMap();
                        hashMap.put("nick",name.getText().toString());
                        hashMap.put("id",name_);
                        hashMap.put("social","naver");
                        http.nick(hashMap).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                            progressDialog.dismiss();
                                Intent intent1= new Intent();
                                intent1.putExtra("id",name_);
                                intent1.putExtra("social","naver");
                                intent1.putExtra("change",String.valueOf(change));
                                setResult(RESULT_OK,intent1);
                                finish();
                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }

                }else if(social.equals("kakao")){
                    //카카오 프로필

                    try{
                        Log.d("리", imagePath.toString());
                        File file = new File(tmpfile.getAbsolutePath());
                        Log.d("리", "Filename " + file.getName());
                        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                        RequestBody id_send = RequestBody.create(MediaType.parse("text/plain"), name_);
                        RequestBody nick = RequestBody.create(MediaType.parse("text/plain"),name.getText().toString());
                        HashMap<String, RequestBody> data = new HashMap<>();
                        data.put("name", filename);
                        data.put("id", id_send);
                        data.put("nick",nick);
                        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                        Call<ResponseBody> upload = http.kakao_upload(fileToUpload, data);
                        upload.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String result = response.body().string();
                                    if(result.equals("1")){
                                        Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();
                                        Intent intent1= new Intent();
                                        intent1.putExtra("id",name_);
                                        intent1.putExtra("social","kakao");


                                        setResult(RESULT_OK,intent1);
                                        finish();
                                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                                    }else{
                                        Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });

                    }catch (NullPointerException e){
                        Log.d("리", "널");
                        //사진을 선택하지않음
                        HashMap hashMap=new HashMap();
                        hashMap.put("nick",name.getText().toString());
                        hashMap.put("id",name_);
                        hashMap.put("social","kakao");
                        http.nick(hashMap).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                            progressDialog.dismiss();
                                Intent intent1= new Intent();
                                intent1.putExtra("id",name_);
                                intent1.putExtra("social","kakao");
                                intent1.putExtra("change",String.valueOf(change));
                                setResult(RESULT_OK,intent1);
                                finish();
                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }




                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_FROM_ALBUM) {
            if(resultCode==RESULT_OK) {
                change=true;

                Uri uri = data.getData();
                try {
                    // 비트맵 이미지로 가져온다

                    imagePath = getPath(uri);


                        Bitmap image = BitmapFactory.decodeFile(imagePath);
                        Log.d("리사이즈", String.valueOf(image.getByteCount()));
                        // 이미지를 상황에 맞게 회전시킨다
                        ExifInterface exif = new ExifInterface(imagePath);
                        int exifOrientation = exif.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        int exifDegree = exifOrientationToDegrees(exifOrientation);

                        image = rotate(image, exifDegree);
                        File storage =getApplicationContext().getCacheDir();
                        if(name_==null) {
                            tmpfile = new File(storage, "" + id + ".jpg");
                        }else{
                            tmpfile = new File(storage, "" + name_ + ".jpg");

                        }
                        tmpfile.createNewFile();
                        FileOutputStream out = new FileOutputStream(tmpfile);

                        image.compress(Bitmap.CompressFormat.JPEG,90,out);
                    Log.d("크기", String.valueOf(tmpfile.length()));


                    if (tmpfile.length() > 2000000) {
                        Toast.makeText(getApplicationContext(),"2MB 이하의 이미지를 사용해주세요",Toast.LENGTH_SHORT).show();

                    }else {
                        Log.d("크기", String.valueOf(tmpfile.length()));
                        // 변환된 이미지 사용
                        Glide.with(getApplicationContext()).load(image).into(profile);
                    }
                    } catch(Exception e){
                        Log.d("회전", String.valueOf(e));
                    }


            }
        }


    }

    //회전각 구하기
    public static int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    //회전하기
    public static Bitmap rotate(Bitmap bitmap, int degrees) {
        Log.d("회전","2");
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap;


    }

    //이미지 경로
    public String getPath(Uri contentURI) {

        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();

        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;


    }

    @Override
    public void onBackPressed() {

//        Intent intent=new Intent(getApplicationContext(),Main_Activity.class);
        Intent intent1= new Intent();
        intent1.putExtra("change",String.valueOf(change));
        if(name_!=null){
            Log.d("실행",name_);
            intent1.putExtra("id",name_);
            intent1.putExtra("social",social);
        }else{
            intent1.putExtra("id",id);
        }
        setResult(RESULT_OK,intent1);
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        super.onBackPressed();

    }


}





















