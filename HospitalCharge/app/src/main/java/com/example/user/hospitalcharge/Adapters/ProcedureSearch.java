package com.example.user.hospitalcharge.Adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Holders.ProcedureSearchHolder;
import com.example.user.hospitalcharge.Main.AdditionalServicies;
import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
import com.example.user.hospitalcharge.Main.ProcedureAdditional;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.AddServiciesData;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoseshospitalslist;
import com.example.user.hospitalcharge.Model.Physician.AdditionalData;
import com.example.user.hospitalcharge.Model.Physician.ProviderCategory;
import com.example.user.hospitalcharge.Model.Physician.State;
import com.example.user.hospitalcharge.Model.Procedure.ProcedureData;
import com.example.user.hospitalcharge.NavigationFragments.PhysicianSearchFragment;
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
 * Created by user on 05-01-2016.
 */
public class ProcedureSearch extends BaseAdapter {


    private Context context;
    private ArrayList<ProcedureData> procedureDataArrayList=new ArrayList<ProcedureData>();
    private ObjectMapper objectMapper = null;
    private JsonFactory jsonFactory = null;
    private JsonParser jp = null;
    private ArrayList<com.example.user.hospitalcharge.Model.Physician.Physiciansprocedureslist> physicianlist_search=null;
    private ProviderCategory providerCategory;
    private State state;
    private ArrayList<AdditionalData> additionalDatas=new ArrayList<AdditionalData>();
    public ProcedureSearch(Context context,ArrayList<ProcedureData> procedureDataArrayList)
    {
        this.context=context;
        this.procedureDataArrayList=procedureDataArrayList;
        objectMapper = new ObjectMapper();
        jsonFactory = new JsonFactory();
    }
    @Override
    public int getCount() {
        return procedureDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return procedureDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ProcedureSearchHolder holder=null;

        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_search_listview2, null);
            holder=new ProcedureSearchHolder();

            holder.nameText=(TextView)convertView.findViewById(R.id.textview1);
            holder.locText=(TextView)convertView.findViewById(R.id.textview2);
            holder.phoneText=(TextView)convertView.findViewById(R.id.textview3);
            holder.loadMoreButton=(Button)convertView.findViewById(R.id.add_servicies);
            holder.nameText.setText(procedureDataArrayList.get(position).getNameText());
            holder.locText.setText(procedureDataArrayList.get(position).getLocText());
            holder.phoneText.setText(procedureDataArrayList.get(position).getPhoneText());


            View includeView = convertView.findViewById(R.id.include);
            holder.avgChargesText=(TextView)includeView.findViewById(R.id.textView2);
            holder.avgTotalText=(TextView)includeView.findViewById(R.id.relative_textview2);
            holder.avgMedicareText=(TextView)includeView.findViewById(R.id.textView3);


            holder.avgChargesText.setText("$ "+procedureDataArrayList.get(position).getAvgChargesText());
            holder.avgTotalText.setText("$ "+procedureDataArrayList.get(position).getAvgTotalText());
            holder.avgMedicareText.setText("$ "+procedureDataArrayList.get(position).getAvgMedicareText());
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
            holder=(ProcedureSearchHolder)convertView.getTag();
            holder.nameText.setText(procedureDataArrayList.get(position).getNameText());
            holder.locText.setText(procedureDataArrayList.get(position).getLocText());
            holder.phoneText.setText(procedureDataArrayList.get(position).getPhoneText());
            holder.avgChargesText.setText("$ "+procedureDataArrayList.get(position).getAvgChargesText());
            holder.avgTotalText.setText("$ "+procedureDataArrayList.get(position).getAvgTotalText());
            holder.avgMedicareText.setText("$ "+procedureDataArrayList.get(position).getAvgMedicareText());
        }

        holder.loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] params={procedureDataArrayList.get(position).getProcedure_id(),
                procedureDataArrayList.get(position).getNameText(),procedureDataArrayList.get(position).getFirstName()
                        ,procedureDataArrayList.get(position).getLastName()};

                if(Connectivity.checkInternet(context))
                {
                    new AdditionalServiciesTask().execute(params);
                }else
                {
                    Toast.makeText(context,"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(context, procedureDataArrayList.get(position).getNameText(), Toast.LENGTH_SHORT).show();

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
        private String name,firstName,lastName;
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
                com.example.user.hospitalcharge.Model.Physician.MainWrapper mainWrapper=objectMapper.readValue(jp, com.example.user.hospitalcharge.Model.Physician.MainWrapper.class);
                int  response=mainWrapper.getResponse();
                physicianlist_search=mainWrapper.getPhysiciansprocedureslist();
                String name1=mainWrapper.getProviderCategory().getCategories().getCategory_name();
                providerCategory=mainWrapper.getProviderCategory();
                state=mainWrapper.getState();
                String address=mainWrapper.getPhysician().getPhysicians().getStreet_address1()+","+
                        mainWrapper.getPhysician().getPhysicians().getStreet_address2()+","+mainWrapper.getPhysician()
                        .getPhysicians().getCity()+","+mainWrapper.getPhysician().getPhysicians().getState_code();
                String categoryName=mainWrapper.getProviderCategory().getCategories().getCategory_name();
                Bundle bundle=new Bundle();
                bundle.putString("physicianHeading", name);
                bundle.putString("providerCategory", categoryName);
                bundle.putString("state",address);
                bundle.putString("firstName",firstName);
                bundle.putString("lastName",lastName);
                bundle.putDouble("lat", mainWrapper.getLat());
                bundle.putDouble("lng", mainWrapper.getLang());
                bundle.putString("gender", mainWrapper.getPhysician().getPhysicians().getGender());
                bundle.putString("credentials", mainWrapper.getPhysician().getPhysicians().getCredentials());
                bundle.putString("zip", mainWrapper.getPhysician().getPhysicians().getZip_code());
                bundle.putString("email", mainWrapper.getPhysician().getPhysicians().getEmail());
                bundle.putString("phone", mainWrapper.getPhysician().getPhysicians().getPhone());
                bundle.putString("image", mainWrapper.getPhysician().getPhysicians().getPhysician_images());
                bundle.putString("lat_string",mainWrapper.getPhysician().getPhysicians().getLatitude());
                bundle.putString("lng_string",mainWrapper.getPhysician().getPhysicians().getLongitude());
                bundle.putString("id",mainWrapper.getPhysician().getPhysicians().getId());
                additionalDatas.clear();
                for (int i=0;i<physicianlist_search.size();i++)
                {
                    try {
                        AdditionalData data=new AdditionalData();
                        data.setProviderName(physicianlist_search.get(i).getProcedures().getProcedure_name());
                        data.setAvgChargesText(physicianlist_search.get(i).getPhysiciansProcedures().getCharge_amt());
                        data.setAvgTotalText(physicianlist_search.get(i).getPhysiciansProcedures().getTotal_amt());
                        data.setAvgMedicareText(physicianlist_search.get(i).getPhysiciansProcedures().getMedicare_amt());
                        additionalDatas.add(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                bundle.putParcelableArrayList("list", additionalDatas);
                Intent intent=new Intent(context, ProcedureAdditional.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            String parameters=params[0];
            name=params[1];
            firstName=params[2];
            lastName=params[3];
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("procedure_id", params[0]));
            return NetworkCall.makePostRequest(nameValuePair, "http://hospitalcharge.com/api/detail/physicians/"+params[0]);

            /*String result= makeNetwowkCall(parameters,"http://hospitalcharge.com/api/detail/physicians/"+params[0]);
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
}
