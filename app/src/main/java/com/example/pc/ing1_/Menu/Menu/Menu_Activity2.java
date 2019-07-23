package com.example.pc.ing1_.Menu.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Menu_Activity2 extends AppCompatActivity {

    public static int SUNDAY = 1;
    public static int MONDAY = 2;
    public static int TUESDAY = 3;
    public static int WEDNSESDAY = 4;
    public static int THURSDAY = 5;
    public static int FRIDAY = 6;
    public static int SATURDAY = 7;

    private TextView title;
    private GridView mGvCalendar;

    private ArrayList<Grid_Item> arrayList;
    private Calendar_Adapter mCalendarAdapter;
    HashMap<String, String> all_hashMap;
    Calendar mLastMonthCalendar;
    Calendar mThisMonthCalendar;
    Calendar mNextMonthCalendar;

    FoodDataBase foodDataBase;
    SQLiteDatabase database;
    RetrofitExService http;
    SharedPreferences sf;
    String heigth,weight,age,gender;
//    double ccal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_2);
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);

        sf = getSharedPreferences("login", MODE_PRIVATE);
        foodDataBase = new FoodDataBase(getApplicationContext(), "food", null, 14);
        database = foodDataBase.getWritableDatabase();


        Button bLastMonth = (Button) findViewById(R.id.cback);
        Button bNextMonth = (Button) findViewById(R.id.cnext);

        title = (TextView) findViewById(R.id.title);
        mGvCalendar = (GridView) findViewById(R.id.grid);
        bLastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
            }
        });
        bNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
            }
        });

        mGvCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Schedule_Activity.class);
                intent.putExtra("day", arrayList.get(position).getFomat());
                intent.putExtra("height",heigth);
                intent.putExtra("weight",weight);
                intent.putExtra("gender",gender);
                intent.putExtra("age",age);

                startActivityForResult(intent, 200);
            }
        });
        arrayList = new ArrayList<Grid_Item>();


// 이번달 의 캘린더 인스턴스를 생성한다.
        mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(mThisMonthCalendar);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                String result=data.getStringExtra("result");
                Log.d("리절트",result);
                if(result.split(" ")[5].equals("0")){
                    all_hashMap.put(result.split(" ")[0],null);
                }else{
                    all_hashMap.put(result.split(" ")[0],result.split(" ")[1]+" "+result.split(" ")[2]+" "+result.split(" ")[3]+" "+result.split(" ")[4]);
                }
                mCalendarAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    /**
     * 달력을 셋팅한다.
     *
     * @param calendar 달력에 보여지는 이번달의 Calendar 객체
     */
    private void getCalendar(Calendar calendar) {
        int lastMonthStartDay;
        int dayOfMonth;
        int thisMonthLastDay;

        arrayList.clear();

        arrayList.add(new Grid_Item("일", 1, "0"));
        arrayList.add(new Grid_Item("월", 2, "0"));
        arrayList.add(new Grid_Item("화", 3, "0"));
        arrayList.add(new Grid_Item("수", 4, "0"));
        arrayList.add(new Grid_Item("목", 5, "0"));
        arrayList.add(new Grid_Item("금", 6, "0"));
        arrayList.add(new Grid_Item("토", 7, "0"));


        // 이번달 시작일의 요일을 구한다. 시작일이 일요일인 경우 인덱스를 1(일요일)에서 8(다음주 일요일)로 바꾼다.)
        dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, -1);
        Log.e("지난달 마지막일", calendar.get(Calendar.DAY_OF_MONTH) + "");

        // 지난달의 마지막 일자를 구한다.
        lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, 1);
        Log.e("이번달 시작일", calendar.get(Calendar.DAY_OF_MONTH) + "");

        if (dayOfMonth == SUNDAY) {
            dayOfMonth += 7;
        }

        lastMonthStartDay -= (dayOfMonth - 1) - 1;


        // 캘린더 타이틀(년월 표시)을 세팅한다.
        title.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        Grid_Item day;

        Log.e("DayOfMOnth", dayOfMonth + "");

        for (int i = 0; i < dayOfMonth - 1; i++) {


            arrayList.add(null);
        }
        String httpformat = null;
        for (int i = 1; i <= thisMonthLastDay; i++) {
            Calendar a = Calendar.getInstance();
            a.set(mThisMonthCalendar.get(Calendar.YEAR), mThisMonthCalendar.get(Calendar.MONTH), i);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String formatted = format1.format(a.getTime());

            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
            httpformat = format2.format(a.getTime());
            day = new Grid_Item(String.valueOf(i), a.get(Calendar.DAY_OF_WEEK), formatted);

            arrayList.add(day);
        }
        Log.d("인덱스", (42 - (thisMonthLastDay + dayOfMonth - 1)) + "");
        Log.d("인덱스", (thisMonthLastDay + dayOfMonth - 1) + "");
        int num = 0;
        if (42 - (thisMonthLastDay + dayOfMonth - 1) > 7) {
            num = 7;
        }
        for (int i = 0; i < (42 - (thisMonthLastDay + dayOfMonth - 1)) - num; i++) {

            arrayList.add(null);
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("day", httpformat);
        hashMap.put("no", sf.getString("no", ""));
        http.schedule_cal(hashMap).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                all_hashMap = new HashMap<>();

                try {
                    String total = response.body().string();
                    heigth=total.split("/")[0];
                    weight=total.split("/")[1];
                    gender=total.split("/")[2];
                    age=total.split("/")[3];
//                    if (gender .equals("남") ) {
//                        ccal = (66.48 + (13.75 * Double.parseDouble(weight)) + (5 * Double.parseDouble(heigth)) - (6.76 * Double.parseDouble(age))) * 1.4;
//                    } else {
//                        ccal = (655.1 + (9.56 * Double.parseDouble(weight)) + (1.85 * Double.parseDouble(heigth)) - (4.68 * Double.parseDouble(age))) * 1.4;
//                    }
                    Log.d("이게뭐지","키"+heigth);
                    Log.d("이게뭐지","몸"+weight);
                    Log.d("이게뭐지","성별"+gender);
                    Log.d("이게뭐지","나이"+age);

//                    Log.d("이게뭐지",""+ccal);
                    try {
                        total = total.split("/")[4];
                    }catch (ArrayIndexOutOfBoundsException e){
                        total="";
                    }

                        if (!total.equals("")) {
                        for (int i = 0; i < total.split("\n").length; i++) {
                            String day = total.split("\n")[i].split(" ")[0];
                            all_hashMap.put(day, total.split("\n")[i].split(day + " ")[1]);
                            Log.d("해쉬맵", all_hashMap.get(day));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                initCalendarAdapter();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    /**
     * 지난달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return LastMonthCalendar
     */
    private Calendar getLastMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, -1);
        title.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
        return calendar;
    }

    /**
     * 다음달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return NextMonthCalendar
     */
    private Calendar getNextMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, +1);
        title.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
        return calendar;
    }

    private void initCalendarAdapter() {
        mCalendarAdapter = new Calendar_Adapter(this, arrayList, all_hashMap);
        mGvCalendar.setAdapter(mCalendarAdapter);
    }


}
