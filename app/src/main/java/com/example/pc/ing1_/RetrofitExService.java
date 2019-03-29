package com.example.pc.ing1_;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitExService {

    String url = "http://13.124.8.83";

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

}

