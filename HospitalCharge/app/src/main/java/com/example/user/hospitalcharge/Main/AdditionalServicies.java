package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Adapters.AdditionalSearch;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.AddServiciesData;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoseshospitalslist;
import com.example.user.hospitalcharge.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 06-01-2016.
 */
public class AdditionalServicies extends Activity {


    private ListView listView;
    private AdditionalSearch additionalSearch;
    private TextView hospitalNameText,hospitalAddressText,hospitalPhoneText,hospitalZipText,hospitalEmailText,mapText;
    private String hospitalName,hospitalAddress,hospitalPhone,hospitalZipCode,hospitalEmail,hospital_id;
    private ArrayList<Diagnoseshospitalslist> diagnoseshospitalslists;
    private ArrayList<AddServiciesData> addServiciesDatas;
    private ImageView backButton;
    private Button claimButtom,diagListButton;
    private double lat,lang;

    private GoogleMap googleMap;
    private MapView mapView;
    public void onCreate(final Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_additional_servicies_details_page_1);
        //listView=(ListView) findViewById(R.id.search_listview);
        View view=findViewById(R.id.header);



        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        claimButtom=(Button)findViewById(R.id.claim);
        diagListButton=(Button)findViewById(R.id.diaglistbutton);
        hospitalNameText=(TextView)findViewById(R.id.search_textview);
        hospitalAddressText=(TextView)findViewById(R.id.textview1);
        hospitalPhoneText=(TextView)findViewById(R.id.textview2);
        hospitalZipText=(TextView)findViewById(R.id.textview3);
        hospitalEmailText=(TextView)findViewById(R.id.textview4);
        /*mapText=(TextView)findViewById(R.id.mapText);
        mapText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LocationActivity.class);
                Bundle bundle1=new Bundle();
                bundle1.putDouble("lat",lat);
                bundle1.putDouble("lng",lang);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });*/

        Bundle bundle1=getIntent().getExtras();
        hospitalName=bundle1.getString("hospitalName");
        hospitalAddress=bundle1.getString("hospitalAddress");
        hospitalPhone=bundle1.getString("hospitalPhone");
        hospitalZipCode=bundle1.getString("hospitalZipCode");
        hospitalEmail=bundle1.getString("hospitalEmail");
        addServiciesDatas=bundle1.getParcelableArrayList("list");
        lat=bundle1.getDouble("lat");
        lang=bundle1.getDouble("lng");
        hospital_id=bundle1.getString("hospital_id");
        hospitalNameText.setText(hospitalName);
        hospitalAddressText.setText(hospitalAddress);
        hospitalPhoneText.setText(hospitalPhone);
        hospitalZipText.setText(hospitalZipCode);

        mapView = (MapView)findViewById(R.id.map);

        Geocoder coder = new Geocoder(this);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(hospitalAddress, 50);
            if(adresses!=null && adresses.size()>0)
            {
                for(Address add : adresses){

                    lang = add.getLongitude();
                    lat = add.getLatitude();

                }
            }
            else
            {
                lat=bundle1.getDouble("lat");
                lang=bundle1.getDouble("lng");
            }
        } catch (Exception e) {

            lat=bundle1.getDouble("lat");
            lang=bundle1.getDouble("lng");
            e.printStackTrace();
        }
        //googleMap.getUiSettings();
        LatLng sydney = new LatLng(lat, lang);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(status== ConnectionResult.SUCCESS) {
            mapView.onCreate(bundle);
            googleMap = mapView.getMap();
            googleMap.addMarker(new MarkerOptions().position(sydney));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
        }else{

            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, 10);
            dialog.show();

        }
        if(hospitalEmail.equals(""))
        {
            hospitalEmailText.setText("NA");
        }else
        {
            hospitalEmailText.setText(hospitalEmail);
        }

        /*additionalSearch=new AdditionalSearch(this,addServiciesDatas);
        listView.setAdapter(additionalSearch);*/
        backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
        claimButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClaim();
            }
        });
        claimButtom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
        diagListButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return false;
            }
        });

        diagListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Diagnosis_add_listview.class);
                Bundle bundle1=new Bundle();
                bundle1.putParcelableArrayList("list", addServiciesDatas);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    public void getClaim()
    {
        Intent intent=new Intent(getApplicationContext(), HospitalClaimActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("hospitalName",hospitalName);
        bundle.putString("hospitalEmail",hospitalEmail);
        bundle.putString("hospitalPhone",hospitalPhone);
        bundle.putString("hospital_id",hospital_id);
        intent.putExtras(bundle);
        startActivity(intent);
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
