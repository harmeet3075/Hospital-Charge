package com.example.user.hospitalcharge.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.user.hospitalcharge.Url.Urls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by user on 19-01-2016.
 */
public class BackgroundCalls {


    private ArrayList<String> states_list=new ArrayList<String>();
    private ArrayList<String> city_list=new ArrayList<String>();
    private ArrayList<String> diagnosisList=new ArrayList<String>();
    private ArrayList<String> diagnosis_Id_List=new ArrayList<String>();
    private ArrayList<String> procedureList=new ArrayList<String>();
    private ArrayList<String> physicianList=new ArrayList<String>();
    private ArrayList<String> procedure_Id_List=new ArrayList<String>();
    private ArrayList<String> physician_Id_List=new ArrayList<String>();
    private Context context;

    public BackgroundCalls(Context context)
    {

            this.context=context;
            states();
    }
    private void states() {

        new StatesTask().execute();
    }

    public ArrayList<String> getStates()
    {
        return states_list;
    }
    public ArrayList<String> getCity()
    {
        return city_list;
    }
    private class StatesTask extends AsyncTask<Void,Void,String>
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("states");
                    states_list=new ArrayList<String>();
                    Iterator<String> iterator=jsonObject1.keys();
                    ArrayList<String> keys=new ArrayList<String>();
                    states_list.add(0,"City");
                    city_list.add(0,"State");
                    while(iterator.hasNext())
                    {
                        keys.add(iterator.next());
                    }
                    for(int i=0;i<jsonObject1.length();i++)
                    {
                        states_list.add(jsonObject1.getString(keys.get(i)));
                        city_list.add(keys.get(i));
                    }



                }
                else {
                    Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
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
            nameValuePair.add(new BasicNameValuePair("api_key=", "bf45c093e542f057c123ae7d6"));
            String result=NetworkCall.makePostRequest(nameValuePair, Urls.STATES_LIST_URL);

            return result;
        }
    }
    public void diagList()
    {
        String parameters = "api_key=bf45c093e542f057c123ae7d6";
        new GetDiagList().execute(parameters);
    }
    public void procedureList()
    {
        new ProcedureTask().execute();
    }
    public void physicianList()
    {
        new PhysicianTask().execute();
    }
    private class GetDiagList extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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



                }
                else {
                    Toast.makeText(context,"error",Toast.LENGTH_SHORT).show();
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
            nameValuePair.add(new BasicNameValuePair("api_key=", "bf45c093e542f057c123ae7d6"));
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

                }
                else {
                    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
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
            nameValuePair.add(new BasicNameValuePair("api_key=", "bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair,Urls.SEARCH_PROCEDURE_URL);

        }
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

                }
                else {
                    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
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
            nameValuePair.add(new BasicNameValuePair("api_key=", "bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair,Urls.SEARCH_PHYSICIAN_URL);

        }
    }


}
