package com.example.user.hospitalcharge.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
 * Created by user on 12/3/2015.
 */
public class HospitalActivity extends Activity {

    private ProgressDialog progressDialog;
    private EditText nameText,codeText,phoneText,emailText
            ,passText,addressText1,addressText2,cityText,zipText,licenseText;
    private Spinner statesSpinner,countrySpinner;
    private Button submitButton;
    private ArrayList<String> states_list=new ArrayList<String>();
    private ArrayList<String> country_list=new ArrayList<String>();
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_hospital_form);
        statesSpinner=(Spinner)findViewById(R.id.hospital_form_spinner1);
        countrySpinner=(Spinner)findViewById(R.id.hospital_form_spinner2);
        nameText=(EditText)findViewById(R.id.hospital_form_edittext1);
        codeText=(EditText)findViewById(R.id.hospital_form_edittext2);
        phoneText=(EditText)findViewById(R.id.hospital_form_edittext3);
        emailText=(EditText)findViewById(R.id.hospital_form_edittext4);
        passText=(EditText)findViewById(R.id.hospital_form_edittext5);
        addressText1=(EditText)findViewById(R.id.hospital_form_edittext6);
        addressText2=(EditText)findViewById(R.id.hospital_form_edittext7);
        cityText=(EditText)findViewById(R.id.hospital_form_edittext8);
        zipText=(EditText)findViewById(R.id.hospital_form_edittext9);
        licenseText=(EditText)findViewById(R.id.hospital_form_edittext12);
        submitButton=(Button)findViewById(R.id.hospital_form_button);
        submitButton.setOnTouchListener(new View.OnTouchListener() {
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
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String parameters="data[Register][type]=2"+
                        "&data[Register][name]="+nameText.getText().toString()+
                        "&data[Register][code]="+codeText.getText().toString()+
                        "&data[Register][phone]="+phoneText.getText().toString()+
                        "&data[Register][email]="+emailText.getText().toString()+
                        "&data[Register][password]="+passText.getText().toString()+
                        "&data[Register][street_address1]="+addressText1.getText().toString()+
                        "&data[Register][street_address2]="+addressText2.getText().toString()+
                        "&data[Register][city]="+cityText.getText().toString()+
                        "&data[Register][zip_code]="+zipText.getText().toString()+
                        "&data[Register][state_code]="+String.valueOf(statesSpinner.getSelectedItemPosition())+
                        "&data[Register][country_code]="+String.valueOf(countrySpinner.getSelectedItemPosition())+
                        "&data[Register][license_no]="+licenseText.getText().toString()+
                        "&data[Register][user_type]=new"+"&api_key=bf45c093e542f057c123ae7d6";

                String[] params={"2",nameText.getText().toString(),codeText.getText().toString(),
                        phoneText.getText().toString(),emailText.getText().toString(),passText.getText().toString()
                ,addressText1.getText().toString(),addressText2.getText().toString(),cityText.getText().toString()
                ,zipText.getText().toString(),String.valueOf(statesSpinner.getSelectedItemPosition()),
                        String.valueOf(countrySpinner.getSelectedItemPosition()),licenseText.getText().toString(),
                "new","bf45c093e542f057c123ae7d6"};

                if(Connectivity.checkInternet(getApplicationContext()))
                {
                    if(nameText.getText().toString().equals("")||codeText.getText().toString().equals("")
                ||phoneText.getText().toString().equals("")||emailText.getText().toString().equals("")
                            ||passText.getText().toString().equals("")||addressText1.getText().toString().equals("")
                            ||addressText2.getText().toString().equals("")||cityText.getText().toString().equals("")
                            ||zipText.getText().toString().equals("")||licenseText.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "All fields are mandatory.", Toast.LENGTH_SHORT).show();
                        return;
                    }else
                    {
                        new SubmitTask().execute(params);
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();

            }
        });

        Bundle bundle1=getIntent().getExtras();
        states_list=bundle1.getStringArrayList("states");
        country_list=bundle1.getStringArrayList("country");
        ArrayAdapter<String> adapter3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,states_list);
        ArrayAdapter<String> adapter4=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,country_list);
        statesSpinner.setAdapter(adapter3);
        countrySpinner.setAdapter(adapter4);
    }






    private class SubmitTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(HospitalActivity.this,ProgressDialog.STYLE_SPINNER);
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
                    Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            //return makeNetwowkCall(params[0], Urls.HOSPITAL_REGISTER_URL);

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Register][type]",params[0]));
            nameValuePair.add(new BasicNameValuePair("data[Register][name]",params[1]));
            nameValuePair.add(new BasicNameValuePair("data[Register][code]",params[2]));
            nameValuePair.add(new BasicNameValuePair("data[Register][phone]",params[3]));
            nameValuePair.add(new BasicNameValuePair("data[Register][email]",params[4]));
            nameValuePair.add(new BasicNameValuePair("data[Register][password]",params[5]));
            nameValuePair.add(new BasicNameValuePair("data[Register][street_address1]",params[6]));
            nameValuePair.add(new BasicNameValuePair("data[Register][street_address2]",params[7]));
            nameValuePair.add(new BasicNameValuePair("data[Register][city]",params[8]));
            nameValuePair.add(new BasicNameValuePair("data[Register][zip_code]",params[9]));
            nameValuePair.add(new BasicNameValuePair("data[Register][state_code]",params[10]));
            nameValuePair.add(new BasicNameValuePair("data[Register][country_code]",params[11]));
            nameValuePair.add(new BasicNameValuePair("data[Register][license_no]",params[12]));
            nameValuePair.add(new BasicNameValuePair("data[Register][user_type]",params[13]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[14]));
            return NetworkCall.makePostRequest(nameValuePair,Urls.HOSPITAL_REGISTER_URL);
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
            URL url=new URL( request );
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
