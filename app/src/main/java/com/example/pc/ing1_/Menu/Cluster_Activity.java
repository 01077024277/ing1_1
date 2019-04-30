package com.example.pc.ing1_.Menu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.example.pc.ing1_.R;
import com.example.pc.ing1_.Store;

import java.util.ArrayList;

public class Cluster_Activity extends Activity {
    private static final String TAG = "Cluster_Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cluster_activity);
        Intent intent = getIntent();
        ArrayList<Store> store = (ArrayList<Store>) intent.getSerializableExtra("store");
        RecyclerView recyclerView= findViewById(R.id.recycle);
        Cluster_recycle_adapter cluster_recycle_adapter= new Cluster_recycle_adapter(store,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cluster_recycle_adapter);

    }
}
