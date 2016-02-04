package com.example.user.hospitalcharge.NavigationFragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.hospitalcharge.Adapters.ProcedureSearch;
import com.example.user.hospitalcharge.Adapters.SearchAdapter;
import com.example.user.hospitalcharge.Main.LegalDisclaimerActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Model.Diagnosis.DaignosisData;
import com.example.user.hospitalcharge.Model.Diagnosis.Diagnoseslist;
import com.example.user.hospitalcharge.Model.Procedure.Categories;
import com.example.user.hospitalcharge.Model.Procedure.Physicians;
import com.example.user.hospitalcharge.Model.Procedure.PhysiciansProcedures;
import com.example.user.hospitalcharge.Model.Procedure.Physiciansprocedureslist;
import com.example.user.hospitalcharge.Model.Procedure.ProcedureData;
import com.example.user.hospitalcharge.Model.Procedure.Procedures;
import com.example.user.hospitalcharge.R;

import java.util.ArrayList;

/**
 * Created by user on 05-01-2016.
 */
public class ProcedureSearchFragment extends Fragment {

    private ListView listView;
    private ProcedureSearch procedureSearch;
    //private ArrayList<Physiciansprocedureslist> procedurelist_search=null;

    private ArrayList<PhysiciansProcedures> physiciansProcedureslist;
    private ArrayList<Procedures> procedureslist;
    private ArrayList<Physicians> physicianslist;
    private ArrayList<Categories> categorieslist;
    private ArrayList<com.example.user.hospitalcharge.Model.Procedure.Countries> countrieslist_procedure;
    private ArrayList<com.example.user.hospitalcharge.Model.Procedure.States> stateslist_procedure;

    private TextView procedureHeadingText;
    private Button loadButton;
    private ImageView termsImage;
    private ProgressDialog dialog;
    private int pageCount = 0;
    private ArrayList<ProcedureData> procedureDataArrayList=new ArrayList<ProcedureData>();

    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_search_custom,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("PROCEDURE");
        navigationActivity.setBackVisibility(true);
        listView=(ListView)view.findViewById(R.id.search_listview);
        loadButton=(Button)view.findViewById(R.id.load_more);
        /*loadButton.setOnTouchListener(new View.OnTouchListener() {
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
        });*/
        termsImage=(ImageView)view.findViewById(R.id.terms);
        /*termsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });*/
        procedureHeadingText=(TextView)view.findViewById(R.id.search_textview);
        Bundle bundle1=getArguments();
        //procedurelist_search=bundle1.getParcelableArrayList("list");
        physiciansProcedureslist=bundle1.getParcelableArrayList("physiciansProcedureslist");
        procedureslist=bundle1.getParcelableArrayList("procedureslist");
        physicianslist=bundle1.getParcelableArrayList("physicianslist");
        categorieslist=bundle1.getParcelableArrayList("categorieslist");
        countrieslist_procedure=bundle1.getParcelableArrayList("countrieslist_procedure");
        stateslist_procedure=bundle1.getParcelableArrayList("stateslist_procedure");
        procedureHeadingText.setText(bundle1.getString("procedureHeading"));
        String start="0";
        String end="10";

        for (int i=0;i<physicianslist.size();i++)
        {

                ProcedureData data=new ProcedureData();
                data.setProcedure_id(physicianslist.get(i).getId());
                data.setNameText(physicianslist.get(i).getPhysician_fname() +
                        " " + physicianslist.get(i).getPhysician_lname());
                data.setLocText(physicianslist.get(i).getStreet_address1());
                data.setPhoneText(physicianslist.get(i).getPhone());
                data.setAvgChargesText(physiciansProcedureslist.get(i).getCharge_amt());
                data.setAvgTotalText(physiciansProcedureslist.get(i).getTotal_amt());
                data.setAvgMedicareText(physiciansProcedureslist.get(i).getMedicare_amt());
                data.setFirstName(physicianslist.get(i).getPhysician_fname());
                data.setLastName(physicianslist.get(i).getPhysician_lname());
                procedureDataArrayList.add(data);


        }
        /*if (Integer.parseInt(end) >= physiciansProcedureslist.size()) {
            end = String.valueOf(physiciansProcedureslist.size());
        }
        final String[] params={start,end};
        new Task().execute(params);*/
        procedureSearch=new ProcedureSearch(getActivity(),procedureDataArrayList);
        listView.setAdapter(procedureSearch);
        /*listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                final int count = listView.getCount();
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() >= count - threshold && pageCount < physiciansProcedureslist.size()) {

                        loadButton.setVisibility(View.VISIBLE);
                        termsImage.setVisibility(View.VISIBLE);
                        //Toast.makeText(getActivity(), String.valueOf(count)+":"+String.valueOf(count+10), Toast.LENGTH_SHORT).show();
                        String end = String.valueOf(count + 10);
                        if (Integer.parseInt(end) >= physiciansProcedureslist.size()) {
                            end = String.valueOf(physiciansProcedureslist.size());
                            //Toast.makeText(getActivity(), String.valueOf(count)+":"+String.valueOf(end), Toast.LENGTH_SHORT).show();
                        }
                        final String finalEnd = end;
                        loadButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String[] params = {String.valueOf(count), finalEnd};
                                new Task().execute(params);
                            }
                        });
                    } else {
                        loadButton.setVisibility(View.GONE);
                        termsImage.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });*/
        return view;
    }

    private void loadData(int start,int end)
    {

        pageCount++;
        for (int i=start;i<end;i++)
        {
            try {
                Thread.sleep(200);
                ProcedureData data=new ProcedureData();
                data.setProcedure_id(physicianslist.get(i).getId());
                data.setNameText(physicianslist.get(i).getPhysician_fname() +
                        " " + physicianslist.get(i).getPhysician_lname());
                data.setLocText(physicianslist.get(i).getStreet_address1());
                data.setPhoneText(physicianslist.get(i).getPhone());
                data.setAvgChargesText(physiciansProcedureslist.get(i).getCharge_amt());
                data.setAvgTotalText(physiciansProcedureslist.get(i).getTotal_amt());
                data.setAvgMedicareText(physiciansProcedureslist.get(i).getMedicare_amt());
                data.setFirstName(physicianslist.get(i).getPhysician_fname());
                data.setLastName(physicianslist.get(i).getPhysician_lname());
                procedureDataArrayList.add(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    private class Task extends AsyncTask<String,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            loadButton.setVisibility(View.GONE);
            termsImage.setVisibility(View.GONE);
            procedureSearch.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(String... params) {

            loadData(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
            return null;
        }
    }
}
