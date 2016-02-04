package com.example.user.hospitalcharge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.hospitalcharge.Holders.PhysicianHolder;
import com.example.user.hospitalcharge.Model.Physician.AdditionalData;
import com.example.user.hospitalcharge.Model.Physician.Physiciansprocedureslist;
import com.example.user.hospitalcharge.Model.Physician.ProviderCategory;
import com.example.user.hospitalcharge.Model.Physician.State;
import com.example.user.hospitalcharge.R;

import java.util.ArrayList;

/**
 * Created by user on 07-01-2016.
 */
public class ProcedureAdditional extends BaseAdapter{


    private Context context;
    private ArrayList<AdditionalData> additionalDatas=new ArrayList<AdditionalData>();
    public ProcedureAdditional(Context context,ArrayList<AdditionalData> additionalDatas)
    {
        this.context=context;
        this.additionalDatas=additionalDatas;

    }

    @Override
    public int getCount() {
        return additionalDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return additionalDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhysicianHolder holder=null;

        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_search_physician_include_1, null);
            holder=new PhysicianHolder();

            holder.procedureNameText=(TextView)convertView.findViewById(R.id.diagnosis_textview);
            holder.avgChargesText=(TextView)convertView.findViewById(R.id.textView2);
            holder.avgTotalText=(TextView)convertView.findViewById(R.id.relative_textview2);
            holder.avgMedicareText=(TextView)convertView.findViewById(R.id.textView3);

            holder.procedureNameText.setText(additionalDatas.get(position).getProviderName());
            holder.avgChargesText.setText("$ "+additionalDatas.get(position).getAvgChargesText());
            holder.avgTotalText.setText("$ "+additionalDatas.get(position).getAvgTotalText());
            holder.avgMedicareText.setText("$ "+additionalDatas.get(position).getAvgMedicareText());





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
            holder=(PhysicianHolder)convertView.getTag();
            holder.procedureNameText.setText(additionalDatas.get(position).getProviderName());
            holder.avgChargesText.setText("$ "+additionalDatas.get(position).getAvgChargesText());
            holder.avgTotalText.setText("$ "+additionalDatas.get(position).getAvgTotalText());
            holder.avgMedicareText.setText("$ "+additionalDatas.get(position).getAvgMedicareText());
        }
        return convertView;
    }
}
