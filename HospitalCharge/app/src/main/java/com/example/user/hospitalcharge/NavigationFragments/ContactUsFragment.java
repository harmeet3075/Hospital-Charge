package com.example.user.hospitalcharge.NavigationFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.LegalDisclaimerActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by user on 12/8/2015.
 */
public class ContactUsFragment extends Fragment {


    private GoogleMap googleMap;
    private MapView mapView;
    View view;
    private ProgressDialog progressDialog;
    private Button contactButton;
    private EditText nameEdit,emailEdit,msgEdit;
    private ImageView imageView1;
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        view=layoutInflater.inflate(R.layout.layout_contact_frag,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("CONTACT US");
        navigationActivity.setBackVisibility(true);
        navigationActivity.setVisibility(false);
        navigationActivity.showDrawer(false);
        nameEdit=(EditText)view.findViewById(R.id.editext1);
        emailEdit=(EditText)view.findViewById(R.id.editext2);
        msgEdit=(EditText)view.findViewById(R.id.editext3);
        imageView1=(ImageView)view.findViewById(R.id.terms_conditions);
        contactButton=(Button)view.findViewById(R.id.contactButton);
        contactButton.setOnTouchListener(new View.OnTouchListener() {
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
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Connectivity.checkInternet(getActivity())){

                    if (emailEdit.getText().toString().equals("") || nameEdit.getText().toString().equals("")
                            || msgEdit.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "All fields are mandatory.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String[] params = {emailEdit.getText().toString(),
                                nameEdit.getText().toString(),
                                msgEdit.getText().toString()};
                        new ContactUsTask().execute(params);
                    }
                }else
                {

                    Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                }


            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });
        if (Connectivity.checkInternet(getActivity()))
        {

            home();




        }else
        {
            Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
        }
        mapView = (MapView)view.findViewById(R.id.map);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        // Showing status
        if(status== ConnectionResult.SUCCESS) {
        mapView.onCreate(bundle);
    }
    else{

        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 10);
        dialog.show();

    }

        return view;
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

    private void home()
    {
        String parameters="data[Page][title]=contact-us"+"&api_key=bf45c093e542f057c123ae7d6";
        new HomeTask().execute(parameters);
    }

    private class HomeTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

            // Showing status
            if(status== ConnectionResult.SUCCESS) {

                MapsInitializer.initialize(getActivity());
                googleMap = mapView.getMap();
                //googleMap.getUiSettings();
                LatLng sydney = new LatLng(47.771657, -122.332109);
                googleMap.addMarker(new MarkerOptions().position(sydney));
                //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));
            }
            else{

                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 10);
                dialog.show();

            }

        }
        @Override
        protected String doInBackground(String... params) {
            String parameters=params[0];
            String result="done";
            try
            {
                Thread.sleep(2000);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }
    }


    private class ContactUsTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                if(response==200)
                {
                    String msg=jsonObject.getString("msg");
                    Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"error.",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            /*String parameters=params[0];
            String result= makeNetwowkCall(parameters,Urls.CONTACT_US_URL);
            return result;*/
            //// TODO: 13-01-2016 changes.
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

            nameValuePair.add(new BasicNameValuePair("name",params[1]));
            nameValuePair.add(new BasicNameValuePair("email",params[0]));
            nameValuePair.add(new BasicNameValuePair("message",params[2]));
            nameValuePair.add(new BasicNameValuePair("api_key","bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair, Urls.CONTACT_US_URL);

        }
    }
    @SuppressLint("NewApi")
    private String makeNetwowkCall(String params,String requestUrl)
    {
        int responseCode=0;
        StringBuffer data = new StringBuffer();
        try {


            String urlParameters  = params;
            byte[] postData       = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
            }
            int    postDataLength = postData.length;
            String request        =requestUrl;
            URL url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            //conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
                wr.flush();
                wr.close();
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine=null;
            while ((inputLine = in.readLine()) != null) {
                data.append(inputLine);
            }
            in.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return data.toString();
    }
}
