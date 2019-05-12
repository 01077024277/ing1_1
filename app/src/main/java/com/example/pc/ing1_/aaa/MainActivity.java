package com.example.pc.ing1_.aaa;


import com.example.pc.ing1_.aaa.MapWrapperLayout;
import com.example.pc.ing1_.aaa.OnInfoWindowElemTouchListener;
import com.example.pc.ing1_.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;


import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnMapReadyCallback {
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private Button infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener;
    MapFragment mapFragment;
    MapWrapperLayout mapWrapperLayout;
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        mapFragment.getMapAsync(this);
        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge


        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up





        // Let's add a couple of markers

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    @Override
    public void onMapReady(GoogleMap map) {



        mapWrapperLayout.init(map, getPixelsFromDp(this, 39 + 20));
        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.info__window, null);
        this.infoTitle = (TextView)infoWindow.findViewById(R.id.title);
        this.infoSnippet = (TextView)infoWindow.findViewById(R.id.snippet);
        this.infoButton = (Button)infoWindow.findViewById(R.id.button);


        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.ic_user_1),
                getResources().getDrawable(R.drawable.bubble_mask))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Toast.makeText(MainActivity.this, marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
            }
        };
        this.infoButton.setOnTouchListener(infoButtonListener);


        map.setInfoWindowAdapter(new InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoTitle.setText(marker.getTitle());
                infoSnippet.setText(marker.getSnippet());
                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });






        map.addMarker(new MarkerOptions()
                .title("Prague")
                .snippet("Czech Republic")
                .position(new LatLng(50.08, 14.43)));

        map.addMarker(new MarkerOptions()
                .title("Paris")
                .snippet("France")
                .position(new LatLng(48.86,2.33)));

        map.addMarker(new MarkerOptions()
                .title("London")
                .snippet("United Kingdom")
                .position(new LatLng(51.51,-0.1)));
    }
}