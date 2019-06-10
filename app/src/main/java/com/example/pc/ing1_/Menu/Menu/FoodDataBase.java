package com.example.pc.ing1_.Menu.Menu;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.pc.ing1_.RetrofitExService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodDataBase extends SQLiteOpenHelper {
    Context context;
    RetrofitExService http;
    public FoodDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        StringBuffer ss =new StringBuffer();
        ss.append("create table food (");
        ss.append("food_name varchar(300), primary key (food_name))");
        db.execSQL(ss.toString());
        final long start=System.currentTimeMillis();
        http.food_sqlite("").enqueue(new Callback<List<Food_Item>>() {
            @Override
            public void onResponse(Call<List<Food_Item>> call, Response<List<Food_Item>> response) {
                List<Food_Item> food_items=response.body();
                db.beginTransaction();
                db.setTransactionSuccessful();
                for(int i=0;i<food_items.size();i++){

                    String sql = "insert into food values('"+food_items.get(i).getItem().replace("'","''")+"')";
                    db.execSQL(sql);
//                    Log.d("sqlite",sql);
                }
                db.endTransaction();
                long end=System.currentTimeMillis();
                Log.d("시간측정",(end-start)/1000.0 +"");
            }

            @Override
            public void onFailure(Call<List<Food_Item>> call, Throwable t) {

            }
        });

        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS food");
        Toast.makeText(context, "Table 업그레이드", Toast.LENGTH_SHORT).show();
        onCreate(db);


    }
}
