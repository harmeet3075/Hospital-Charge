package com.example.user.hospitalcharge.Adapters;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Holders.SearchHolder;
import com.example.user.hospitalcharge.Main.AdditionalServicies;
import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
import com.example.user.hospitalcharge.Model.Diagnosis.DaignosisData;
import com.example.user.hospitalcharge.Model.Diagnosis.Diagnoseslist;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.AddServiciesData;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoseshospitalslist;
import com.example.user.hospitalcharge.NavigationFragments.SearchFragment;
import com.example.user.hospitalcharge.R;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by user on 28-12-2015.
 */
public class SearchAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Diagnoseslist> diagnoseslist=null;
    private ArrayList<DaignosisData> daignosisDataArrayList=null;
    private ArrayList<AddServiciesData> addServiciesDatas=new ArrayList<AddServiciesData>();
    private ObjectMapper objectMapper = null;
    private JsonFactory jsonFactory = null;
    private JsonParser jp = null;
    public SearchAdapter(Context context,/* ArrayList<Diagnoseslist> diagnoseslist,*/ArrayList<DaignosisData> daignosisDataArrayList)
    {
        this.context=context;
        this.diagnoseslist=diagnoseslist;
        this.daignosisDataArrayList=daignosisDataArrayList;
//        Log.d("size",String.valueOf(diagnoseslist.size()));
        objectMapper = new ObjectMapper();
        jsonFactory = new JsonFactory();
    }
    @Override
    public int getCount() {
        return daignosisDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return daignosisDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            SearchHolder holder=null;

            if(convertView==null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.layout_search_listview, null);
                holder=new SearchHolder();

                holder.nameText=(TextView)convertView.findViewById(R.id.textview1);
                holder.locText=(TextView)convertView.findViewById(R.id.textview2);
                holder.phoneText=(TextView)convertView.findViewById(R.id.textview3);
                holder.loadMoreButton=(Button)convertView.findViewById(R.id.add_servicies);
                holder.nameText.setText(daignosisDataArrayList.get(position).getNameText());
                holder.locText.setText(daignosisDataArrayList.get(position).getLocText());
                holder.phoneText.setText(daignosisDataArrayList.get(position).getPhoneText());


                View includeView = convertView.findViewById(R.id.include);
                holder.discharges=(TextView)includeView.findViewById(R.id.relative_textview1);
                holder.avgChargesText=(TextView)includeView.findViewById(R.id.avgCharges);
                holder.avgTotalText=(TextView)includeView.findViewById(R.id.relative_textview2);
                holder.avgMedicareText=(TextView)includeView.findViewById(R.id.amp);

                holder.discharges.setText(daignosisDataArrayList.get(position).getDischarges());
                holder.avgChargesText.setText("$ "+daignosisDataArrayList.get(position).getAvgChargesText());
                holder.avgTotalText.setText("$ "+daignosisDataArrayList.get(position).getAvgTotalText());
                holder.avgMedicareText.setText("$ " + daignosisDataArrayList.get(position).getAvgMedicareText());

                /*if (position % 2 == 1) {
                    convertView.setBackgroundColor(Color.LTGRAY);
                    includeView.setBackgroundColor(Color.WHITE);
                } else {
                    convertView.setBackgroundColor(Color.WHITE);
                    includeView.setBackgroundColor(Color.LTGRAY);
                }*/
                convertView.setTag(holder);
            }
        else
            {
                holder=(SearchHolder)convertView.getTag();
                holder.nameText.setText(daignosisDataArrayList.get(position).getNameText());
                holder.locText.setText(daignosisDataArrayList.get(position).getLocText());
                holder.phoneText.setText(daignosisDataArrayList.get(position).getPhoneText());
                holder.discharges.setText(daignosisDataArrayList.get(position).getDischarges());
                holder.avgChargesText.setText("$ "+daignosisDataArrayList.get(position).getAvgChargesText());
                holder.avgTotalText.setText("$ "+daignosisDataArrayList.get(position).getAvgTotalText());
                holder.avgMedicareText.setText("$ "+daignosisDataArrayList.get(position).getAvgMedicareText());
            }
        holder.loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] params={daignosisDataArrayList.get(position).getHospital_id()};
                if(Connectivity.checkInternet(context))
                {
                    new AdditionalServiciesTask().execute(params);
                }else
                {
                    Toast.makeText(context,"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(context, daignosisDataArrayList.get(position).getHospital_id(), Toast.LENGTH_SHORT).show();

            }
        });
        holder.loadMoreButton.setOnTouchListener(new View.OnTouchListener() {
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
                    case MotionEvent.ACTION_CANCEL:
                    {
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return false;
            }
        });

        return convertView;
    }

    private ProgressDialog progressDialog;
    private class AdditionalServiciesTask extends AsyncTask<String,Void,String>
    {
        String hospital_id;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            try {
                jp = jsonFactory.createJsonParser(aVoid);
                com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.MainWrapper mainWrapper=objectMapper.readValue(jp, com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.MainWrapper.class);
                int  response=mainWrapper.getResponse();
                String hospitalName=mainWrapper.getHospital().getHospitals().getHospital_name();
                String hospitalAddress=mainWrapper.getHospital().getHospitals().getStreet_address1()+","
                        +mainWrapper.getHospital().getHospitals().getCity()+","+
                        mainWrapper.getState().getStates().getName();
                String hospitalPhone=mainWrapper.getHospital().getHospitals().getPhone();
                String hospitalZipCode=mainWrapper.getHospital().getHospitals().getZip_code();
                String hospitalEmail=mainWrapper.getHospital().getHospitals().getEmail();
                double lat=mainWrapper.getLat();
                double lng=mainWrapper.getLang();
                ArrayList<Diagnoseshospitalslist> diagnoseshospitalslists=mainWrapper.getDiagnoseshospitalslist();
                Bundle bundle=new Bundle();
                bundle.putString("hospitalName",hospitalName);
                bundle.putString("hospitalAddress",hospitalAddress);
                bundle.putString("hospitalPhone",hospitalPhone);
                bundle.putString("hospitalZipCode",hospitalZipCode);
                bundle.putString("hospitalEmail",hospitalEmail);
                bundle.putDouble("lat", lat);
                bundle.putDouble("lng", lng);
                bundle.putString("hospital_id",hospital_id);
                addServiciesDatas.clear();
                for (int i=0;i<diagnoseshospitalslists.size();i++)
                {
                    try {
                        AddServiciesData data=new AddServiciesData();
                        data.setDiagnosisName(diagnoseshospitalslists.get(i).getDiagnoses().getDiagnosis_name());
                        data.setDischarges(diagnoseshospitalslists.get(i).getDiagnosesHospitals().getDischarges());
                        data.setAvgChargesText(diagnoseshospitalslists.get(i).getDiagnosesHospitals().getCharge_amt());
                        data.setAvgTotalText(diagnoseshospitalslists.get(i).getDiagnosesHospitals().getTotal_amt());
                        data.setAvgMedicareText(diagnoseshospitalslists.get(i).getDiagnosesHospitals().getMedicare_amt());
                        addServiciesDatas.add(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                bundle.putParcelableArrayList("list",addServiciesDatas);
                Intent intent=new Intent(context, AdditionalServicies.class);

                intent.putExtras(bundle);
                context.startActivity(intent);
                /*physicianlist_search=mainWrapper.getPhysiciansprocedureslist();
                String name=mainWrapper.getProviderCategory().getCategories().getCategory_name();
                providerCategory=mainWrapper.getProviderCategory();
                state=mainWrapper.getState();
                PhysicianSearchFragment nextFrag = new PhysicianSearchFragment();
                Bundle bundle=new Bundle();
                bundle.putString("physicianHeading",autoCompleteTextView.getText().toString());
                bundle.putParcelableArrayList("list", physicianlist_search);
                bundle.putParcelable("providerCategory", providerCategory);
                bundle.putParcelable("state",state);
                nextFrag.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag)
                        .addToBackStack("nextFrag")
                        .commit();*/
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            String parameters=params[0];
            hospital_id=parameters;

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("hospitalid",params[0]));
            return NetworkCall.makePostRequest(nameValuePair, "http://hospitalcharge.com/api/detail/hospitals/" + params[0]);
           /* String result= makeNetwowkCall(parameters,"http://hospitalcharge.com/api/detail/hospitals/"+params[0]);
            return result;*/
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
}
