package com.example.user.hospitalcharge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.hospitalcharge.Holders.AdditionalHolder;
import com.example.user.hospitalcharge.Holders.ProcedureSearchHolder;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.AddServiciesData;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.Diagnoseshospitalslist;
import com.example.user.hospitalcharge.Model.Physician.PhysicianData;
import com.example.user.hospitalcharge.Model.Physician.ProviderCategory;
import com.example.user.hospitalcharge.Model.Physician.State;
import com.example.user.hospitalcharge.R;
import com.fasterxml.jackson.databind.deser.Deserializers;

import java.util.ArrayList;

/**
 * Created by user on 06-01-2016.
 */
public class AdditionalSearch extends BaseAdapter {

    private Context context;
    private ArrayList<AddServiciesData> addServiciesDatas;
    public AdditionalSearch(Context context,ArrayList<AddServiciesData> addServiciesDatas)
    {
        this.context=context;
        this.addServiciesDatas=addServiciesDatas;
    }

    @Override
    public int getCount() {
        return addServiciesDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return addServiciesDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AdditionalHolder holder=null;

        /*LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.layout_additional_servicies, null);*/
        if (convertView==null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_additional_servicies_1, null);
            holder=new AdditionalHolder();
            holder.diagnosisNameText=(TextView)convertView.findViewById(R.id.diagnosis_textview);
            holder.dischargesText=(TextView)convertView.findViewById(R.id.relative_textview1);
            holder.avgChargesText=(TextView)convertView.findViewById(R.id.avgCharges);
            holder.avgTotalText=(TextView)convertView.findViewById(R.id.relative_textview2);
            holder.avgMedicareText=(TextView)convertView.findViewById(R.id.amp);

            holder.diagnosisNameText.setText(addServiciesDatas.get(position).getDiagnosisName());
            holder.dischargesText.setText(addServiciesDatas.get(position).getDischarges());
            holder.avgChargesText.setText("$ "+addServiciesDatas.get(position).getAvgChargesText());
            holder.avgTotalText.setText("$ "+addServiciesDatas.get(position).getAvgTotalText());
            holder.avgMedicareText.setText("$ "+addServiciesDatas.get(position).getAvgMedicareText());
            convertView.setTag(holder);
        }
        else
        {
            holder=(AdditionalHolder)convertView.getTag();
            holder.diagnosisNameText.setText(addServiciesDatas.get(position).getDiagnosisName());
            holder.dischargesText.setText(addServiciesDatas.get(position).getDischarges());
            holder.avgChargesText.setText("$ "+addServiciesDatas.get(position).getAvgChargesText());
            holder.avgTotalText.setText("$ "+addServiciesDatas.get(position).getAvgTotalText());
            holder.avgMedicareText.setText("$ "+addServiciesDatas.get(position).getAvgMedicareText());
        }
        /*if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_search_listview4, null);
            holder=new ProcedureSearchHolder();

            holder.nameText=(TextView)convertView.findViewById(R.id.textview1);
            holder.locText=(TextView)convertView.findViewById(R.id.textview2);
            holder.phoneText=(TextView)convertView.findViewById(R.id.textview3);

            holder.nameText.setText(physicianDataArrayList.get(position).getProviderTypeText());
            holder.locText.setText(physicianDataArrayList.get(position).getLocText());
            holder.phoneText.setText(physicianDataArrayList.get(position).getProviderNameText());


            View includeView = convertView.findViewById(R.id.include);
            holder.avgChargesText=(TextView)includeView.findViewById(R.id.textView2);
            holder.avgTotalText=(TextView)includeView.findViewById(R.id.relative_textview2);
            holder.avgMedicareText=(TextView)includeView.findViewById(R.id.textView3);


            holder.avgChargesText.setText("$ "+physicianDataArrayList.get(position).getAvgChargesText());
            holder.avgTotalText.setText("$ "+physicianDataArrayList.get(position).getAvgTotalText());
            holder.avgMedicareText.setText("$ "+physicianDataArrayList.get(position).getAvgMedicareText());
                *//*if (position % 2 == 1) {
                    convertView.setBackgroundColor(Color.LTGRAY);
                    includeView.setBackgroundColor(Color.WHITE);
                } else {
                    convertView.setBackgroundColor(Color.WHITE);
                    includeView.setBackgroundColor(Color.LTGRAY);
                }*//*
            convertView.setTag(holder);
        }
        else
        {
            holder=(ProcedureSearchHolder)convertView.getTag();
            holder.nameText.setText(physicianDataArrayList.get(position).getProviderTypeText());
            holder.locText.setText(physicianDataArrayList.get(position).getLocText());
            holder.phoneText.setText(physicianDataArrayList.get(position).getProviderNameText());
            holder.avgChargesText.setText("$ "+physicianDataArrayList.get(position).getAvgChargesText());
            holder.avgTotalText.setText("$ "+physicianDataArrayList.get(position).getAvgTotalText());
            holder.avgMedicareText.setText("$ "+physicianDataArrayList.get(position).getAvgMedicareText());
        }*/
        return convertView;
    }
}
