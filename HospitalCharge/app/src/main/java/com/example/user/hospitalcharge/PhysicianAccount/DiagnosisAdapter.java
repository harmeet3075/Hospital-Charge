package com.example.user.hospitalcharge.PhysicianAccount;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
import com.example.user.hospitalcharge.Main.PhysicianDeleteActivity;
import com.example.user.hospitalcharge.Main.PhysicianEditActivity;
import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 12/8/2015.
 */
public class DiagnosisAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> procedureNameList=new ArrayList<String>()
            ,procedure_ID=new ArrayList<String>()
            ,averageList=new ArrayList<String>()
            ,totalPaymentList=new ArrayList<String>(),
            medicalPaymentList=new ArrayList<String>()
            ,physicianId=new ArrayList<String>(),
            procedureCode=new ArrayList<String>();
    private String ref_id;
    private ProgressDialog progressDialog;

    public DiagnosisAdapter(Context context,ArrayList<String> procedureNameList,
                            ArrayList<String>procedure_ID,ArrayList<String>averageList,
                            ArrayList<String>totalPaymentList,
                            ArrayList<String>medicalPaymentList,ArrayList<String> physicianId,ArrayList<String>procedureCode,String ref_id)
    {
        this.context=context;
        this.procedureNameList=procedureNameList;
        this.procedure_ID=procedure_ID;
        this.averageList=averageList;
        this.totalPaymentList=totalPaymentList;
        this.medicalPaymentList=medicalPaymentList;
        this.physicianId=physicianId;
        this.procedureCode=procedureCode;
        this.ref_id=ref_id;
    }
    @Override
    public int getCount() {
        return procedureNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return procedureNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        DiagnosisHolder holder=null;
        if(convertView==null)
        {
            holder=new DiagnosisHolder();
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.layout_phy_diag_custom_list,null);
            holder.diagnosisNameText=(TextView)convertView.findViewById(R.id.textview2);
            holder.procedure_ID_Text=(TextView)convertView.findViewById(R.id.textview4);
            holder.averageText=(TextView)convertView.findViewById(R.id.textview6);
            holder.totalPayText=(TextView)convertView.findViewById(R.id.textview8);
            holder.medicalText=(TextView)convertView.findViewById(R.id.textview10);
            holder.physicianIDText=(TextView)convertView.findViewById(R.id.textview12);
            holder.procedureCodeText=(TextView)convertView.findViewById(R.id.textview14);
            holder.editImageview=(ImageView)convertView.findViewById(R.id.imageView1);
            holder.delete_imageview=(ImageView)convertView.findViewById(R.id.imageview2);
            holder.procedure_ID_Text.setText(procedure_ID.get(position));
            holder.diagnosisNameText.setText(procedureNameList.get(position));
            holder.averageText.setText("$"+averageList.get(position));
            holder.totalPayText.setText("$"+totalPaymentList.get(position));
            holder.medicalText.setText("$"+medicalPaymentList.get(position));
            holder.physicianIDText.setText(physicianId.get(position));
            holder.procedureCodeText.setText(procedureCode.get(position));
            holder.editImageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent=new Intent(context,PhysicianEditActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("position", String.valueOf(position));
                    bundle.putString("ref_id",ref_id);
                    bundle.putString("procedure_id",procedure_ID.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            holder.delete_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, PhysicianDeleteActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("position", String.valueOf(position));
                    bundle.putString("ref_id",ref_id);
                    bundle.putString("procedure_id",procedure_ID.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    /*if(Connectivity.checkInternet(context))
                    {
                        String parameters=procedure_ID.get(position);
                        String[] params={parameters,String.valueOf(position)};
                        new DeleteTask().execute(params);
                    }else
                    {
                        Toast.makeText(context,"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                    }*/

                }
            });
            convertView.setTag(holder);
        }
        else
        {
            holder=(DiagnosisHolder)convertView.getTag();
            holder.procedure_ID_Text.setText(procedure_ID.get(position));
            holder.diagnosisNameText.setText(procedureNameList.get(position));
            holder.averageText.setText("$"+averageList.get(position));
            holder.totalPayText.setText("$"+totalPaymentList.get(position));
            holder.medicalText.setText("$"+medicalPaymentList.get(position));
            holder.physicianIDText.setText(physicianId.get(position));
            holder.procedureCodeText.setText(procedureCode.get(position));
            holder.editImageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PhysicianEditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("position", String.valueOf(position));
                    bundle.putString("ref_id", ref_id);
                    bundle.putString("procedure_id", procedure_ID.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            holder.delete_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, PhysicianDeleteActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("position", String.valueOf(position));
                    bundle.putString("ref_id",ref_id);
                    bundle.putString("procedure_id",procedure_ID.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

        return convertView;
    }


    private class DeleteTask extends AsyncTask<String,Void,String>
    {
        private int position=0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(false);
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
                    notifyDataSetInvalidated();
                    Toast.makeText(context,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
