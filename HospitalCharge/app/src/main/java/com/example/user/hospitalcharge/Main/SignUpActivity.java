package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.example.user.hospitalcharge.R;

import java.util.ArrayList;

/**
 * Created by user on 12/3/2015.
 */
public class SignUpActivity extends TabActivity {

    private ArrayList<String> providers_list=new ArrayList<String>();
    private ArrayList<String> states_list=new ArrayList<String>();
    private ArrayList<String> country_list=new ArrayList<String>();
    private ImageView backImageView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup_page);
        View includeView=findViewById(R.id.header);
        backImageView=(ImageView)includeView.findViewById(R.id.signup_header_imageview);
        final TabHost tabHost = getTabHost();
        TabHost.TabSpec physician = tabHost.newTabSpec("Physician");
        physician.setIndicator("");

        Intent phIntent = new Intent(this, PhysicianActivity.class);
        Bundle bundle1=getIntent().getExtras();
        providers_list=bundle1.getStringArrayList("providers");
        states_list=bundle1.getStringArrayList("states");
        country_list=bundle1.getStringArrayList("country");
        phIntent.putExtras(bundle1);
        physician.setContent(phIntent);

        // Tab for Hospital
        TabHost.TabSpec hospital = tabHost.newTabSpec("Hospital");
        // setting Title and Icon for the Tab
        hospital.setIndicator("");
        Intent hospitalIntent = new Intent(this, HospitalActivity.class);
        hospitalIntent.putExtras(bundle1);
        hospital.setContent(hospitalIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(physician);
        tabHost.addTab(hospital);

        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.physician_tab);
        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.hospital_tab);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
