package com.example.user.hospitalcharge.NavigationFragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;

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
 * Created by user on 21-12-2015.
 */
public class PhysicianEditProfile extends Fragment {

    private ProgressDialog progressDialog;
    private EditText firstNameText,lastNameText,genderText,credentialsText,
            addressText1,addressText2,licenseText,phoneText,emailText,zipCodeText;
    private Button saveButton,cancelButton;
    private String ref_id,group_id;
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_hosp_editprofile,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("EDIT PROFILE");
        navigationActivity.setVisibility(false);
        navigationActivity.showDrawer(false);
        navigationActivity.setBackVisibility(true);
        firstNameText=(EditText)view.findViewById(R.id.editext1);
        lastNameText=(EditText)view.findViewById(R.id.editext2);
        genderText=(EditText)view.findViewById(R.id.editext3);
        credentialsText=(EditText)view.findViewById(R.id.editext4);
        addressText1=(EditText)view.findViewById(R.id.editext5);
        addressText2=(EditText)view.findViewById(R.id.editext6);
        licenseText=(EditText)view.findViewById(R.id.editext7);
        phoneText=(EditText)view.findViewById(R.id.editext8);
        emailText=(EditText)view.findViewById(R.id.editext9);
        zipCodeText=(EditText)view.findViewById(R.id.editext10);
        saveButton=(Button)view.findViewById(R.id.save);
        cancelButton=(Button)view.findViewById(R.id.cancel);
        cancelButton.setOnTouchListener(new View.OnTouchListener() {
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

        saveButton.setOnTouchListener(new View.OnTouchListener() {
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
        Bundle bundle1=getArguments();
        ref_id=bundle1.getString("ref_id");
        group_id=bundle1.getString("group_id");
        if(Connectivity.checkInternet(getActivity()))
        {
            String parameters[]={ref_id ,group_id,"bf45c093e542f057c123ae7d6"};
            new GetDetailsTask().execute(parameters);
        }
        else
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });
        cancelButton=(Button)view.findViewById(R.id.cancel);
        return view;
    }


    private class GetDetailsTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("data");
                    JSONObject jsonObject2=jsonObject1.getJSONObject("Physicians");
                    String firstName=jsonObject2.getString("physician_fname");
                    String lastName=jsonObject2.getString("physician_lname");
                    String gender=jsonObject2.getString("gender");
                    String credentials=jsonObject2.getString("credentials");
                    String address1=jsonObject2.getString("street_address1");
                    String address2=jsonObject2.getString("street_address2");
                    String phone=jsonObject2.getString("phone");
                    String email=jsonObject2.getString("email");
                    String license=jsonObject2.getString("license_no");
                    String zip=jsonObject2.getString("zip_code");
                    firstNameText.setText(firstName);
                    lastNameText.setText(lastName);
                    genderText.setText(gender);
                    credentialsText.setText(credentials);
                    zipCodeText.setText(zip);
                    addressText1.setText(address1);
                    addressText2.setText(address2);
                    phoneText.setText(phone);
                    emailText.setText(email);
                    licenseText.setText(license);
                }
                else {
                    Toast.makeText(getActivity(),"Error Occured",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("Users[ref_id]",params[0]));
            nameValuePair.add(new BasicNameValuePair("Users[group_id]",params[1]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[2]));
            return NetworkCall.makePostRequest(nameValuePair,Urls.PHYSICIAN_EDIT_PROFILE_URL);

            //return makeNetwowkCall(params[0], Urls.PHYSICIAN_EDIT_PROFILE_URL);
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
           // conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
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

    private void saveProfile() {

        if(Connectivity.checkInternet(getActivity()))
        {
            String[] parameters={ref_id,group_id,
                    firstNameText.getText().toString(),
                    lastNameText.getText().toString(),
                    addressText1.getText().toString(),
                    addressText2.getText().toString(),
                    emailText.getText().toString(),
                    zipCodeText.getText().toString(),
                    "bf45c093e542f057c123ae7d6"};
            if(firstNameText.getText().toString().equals("")||lastNameText.getText().toString().equals("")
                    ||addressText1.getText().toString().equals("")||addressText2.getText().toString().equals("")
                    ||emailText.getText().toString().equals("")||zipCodeText.getText().toString().equals(""))
            {
                Toast.makeText(getActivity(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
                return;
            }else
            {
                new SaveDetailsTask().execute(parameters);
            }

        }
        else
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();

    }


    private class SaveDetailsTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
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
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    String parameters[]={ref_id,group_id,"bf45c093e542f057c123ae7d6"};
                    new GetDetailsTask().execute(parameters);
                }
                else {
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            //return makeNetwowkCall(params[0], Urls.PHYSICIAN_UPDATE_PROFILE);
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Users][ref_id]",params[0]));
            nameValuePair.add(new BasicNameValuePair("data[Users][group_id]",params[1]));
            nameValuePair.add(new BasicNameValuePair("data[Physicians][physician_fname]",params[2]));
            nameValuePair.add(new BasicNameValuePair("data[Physicians][physician_lname]",params[3]));
            nameValuePair.add(new BasicNameValuePair("data[Physicians][street_address1]",params[4]));
            nameValuePair.add(new BasicNameValuePair("data[Physicians][street_address2]",params[5]));
            nameValuePair.add(new BasicNameValuePair("data[Physicians][email]",params[6]));
            nameValuePair.add(new BasicNameValuePair("data[Physicians][zip_code]",params[7]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[8]));
            return NetworkCall.makePostRequest(nameValuePair,Urls.PHYSICIAN_UPDATE_PROFILE);
        }
    }
}
