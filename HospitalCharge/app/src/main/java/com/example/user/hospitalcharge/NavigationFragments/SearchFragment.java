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

import com.example.user.hospitalcharge.Adapters.SearchAdapter;
import com.example.user.hospitalcharge.Main.LegalDisclaimerActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Model.Diagnosis.Countries;
import com.example.user.hospitalcharge.Model.Diagnosis.DaignosisData;
import com.example.user.hospitalcharge.Model.Diagnosis.Diagnoses;
import com.example.user.hospitalcharge.Model.Diagnosis.DiagnosesHospitals;
import com.example.user.hospitalcharge.Model.Diagnosis.Diagnoseslist;
import com.example.user.hospitalcharge.Model.Diagnosis.Hospitals;
import com.example.user.hospitalcharge.Model.Diagnosis.States;
import com.example.user.hospitalcharge.R;

import java.util.ArrayList;

/**
 * Created by user on 29-12-2015.
 */
public class SearchFragment extends Fragment {

    private ListView listView;
    private ArrayList<Diagnoseslist> diagnoseslist=null;
    private ArrayList<Hospitals> hospitalslist=null;
    private ArrayList<Countries> countrieslist=null;
    private ArrayList<States> stateslist=null;
    private ArrayList<Diagnoses> diagnoses=null;
    private ArrayList<DiagnosesHospitals> diagnosesHospitals=null;
    private TextView diagosisHeadingText;
    private Button loadButton;
    private ImageView termsImage;
    private ProgressDialog dialog;
    private int pageCount = 0;
    SearchAdapter searchAdapter;
    private ArrayList<DaignosisData> daignosisDataArrayList=new ArrayList<DaignosisData>();
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_search_custom,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("DIAGNOSIS");
        navigationActivity.setBackVisibility(true);
        listView=(ListView)view.findViewById(R.id.search_listview);
        loadButton=(Button)view.findViewById(R.id.load_more);
        termsImage=(ImageView)view.findViewById(R.id.terms);
        /*termsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });*/

        diagosisHeadingText=(TextView)view.findViewById(R.id.search_textview);
        Bundle bundle1=getArguments();
        //diagnoseslist=bundle1.getParcelableArrayList("list");

        //// TODO: 13-01-2016 changes 5 following lines.
        hospitalslist=bundle1.getParcelableArrayList("hospitalslist");
        countrieslist=bundle1.getParcelableArrayList("countrieslist");
        stateslist=bundle1.getParcelableArrayList("stateslist");
        diagnoses=bundle1.getParcelableArrayList("diagnoses");
        diagnosesHospitals=bundle1.getParcelableArrayList("diagnosesHospitals");
        for (int i=0;i<diagnosesHospitals.size();i++)
        {

                //Thread.sleep(200);
                DaignosisData data=new DaignosisData();
                data.setHospital_id(diagnosesHospitals.get(i).getHospital_id());
                data.setNameText(hospitalslist.get(i).getHospital_name());
                data.setLocText(hospitalslist.get(i).getStreet_address1()+","+hospitalslist.get(i).getCity()+","
                        +stateslist.get(i).getName());
                data.setPhoneText(hospitalslist.get(i).getPhone());
                data.setDischarges(diagnosesHospitals.get(i).getDischarges());
                data.setAvgChargesText(diagnosesHospitals.get(i).getCharge_amt());
                data.setAvgTotalText(diagnosesHospitals.get(i).getTotal_amt());
                data.setAvgMedicareText(diagnosesHospitals.get(i).getMedicare_amt());
                daignosisDataArrayList.add(data);


        }
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
        diagosisHeadingText.setText(bundle1.getString("diagnosisHeading"));
        searchAdapter=new SearchAdapter(getActivity(),/*diagnoseslist,*/daignosisDataArrayList);
        String start="0";
        String end="10";
        /*if (Integer.parseInt(end) >= diagnosesHospitals.size()) {
            end = String.valueOf(diagnosesHospitals.size());
        }
        final String[] params={start,end};
        new Task().execute(params);*/

        listView.setAdapter(searchAdapter);
        /*listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                final int count = listView.getCount();
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() >= count - threshold && pageCount < diagnosesHospitals.size()) {

                        loadButton.setVisibility(View.VISIBLE);
                        termsImage.setVisibility(View.VISIBLE);
                        //Toast.makeText(getActivity(), String.valueOf(count)+":"+String.valueOf(count+10), Toast.LENGTH_SHORT).show();
                        String end=String.valueOf(count+10);
                        if(Integer.parseInt(end)>=diagnosesHospitals.size())
                        {
                            end=String.valueOf(diagnosesHospitals.size());
                            //Toast.makeText(getActivity(), String.valueOf(count)+":"+String.valueOf(end), Toast.LENGTH_SHORT).show();
                        }
                        final String finalEnd = end;
                        loadButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String[] params={String.valueOf(count), finalEnd};
                                new Task().execute(params);
                            }
                        });
                    }
                    else
                    {
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
                DaignosisData data=new DaignosisData();
                data.setHospital_id(diagnosesHospitals.get(i).getHospital_id());
                data.setNameText(hospitalslist.get(i).getHospital_name());
                data.setLocText(hospitalslist.get(i).getStreet_address1()+","+hospitalslist.get(i).getCity()+","
                +stateslist.get(i).getName());
                data.setPhoneText(hospitalslist.get(i).getPhone());
                data.setDischarges(diagnosesHospitals.get(i).getDischarges());
                data.setAvgChargesText(diagnosesHospitals.get(i).getCharge_amt());
                data.setAvgTotalText(diagnosesHospitals.get(i).getTotal_amt());
                data.setAvgMedicareText(diagnosesHospitals.get(i).getMedicare_amt());
                daignosisDataArrayList.add(data);
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
            //dialog.setIndeterminate(false);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            loadButton.setVisibility(View.GONE);
            termsImage.setVisibility(View.GONE);
            searchAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(String... params) {

            loadData(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
            return null;
        }
    }
}
