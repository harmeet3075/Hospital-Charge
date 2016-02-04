package com.example.user.hospitalcharge.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SplashActivity extends Activity {

    private long delay=10;
    SharedPreferences sharedpreferences;
    private ProgressDialog progressDialog;
    private ArrayList<String> states_list=new ArrayList<String>();
    private ArrayList<String> city_list=new ArrayList<String>();
    private ArrayList<String> diagnosisList=new ArrayList<String>();
    private ArrayList<String> diagnosis_Id_List=new ArrayList<String>();
    private ArrayList<String> procedureList=new ArrayList<String>();
    private ArrayList<String> physicianList=new ArrayList<String>();
    private ArrayList<String> procedure_Id_List=new ArrayList<String>();
    private ArrayList<String> physician_Id_List=new ArrayList<String>();
    private long startTime,endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String email=sharedpreferences.getString("email","");
        final String password=sharedpreferences.getString("password","");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
                finish();*/
                start();
            }
        },delay);




    }

    private void start()
    {
        if(Connectivity.checkInternet(this))
        {
            states();
        }
        else{
            new AlertDialog.Builder(this)
                    .setMessage("Please check your internet connection and try again.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }
    private void states() {

        new StatesTask().execute();
    }

    private class StatesTask extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startTime=System.currentTimeMillis();

            /*progressDialog=new ProgressDialog(SplashActivity.this,ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(true);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressDialog.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                if(response==200)
                {
                    JSONObject jsonObject1=jsonObject.getJSONObject("states");
                    states_list=new ArrayList<String>();
                    Iterator<String> iterator=jsonObject1.keys();
                    ArrayList<String> keys=new ArrayList<String>();
                    states_list.add(0,"State Code");
                    city_list.add(0,"City");
                    while(iterator.hasNext())
                    {
                        keys.add(iterator.next());
                    }
                    for(int i=0;i<jsonObject1.length();i++)
                    {
                        states_list.add(jsonObject1.getString(keys.get(i)));
                        city_list.add(keys.get(i));
                    }

                    String parameters = "api_key=bf45c093e542f057c123ae7d6";
                    new GetDiagList().execute(parameters);
                    /*Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putStringArrayList("states_list",states_list);
                    bundle.putStringArrayList("city_list",city_list);
                    intent.putExtras(bundle);
                    endTime=System.currentTimeMillis();
                    long diff=endTime-startTime;
                    Log.d("diff", String.valueOf(diff));
                    startActivity(intent);*/
                    //finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... params) {

            String parameters="api_key=bf45c093e542f057c123ae7d6";
            //String result= makeNetwowkCall(parameters, Urls.STATES_LIST_URL);
            //// TODO: 13-01-2016 changes.
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("api_key", "bf45c093e542f057c123ae7d6"));
            String result=NetworkCall.makePostRequest(nameValuePair,Urls.STATES_LIST_URL);

            return result;
        }
    }

    private class GetDiagList extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(SplashActivity.this,ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("s", s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");


                if(response==200)
                {
                    JSONArray jsonArray=jsonObject.getJSONArray("diagnosis");

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        JSONObject jsonObject2=jsonObject1.getJSONObject("Diagnoses");
                        diagnosisList.add(jsonObject2.getString("diagnosis_name"));
                        diagnosis_Id_List.add(jsonObject2.getString("id"));

                    }

                    new ProcedureTask().execute();

                }
                else {
                    Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            //return makeNetwowkCall(params[0], Urls.SEARCH_DIAGNOSIS_URL);

            //// TODO: 13-01-2016 changes.
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("api_key", "bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair,Urls.SEARCH_DIAGNOSIS_URL);
    }
    }
    private class ProcedureTask extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                if(response==200)
                {

                    JSONArray jsonArray=jsonObject.getJSONArray("procedures");

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        JSONObject jsonObject2=jsonObject1.getJSONObject("Procedures");
                        procedureList.add(jsonObject2.getString("procedure_name"));
                        procedure_Id_List.add(jsonObject2.getString("id"));
                    }
                    new PhysicianTask().execute();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Void... params) {
            /*String parameters="api_key=bf45c093e542f057c123ae7d6";
            String result= makeNetwowkCall(parameters,Urls.SEARCH_PROCEDURE_URL);*/
            //// TODO: 13-01-2016 changes.
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("api_key", "bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair,Urls.SEARCH_PROCEDURE_URL);

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
            //postData = urlParameters.getBytes( StandardCharsets.US_ASCII );
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

    private class PhysicianTask extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
                    JSONArray jsonArray=jsonObject.getJSONArray("physicians");

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        JSONObject jsonObject2=jsonObject1.getJSONObject("Physicians");
                        physicianList.add(jsonObject2.getString("physician_lname"));
                        physician_Id_List.add(jsonObject2.getString("id"));
                    }

                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putStringArrayList("states_list",states_list);
                    bundle.putStringArrayList("city_list",city_list);
                    bundle.putStringArrayList("diagnosis_list",diagnosisList);
                    bundle.putStringArrayList("procedure_list", procedureList);
                    bundle.putStringArrayList("physician_list", physicianList);
                    bundle.putStringArrayList("diagnosis_id", diagnosis_Id_List);
                    bundle.putStringArrayList("procedure_id", procedure_Id_List);
                    bundle.putStringArrayList("physician_id",physician_Id_List);

                    intent.putExtras(bundle);

                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Void... params) {
            /*String parameters="api_key=bf45c093e542f057c123ae7d6";
            String result= makeNetwowkCall(parameters,Urls.SEARCH_PHYSICIAN_URL);*/
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("api_key", "bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair,Urls.SEARCH_PHYSICIAN_URL);

        }
    }
}
