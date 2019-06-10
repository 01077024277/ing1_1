package com.example.pc.ing1_.Menu.Menu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.RetrofitExService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Search_Activity extends AppCompatActivity {
RetrofitExService http;

    FoodDataBase foodDataBase;
    SQLiteDatabase db;
    List<String> autolist,searchlist,blankremove;
    RecyclerView recyclerView;
    Food_List_Adapter food_list_adapter;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);


        foodDataBase=new FoodDataBase(getApplicationContext(),"food",null,13);
        db=foodDataBase.getWritableDatabase();


        http = new Retrofit.Builder().baseUrl(RetrofitExService.url).addConverterFactory(GsonConverterFactory.create()).build().create(RetrofitExService.class);

        Intent intent=getIntent();

        final Button button=findViewById(R.id.button);
          recyclerView=findViewById(R.id.recycle);
          recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));






        autolist=new ArrayList<>();
        searchlist=new ArrayList<>();
        blankremove=new ArrayList<>();
        db.beginTransaction();
        Cursor c= db.rawQuery("select * from food ",null);
        while (c.moveToNext()){

            autolist.add(c.getString(0));
            blankremove.add(c.getString(0).replace(" ","").toLowerCase());


        }
        db.setTransactionSuccessful();
        db.endTransaction();






        Log.d("리스트",autolist.size()+"");
        food_list_adapter=new Food_List_Adapter(getApplicationContext(),searchlist);
        Log.d("리스트",food_list_adapter.getItemCount()+"");
//

//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                if(!recyclerView.canScrollVertically(1)){
//                    int num=1;
//
//                    for (int i = 0; i < blankremove.size(); i++) {
////                    for (int i = 0; i < page; i++) {
//                        if (blankremove.get(i).contains(search.getText().toString().replace(" ", "").toLowerCase())) {
//                            num=num+1;
//                            if(num>page) {
//                                searchlist.add(autolist.get(i));
//                            }
//                            if(page+10==num){
//                                break;
//                            }
//                        }
//
//                    }
//                    page=page+10;
//
//                }
//            }
//        });

        recyclerView.setAdapter(food_list_adapter);


        food_list_adapter.SetItemClick_j(new Food_List_Adapter.ItemClickeListener() {
            @Override
            public void onClick(View v, int position) {
//                Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Food_input.class);
                intent.putExtra("value", searchlist.get(position));
                startActivityForResult(intent, 100);
                searchlist.clear();
                search.setText("");
                food_list_adapter.notifyDataSetChanged();

            }
                });

        search=findViewById(R.id.edit1);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return true;
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("텍스트1",s+"");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("텍스트2", s + "");
                searchlist.clear();


                if (s.toString().replace(" ","").length()!=0) {
                    Food_List_Adapter.textcolor=s.toString().replace(" ","").toLowerCase();
                    for (int i = 0; i < blankremove.size(); i++) {
//                    for (int i = 0; i < page; i++) {
                        if (blankremove.get(i).contains(s.toString().replace(" ", "").toLowerCase())) {
                            searchlist.add(autolist.get(i));

                        }

                    }
                }

                food_list_adapter.notifyDataSetChanged();

            }
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("텍스트3",s+"");


            }
        });






        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("리스트",autolist.size()+"");
        Log.d("리스트",food_list_adapter.getItemCount()+"");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==100){
            if(resultCode==Activity.RESULT_OK){
              Toast.makeText(getApplicationContext(),data.getStringExtra("이름"),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
