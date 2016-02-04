package com.example.user.hospitalcharge.HospitalAccount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.hospitalcharge.Main.HospitalDeleteActivity;
import com.example.user.hospitalcharge.Main.HospitalEditActivity;
import com.example.user.hospitalcharge.Main.PhysicianDeleteActivity;
import com.example.user.hospitalcharge.Main.PhysicianEditActivity;
import com.example.user.hospitalcharge.R;

import java.util.ArrayList;

/**
 * Created by user on 12/8/2015.
 */
public class DiagnosisAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> diagnosisNameList=new ArrayList<String>()
            ,dischargesList=new ArrayList<String>()
            ,averageList=new ArrayList<String>()
            ,totalPaymentList=new ArrayList<String>(),medicalPaymentList=new ArrayList<String>(),diagnosisId=new ArrayList<String>();
    public String ref_id;
    public DiagnosisAdapter(Context context,ArrayList<String> diagnosisNameList,
                            ArrayList<String>dischargesList,ArrayList<String>averageList,
                            ArrayList<String>totalPaymentList,
                            ArrayList<String>medicalPaymentList,ArrayList<String>diagnosisId,String ref_id)
    {
        this.context=context;
        this.diagnosisNameList=diagnosisNameList;
        this.dischargesList=dischargesList;
        this.averageList=averageList;
        this.totalPaymentList=totalPaymentList;
        this.medicalPaymentList=medicalPaymentList;
        this.diagnosisId=diagnosisId;
        this.ref_id=ref_id;
    }
    @Override
    public int getCount() {
        return diagnosisNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return diagnosisNameList.get(position);
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
            convertView=layoutInflater.inflate(R.layout.layout_hospital_diag_custom_list,null);
            holder.diagnosisNameText=(TextView)convertView.findViewById(R.id.textview2);
            holder.dischargesText=(TextView)convertView.findViewById(R.id.textview4);
            holder.averageText=(TextView)convertView.findViewById(R.id.textview6);
            holder.totalPayText=(TextView)convertView.findViewById(R.id.textview8);
            holder.medicalText=(TextView)convertView.findViewById(R.id.textview10);;
            holder.editImageview=(ImageView)convertView.findViewById(R.id.imageView1);
            holder.delete_imageview=(ImageView)convertView.findViewById(R.id.imageview2);
            holder.dischargesText.setText(dischargesList.get(position));
            holder.diagnosisNameText.setText(diagnosisNameList.get(position));
            holder.averageText.setText("$"+averageList.get(position));
            holder.totalPayText.setText("$"+totalPaymentList.get(position));
            holder.medicalText.setText("$"+medicalPaymentList.get(position));
            holder.editImageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context,HospitalEditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("position", String.valueOf(position));
                    bundle.putString("ref_id", ref_id);
                    bundle.putString("diagnosis_id", diagnosisId.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            holder.delete_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HospitalDeleteActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("position", String.valueOf(position));
                    bundle.putString("ref_id", ref_id);
                    bundle.putString("diagnosis_id", diagnosisId.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            convertView.setTag(holder);
        }
        else
        {
            holder=(DiagnosisHolder)convertView.getTag();
            holder.dischargesText.setText(dischargesList.get(position));
            holder.diagnosisNameText.setText(diagnosisNameList.get(position));
            holder.averageText.setText(averageList.get(position));
            holder.totalPayText.setText(totalPaymentList.get(position));
            holder.medicalText.setText(medicalPaymentList.get(position));

            holder.editImageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, HospitalEditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("position", String.valueOf(position));
                    bundle.putString("ref_id", ref_id);
                    bundle.putString("diagnosis_id", diagnosisId.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            holder.delete_imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HospitalDeleteActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("position", String.valueOf(position));
                    bundle.putString("ref_id", ref_id);
                    bundle.putString("diagnosis_id", diagnosisId.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }
}
