package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 27-01-2016.
 */
public class PhysicianDeleteActivity extends Activity {


    private Button deleteButton,cancelButton;
    private String ref_id,procedure_ID,position;
    private ProgressDialog progressDialog;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_physician_delete);
        Bundle bundle1=getIntent().getExtras();
        ref_id=bundle1.getString("ref_id");
        procedure_ID=bundle1.getString("procedure_id");
        position=bundle1.getString("position");
        deleteButton=(Button)findViewById(R.id.delete);
        cancelButton=(Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Connectivity.checkInternet(PhysicianDeleteActivity.this))
                    {
                        String parameters=procedure_ID
                                ;
                        String[] params={parameters,String.valueOf(position)};
                        new DeleteTask().execute(params);
                    }else
                    {
                        Toast.makeText(PhysicianDeleteActivity.this,"Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private class DeleteTask extends AsyncTask<String,Void,String>
    {
        private int position=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(PhysicianDeleteActivity.this,ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            position=Integer.parseInt(params[1]);
            //return makeNetwowkCall(params[0], Urls.PHYSICIAN_PROCEDURE_DELETE);

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Procedure][id]",params[0]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.PHYSICIAN_PROCEDURE_DELETE);
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
                   /* procedureNameList.remove(position);
                    physicianID.remove(position);
                    averageList.remove(position);
                    totalPaymentList.remove(position);
                    medicalPaymentList.remove(position);
                    procedureCode.remove(position);
                    procedure_ID.remove(position);
                    diagnosisAdapter.notifyDataSetChanged();*/

                    Toast.makeText(PhysicianDeleteActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(PhysicianDeleteActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
