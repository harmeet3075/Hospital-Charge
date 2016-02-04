package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
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

import java.util.ArrayList;

/**
 * Created by user on 27-01-2016.
 */
public class HospitalEditActivity extends Activity {


    public String position,ref_id,diagnosis_id;
    private ImageView backButton;
    private ProgressDialog progressDialog;
    private TextView textView1,textView2;
    private EditText editText1,editText2,editText3,editText4;
    private Button saveButton;
    private int id;
    String name;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_hosp_diag_list_edit);
        View view=findViewById(R.id.header);
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView1=(TextView)findViewById(R.id.textview2);
        textView2=(TextView)findViewById(R.id.textview4);
        editText1=(EditText)findViewById(R.id.editext1);
        editText2=(EditText)findViewById(R.id.editext2);
        editText3=(EditText)findViewById(R.id.editext3);
        editText4=(EditText)findViewById(R.id.editext4);
        saveButton=(Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(id,ref_id,diagnosis_id,editText1.getText().toString(),editText2.getText().toString(),
                        editText3.getText().toString(),editText4.getText().toString(),name);
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
        Bundle bundle1=getIntent().getExtras();
        position=bundle1.getString("position");
        ref_id=bundle1.getString("ref_id");
        diagnosis_id=bundle1.getString("diagnosis_id");
        if(Connectivity.checkInternet(this))
        {
            String[] params={diagnosis_id,position};
            new EditTask().execute(params);
        }else
        {
            Toast.makeText(this, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private class EditTask extends AsyncTask<String,Void,String>
    {
        private int _id=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(HospitalEditActivity.this,ProgressDialog.STYLE_SPINNER);
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
                    JSONObject jsonObject2=jsonObject1.getJSONObject("DiagnosesHospitals");
                    String id=jsonObject2.getString("id");
                    String diagnosis_id=jsonObject2.getString("diagnosis_id");
                    String hospital_id=jsonObject2.getString("hospital_id");
                    String amount=jsonObject2.getString("charge_amt");
                    String total_amount=jsonObject2.getString("total_amt");
                    String medical_amount=jsonObject2.getString("medicare_amt");
                    String discharges=jsonObject2.getString("discharges");
                    JSONObject jsonObject3=jsonObject1.getJSONObject("Diagnoses");
                    String diagnosis_code=jsonObject3.getString("diagnosis_code");
                    String diagnosis_name=jsonObject3.getString("diagnosis_name");

                    edit(_id,diagnosis_name,diagnosis_code,discharges,amount,total_amount,medical_amount);
                }
                else {
                    Toast.makeText(HospitalEditActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            _id=Integer.parseInt(params[1]);
            //return makeNetwowkCall(params[0], Urls.HOSPITAL_DIAGNOSIS_show_edit);

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Diagnose][id]",params[0]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.HOSPITAL_DIAGNOSIS_show_edit);
        }
    }


    public void edit(final int id,final String name,String code,String discharges,
                     String amount,String totalPayment,String medicalPayment)
    {

        textView1.setText(name);
        textView2.setText(code);
        editText1.setText(discharges);
        editText2.setText(amount);
        editText3.setText(totalPayment);
        editText4.setText(medicalPayment);
        this.name=name;
        this.id=id;
    }


    public void save(int id,String ref_id,String name,String discharges,String amount,String totalPayment,
                     String medicalPayment,String diagnosisName)
    {
        String parameters=
                "data[Diagnose][id]="+name+
                        "&data[DiagnosesHospitals][discharges]="+discharges+
                        "&data[DiagnosesHospitals][charge_amt]="+amount+
                        "&data[DiagnosesHospitals][total_amt]="+totalPayment+
                        "&data[DiagnosesHospitals][medicare_amt]="+medicalPayment+
                        "&api_key=bf45c093e542f057c123ae7d6";
        String[] params={parameters,name,amount,totalPayment,medicalPayment,String.valueOf(id),discharges,diagnosisName};

        String[] params1={name,discharges,amount,totalPayment,medicalPayment,"bf45c093e542f057c123ae7d6",
                name,amount,totalPayment,medicalPayment,String.valueOf(id),discharges,diagnosisName};
        if(Connectivity.checkInternet(HospitalEditActivity.this))
        {
            new SaveTask().execute(params1);
        }else
        {
            Toast.makeText(HospitalEditActivity.this,"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
        }

    }

    private class SaveTask extends AsyncTask<String,Void,String>
    {
        private int position=0;
        private String name,amount,totalpayment,medicalPayment,ID,discharges,diagnosisName;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(HospitalEditActivity.this,ProgressDialog.STYLE_SPINNER);
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

                    Toast.makeText(HospitalEditActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(HospitalEditActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            name=params[6];
            amount=params[7];
            totalpayment=params[8];
            medicalPayment=params[9];
            position=Integer.parseInt(params[10]);
            discharges=params[11];
            diagnosisName=params[12];
            //return makeNetwowkCall(params[0], Urls.HOSPITAL_DIAGNOSIS_UPDATE);

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Diagnose][id]",params[0]));
            nameValuePair.add(new BasicNameValuePair("data[DiagnosesHospitals][discharges]",params[1]));
            nameValuePair.add(new BasicNameValuePair("data[DiagnosesHospitals][charge_amt]",params[2]));
            nameValuePair.add(new BasicNameValuePair("data[DiagnosesHospitals][total_amt]",params[3]));
            nameValuePair.add(new BasicNameValuePair("data[DiagnosesHospitals][medicare_amt]",params[4]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[5]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.HOSPITAL_DIAGNOSIS_UPDATE);
        }
    }
}
