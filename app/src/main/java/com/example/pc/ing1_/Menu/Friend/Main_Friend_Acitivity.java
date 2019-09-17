package com.example.pc.ing1_.Menu.Friend;

import android.graphics.Color;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pc.ing1_.Menu.Friend.multi_chat.Multi_Chat_Frag;
import com.example.pc.ing1_.R;

public class Main_Friend_Acitivity extends AppCompatActivity {

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    private Button bt_tab1, bt_tab2,bt_tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main__friend_acitivity);

        // 위젯에 대한 참조
        bt_tab1 = (Button)findViewById(R.id.bt_tab1);
        bt_tab2 = (Button)findViewById(R.id.bt_tab2);
        bt_tab3 = (Button)findViewById(R.id.bt_tab3);


        // 임의로 액티비티 호출 시점에 어느 프레그먼트를 프레임레이아웃에 띄울 것인지를 정함
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        friend_list_frag fragment1 = new friend_list_frag();
        transaction.replace(R.id.fragment_container, fragment1);
        transaction.commit();
        bt_tab1.setBackgroundColor(Color.parseColor("#000000"));
        bt_tab1.setTextColor(Color.parseColor("#ffffff"));
        bt_tab2.setBackgroundColor(Color.parseColor("#d8d8d8"));
        bt_tab2.setTextColor(Color.parseColor("#000000"));
        bt_tab3.setBackgroundColor(Color.parseColor("#d8d8d8"));
        bt_tab3.setTextColor(Color.parseColor("#000000"));

        // 탭 버튼에 대한 리스너 연결
      bt_tab1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

              friend_list_frag fragment1 = new friend_list_frag();
              transaction.replace(R.id.fragment_container, fragment1);
              transaction.commit();
              bt_tab1.setBackgroundColor(Color.parseColor("#000000"));
              bt_tab1.setTextColor(Color.parseColor("#ffffff"));
              bt_tab2.setBackgroundColor(Color.parseColor("#d8d8d8"));
              bt_tab2.setTextColor(Color.parseColor("#000000"));
              bt_tab3.setBackgroundColor(Color.parseColor("#d8d8d8"));
              bt_tab3.setTextColor(Color.parseColor("#000000"));

          }
      });
      bt_tab2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

              chat_list_frag fragment2 = new chat_list_frag();
              transaction.replace(R.id.fragment_container, fragment2);
              transaction.commit();
              bt_tab2.setBackgroundColor(Color.parseColor("#000000"));
              bt_tab2.setTextColor(Color.parseColor("#ffffff"));
              bt_tab1.setBackgroundColor(Color.parseColor("#d8d8d8"));
              bt_tab1.setTextColor(Color.parseColor("#000000"));
              bt_tab3.setBackgroundColor(Color.parseColor("#d8d8d8"));
              bt_tab3.setTextColor(Color.parseColor("#000000"));
          }
      });
      bt_tab3.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

              Multi_Chat_Frag fragment3 = new Multi_Chat_Frag();
              transaction.replace(R.id.fragment_container, fragment3);
              transaction.commit();
              bt_tab2.setBackgroundColor(Color.parseColor("#d8d8d8"));
              bt_tab2.setTextColor(Color.parseColor("#000000"));
              bt_tab1.setBackgroundColor(Color.parseColor("#d8d8d8"));
              bt_tab1.setTextColor(Color.parseColor("#000000"));
              bt_tab3.setBackgroundColor(Color.parseColor("#000000"));
              bt_tab3.setTextColor(Color.parseColor("#ffffff"));
          }
      });


    }



}
