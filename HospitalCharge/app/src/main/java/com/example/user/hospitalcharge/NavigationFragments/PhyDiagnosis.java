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

import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
import com.example.user.hospitalcharge.PhysicianAccount.DiagnosisAdapter;
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
public class PhyDiagnosis extends Fragment {


    private ListView listView;
    private ProgressDialog progressDialog;
    private String ref_id,group_id;
    private ArrayList<String> procedureNameList,
            physicianID,averageList,
            procedureCode, totalPaymentList,
            medicalPaymentList,procedure_ID
            ,procedureList;
    private DiagnosisAdapter diagnosisAdapter;
    private Button addButton;
    private int counter=0;
    ArrayList<String> keys;


    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_phy_diagnosis,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setVisibility(false);
        navigationActivity.setHeading("DIAGNOSIS");
        navigationActivity.showDrawer(false);
        navigationActivity.setBackVisibility(true);
        listView=(ListView)view.findViewById(R.id.phy_diag_lisview);
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
        this.registerForContextMenu(listView);
        //Toast.makeText(getActivity(),"onCreateView()",Toast.LENGTH_SHORT).show();

        /*if(Connectivity.checkInternet(getActivity()))
        {
            getProcedureList();
        }
        else
            Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();*/

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String parameters = "bf45c093e542f057c123ae7d6";
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
        //Toast.makeText(getActivity(),"onResume()",Toast.LENGTH_SHORT).show();
        if(Connectivity.checkInternet(getActivity()))
        {
            getProcedureList();
        }
        else
            Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
    }

    public void onPause()
    {
        super.onPause();
        //Toast.makeText(getActivity(),"onPause()",Toast.LENGTH_SHORT).show();
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
                procedureList=new ArrayList<String>();

                if(response==200)
                {

                    JSONObject jsonObject1=jsonObject.getJSONObject("procedures");
                    procedureList=new ArrayList<String>();
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
                        procedureList.add(jsonObject1.getString(keys.get(i)));
                    }
                    showAddAlert(procedureList);
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
            nameValuePair.add(new BasicNameValuePair("api_key", params[0]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.PHYSICIAN_PROCEDURE_TYPES);
            //return makeNetwowkCall(params[0], Urls.PHYSICIAN_PROCEDURE_TYPES);
        }
    }

    public void showAddAlert(ArrayList<String> procedureList)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_phy_diag_save, null);
        TextView textView1=(TextView)view.findViewById(R.id.textview2);
        final Spinner spinner=(Spinner)view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,procedureList);
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
                addList(ref_id,String.valueOf(spinner.getSelectedItemPosition()),
                        editText2.getText().toString(),editText3.getText().toString(),
                        editText4.getText().toString(),spinner.getSelectedItem().toString()
                );
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void addList(String ref_id,String name,String amount,String totalPayment,String medicalPayment,String diagName)
    {
        String parameters="data[Users][ref_id]="+ref_id+
                "&data[PhysiciansProcedures][procedure_id]="+ keys.get(Integer.parseInt(name))+
                "&data[PhysiciansProcedures][charge_amt]="+amount+
                "&data[PhysiciansProcedures][total_amt]="+totalPayment+
                "&data[PhysiciansProcedures][medicare_amt]="+medicalPayment+
                "&api_key=bf45c093e542f057c123ae7d6";
        String[] params={parameters,diagName,amount,totalPayment,medicalPayment};
        String[] params1={ref_id,keys.get(Integer.parseInt(name)),amount,totalPayment,medicalPayment,
    "bf45c093e542f057c123ae7d6",diagName,amount,totalPayment,medicalPayment};
        if(Connectivity.checkInternet(getActivity()))
        {
            new AddTask().execute(params1);
        }else
        {
            Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
        }
    }

    private class AddTask extends AsyncTask<String,Void,String>
    {
        private String name,amount,totlPayment,medicalPayment,id01;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            name=params[6];
            amount=params[7];
            totlPayment=params[8];
            medicalPayment=params[9];
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Users][ref_id]",params[0]));
            nameValuePair.add(new BasicNameValuePair("data[PhysiciansProcedures][procedure_id]",params[1]));
            nameValuePair.add(new BasicNameValuePair("data[PhysiciansProcedures][charge_amt]",params[2]));
            nameValuePair.add(new BasicNameValuePair("data[PhysiciansProcedures][total_amt]",params[3]));
            nameValuePair.add(new BasicNameValuePair("data[PhysiciansProcedures][medicare_amt]",params[4]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[5]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.PHYSICIAN_PROCEDURE_SAVE);

            //return makeNetwowkCall(params[0], Urls.PHYSICIAN_PROCEDURE_SAVE);
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
                    getProcedureList();
                    /*procedureNameList.add(counter,name);
                    physicianID.add(counter,name);
                    averageList.add(counter,amount);
                    totalPaymentList.add(counter,totlPayment);
                    medicalPaymentList.add(counter,medicalPayment);
                    procedure_ID.add(counter,name);
                    procedureCode.add(counter,name);
                    diagnosisAdapter.notifyDataSetChanged();
                    counter++;*/
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
    private void getProcedureList()
    {
        String parameters[]={ref_id,"bf45c093e542f057c123ae7d6"};
        if(Connectivity.checkInternet(getActivity()))
        {
            new DiagnosisTask().execute(parameters);
        }else
        {
            Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
        }

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

                procedureNameList=new ArrayList<String>();
                physicianID=new ArrayList<String>();
                averageList=new ArrayList<String>();
                totalPaymentList=new ArrayList<String>();
                medicalPaymentList=new ArrayList<String>();
                procedure_ID=new ArrayList<String>();
                procedureCode=new ArrayList<String>();
                if(response==200)
                {
                    JSONArray jsonArray=jsonObject.getJSONArray("procedure");
                    counter=jsonArray.length();
                    if(counter==0)
                    {
                        Toast.makeText(getActivity(),"Data not Available",Toast.LENGTH_SHORT).show();
                    }
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        JSONObject jsonObject2=jsonObject1.getJSONObject("PhysiciansProcedures");
                        procedure_ID.add(jsonObject2.getString("id"));
                        physicianID.add(jsonObject2.getString("physician_id"));
                        averageList.add(jsonObject2.getString("charge_amt"));
                        totalPaymentList.add(jsonObject2.getString("total_amt"));
                        medicalPaymentList.add(jsonObject2.getString("medicare_amt"));
                        JSONObject jsonObject3=jsonObject1.getJSONObject("Procedures");
                        procedureNameList.add(jsonObject3.getString("procedure_name"));
                        procedureCode.add(jsonObject3.getString("procedure_code"));
                    }
                    diagnosisAdapter=new DiagnosisAdapter(getActivity(),procedureNameList,procedure_ID
                            ,averageList,totalPaymentList,medicalPaymentList,physicianID,procedureCode,ref_id);
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

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Users][ref_id]",params[0]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[1]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.PHYSICIAN_PROCEDURE_LIST);
            // return makeNetwowkCall(params[0], Urls.PHYSICIAN_PROCEDURE_LIST);
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
            String parameters=procedure_ID.get(info.position);
            String[] params={parameters,String.valueOf(info.position)};
            if(Connectivity.checkInternet(getActivity()))
            {
                new EditTask().execute(params);
            }else
            {
                Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
            }

        }
        else if(item.getTitle()=="Delete"){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            String parameters=procedure_ID.get(info.position);
            String[] params={parameters,String.valueOf(info.position)};
            if(Connectivity.checkInternet(getActivity()))
            {
                new DeleteTask().execute(params);
            }else
            {
                Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
            }

        }else{
            return false;
        }
        return true;
    }
    public void edit(final int id,final String name, final String code,
                     String amount,String totalPayment,String medicalPayment,final String procedureID,
                     final String physicianId, final String ID)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_phy_diag_list_update, null);
        TextView textView1=(TextView)view.findViewById(R.id.textview2);
        textView1.setText(name);
        TextView textView2=(TextView)view.findViewById(R.id.textview4);
        textView2.setText(code);
        final EditText editText1=(EditText)view.findViewById(R.id.editext1);
        final EditText editText2=(EditText)view.findViewById(R.id.editext2);
        final EditText editText3=(EditText)view.findViewById(R.id.editext3);
        final EditText editText4=(EditText)view.findViewById(R.id.editext4);
        //editText1.setText(discharges);
        editText2.setText(amount);
        editText3.setText(totalPayment);
        editText4.setText(medicalPayment);
        builder.setView(view);
        builder.setCancelable(true);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                save(id,ref_id,name,editText2.getText().toString(),
                        editText3.getText().toString(),editText4.getText().toString()
                        ,ID,procedureID,physicianId,code);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void save(int id,String ref_id,String name,String amount,String totalPayment,String medicalPayment
            ,String ID,String procedureId,String physicianId,String code)
    {
        String parameters=
                "data[Procedure][id]="+ID+
                        "&data[PhysiciansProcedures][charge_amt]="+amount+
                        "&data[PhysiciansProcedures][total_amt]="+totalPayment+
                        "&data[PhysiciansProcedures][medicare_amt]="+medicalPayment+
                        "&api_key=bf45c093e542f057c123ae7d6";
        //String[] params={parameters,name,amount,totalPayment,medicalPayment,String.valueOf(id),ID,procedureId,physicianId,code};

        String[] params1={ID,amount,totalPayment,medicalPayment,
                "bf45c093e542f057c123ae7d6",name,amount,totalPayment,medicalPayment,
                String.valueOf(id),ID,procedureId,physicianId,code};
        if (Connectivity.checkInternet(getActivity()))
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
        private String name,amount,totalpayment,medicalPayment,ID,procedureId,physicianId,code;
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
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                if(response==200)
                {
                    procedureNameList.remove(position);
                    physicianID.remove(position);
                    averageList.remove(position);
                    totalPaymentList.remove(position);
                    medicalPaymentList.remove(position);
                    procedureCode.remove(position);
                    procedure_ID.remove(position);



                    procedureNameList.add(position, name);
                    physicianID.add(position, physicianId);
                    averageList.add(position,amount);
                    totalPaymentList.add(position,totalpayment);
                    medicalPaymentList.add(position, medicalPayment);
                    procedureCode.add(position,code);
                    procedure_ID.add(position,ID);
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


    private class DeleteTask extends AsyncTask<String,Void,String>
    {
        private int position=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
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
                    procedureNameList.remove(position);
                    physicianID.remove(position);
                    averageList.remove(position);
                    totalPaymentList.remove(position);
                    medicalPaymentList.remove(position);
                    procedureCode.remove(position);
                    procedure_ID.remove(position);
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


    private class EditTask extends AsyncTask<String,Void,String>
    {
        private int _id=0;
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
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
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
            return NetworkCall.makePostRequest(nameValuePair,Urls.PHYSICIAN_PROCEDURE_EDIT);
        }
    }
}
