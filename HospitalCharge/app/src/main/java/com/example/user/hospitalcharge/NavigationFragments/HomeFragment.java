package com.example.user.hospitalcharge.NavigationFragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.hospitalcharge.Adapters.AutoCompleteAdapter;
import com.example.user.hospitalcharge.Main.BackgroundCalls;
import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.LegalDisclaimerActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
import com.example.user.hospitalcharge.Main.VideoActivity;
import com.example.user.hospitalcharge.Model.Diagnosis.Diagnoses;
import com.example.user.hospitalcharge.Model.Diagnosis.DiagnosesHospitals;
import com.example.user.hospitalcharge.Model.Diagnosis.Diagnoseslist;
import com.example.user.hospitalcharge.Model.Diagnosis.Hospitals;
import com.example.user.hospitalcharge.Model.Diagnosis.MainWrapper;
import com.example.user.hospitalcharge.Model.Diagnosis.Recipe;
import com.example.user.hospitalcharge.Model.Diagnosis.Recipes;
import com.example.user.hospitalcharge.Model.Diagnosis.States;
import com.example.user.hospitalcharge.Model.Physician.Physician;
import com.example.user.hospitalcharge.Model.Physician.ProviderCategory;
import com.example.user.hospitalcharge.Model.Physician.State;
import com.example.user.hospitalcharge.Model.Diagnosis.Countries;
import com.example.user.hospitalcharge.Model.Procedure.Categories;
import com.example.user.hospitalcharge.Model.Procedure.Physicians;
import com.example.user.hospitalcharge.Model.Procedure.PhysiciansProcedures;
import com.example.user.hospitalcharge.Model.Procedure.Physiciansprocedureslist;
import com.example.user.hospitalcharge.Model.Procedure.Procedures;
import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import java.util.HashMap;

/**
 * Created by user on 12/8/2015.
 */
public class HomeFragment extends Fragment {


    private int[] images={R.drawable.screen1,R.drawable.screen2,R.drawable.screen3,R.drawable.screen4,
    R.drawable.screen5,R.drawable.screen6};
    private ProgressDialog progressDialog;
    private EditText editText;
    private AutoCompleteTextView autoCompleteTextView;
    private WebView webView;
    private Button searchButton;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    private int counter1=0,counter2;
    private ArrayList<String> urls_list=new ArrayList<String>();
    private ArrayList<String> states_list=new ArrayList<String>();
    private ArrayList<String> city_list=new ArrayList<String>();
    private ArrayList<String> diagnosisList=new ArrayList<String>();
    private ArrayList<String> procedureList=new ArrayList<String>();
    private ArrayList<String> physicianList=new ArrayList<String>();
    private ArrayList<String> diagnosisList_adapter=new ArrayList<String>();
    private ArrayList<String> procedureList_adapter=new ArrayList<String>();
    private ArrayList<String> physicianList_adapter=new ArrayList<String>();
    private ArrayList<String> diagnosis_Id_List=new ArrayList<String>();
    private ArrayList<String> procedure_Id_List=new ArrayList<String>();
    private ArrayList<String> physician_Id_List=new ArrayList<String>();
    private String[] spinner1_list={"Select type","Diagnosis","Procedure","Provider"};
    private Spinner spinner1,spinner2,spinner3,spinner4;
    ArrayAdapter<String> adapter2,adapter5,physicianAdapter;
    private HashMap<String,String> diagnosisData=new HashMap<String,String>();
    private HashMap<String,String> procedureData=new HashMap<String,String>();
    private HashMap<String,String> physicianData=new HashMap<String,String>();
    private ObjectMapper objectMapper = null;
    private JsonFactory jsonFactory = null;
    private JsonParser jp = null;
    private ArrayList<Diagnoseslist> diagnoseslist=null;
    private ArrayList<Hospitals> hospitalslist=new ArrayList<Hospitals>();
    private ArrayList<Countries> countrieslist=new ArrayList<Countries>();
    private ArrayList<States> stateslist=new ArrayList<States>();
    private ArrayList<Diagnoses> diagnoses=new ArrayList<Diagnoses>();
    private ArrayList<DiagnosesHospitals> diagnosesHospitals=new ArrayList<DiagnosesHospitals>();
    private ArrayList<Physiciansprocedureslist> procedurelist_search=null;

    private ArrayList<PhysiciansProcedures> physiciansProcedureslist= new ArrayList<PhysiciansProcedures>();
    private ArrayList<Procedures> procedureslist= new ArrayList<Procedures>();
    private ArrayList<Physicians> physicianslist= new ArrayList<Physicians>();
    private ArrayList<Categories> categorieslist= new ArrayList<Categories>();
    private ArrayList<com.example.user.hospitalcharge.Model.Procedure.Countries> countrieslist_procedure= new ArrayList<com.example.user.hospitalcharge.Model.Procedure.Countries>();
    private ArrayList<com.example.user.hospitalcharge.Model.Procedure.States> stateslist_procedure= new ArrayList<com.example.user.hospitalcharge.Model.Procedure.States>();

    private ArrayList<com.example.user.hospitalcharge.Model.Physician.Physiciansprocedureslist> physicianlist_search1=null;
    private ArrayList<com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures> physiciansProcedureslist_physician= new ArrayList<com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures>();
    private ArrayList<com.example.user.hospitalcharge.Model.Physician.Procedures> procedureslist_physician= new ArrayList<com.example.user.hospitalcharge.Model.Physician.Procedures>();
    private AutoCompleteAdapter autoCompleteAdapter1,autoCompleteAdapter2,autoCompleteAdapter3;
    private ProviderCategory providerCategory;
    private State state;
    private Physician physician;
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_home_frag, viewGroup, false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("SEARCH");
        //Toast.makeText(getActivity(),"onCreateView",Toast.LENGTH_SHORT).show();
        navigationActivity.setVisibility(true);
        navigationActivity.setBackVisibility(false);
        navigationActivity.showDrawer(true);
        objectMapper = new ObjectMapper();
        jsonFactory = new JsonFactory();
       /* BackgroundCalls backgroundCalls=new BackgroundCalls(getActivity());
        states_list=backgroundCalls.getStates();
        city_list=backgroundCalls.getCity();*/
        home();
        imageView1=(ImageView)view.findViewById(R.id.imageview1);
        imageView2=(ImageView)view.findViewById(R.id.imageview2);
        imageView3=(ImageView)view.findViewById(R.id.terms_conditions);
        searchButton=(Button)view.findViewById(R.id.search_button);
        spinner1=(Spinner)view.findViewById(R.id.spinner1);
        imageView4=(ImageView)view.findViewById(R.id.home_webview);

        spinner3=(Spinner)view.findViewById(R.id.spinner3);
        spinner4=(Spinner)view.findViewById(R.id.spinner4);
        autoCompleteTextView=(AutoCompleteTextView)view.findViewById(R.id.auto);
        autoCompleteTextView.setHint("Search Diagnosis/Procedure/Physician");
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,spinner1_list);
        spinner1.setAdapter(adapter1);
        Bundle bundle1=getArguments();
        states_list=bundle1.getStringArrayList("states_list");
        city_list=bundle1.getStringArrayList("city_list");
        /*procedureList.clear();
        physicianList.clear();
        diagnosisList.clear();*/
        diagnosisList=bundle1.getStringArrayList("diagnosis_list");
        procedureList=bundle1.getStringArrayList("procedure_list");
        physicianList=bundle1.getStringArrayList("physician_list");
        diagnosis_Id_List=bundle1.getStringArrayList("diagnosis_id");
        procedure_Id_List=bundle1.getStringArrayList("procedure_id");
        physician_Id_List=bundle1.getStringArrayList("physician_id");
        diagnosisList_adapter=diagnosisList;
        procedureList_adapter=procedureList;
        physicianList_adapter=physicianList;
        /*imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), VideoActivity.class);
                startActivity(intent);
            }
        });*/
        for(int i=0;i<diagnosisList.size();i++)
        {
            diagnosisData.put(diagnosisList.get(i),diagnosis_Id_List.get(i));
        }
        for (int i=0;i<procedureList.size();i++)
        {
            procedureData.put(procedureList.get(i),procedure_Id_List.get(i));
        }
        for (int i=0;i<physicianList.size();i++)
        {
            physicianData.put(physicianList.get(i),physician_Id_List.get(i));
        }
        autoCompleteAdapter1=new AutoCompleteAdapter(getActivity(),diagnosisList);
        autoCompleteAdapter2=new AutoCompleteAdapter(getActivity(),procedureList);
        autoCompleteAdapter3=new AutoCompleteAdapter(getActivity(),physicianList);
        adapter2=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,diagnosisList);
        ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,city_list);
        ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,states_list);
        adapter5=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,procedureList);
        physicianAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,physicianList);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);
        spinner1.setSelection(0);
        spinner3.setSelection(0);
        spinner4.setSelection(0);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {

                    autoCompleteTextView.clearListSelection();
                    autoCompleteTextView.setHint("Search Diagnosis/Procedure/Physician");
                    autoCompleteTextView.setText("");
                    spinner3.setVisibility(View.VISIBLE);
                    spinner4.setVisibility(View.VISIBLE);
                }
                if (position == 1) {

                    autoCompleteTextView.clearListSelection();
                    autoCompleteTextView.setHint("Search Diagnosis/Procedure/Physician");
                    autoCompleteTextView.setText("");
                    //String parameters = "api_key=bf45c093e542f057c123ae7d6";
                    //new GetDiagList().execute(parameters);

                    autoCompleteTextView.setAdapter(autoCompleteAdapter1);
                    autoCompleteAdapter1.notifyDataSetChanged();
                    spinner3.setVisibility(View.VISIBLE);
                    spinner4.setVisibility(View.VISIBLE);
                }
                if (position == 2) {


                    autoCompleteTextView.clearListSelection();
                    autoCompleteTextView.setHint("Search Diagnosis/Procedure/Physician");
                    autoCompleteTextView.setText("");
                    autoCompleteTextView.setAdapter(autoCompleteAdapter2);
                    autoCompleteAdapter2.notifyDataSetChanged();
                    //new ProcedureTask().execute();
                    spinner3.setVisibility(View.VISIBLE);
                    spinner4.setVisibility(View.VISIBLE);
                }
                if (position == 3) {
                    autoCompleteTextView.clearListSelection();
                    autoCompleteTextView.setHint("Search Diagnosis/Procedure/Physician");
                    autoCompleteTextView.setText("");
                    autoCompleteTextView.setAdapter(autoCompleteAdapter3);
                    autoCompleteAdapter3.notifyDataSetChanged();
                    //new PhysicianTask().execute();
                    spinner3.setVisibility(View.INVISIBLE);
                    spinner4.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });


        searchButton.setOnTouchListener(new View.OnTouchListener() {
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
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner1.getSelectedItem().toString().equals("Select type"))
                {
                    if(Connectivity.checkInternet(getActivity()))
                    {
                        if(spinner3.getSelectedItemPosition()==0&& spinner4.getSelectedItemPosition()==0&&
                                autoCompleteTextView.getText().equals(""))
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(spinner3.getSelectedItemPosition()==0)
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (spinner4.getSelectedItemPosition()==0)
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(autoCompleteTextView.getText().toString().equals(""))
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                            Toast.makeText(getActivity(),"please select type.",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                    }

                }
                if(spinner1.getSelectedItem().toString().equals("Diagnosis")) {

                    if(Connectivity.checkInternet(getActivity()))
                    {

                        if(spinner3.getSelectedItemPosition()==0&& spinner4.getSelectedItemPosition()==0&&
                                autoCompleteTextView.getText().equals(""))
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(spinner3.getSelectedItemPosition()==0)
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (spinner4.getSelectedItemPosition()==0)
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(autoCompleteTextView.getText().toString().equals(""))
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else {

                       /* String parameters="type="+spinner1.getSelectedItem().toString()+
                                "&itemid="+diagnosisData.get(autoCompleteTextView.getText().toString())+"&name="+autoCompleteTextView.getText().toString()
                                +"&state_code="+spinner4.getSelectedItem().toString();*/

                            String[] parameters1={spinner1.getSelectedItem().toString(),
                                    diagnosisData.get(autoCompleteTextView.getText().toString()),
                                    autoCompleteTextView.getText().toString(),spinner3.getSelectedItem().toString()};
                            new DiagSearchTask().execute(parameters1);
                        }
                    }else
                    {
                        Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                    }
                    /*Toast.makeText(getActivity(), spinner1.getSelectedItem().toString()
                            + " Name:" + autoCompleteTextView.getText().toString() + " id:" +
                            diagnosisData.get(autoCompleteTextView.getText().toString()) + " City:"
                            + spinner4.getSelectedItem().toString() + "state" + spinner3.getSelectedItem().toString()
                            , Toast.LENGTH_SHORT).show();*/

                    /*SearchFragment nextFrag = new SearchFragment();
                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, nextFrag)
                            .addToBackStack("nextFrag")
                            .commit();*/
                }
                if(spinner1.getSelectedItem().toString().equals("Procedure")) {
                    /*Toast.makeText(getActivity(), spinner1.getSelectedItem().toString()
                            + " Name:" + autoCompleteTextView.getText().toString() + " id:" +
                            procedureData.get(autoCompleteTextView.getText().toString()) + " City:"
                            + spinner3.getSelectedItem().toString() + "state" + spinner4.getSelectedItem().toString()
                            , Toast.LENGTH_SHORT).show();*/

                    if(Connectivity.checkInternet(getActivity()))
                    {
                        if(spinner3.getSelectedItemPosition()==0&& spinner4.getSelectedItemPosition()==0&&
                                autoCompleteTextView.getText().equals(""))
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(spinner3.getSelectedItemPosition()==0)
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (spinner4.getSelectedItemPosition()==0)
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(autoCompleteTextView.getText().toString().equals(""))
                        {
                            Toast.makeText(getActivity(),"please select all fields.",Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            String parameters = "type=" + spinner1.getSelectedItem().toString() +
                                    "&itemid=" + procedureData.get(autoCompleteTextView.getText().toString()) + "&name=" + autoCompleteTextView.getText().toString()
                                    + "&state_code=" + spinner3.getSelectedItem().toString();

                            String[] parameters1={spinner1.getSelectedItem().toString(),
                                    procedureData.get(autoCompleteTextView.getText().toString()),
                                    autoCompleteTextView.getText().toString(),spinner3.getSelectedItem().toString()};

                            new ProcedureSearchTask().execute(parameters1);
                        }/*ProcedureSearchFragment nextFrag = new ProcedureSearchFragment();
                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, nextFrag)
                            .addToBackStack("nextFrag")
                            .commit();*/
                    }else
                    {
                        Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                    }

                }
                if(spinner1.getSelectedItem().toString().equals("Provider")) {
                    /*Toast.makeText(getActivity(), spinner1.getSelectedItem().toString()
                            + " Name:" + autoCompleteTextView.getText().toString() + " id:" +
                            physicianData.get(autoCompleteTextView.getText().toString()) + " City:"
                            + spinner3.getSelectedItem().toString() + "state" + spinner4.getSelectedItem().toString()
                            , Toast.LENGTH_SHORT).show();*/

                        /*String parameters = "type=" + spinner1.getSelectedItem().toString() +
                                "&itemid=" + physicianData.get(autoCompleteTextView.getText().toString()) + "&name=" + autoCompleteTextView.getText().toString();*/
                    if(Connectivity.checkInternet(getActivity()))
                    {
                        if(autoCompleteTextView.getText().toString().equals(""))
                        {
                            Toast.makeText(getActivity(),"please select Provider.",Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getActivity(),"please select City.",Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            String parameters1[] = {spinner1.getSelectedItem().toString(),
                                    physicianData.get(autoCompleteTextView.getText().toString()),
                                    autoCompleteTextView.getText().toString()};
                            new PhysicianSearchTask().execute(parameters1);
                        }
                    }else
                    {
                        Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                    }


                }
                /*SearchFragment nextFrag = new SearchFragment();

                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag)
                        .addToBackStack("nextFrag")
                        .commit();*/
            }
        });
       /* webView=(WebView)view.findViewById(R.id.home_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.canGoBack();*/
        /*android.support.v4.app.FragmentManager fragmentManager=((NavigationActivity) getActivity()).getSupportFragmentManager();
        MyFragment myFragment=(MyFragment)fragmentManager.findFragmentById(R.id.frag01);
        myFragment.setVideoId("tttG6SdnCd4");*/
        //webView.loadUrl(urls[0]);
        //webView.setWebChromeClient(new WebChromeClient());
        imageView4.setImageResource(images[0]);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //webView.loadUrl("https://player.vimeo.com/video/16144037");
                if (counter2 != 0) {
                    counter2--;
                    counter1++;
                    //webView.loadUrl(urls_list.get(counter1));
                    imageView4.setImageResource(images[counter1]);
                    if (counter2 == 0) {

                    }
                }
            }
        });


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (counter1 != 0) {
                    counter1--;
                    counter2++;
                    imageView4.setImageResource(images[counter1]);
                    //webView.loadUrl(urls_list.get(counter1));
                    if (counter1 == 0) {
                        imageView4.setImageResource(images[counter1]);
                        //webView.loadUrl(urls_list.get(counter1));
                    }
                }
            }
        });
        return view;
    }
    public void onPause()
    {
        super.onPause();
        //Toast.makeText(getActivity(),"onPause",Toast.LENGTH_SHORT).show();
        /*webView.stopLoading();
        webView.onPause();*/
    }
    private void home()
    {
        String parameters="api_key=bf45c093e542f057c123ae7d6";
        new HomeTask().execute(parameters);
    }
    public void onStop()
    {
        super.onStop();
        //Toast.makeText(getActivity(),"onStop",Toast.LENGTH_SHORT).show();
    }

    private class HomeTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            counter2=5;
            //progressDialog.dismiss();
            /*try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                if(response==200)
                {

                    JSONArray jsonArray = jsonObject.getJSONArray("video");
                    counter2=jsonArray.length()-1;

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        JSONObject jsonObject2=jsonObject1.getJSONObject("YoutubeVideo");
                        urls_list.add(jsonObject2.getString("link"));
                    }
                    //webView.loadUrl(urls_list.get(0));
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
        @Override
        protected String doInBackground(String... params) {
            /*String parameters=params[0];
            String result= makeNetwowkCall(parameters, Urls.VIDEO_URL); //// TODO: 18-01-2016 to make this call through httpclient. 
            return result;*/
            return "done";
        }
    }

    private class DiagSearchTask extends AsyncTask<String,Void,String>
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
            if(s.equals(""))
            {
                Toast.makeText(getActivity(),"Data not found",Toast.LENGTH_SHORT).show();
                return;
            }
            //
           try {
               jp = jsonFactory.createJsonParser(s);
               MainWrapper mainWrapper=objectMapper.readValue(jp, MainWrapper.class);
               String name=mainWrapper.getDiagnosisname();
               diagnoseslist=mainWrapper.getDiagnosisList();
               int size=mainWrapper.getDiagnosisList().size();
               diagnosesHospitals.clear();
               hospitalslist.clear();
               countrieslist.clear();
               stateslist.clear();
               diagnoses.clear();
              // Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
                for (int i=0;i<diagnoseslist.size();i++)   //// TODO: 13-01-2016  changes for loop
                {
                    diagnosesHospitals.add(diagnoseslist.get(i).getDiagnosesHospitals());
                    hospitalslist.add(diagnoseslist.get(i).getHospitals());
                    countrieslist.add(diagnoseslist.get(i).getCountries());
                    stateslist.add(diagnoseslist.get(i).getStates());
                    diagnoses.add(diagnoseslist.get(i).getDiagnoses());
                }

               SearchFragment nextFrag = new SearchFragment();
               Bundle bundle=new Bundle();
               bundle.putString("diagnosisHeading", name);
               //bundle.putParcelableArrayList("list", diagnoseslist);

               //// TODO: 13-01-2016 changes 5 following lines
               bundle.putParcelableArrayList("diagnosesHospitals",diagnosesHospitals);
               bundle.putParcelableArrayList("hospitalslist",hospitalslist);
               bundle.putParcelableArrayList("countrieslist",countrieslist);
               bundle.putParcelableArrayList("stateslist",stateslist);
               bundle.putParcelableArrayList("diagnoses",diagnoses);

               nextFrag.setArguments(bundle);

               getActivity().getFragmentManager().beginTransaction()
                       .replace(R.id.frame_container, nextFrag)
                       .addToBackStack("nextFrag")
                       .commit();
            } catch (Exception e) {
               Toast.makeText(getActivity(),"Data not found",Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }
        }
        @Override
        protected String doInBackground(String... params) {
            /*String parameters=params[0];
            String result= makeNetwowkCall(parameters,"http://hospitalcharge.com/api/search");*/
           ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("type", params[0]));
            nameValuePair.add(new BasicNameValuePair("itemid",params[1]));
            nameValuePair.add(new BasicNameValuePair("name", params[2]));
            nameValuePair.add(new BasicNameValuePair("state_code", params[3]));
            return NetworkCall.makePostRequest(nameValuePair,"http://hospitalcharge.com/api/search");

        }
    }
    private class ProcedureSearchTask extends AsyncTask<String,Void,String>
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
            Log.d("data",s);

            if(s.equals(""))
            {
                Toast.makeText(getActivity(),"Data not found",Toast.LENGTH_SHORT).show();
                return;
            }
            //
            try {
                jp = jsonFactory.createJsonParser(s);
                com.example.user.hospitalcharge.Model.Procedure.MainWrapper mainWrapper=objectMapper.readValue(jp, com.example.user.hospitalcharge.Model.Procedure.MainWrapper.class);
                String name=mainWrapper.getPhysiciansname();
                procedurelist_search=mainWrapper.getPhysiciansprocedureslist();
                physiciansProcedureslist.clear();
                procedureslist.clear();
                physicianslist.clear();
                categorieslist.clear();
                countrieslist.clear();
                stateslist_procedure.clear();
                /*String size=mainWrapper.getPhysiciansprocedureslist().get(0).getProcedures().getProcedure_code();
                String fn_ln=procedurelist_search.get(0).getPhysicians().getPhysician_fname() +
                        " " + procedurelist_search.get(0).getPhysicians().getPhysician_lname();*/
                //Toast.makeText(getActivity(), name+":"+String.valueOf(size)+":"+fn_ln, Toast.LENGTH_SHORT).show();

                for(int i=0;i<procedurelist_search.size();i++) //// TODO: 13-01-2016 changes for loop
                {
                    physiciansProcedureslist.add(procedurelist_search.get(i).getPhysiciansProcedures());
                    procedureslist.add(procedurelist_search.get(i).getProcedures());
                    physicianslist.add(procedurelist_search.get(i).getPhysicians());
                    categorieslist.add(procedurelist_search.get(i).getCategories());
                    countrieslist_procedure.add(procedurelist_search.get(i).getCountries());
                    stateslist_procedure.add(procedurelist_search.get(i).getStates());
                }
                ProcedureSearchFragment nextFrag = new ProcedureSearchFragment();
                Bundle bundle=new Bundle();
                bundle.putString("procedureHeading", name);
                //bundle.putParcelableArrayList("list", procedurelist_search);
                //// TODO: 13-01-2016 changes following six lines. 
                bundle.putParcelableArrayList("physiciansProcedureslist", physiciansProcedureslist);
                bundle.putParcelableArrayList("procedureslist", procedureslist);
                bundle.putParcelableArrayList("physicianslist", physicianslist);
                bundle.putParcelableArrayList("categorieslist", categorieslist);
                bundle.putParcelableArrayList("countrieslist_procedure", countrieslist_procedure);
                bundle.putParcelableArrayList("stateslist_procedure", stateslist_procedure);
                
                
                nextFrag.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag)
                        .addToBackStack("nextFrag")
                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
           /*String parameters=params[0];
            String result= makeNetwowkCall(parameters,"http://hospitalcharge.com/api/search");
            return result;*/

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("type", params[0]));
            nameValuePair.add(new BasicNameValuePair("itemid",params[1]));
            nameValuePair.add(new BasicNameValuePair("name", params[2]));
            nameValuePair.add(new BasicNameValuePair("state_code", params[3]));
            return NetworkCall.makePostRequest(nameValuePair,"http://hospitalcharge.com/api/search");
        }
    }


    private class PhysicianSearchTask extends AsyncTask<String,Void,String>
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
            if(s.equals(""))
            {
                Toast.makeText(getActivity(),"Data not found",Toast.LENGTH_SHORT).show();
                return;
            }
            //Log.d("result",s);
            //
           try {
                jp = jsonFactory.createJsonParser(s);
                com.example.user.hospitalcharge.Model.Physician.MainWrapper mainWrapper=objectMapper.readValue(jp, com.example.user.hospitalcharge.Model.Physician.MainWrapper.class);
                int  response=mainWrapper.getResponse();
                physicianlist_search1=mainWrapper.getPhysiciansprocedureslist();
                String name=mainWrapper.getProviderCategory().getCategories().getCategory_name();
                providerCategory=mainWrapper.getProviderCategory();
                state=mainWrapper.getState();
                double lat=mainWrapper.getLat();
                double lng=mainWrapper.getLang();
                String lat_string=mainWrapper.getPhysician().getPhysicians().getLatitude();
                String lng_string=mainWrapper.getPhysician().getPhysicians().getLongitude();
                String image=mainWrapper.getPhysician().getPhysicians().getPhysician_images();
                physician=mainWrapper.getPhysician();
                String completeAddress=physician.getPhysicians().getStreet_address1()+","+
                        physician.getPhysicians().getCity()+","+mainWrapper.getState().getStates().getName();

               for(int i=0;i<physicianlist_search1.size();i++)
               {
                   physiciansProcedureslist_physician.add(physicianlist_search1.get(i).getPhysiciansProcedures());
                   procedureslist_physician.add(physicianlist_search1.get(i).getProcedures());
               }
                PhysicianSearchFragment nextFrag = new PhysicianSearchFragment();
                Bundle bundle=new Bundle();
                bundle.putString("physicianHeading", autoCompleteTextView.getText().toString());
                //bundle.putParcelableArrayList("list", physicianlist_search1);
               bundle.putParcelableArrayList("physiciansProcedureslist_physician", physiciansProcedureslist_physician);
               bundle.putParcelableArrayList("procedureslist_physician", procedureslist_physician);
                bundle.putString("completeAddress", completeAddress);
                bundle.putDouble("lng", lng);
                bundle.putDouble("lat", lat);
                bundle.putString("providerCategory", providerCategory.getCategories().getCategory_name());
                bundle.putString("state", state.getStates().getName() + "," + state.getStates().getCode());
                bundle.putString("zip", physician.getPhysicians().getZip_code());
                bundle.putString("phone", physician.getPhysicians().getPhone());
                bundle.putString("gender", physician.getPhysicians().getGender());
                bundle.putString("credentials", physician.getPhysicians().getCredentials());
                bundle.putString("email",physician.getPhysicians().getEmail());
                bundle.putString("image",image);
                bundle.putString("lat_string",lat_string);
                bundle.putString("lng_string",lng_string);
                bundle.putString("id",mainWrapper.getPhysician().getPhysicians().getId());

                /*bundle.putParcelable("providerCategory", providerCategory);
                bundle.putParcelable("state",state);*/
                nextFrag.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, nextFrag)
                        .addToBackStack("nextFrag")
                        .commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
           /* String parameters=params[0];
            String result= makeNetwowkCall(parameters,"http://hospitalcharge.com/api/detail/physicians/"+params[0]);
            return result;*/
            
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("itemid",params[1]));
            return NetworkCall.makePostRequest(nameValuePair,"http://hospitalcharge.com/api/detail/physicians/"+params[1]);


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
