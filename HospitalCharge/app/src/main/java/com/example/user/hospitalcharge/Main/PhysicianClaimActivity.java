package com.example.user.hospitalcharge.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.R;

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
public class PhysicianClaimActivity extends Activity{


    private ImageView backButton;
    private String physicianName,physician_id;
    private TextView physicianNameText;
    private EditText physicianNameEdit,firstNameEdit,lastNameEdit,emailEdit,passEdit,phoneEdit,licenceEdit,messageEdit;
    private Button saveButton,cancelButton;
    private ProgressDialog progressDialog;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_claim_listing_physician_page);
        View view=findViewById(R.id.header);
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        physicianNameText=(TextView)findViewById(R.id.claim);
        firstNameEdit=(EditText)findViewById(R.id.editext1);
        lastNameEdit=(EditText)findViewById(R.id.editext7);
        emailEdit=(EditText)findViewById(R.id.editext2);
        passEdit=(EditText)findViewById(R.id.editext3);
        phoneEdit=(EditText)findViewById(R.id.editext4);
        licenceEdit=(EditText)findViewById(R.id.editext5);
        messageEdit=(EditText)findViewById(R.id.editext6);
        Bundle bundle1=getIntent().getExtras();
        physicianName=bundle1.getString("physicianHeading");
        physicianNameText.setText(physicianNameText.getText().toString()+physicianName);
        firstNameEdit.setText(bundle1.getString("firstName"));
        lastNameEdit.setText(bundle1.getString("lastName"));
        physician_id=bundle1.getString("id");
        saveButton=(Button)findViewById(R.id.save);
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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClaim();
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
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void saveClaim()
    {
        /*String parameters="physician_fname="+firstNameEdit.getText().toString()+
                "&physician_lname="+lastNameEdit.getText().toString()+
                "&email="+emailEdit.getText().toString()+
                "&password="+passEdit.getText().toString()+
                "&phone="+phoneEdit.getText().toString()+
                "&license_no="+licenceEdit.getText().toString()+
                "&message="+messageEdit.getText().toString();*/


        String[] params={firstNameEdit.getText().toString(),
                lastNameEdit.getText().toString(),
                emailEdit.getText().toString(),
                passEdit.getText().toString(),
                phoneEdit.getText().toString(),
                licenceEdit.getText().toString(),
                messageEdit.getText().toString(),
                physician_id};

        if(Connectivity.checkInternet(getApplicationContext()))
        {
            if(firstNameEdit.getText().toString().equals("")||lastNameEdit.getText().toString().equals("")
                    ||passEdit.getText().toString().equals("")||emailEdit.getText().toString().equals("")
                    ||phoneEdit.getText().toString().equals("")||licenceEdit.getText().toString().equals("")
                    ||messageEdit.getText().toString().equals(""))
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
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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
            progressDialog=new ProgressDialog(PhysicianClaimActivity.this,ProgressDialog.STYLE_SPINNER);
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
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("physician_fname", params[0]));
            nameValuePair.add(new BasicNameValuePair("physician_lname",params[1]));
            nameValuePair.add(new BasicNameValuePair("email", params[2]));
            nameValuePair.add(new BasicNameValuePair("password", params[3]));
            nameValuePair.add(new BasicNameValuePair("phone", params[4]));
            nameValuePair.add(new BasicNameValuePair("license_no", params[5]));
            nameValuePair.add(new BasicNameValuePair("message", params[6]));
            return NetworkCall.makePostRequest(nameValuePair, "http://hospitalcharge.com/api/claim/physician/"+params[7]);
            /*return makeNetwowkCall(params[0],"http://hospitalcharge.com/api/claim/physician/"+params[1]);*/
        }
    }
}
