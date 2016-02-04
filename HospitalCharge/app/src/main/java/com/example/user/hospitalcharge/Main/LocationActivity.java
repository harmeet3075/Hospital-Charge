package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.user.hospitalcharge.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 11-01-2016.
 */
public class LocationActivity extends Activity {

    private GoogleMap googleMap;
    private MapView mapView;
    private ImageView backButton;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_location);
        View view=findViewById(R.id.header);
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        mapView = (MapView)findViewById(R.id.map);
        mapView.onCreate(bundle);
        Bundle bundle1=getIntent().getExtras();
        double lat=bundle1.getDouble("lat");
        double lng=bundle1.getDouble("lng");
        MapsInitializer.initialize(this);
        googleMap = mapView.getMap();
        //googleMap.getUiSettings();
        LatLng sydney = new LatLng(lat,lng);
        googleMap.addMarker(new MarkerOptions().position(sydney));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 9));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void onResume() {
        super.onResume();
        mapView.onResume();
    }


    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
