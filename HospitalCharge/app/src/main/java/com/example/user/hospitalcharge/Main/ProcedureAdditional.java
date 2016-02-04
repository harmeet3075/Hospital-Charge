package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Adapters.PhysicianSearch;
import com.example.user.hospitalcharge.Model.Physician.AdditionalData;
import com.example.user.hospitalcharge.Model.Physician.PhysicianData;
import com.example.user.hospitalcharge.Model.Physician.Physiciansprocedureslist;
import com.example.user.hospitalcharge.Model.Physician.ProviderCategory;
import com.example.user.hospitalcharge.Model.Physician.State;
import com.example.user.hospitalcharge.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by user on 07-01-2016.
 */
public class ProcedureAdditional extends Activity {

    private ListView listView;
    private com.example.user.hospitalcharge.Adapters.ProcedureAdditional procedureAdditional;
    private ArrayList<AdditionalData> additionalDatas=null;
    private TextView physicianHeadingText,categoryTypeText,addressText,genderText,credentialsText,phoneText,zipText,emailText,locationText;
    private ImageView backButton;
    private Button claimButtom,diagListButton;
    private String firstName,lastName;
    private ImageView imageView1;
    private String default_url="http://hospitalcharge.com/img/physician/thumb/";
    private String default_male_image_url="general_doctor_male.png",
            default_female_image_url="general_doctor_female.jpg",imageName;
    private double lat,lng;
    private String lat_String,lng_String,physician_id;

    private GoogleMap googleMap;
    private MapView mapView;
    LatLng sydney;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_additional_servicies_details_page_2);
        View view=findViewById(R.id.header);
        //listView=(ListView)findViewById(R.id.search_listview);
        claimButtom=(Button)findViewById(R.id.claim);
        diagListButton=(Button)findViewById(R.id.diaglistbutton);
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
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        physicianHeadingText=(TextView)findViewById(R.id.search_textview);
        categoryTypeText=(TextView) findViewById(R.id.textview1);
        addressText=(TextView)findViewById(R.id.textview2);
        genderText=(TextView)findViewById(R.id.textview3);
        credentialsText=(TextView)findViewById(R.id.textview4);
        phoneText=(TextView)findViewById(R.id.textview5);
        zipText=(TextView)findViewById(R.id.textview6);
        emailText=(TextView)findViewById(R.id.textview7);
        imageView1=(ImageView)findViewById(R.id.imageview1);
        //locationText = (TextView)findViewById(R.id.map);
        Bundle bundle1=getIntent().getExtras();
        additionalDatas=bundle1.getParcelableArrayList("list");
        firstName=bundle1.getString("firstName");
        lastName=bundle1.getString("lastName");
        physicianHeadingText.setText(bundle1.getString("physicianHeading"));
        categoryTypeText.setText(bundle1.getString("providerCategory"));
        addressText.setText(bundle1.getString("state"));
        genderText.setText(bundle1.getString("gender"));
        credentialsText.setText(bundle1.getString("credentials"));
        phoneText.setText(bundle1.getString("phone"));
        zipText.setText(bundle1.getString("zip"));
        emailText.setText(bundle1.getString("email"));
        imageName=bundle1.getString("image");
        lat=bundle1.getDouble("lat");
        lng=bundle1.getDouble("lng");
        lat_String=bundle1.getString("lat_string");
        lng_String=bundle1.getString("lng_string");
        physician_id=bundle1.getString("id");

        mapView = (MapView)findViewById(R.id.map);

        //googleMap.getUiSettings();
        if(lat_String.equals("")&& lng_String.equals(""))
        {
            sydney = new LatLng(lat,lng);

        }else
        {
            sydney = new LatLng(Double.parseDouble(lat_String),Double.parseDouble(lng_String));

        }
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(status== ConnectionResult.SUCCESS) {
            mapView.onCreate(bundle);
            googleMap = mapView.getMap();
            googleMap.addMarker(new MarkerOptions().position(sydney));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
        }
        else{

            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, 10);
            dialog.show();

        }
       // procedureAdditional=new com.example.user.hospitalcharge.Adapters.ProcedureAdditional(this,additionalDatas);
        //listView.setAdapter(procedureAdditional);
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
        if(imageName.equals("")&&bundle1.getString("gender").equals(""))
        {
            new DownloadImage().execute(default_url+default_male_image_url);
        }
        else if(imageName.equals("")&&bundle1.getString("gender").equals("M"))
        {
            new DownloadImage().execute(default_url+default_male_image_url);
        }
        else if(imageName.equals("")&&bundle1.getString("gender").equals("F"))
        {
            new DownloadImage().execute(default_url+default_female_image_url);
        }
        else
        {
            new DownloadImage().execute(default_url+imageName);
        }
        diagListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Diagnosis_add_listview_2.class);
                Bundle bundle1=new Bundle();
                bundle1.putParcelableArrayList("list",additionalDatas);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        /*locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), LocationActivity.class);
                Bundle bundle1=new Bundle();
                if(lat_String.equals("")&& lng_String.equals(""))
                {
                    bundle1.putDouble("lat",lat);
                    bundle1.putDouble("lng",lng);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }else
                {
                    bundle1.putDouble("lat",Double.parseDouble(lat_String));
                    bundle1.putDouble("lng",Double.parseDouble(lng_String));
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }


            }
        });*/
        //Toast.makeText(this, "size: " + additionalDatas.size(), Toast.LENGTH_SHORT).show();
    }

    public void getClaim()
    {
        Intent intent=new Intent(getApplicationContext(), ProcedureClaimActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("physicianHeading",physicianHeadingText.getText().toString());
        bundle.putString("firstName",firstName);
        bundle.putString("lastName",lastName);
        bundle.putString("id",physician_id);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            imageView1.setImageBitmap(result);

        }
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
