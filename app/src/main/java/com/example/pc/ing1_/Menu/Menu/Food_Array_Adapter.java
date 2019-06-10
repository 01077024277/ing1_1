package com.example.pc.ing1_.Menu.Menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.pc.ing1_.R;

import java.util.ArrayList;
import java.util.List;

public class Food_Array_Adapter extends ArrayAdapter<String> {
    Context context;

    List<String> list, suggestions;


    public Food_Array_Adapter( Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }
}

