package com.example.pc.ing1_;

import com.example.pc.ing1_.Login.Hash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitExService {

    String url = "http://54.180.168.210";

//    @GET("/Sign/Phone_check.php")
//    Call<User> phone_user(@Query("qwe") String aa);

    @FormUrlEncoded
    @POST("/login.php")
    Call<ResponseBody> login(@FieldMap HashMap<String,String> id);

    @FormUrlEncoded
    @POST("/sms_api.sample.php")
    Call<ResponseBody> smsauth(@FieldMap HashMap<String,String> auth);

    @FormUrlEncoded
    @POST("/Sign/Phone_check.php")
    Call<User> phone_check(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("/Sign/Id_check.php")
    Call<ResponseBody> id_check(@Field("id") String id);
    @FormUrlEncoded
    @POST("/Sign/Sign.php")
    Call<ResponseBody> sign(@FieldMap HashMap<String,String> sign);

    @Multipart
    @POST("/profile_img.php")
//    Call<ResponseBody> upload(@Part MultipartBody.Part img, @Part("name") RequestBody name,@Part("id") RequestBody id);
    Call<ResponseBody> upload(@Part MultipartBody.Part img,@PartMap HashMap<String,RequestBody> data);

    @Multipart
    @POST("/naver_profile_img.php")
//    Call<ResponseBody> upload(@Part MultipartBody.Part img, @Part("name") RequestBody name,@Part("id") RequestBody id);
    Call<ResponseBody> naver_upload(@Part MultipartBody.Part img,@PartMap HashMap<String,RequestBody> data);
    @Multipart
    @POST("/kakao_profile_img.php")
//    Call<ResponseBody> upload(@Part MultipartBody.Part img, @Part("name") RequestBody name,@Part("id") RequestBody id);
    Call<ResponseBody> kakao_upload(@Part MultipartBody.Part img,@PartMap HashMap<String,RequestBody> data);
    @FormUrlEncoded
    @POST("/nickname.php")
    Call<ResponseBody> nick(@FieldMap HashMap<String,String> name);
    @GET
    Call<ResponseBody> getProfile(@Url String url);

    @FormUrlEncoded
    @POST("/User/userinfo.php")
    Call<User>  userinfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("/Login/naver_login.php")
    Call<User> naver_login(@Field("id") String id);
    @FormUrlEncoded
    @POST("/Login/kakao_login.php")
    Call<User> kakao_login(@Field("id") String id);

    @FormUrlEncoded
    @POST("/Sign/Naver_Sign.php")
    Call<ResponseBody> Naver_Sign(@FieldMap HashMap<String,String> map);
    @FormUrlEncoded
    @POST("/Sign/kakao_sign.php")
    Call<ResponseBody> Kakao_Sign(@FieldMap HashMap<String,String> map);


    @GET("gps_store.php")
    Call <List<Store>> search_store(@QueryMap HashMap<String,String> aa);








    @FormUrlEncoded
    @POST("python.php")
    Call<ResponseBody> python(@Field("MakerRander") String py);
    @GET("/new.php")
    Call<List<Store_info>> store (@Query("name") String aa);

    @GET("/old.php")
    Call<ResponseBody> re (@QueryMap HashMap<String,String>  map);
}

