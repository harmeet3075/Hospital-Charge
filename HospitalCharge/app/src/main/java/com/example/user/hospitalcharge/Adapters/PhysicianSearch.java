package com.example.user.hospitalcharge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.hospitalcharge.Holders.PhysicianHolder;
import com.example.user.hospitalcharge.Holders.ProcedureSearchHolder;
import com.example.user.hospitalcharge.Model.Physician.PhysicianData;
import com.example.user.hospitalcharge.Model.Physician.Physiciansprocedureslist;
import com.example.user.hospitalcharge.Model.Physician.ProviderCategory;
import com.example.user.hospitalcharge.Model.Physician.State;
import com.example.user.hospitalcharge.Model.Procedure.ProcedureData;
import com.example.user.hospitalcharge.R;

import java.util.ArrayList;

/**
 * Created by user on 06-01-2016.
 */
public class PhysicianSearch extends BaseAdapter {



    private Context context;
    private ArrayList<Physiciansprocedureslist> physicianlist_search=new ArrayList<Physiciansprocedureslist>();
    private ArrayList<com.example.user.hospitalcharge.Model.Physician.Procedures> procedureslist_physician= new ArrayList<com.example.user.hospitalcharge.Model.Physician.Procedures>();
    private ArrayList<com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures> physiciansProcedureslist_physician= new ArrayList<com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures>();
    //private ProviderCategory providerCategory;
    //private State state;
    public PhysicianSearch(Context context,
                           ArrayList<com.example.user.hospitalcharge.Model.Physician.Procedures> procedureslist_physician,
                           ArrayList<com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures> physiciansProcedureslist_physician/*ArrayList<Physiciansprocedureslist> physicianlist_search*//*,ProviderCategory providerCategory,State state*/)
    {
        this.context=context;
        this.procedureslist_physician=procedureslist_physician;
        this.physiciansProcedureslist_physician=physiciansProcedureslist_physician;
        this.physicianlist_search=physicianlist_search;
        //this.providerCategory=providerCategory;
        //this.state=state;
    }

    @Override
    public int getCount() {
        return procedureslist_physician.size();
    }

    @Override
    public Object getItem(int position) {
        return procedureslist_physician.get(position);
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

            holder.procedureNameText.setText(procedureslist_physician.get(position).getProcedure_name());
            holder.avgChargesText.setText("$ "+physiciansProcedureslist_physician.get(position).getCharge_amt());
            holder.avgTotalText.setText("$ "+physiciansProcedureslist_physician.get(position).getTotal_amt());
            holder.avgMedicareText.setText("$ "+physiciansProcedureslist_physician.get(position).getMedicare_amt());





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
            holder.procedureNameText.setText(procedureslist_physician.get(position).getProcedure_name());
            holder.avgChargesText.setText("$ "+physiciansProcedureslist_physician.get(position).getCharge_amt());
            holder.avgTotalText.setText("$ "+physiciansProcedureslist_physician.get(position).getTotal_amt());
            holder.avgMedicareText.setText("$ "+physiciansProcedureslist_physician.get(position).getMedicare_amt());
        }
        return convertView;
    }
}
