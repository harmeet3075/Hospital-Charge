package com.example.user.hospitalcharge.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by user on 08-01-2016.
 */
public class HospitalClaimActivity extends Activity {

    private ImageView backButton;
    private String hospitalName,hospitalAddress,hospitalPhone,hospitalZipCode,hospitalEmail,hospital_id;
    private TextView hospitalNameText;
    private EditText hospitalNameEdit,hospitalEmailEdit,hospitalPassEdit,hospitalPhoneEdit,hospitalLicenceEdit,hositalMessageEdit;
    private Button saveButton,cancelButton;
    private ProgressDialog progressDialog;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_claim_listing_page);
        View view=findViewById(R.id.header);
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        hospitalNameText=(TextView) findViewById(R.id.claim);
        hospitalEmailEdit=(EditText)findViewById(R.id.editext2);
        hospitalPhoneEdit=(EditText)findViewById(R.id.editext4);
        hospitalNameEdit=(EditText)findViewById(R.id.editext1);
        hospitalPassEdit=(EditText)findViewById(R.id.editext3);
        hospitalLicenceEdit=(EditText)findViewById(R.id.editext5);
        hositalMessageEdit=(EditText)findViewById(R.id.editext6);
        saveButton=(Button)findViewById(R.id.save);
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
        cancelButton=(Button)findViewById(R.id.cancel);
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle1=getIntent().getExtras();
        hospitalName=bundle1.getString("hospitalName");
        hospitalPhone=bundle1.getString("hospitalPhone");
        hospitalEmail=bundle1.getString("hospitalEmail");
        hospital_id=bundle1.getString("hospital_id");
        hospitalNameText.setText(hospitalNameText.getText().toString()+hospitalName);
        hospitalNameEdit.setText(hospitalName);
        hospitalEmailEdit.setText(hospitalEmail);
        hospitalPhoneEdit.setText(hospitalPhone);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClaim();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void saveClaim()
    {
        String parameters="hospital_name="+hospitalNameEdit.getText().toString()+
                "&email="+hospitalEmailEdit.getText().toString()+
                "&password="+hospitalPassEdit.getText().toString()+
                "&phone="+hospitalPhoneEdit.getText().toString()+
                "&license_no="+hospitalLicenceEdit.getText().toString()+
                "&message="+hositalMessageEdit.getText().toString();
        String[] params={hospitalNameEdit.getText().toString()
                ,hospitalEmailEdit.getText().toString(),
                hospitalPassEdit.getText().toString(),
                hospitalPhoneEdit.getText().toString(),
                hospitalLicenceEdit.getText().toString(),
                hositalMessageEdit.getText().toString(),
                hospital_id};
        if(Connectivity.checkInternet(getApplicationContext()))
        {
            if(hospitalNameEdit.getText().toString().equals("")||hospitalEmailEdit.getText().toString().equals("")
                    ||hospitalPassEdit.getText().toString().equals("")||hospitalPhoneEdit.getText().toString().equals("")
                    ||hospitalLicenceEdit.getText().toString().equals("")||hositalMessageEdit.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(),"All fields are mandatory.",Toast.LENGTH_SHORT).show();
                return;
            }else
            {

                new SaveClaimTask().execute(params);
            }
        }else
        {
            Toast.makeText(getApplicationContext(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
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

    private class SaveClaimTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(HospitalClaimActivity.this,ProgressDialog.STYLE_SPINNER);
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
                    Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            //return makeNetwowkCall(params[0],"http://hospitalcharge.com/api/claim/hospital/"+params[1]);


            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("hospital_name", params[0]));
            nameValuePair.add(new BasicNameValuePair("email",params[1]));
            nameValuePair.add(new BasicNameValuePair("password", params[2]));
            nameValuePair.add(new BasicNameValuePair("phone", params[3]));
            nameValuePair.add(new BasicNameValuePair("license_no", params[4]));
            nameValuePair.add(new BasicNameValuePair("message", params[5]));
            return NetworkCall.makePostRequest(nameValuePair, "http://hospitalcharge.com/api/claim/hospital/"+params[6]);
        }
    }
}
