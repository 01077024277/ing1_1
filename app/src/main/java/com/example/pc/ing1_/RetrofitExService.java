package com.example.pc.ing1_;

import com.example.pc.ing1_.Login.Hash;
import com.example.pc.ing1_.Menu.Friend.Message_model;
import com.example.pc.ing1_.Menu.Friend.Room_Item;
import com.example.pc.ing1_.Menu.Friend.Socket_Service;
import com.example.pc.ing1_.Menu.Friend.multi_chat.Multi_Room_Item;
import com.example.pc.ing1_.Menu.Menu.Food_Item;
import com.example.pc.ing1_.Menu.Menu.Food_info;
import com.example.pc.ing1_.Menu.Menu.Food_info2;
import com.example.pc.ing1_.Menu.Menu.Food_input;
import com.example.pc.ing1_.Menu.Menu.Food_list_item;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
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

    @GET("/User/userinfo.php")
    Call<User>  userinfo(@Query("id") String id);

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
    @GET("store_size.php")
    Call <ResponseBody> search_size(@QueryMap HashMap<String,String> aa);


    @GET("Store_Info.php")
    Call<JsonObject> store_info(@Query("no") int no);





    @FormUrlEncoded
    @POST("python.php")
    Call<ResponseBody> python(@Field("MakerRander") String py);
    @GET("/new.php")
    Call<List<Store_info>> store (@Query("name") String aa);

    @GET("/old.php")
    Call<ResponseBody> re (@QueryMap HashMap<String,String>  map);


    @GET("/food_sqlite.php")
    Call<List<Food_Item>> food_sqlite (@Query("search") String a);

    @GET("/food_match.php")
    Call<List<Food_list_item>> food_match(@QueryMap HashMap<String,String> a);
    @GET("/food_match_max.php")
    Call<ResponseBody> food_match_max(@Query("value") String a);
    @GET("/food_one.php")
    Call<List<Food_info>> food_one(@Query("food_name") String a);
    @GET("/schedule.php")
    Call<ResponseBody> schedule(@Query("aa")JSONObject a);
    @GET("/schedule_list.php")
    Call<ArrayList<Food_info2>> schedule_list(@QueryMap HashMap<String,String> a);

    @GET("/schedule_cal.php")
    Call<ResponseBody> schedule_cal(@QueryMap HashMap<String,String> a);




    @Headers({
            "X-NCP-APIGW-API-KEY-ID: idp7ne510b",
            "X-NCP-APIGW-API-KEY: Y2W1fJRKsbm8lVm7cdpfuLnoQR4tb1qyzCVhi3ZZ"
    })
    @GET("/map-direction/v1/driving")
    Call<ResponseBody> qwqw (@QueryMap HashMap<String,String> a);

    @GET("ex/ex.php")
    Call<ResponseBody> ex (@QueryMap HashMap<String,String> a);
    @GET("ex/other_recommend.php")
    Call<ResponseBody> recommend (@QueryMap HashMap<String,String> a);


    @Headers({
            "Accept: application/json",
            "appkey: 028e4e8a-ba76-43bf-b13a-e28f42140efa",
            "Host: api2.sktelecom.com",
            "Accept-Language: ko",
            "Content-Type: application/x-www-form-urlencoded"

            })
    @FormUrlEncoded
//    @POST("/tmap/routes/pedestrian?version=1&startX={startX}&startY={startY}&endX={endX}endY={endY}&startName=%EC%B6%9C%EB%B0%9C&endName=%EB%B3%B8%EC%82%AC")
    @POST("/tmap/routes/pedestrian")
    Call<JsonObject> tmap(@FieldMap HashMap<String,String> aa );
//    Call<JsonObject> tmap(@Path("startX") String startX,@Path("startY") String startY,@Path("endX") String endX,@Path("endY") String endY);


//음식점 추천
    @GET("store_recommend.php")
    Call <List<Store>> store_recommend (@QueryMap HashMap<String,String> aa);

    //친구찾기
    @GET("Friend_search.php")
    Call<User> friend_search(@QueryMap HashMap<String,String> key);
    //친구추가
    @GET("Friend_add.php")
    Call<ResponseBody> firend_add(@QueryMap HashMap<String,String> key);
    //친구 목록 불러오기
    @GET("Friend_list.php")
    Call<ArrayList<User>> firend_list(@Query("key") String key);
    //채팅방 불러오기
    @GET("Room_list.php")
    Call<ArrayList<Room_Item>> room_items(@QueryMap HashMap<String,String>  no);
    //채팅방 접속시 채팅내역불러오기
    @GET("Chat_list.php")
    Call<ArrayList<Message_model>> chat_list(@QueryMap HashMap<String,String> no);
    //유저 정보
    @GET("User_class.php")
    Call<User> user_class (@Query("no")String no);
    //방나가기
    @GET("Room_exit.php")
    Call<ResponseBody> room_exit(@QueryMap HashMap<String,String> hashMap);

    //멀티채팅 방번호
    @GET("mul_room.php")
    Call<ResponseBody> mul_room(@Query("no") String no);

    //멀티채팅 리스트
    @GET("mul_list.php")
    Call<ArrayList<Multi_Room_Item>> mul_list(@Query("no") String no);
    //멀티 채팅 목록
    @GET("mul_chat_list.php")
    Call<JsonObject> mul_chat(@QueryMap HashMap<String,String> no);
    //멀티 채팅 나가기
    @GET("mul_exit.php")
    Call<ResponseBody> mul_exit(@QueryMap HashMap<String,String> hashMap );
}

