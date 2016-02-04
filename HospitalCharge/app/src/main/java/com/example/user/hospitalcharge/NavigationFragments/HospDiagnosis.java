package com.example.user.hospitalcharge.NavigationFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.HospitalAccount.DiagnosisAdapter;
import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
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
import java.util.Iterator;

/**
 * Created by user on 21-12-2015.
 */
public class HospDiagnosis extends Fragment {

    private ListView listView;
    private ProgressDialog progressDialog;
    private String ref_id,group_id;
    private ArrayList<String> diagnosisNameList,dischargesList,averageList,
            totalPaymentList,medicalPaymentList,diagnosisId
            ,diagnosisList;
    private DiagnosisAdapter diagnosisAdapter;
    private Button addButton;
    ArrayList<String> keys;


    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_hosp_diagnosis_list,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setVisibility(false);
        navigationActivity.setHeading("DIAGNOSIS");
        navigationActivity.showDrawer(false);
        navigationActivity.setBackVisibility(true);
        listView=(ListView)view.findViewById(R.id.hosp_diag_lisview);
        addButton=(Button)view.findViewById(R.id.add);
        addButton.setOnTouchListener(new View.OnTouchListener() {
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
        /*if(Connectivity.checkInternet(getActivity()))
        {
            getDiagnosisList();
        }
        else
            Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();*/

        this.registerForContextMenu(listView);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parameters = {"bf45c093e542f057c123ae7d6"};
                if(Connectivity.checkInternet(getActivity()))
                {
                    new GetDiagList().execute(parameters);
                }else
                {
                    Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void onResume()
    {
        super.onResume();
        if(Connectivity.checkInternet(getActivity()))
        {
            getDiagnosisList();
        }
        else
            Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
    }

    private void getDiagnosisList()
    {
        String parameters[]={ref_id,"bf45c093e542f057c123ae7d6"};
        new DiagnosisTask().execute(parameters);
    }


    private class DiagnosisTask extends AsyncTask<String,Void,String>
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
            Log.d("s", s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                diagnosisNameList=new ArrayList<String>();
                dischargesList=new ArrayList<String>();
                averageList=new ArrayList<String>();
                totalPaymentList=new ArrayList<String>();
                medicalPaymentList=new ArrayList<String>();
                diagnosisId=new ArrayList<String>();
                if(response==200)
                {
                    JSONArray jsonArray=jsonObject.getJSONArray("diagnoses");
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(getActivity(),"Data not Available",Toast.LENGTH_SHORT).show();
                    }
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        JSONObject jsonObject2=jsonObject1.getJSONObject("DiagnosesHospitals");
                        diagnosisId.add(jsonObject2.getString("id"));
                        dischargesList.add(jsonObject2.getString("discharges"));
                        averageList.add(jsonObject2.getString("charge_amt"));
                        totalPaymentList.add(jsonObject2.getString("total_amt"));
                        medicalPaymentList.add(jsonObject2.getString("medicare_amt"));
                        JSONObject jsonObject3=jsonObject1.getJSONObject("Diagnoses");
                        diagnosisNameList.add(jsonObject3.getString("diagnosis_name"));
                    }
                    diagnosisAdapter=new DiagnosisAdapter(getActivity(),diagnosisNameList,dischargesList
                            ,averageList,totalPaymentList,medicalPaymentList,diagnosisId,ref_id);
                    listView.setAdapter(diagnosisAdapter);
                }
                else {
                    Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            //return makeNetwowkCall(params[0], Urls.HOSPITAL_DIAGNOSIS_LIST_URL);
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Users][ref_id]",params[0]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[1]));

            return NetworkCall.makePostRequest(nameValuePair, Urls.HOSPITAL_DIAGNOSIS_LIST_URL);
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Edit");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Edit"){

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            //String parameters="data[Diagnose][id]="+diagnosisId.get(info.position);
           // String[] params={parameters,String.valueOf(info.position)};
            String[] params1={diagnosisId.get(info.position),String.valueOf(info.position)};
            if(Connectivity.checkInternet(getActivity()))
            {
                new EditTask().execute(params1);
            }else
            {
                Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
            }


            //Toast.makeText(getApplicationContext(),String.valueOf(info.position),Toast.LENGTH_SHORT).show();

        }
        else if(item.getTitle()=="Delete"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            String parameters="data[Diagnose][id]="+diagnosisId.get(info.position);
            String[] params={parameters,String.valueOf(info.position)};
            String[] params1={diagnosisId.get(info.position),String.valueOf(info.position)};
            if(Connectivity.checkInternet(getActivity()))
            {
                new DeleteTask().execute(params1);
            }else
            {
                Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
            }

        }else{
            return false;
        }
        return true;
    }

    public void edit(final int id,final String name,String code,String discharges,
                     String amount,String totalPayment,String medicalPayment)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_hosp_diag_list_edit, null);
        TextView textView1=(TextView)view.findViewById(R.id.textview2);
        textView1.setText(name);
        TextView textView2=(TextView)view.findViewById(R.id.textview4);
        textView2.setText(code);
        final EditText editText1=(EditText)view.findViewById(R.id.editext1);
        final EditText editText2=(EditText)view.findViewById(R.id.editext2);
        final EditText editText3=(EditText)view.findViewById(R.id.editext3);
        final EditText editText4=(EditText)view.findViewById(R.id.editext4);
        editText1.setText(discharges);
        editText2.setText(amount);
        editText3.setText(totalPayment);
        editText4.setText(medicalPayment);
        builder.setView(view);
        builder.setCancelable(true);


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save(id,ref_id,diagnosisId.get(id),editText1.getText().toString(),editText2.getText().toString(),
                        editText3.getText().toString(),editText4.getText().toString(),name);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
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
        if(Connectivity.checkInternet(getActivity()))
        {
            new SaveTask().execute(params1);
        }else
        {
            Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
        }

    }

    private class SaveTask extends AsyncTask<String,Void,String>
    {
        private int position=0;
        private String name,amount,totalpayment,medicalPayment,ID,discharges,diagnosisName;
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
                    diagnosisNameList.remove(position);
                    dischargesList.remove(position);
                    averageList.remove(position);
                    totalPaymentList.remove(position);
                    medicalPaymentList.remove(position);
                    diagnosisId.remove(position);


                    diagnosisNameList.add(position, diagnosisName);
                    diagnosisId.add(position,name);
                    dischargesList.add(position,discharges);
                    averageList.add(position,amount);
                    totalPaymentList.add(position,totalpayment);
                    medicalPaymentList.add(position, medicalPayment);
                    diagnosisAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
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

    private class EditTask extends AsyncTask<String,Void,String>
    {
        private int _id=0;
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
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
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

    private class DeleteTask extends AsyncTask<String,Void,String>
    {
        private int position=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            position=Integer.parseInt(params[1]);
            //return makeNetwowkCall(params[0], Urls.HOSPITAL_DIAGNOSIS_DELETE);

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Diagnose][id]",params[0]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.HOSPITAL_DIAGNOSIS_DELETE);
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
                    diagnosisNameList.remove(position);
                    dischargesList.remove(position);
                    averageList.remove(position);
                    totalPaymentList.remove(position);
                    medicalPaymentList.remove(position);
                    diagnosisId.remove(position);
                    diagnosisAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void showAddAlert(ArrayList<String> diagnosisList)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_hosp_diag_save, null);
        TextView textView1=(TextView)view.findViewById(R.id.textview2);
        final Spinner spinner=(Spinner)view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,diagnosisList);
        spinner.setAdapter(adapter);
        //textView1.setText(name);
        final EditText editText1=(EditText)view.findViewById(R.id.editext1);
        final EditText editText2=(EditText)view.findViewById(R.id.editext2);
        final EditText editText3=(EditText)view.findViewById(R.id.editext3);
        final EditText editText4=(EditText)view.findViewById(R.id.editext4);
        builder.setView(view);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addList(ref_id,String.valueOf(spinner.getSelectedItemPosition()),editText1.getText().toString()
                        ,editText2.getText().toString(),editText3.getText().toString(),editText4.getText().toString());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void addList(String ref_id,String name,String discharges,String amount,String totalPayment,String medicalPayment
    )
    {
        String parameters="data[Users][ref_id]="+ref_id+
                "&data[DiagnosesHospitals][diagnosis_id]="+ keys.get(Integer.parseInt(name))+
                "&data[DiagnosesHospitals][discharges]="+discharges+
                "&data[DiagnosesHospitals][charge_amt]="+amount+
                "&data[DiagnosesHospitals][total_amt]="+totalPayment+
                "&data[DiagnosesHospitals][medicare_amt]="+medicalPayment+
                "&api_key=bf45c093e542f057c123ae7d6";
        String[] params={parameters,amount,totalPayment,medicalPayment,name};

        String[] params1={ref_id,keys.get(Integer.parseInt(name)),
                discharges,amount,totalPayment,medicalPayment,"bf45c093e542f057c123ae7d6",
                amount,totalPayment,medicalPayment,name};
        new AddTask().execute(params1);
    }


    private class AddTask extends AsyncTask<String,Void,String>
    {
        private String name,amount,totlPayment,medicalPayment;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            name=params[10];
            amount=params[7];
            totlPayment=params[8];
            medicalPayment=params[9];
            //return makeNetwowkCall(params[0], Urls.HOSPITAL_DIAGNOSIS_SAVE);

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Users][ref_id]",params[0]));
            nameValuePair.add(new BasicNameValuePair("data[DiagnosesHospitals][diagnosis_id]",params[1]));
            nameValuePair.add(new BasicNameValuePair("data[DiagnosesHospitals][discharges]",params[2]));
            nameValuePair.add(new BasicNameValuePair("data[DiagnosesHospitals][charge_amt]",params[3]));
            nameValuePair.add(new BasicNameValuePair("data[DiagnosesHospitals][total_amt]",params[4]));
            nameValuePair.add(new BasicNameValuePair("data[DiagnosesHospitals][medicare_amt]",params[5]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[6]));

            return NetworkCall.makePostRequest(nameValuePair, Urls.HOSPITAL_DIAGNOSIS_SAVE);
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
                    getDiagnosisList();
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class GetDiagList extends AsyncTask<String,Void,String>
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
            Log.d("s", s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                diagnosisList=new ArrayList<String>();

                if(response==200)
                {

                    JSONObject jsonObject1=jsonObject.getJSONObject("diagnoses");
                    diagnosisList=new ArrayList<String>();
                    //Toast.makeText(getApplicationContext(),"Success "+String.valueOf(jsonObject1.length()),Toast.LENGTH_SHORT).show();

                    Iterator<String> iterator=jsonObject1.keys();
                    keys=new ArrayList<String>();
                    while(iterator.hasNext())
                    {
                        keys.add(iterator.next());
                        //Toast.makeText(getApplicationContext(),iterator.next(),Toast.LENGTH_SHORT).show();
                    }
                    for(int i=0;i<jsonObject1.length();i++)
                    {
                        diagnosisList.add(jsonObject1.getString(keys.get(i)));
                    }
                    showAddAlert(diagnosisList);
                }
                else {
                    Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("itemid",params[0]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.HOSPITAL_DIAGNOSIS_URL);
            //return makeNetwowkCall(params[0], Urls.HOSPITAL_DIAGNOSIS_URL);
        }
    }
}
