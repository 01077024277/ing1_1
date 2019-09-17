package com.example.pc.ing1_.Menu.Menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recommend_random_adapter extends PagerAdapter {

    // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
    private Context mContext = null ;
    HashMap<String,String> qwe;
    RetrofitExService http;
    ProgressDialog progressDialog;
    static HashMap<String,ArrayList<Food_info2>> hashMap;

    public Recommend_random_adapter() {

    }

    // Context를 전달받아 mContext에 저장하는 생성자 추가.
      public Recommend_random_adapter(Context context,String phone,String value,String day) {
        mContext = context ;
        qwe=new HashMap<>();
        qwe.put("phone",phone);
          Log.d("제이슨",phone);
          qwe.put("value",value);
          Log.d("제이슨",value);
          qwe.put("day",day);
        qwe.put("select","체중 유지");
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
hashMap=new HashMap<>();
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = null ;


        if (mContext != null) {

            Log.d("페이저",""+position);
            if(position==0){
                progressDialog=new ProgressDialog(mContext);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("로딩중입니다..");
                progressDialog.show();

            }


            // LayoutInflater를 통해 "/res/layout/page.xml"을 뷰로 생성.
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.page, container, false);

            final TextView textView = (TextView) view.findViewById(R.id.title) ;
            final RoundCornerProgressBar progressBar_cal,progressBar_carb,progressBar_pro,progressBar_fat;
            final TextView textview_carb,textview_cal,textview_fat,textview_pro;
            progressBar_cal=view.findViewById(R.id.progress_cal);
            progressBar_carb=view.findViewById(R.id.progress_carb);
            progressBar_fat=view.findViewById(R.id.progress_fat);
            progressBar_pro=view.findViewById(R.id.progress_pro);
            textview_cal=view.findViewById(R.id.textView_cal);
            textview_carb=view.findViewById(R.id.textView_carb);
            textview_fat=view.findViewById(R.id.textView_fat);
            textview_pro=view.findViewById(R.id.textView_protein);
            String phone,value,day;
            textView.setText("");
            progressBar_carb.setProgressColor(Color.parseColor("#00ff99"));
            progressBar_carb.setMax(100);
            progressBar_fat.setProgressColor(Color.parseColor("#ff9900"));
            progressBar_fat.setMax(100);
            progressBar_pro.setProgressColor(Color.parseColor("#ff0099"));
            progressBar_pro.setMax(100);
            progressBar_cal.setProgressColor(Color.parseColor("#00b3ff"));
            progressBar_cal.setMax(100);


            http.recommend(qwe).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                    JSONObject jsonObject;
                    double carb,protein,fat,cal;
                    double my_cal = 0;
                    double my_carb=0;
                    double my_protein=0;
                    double my_fat=0;
//                    try {
//                        Log.d("원본",response.body().string().toString());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    try {
                        jsonObject= new JSONObject(response.body().string().toString());
                        Log.d("제이슨",jsonObject.toString());
                        carb=jsonObject.getDouble("carb");
                        protein=jsonObject.getDouble("protein");
                        fat=jsonObject.getDouble("fat");
                        cal=jsonObject.getDouble("cal");
                        JSONArray jsonArray = (jsonObject.getJSONArray("item"));
                        Gson gson = new Gson();
                        ArrayList<Food_info2> food_info2s=new ArrayList<>();
                        for(int i=0;i<jsonArray.length();i++){
                            food_info2s.add(gson.fromJson(jsonArray.get(i).toString(), Food_info2.class));
                            textView.append(food_info2s.get(i).getFood_info().getName()+"\n");
                            my_cal=my_cal+food_info2s.get(i).getFood_info().getCal();
                            my_carb=my_carb+food_info2s.get(i).getFood_info().getCarb();
                            my_protein=my_protein+food_info2s.get(i).getFood_info().getProtein();
                            my_fat=my_fat+food_info2s.get(i).getFood_info().getFat();

                        }
                        hashMap.put(""+position,food_info2s);
                        progressBar_cal.setProgress((float) (my_cal / cal * 100));
                        progressBar_carb.setProgress((float) (my_carb / carb * 100));
                        progressBar_pro.setProgress((float) (my_protein / protein * 100));
                        progressBar_fat.setProgress((float) (my_fat / fat * 100));
                        textview_carb.setText(Math.round((float) (my_carb / carb * 100)*100)/100+"%");
                        textview_pro.setText(Math.round((float) (my_protein / protein * 100)*100)/100+"%");
                        textview_fat.setText(Math.round((float) (my_fat / fat * 100)*100)/100+"%");
                        textview_cal.setText(Math.round((float) (my_cal / cal * 100)*100)/100+"%");
//                        textview_fat.setText("qqqqq");
//                        textview_cal.setText("qqqqq");
//                        progressBar_fat.setProgress(42);

                    } catch (JSONException e) {
//                        e.printStackTrace();
                        Log.d("wwwsss", String.valueOf(e));
                    } catch (IOException e) {
//                        e.printStackTrace();
                        Log.d("wwwsss", String.valueOf(e));

                    }




                    if(position==9){
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }

        // 뷰페이저에 추가.
        container.addView(view) ;

        return view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
//        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 전체 페이지 수는 10개로 고정.
        return 10;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}