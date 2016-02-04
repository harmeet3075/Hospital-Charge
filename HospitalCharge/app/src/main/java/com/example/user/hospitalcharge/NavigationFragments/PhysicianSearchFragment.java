package com.example.user.hospitalcharge.NavigationFragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.hospitalcharge.Adapters.PhysicianSearch;
import com.example.user.hospitalcharge.Adapters.ProcedureSearch;
import com.example.user.hospitalcharge.Main.Diagnosis_add_listview_3;
import com.example.user.hospitalcharge.Main.HospitalClaimActivity;
import com.example.user.hospitalcharge.Main.LocationActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Main.PhysicianClaimActivity;
import com.example.user.hospitalcharge.Model.Physician.Physician;
import com.example.user.hospitalcharge.Model.Physician.PhysicianData;
import com.example.user.hospitalcharge.Model.Physician.ProviderCategory;
import com.example.user.hospitalcharge.Model.Physician.State;
import com.example.user.hospitalcharge.Model.Physician.Physiciansprocedureslist;
import com.example.user.hospitalcharge.Model.Procedure.ProcedureData;
import com.example.user.hospitalcharge.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by user on 06-01-2016.
 */
public class PhysicianSearchFragment extends Fragment {


    private ListView listView;
    private PhysicianSearch physicianSearch;
    private ArrayList<Physiciansprocedureslist> physicianlist_search=null;
    private TextView physicianHeadingText,categoryTypeText,addressText,locationText,genderText,emailText,credentialsText,phoneText,zipText;
    private Button loadButton,claimButtom,diagListButton;
    private ImageView termsImage;
    private ProgressDialog dialog;
    private int pageCount = 0;
    private ArrayList<PhysicianData> physicianDataArrayList=new ArrayList<PhysicianData>();
    private ProviderCategory providerCategory;
    private State state;
    private double lat,lng;
    private GoogleMap googleMap;
    private MapView mapView;
    LatLng sydney;
    private String lat_String,lng_String,physician_id;
    private ImageView imageView1;
    private String default_url="http://hospitalcharge.com/img/physician/thumb/";
    private String default_male_image_url="general_doctor_male.png",
            default_female_image_url="general_doctor_female.jpg",imageName,address;
    private ArrayList<com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures> physiciansProcedureslist_physician= new ArrayList<com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures>();
    private ArrayList<com.example.user.hospitalcharge.Model.Physician.Procedures> procedureslist_physician= new ArrayList<com.example.user.hospitalcharge.Model.Physician.Procedures>();
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_additional_servicies_details_page_3,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("PROVIDER");
        navigationActivity.setBackVisibility(true);
        //listView=(ListView)view.findViewById(R.id.search_listview);
        imageView1=(ImageView)view.findViewById(R.id.imageview1);
        claimButtom=(Button)view.findViewById(R.id.claim);
        diagListButton=(Button)view.findViewById(R.id.diaglistbutton);
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
                Intent intent=new Intent(getActivity(), Diagnosis_add_listview_3.class);
                Bundle bundle1=new Bundle();
                bundle1.putParcelableArrayList("physiciansProcedureslist_physician",physiciansProcedureslist_physician);
                bundle1.putParcelableArrayList("procedureslist_physician",procedureslist_physician);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        physicianHeadingText=(TextView)view.findViewById(R.id.search_textview);
        categoryTypeText=(TextView)view.findViewById(R.id.textview1);
        addressText=(TextView)view.findViewById(R.id.textview2);
        //locationText = (TextView)view.findViewById(R.id.map);
        genderText=(TextView)view.findViewById(R.id.textview3);
        credentialsText=(TextView)view.findViewById(R.id.textview4);
        phoneText=(TextView)view.findViewById(R.id.textview5);
        zipText=(TextView)view.findViewById(R.id.textview6);
        emailText=(TextView)view.findViewById(R.id.textview7);
        //new DownloadImage().execute("http://hospitalcharge.com/img/physician/thumb/ruth-atkinson-birmingham-al.png");
        /*locationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LocationActivity.class);
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
        Bundle bundle1=getArguments();
        imageName=bundle1.getString("image");

        //physicianlist_search=bundle1.getParcelableArrayList("list");
        physiciansProcedureslist_physician=bundle1.getParcelableArrayList("physiciansProcedureslist_physician");
        procedureslist_physician=bundle1.getParcelableArrayList("procedureslist_physician");
        physicianHeadingText.setText(bundle1.getString("physicianHeading"));
        addressText.setText(bundle1.getString("completeAddress"));
        address=bundle1.getString("completeAddress");
        lat_String=bundle1.getString("lat_string");
        lng_String=bundle1.getString("lng_string");
        lat=bundle1.getDouble("lat");
        lng=bundle1.getDouble("lng");
        physician_id=bundle1.getString("id");
        if(bundle1.getString("gender").equals(""))
        {
            genderText.setText("NA");
        }
        else
        {
            genderText.setText(bundle1.getString("gender"));
        }
        if(bundle1.getString("credentials").equals(""))
        {
            credentialsText.setText("NA");
        }
        else
        {
            credentialsText.setText(bundle1.getString("credentials"));
        }
        if(bundle1.getString("phone").equals(""))
        {
            phoneText.setText("NA");
        }
        else
        {
            phoneText.setText(bundle1.getString("phone"));
        }
        if(bundle1.getString("zip").equals(""))
        {
            zipText.setText("NA");
        }
        else
        {
            zipText.setText(bundle1.getString("zip"));
        }
        if(bundle1.getString("email").equals(""))
        {
            emailText.setText("NA");
        }
        else
        {
            emailText.setText(bundle1.getString("email"));
        }
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


        mapView = (MapView)view.findViewById(R.id.map);


        Geocoder coder = new Geocoder(getActivity());
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
            if(adresses!=null && adresses.size()>0)
            {
                for(Address add : adresses){

                    sydney = new LatLng(add.getLatitude(),add.getLongitude());

                }
            }
            else
            {
                if(lat_String.equals("")&& lng_String.equals(""))
                {
                    sydney = new LatLng(lat,lng);

                }else
                {
                    sydney = new LatLng(Double.parseDouble(lat_String),Double.parseDouble(lng_String));

                }
            }

        } catch (Exception e) {


            if(lat_String.equals("")&& lng_String.equals(""))
            {
                sydney = new LatLng(lat,lng);

            }else
            {
                sydney = new LatLng(Double.parseDouble(lat_String),Double.parseDouble(lng_String));

            }
            e.printStackTrace();
        }

        //googleMap.getUiSettings();
        /*if(lat_String.equals("")&& lng_String.equals(""))
        {
            sydney = new LatLng(lat,lng);

        }else
        {
            sydney = new LatLng(Double.parseDouble(lat_String),Double.parseDouble(lng_String));

        }*/

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if(status== ConnectionResult.SUCCESS) {

            mapView.onCreate(bundle);
            googleMap = mapView.getMap();
            googleMap.addMarker(new MarkerOptions().position(sydney));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
        }
        else{

            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 10);
            dialog.setCancelable(true);

            dialog.show();

        }

        //providerCategory=bundle1.getParcelable("providerCategory");
        //state=bundle1.getParcelable("state");
        /*categoryTypeText.setText(providerCategory.getCategories().getCategory_name());
        addressText.setText(state.getStates().getName()+","+state.getStates().getCode());*/

        categoryTypeText.setText(bundle1.getString("providerCategory"));
        //addressText.setText(bundle1.getString("state"));

       // physicianlist_search=bundle1.getParcelableArrayList("list");
        claimButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClaim();
            }
        });

        /*physicianSearch=new PhysicianSearch(getActivity(),procedureslist_physician,physiciansProcedureslist_physician*//*,providerCategory,state*//*);
        listView.setAdapter(physicianSearch);*/
        return view;
    }


    /*private void loadData(int start,int end)
    {

        pageCount++;
        for (int i=start;i<end;i++)
        {
            try {
                Thread.sleep(200);
                PhysicianData data=new PhysicianData();
                data.setProviderTypeText(providerCategory.getCategories().getCategory_name());
                data.setLocText(state.getStates().getName()+","+state.getStates().getCode());
                data.setProviderNameText(procedureslist_physician.get(i).getProcedure_name());
                data.setAvgChargesText(physiciansProcedureslist_physician.get(i).getCharge_amt());
                data.setAvgTotalText(physiciansProcedureslist_physician.get(i).getTotal_amt());
                data.setAvgMedicareText(physiciansProcedureslist_physician.get(i).getMedicare_amt());
                physicianDataArrayList.add(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    private class Task extends AsyncTask<String,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            loadButton.setVisibility(View.GONE);
            termsImage.setVisibility(View.GONE);
            physicianSearch.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(String... params) {

            loadData(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
            return null;
        }
    }*/

    public void getClaim()
    {
        Intent intent=new Intent(getActivity(), PhysicianClaimActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("physicianHeading",physicianHeadingText.getText().toString());
        bundle.putString("lastName",physicianHeadingText.getText().toString());
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
