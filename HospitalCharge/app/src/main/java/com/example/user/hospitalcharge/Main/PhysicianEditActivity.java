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

import com.example.user.hospitalcharge.NavigationFragments.PhyDiagnosis;
import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 20-01-2016.
 */
public class PhysicianEditActivity extends Activity {


    private ImageView backButton;
    public String position,ref_id,procedure_id;
    private ProgressDialog progressDialog;
    private TextView textView1,textView2;
    private EditText editText1,editText2,editText3,editText4;
    private Button saveButton;
    private int id;
    String procedure_name;
    String ID,procedureID,physicianId,code;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_edit_phy_activity);
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
                save(id,ref_id,procedure_name,editText2.getText().toString(),
                        editText3.getText().toString(),editText4.getText().toString()
                        ,ID,procedureID,physicianId,code);
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
        procedure_id=bundle1.getString("procedure_id");
        if(Connectivity.checkInternet(this))
        {
            String[] params={procedure_id,position};
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
            progressDialog=new ProgressDialog(PhysicianEditActivity.this,ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(false);
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
                    JSONObject jsonObject2=jsonObject1.getJSONObject("PhysiciansProcedures");
                    String id=jsonObject2.getString("id");
                    String procedure_id=jsonObject2.getString("procedure_id");
                    String physician_id=jsonObject2.getString("physician_id");
                    String amount=jsonObject2.getString("charge_amt");
                    String total_amount=jsonObject2.getString("total_amt");
                    String medical_amount=jsonObject2.getString("medicare_amt");
                    //String discharges=jsonObject2.getString("discharges");
                    JSONObject jsonObject3=jsonObject1.getJSONObject("Procedures");
                    String procedure_code=jsonObject3.getString("procedure_code");
                    String procedure_name=jsonObject3.getString("procedure_name");

                    edit(_id, procedure_name, procedure_code, amount, total_amount, medical_amount, procedure_id, physician_id, id);
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
            _id=Integer.parseInt(params[1]);
            //return makeNetwowkCall(params[0], Urls.PHYSICIAN_PROCEDURE_EDIT);
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Procedure][id]",params[0]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.PHYSICIAN_PROCEDURE_EDIT);
        }
    }


    public void edit(final int id,final String name, final String code,
                     String amount,String totalPayment,String medicalPayment,final String procedureID,
                     final String physicianId, final String ID)
    {
        textView1.setText(name);
        textView2.setText(code);
        editText2.setText(amount);
        editText3.setText(totalPayment);
        editText4.setText(medicalPayment);
        this.id=id;
        this.procedure_name=name;
        this.procedure_id=procedureID;
        this.code=code;
        this.physicianId=physicianId;
        this.ID=ID;

    }


    public void save(int id,String ref_id,String name,String amount,String totalPayment,String medicalPayment
            ,String ID,String procedureId,String physicianId,String code)
    {
        String[] params1={ID,amount,totalPayment,medicalPayment,
                "bf45c093e542f057c123ae7d6",name,amount,totalPayment,medicalPayment,
                String.valueOf(id),ID,procedureId,physicianId,code};
        if (Connectivity.checkInternet(PhysicianEditActivity.this))
        {
            new SaveTask().execute(params1);
        }else
        {
            Toast.makeText(PhysicianEditActivity.this,"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
        }
    }

    private class SaveTask extends AsyncTask<String,Void,String>
    {
        private int position=0;
        private String name,amount,totalpayment,medicalPayment,ID,procedureId,physicianId,code;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(PhysicianEditActivity.this,ProgressDialog.STYLE_SPINNER);
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
                    Toast.makeText(PhysicianEditActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PhysicianEditActivity.this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            name=params[5];
            amount=params[6];
            totalpayment=params[7];
            medicalPayment=params[8];
            position=Integer.parseInt(params[9]);
            code=params[13];
            physicianId=params[12];
            ID=params[10];
            //return makeNetwowkCall(params[0], Urls.PHYSICIAN_PROCEDURE_UPDATE);

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Procedure][id]",params[0]));
            nameValuePair.add(new BasicNameValuePair("data[PhysiciansProcedures][charge_amt]",params[1]));
            nameValuePair.add(new BasicNameValuePair("data[PhysiciansProcedures][total_amt]",params[2]));
            nameValuePair.add(new BasicNameValuePair("data[PhysiciansProcedures][medicare_amt]",params[3]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[4]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.PHYSICIAN_PROCEDURE_UPDATE);
        }
    }



}
