package com.example.user.hospitalcharge.NavigationFragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.LegalDisclaimerActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
import com.example.user.hospitalcharge.Main.SignUpActivity;
import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by user on 21-12-2015.
 */
public class LoginFragment extends Fragment {

    private Button loginButton,signUpButton;
    private EditText emailText,passwordText;
    private ImageView forgotPassImageview,imageView1;
    private ProgressDialog progressDialog;
    private ArrayList<String> providers_list,states_list,country_list;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public String loginPreferences="loginPrefs";
    SharedPreferences sharedpreferences,sharedpreferences2;
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_login_page,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("LOGIN");
        navigationActivity.setBackVisibility(true);
        navigationActivity.setVisibility(false);
        navigationActivity.showDrawer(false);
        loginButton=(Button)view.findViewById(R.id.login_page_button1);
        loginButton.setOnTouchListener(new View.OnTouchListener() {
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
        signUpButton=(Button)view.findViewById(R.id.login_page_button2);
        signUpButton.setOnTouchListener(new View.OnTouchListener() {
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
        emailText=(EditText)view.findViewById(R.id.login_page_edittext1);
        passwordText=(EditText)view.findViewById(R.id.login_page_edittext2);
        forgotPassImageview=(ImageView)view.findViewById(R.id.login_page_imageview2);
        imageView1=(ImageView)view.findViewById(R.id.terms_conditions);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedpreferences2= getActivity().getSharedPreferences(loginPreferences, Context.MODE_PRIVATE);
        String email=sharedpreferences.getString("email","");
        String password=sharedpreferences.getString("password","");
        emailText.setText(email);
        passwordText.setText(password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Connectivity.checkInternet(getActivity())) {
                    login();
                } else
                    Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Connectivity.checkInternet(getActivity()))
                {
                    signUp();
                }
                else
                    Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
            }
        });
        forgotPassImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
        return view;
    }
    private void signUp()
    {
        new SignupTask().execute();
    }
    private void login()
    {
        /*String parameters="data[Users][email]="+emailText.getText().toString()+
                "&data[Users][password]="+passwordText.getText().toString()+"&api_key=bf45c093e542f057c123ae7d6";*/
        if(emailText.getText().toString().equals("")&&passwordText.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Please enter your email and password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(emailText.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Please enter your email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwordText.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(),"Please enter your password",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            String[] params={emailText.getText().toString(),passwordText.getText().toString(),"bf45c093e542f057c123ae7d6"};
            new LoginTask().execute(params);
        }
        /*String[] params={emailText.getText().toString(),passwordText.getText().toString(),"bf45c093e542f057c123ae7d6"};
        new LoginTask().execute(params);*/
    }

    private class LoginTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Users][email]",params[0]));
            nameValuePair.add(new BasicNameValuePair("data[Users][password]",params[1]));
            nameValuePair.add(new BasicNameValuePair("api_key",params[2]));
            return NetworkCall.makePostRequest(nameValuePair, Urls.SIGNUP_URL);

            //return makeNetwowkCall(params[0], Urls.SIGNUP_URL);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.show();
        }
        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(aVoid);
                int response=jsonObject.getInt("response");

                if(response==200)
                {
                    JSONObject jsonObject1=jsonObject.getJSONObject("user_data");
                    String id=jsonObject1.getString("id");
                    String username=jsonObject1.getString("username");
                    String password=jsonObject1.getString("password");
                    String group_id=jsonObject1.getString("group_id");
                    String ref_id=jsonObject1.getString("ref_id");
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("email", emailText.getText().toString());
                    editor.putString("password", passwordText.getText().toString());
                    editor.commit();
                    SharedPreferences.Editor editor1 = sharedpreferences2.edit();
                    editor1.putString("id",id);
                    editor1.putString("username",username);
                    editor1.putString("password", password);
                    editor1.putString("email",emailText.getText().toString());
                    editor1.putString("ref_id",ref_id);
                    editor1.putString("group_id",group_id);
                    editor1.commit();

                    Intent intent=null;
                    if(group_id.equals("2"))
                    {
                        //new MyAccountTask().execute();
                        PhysicianFragment nextFrag= new PhysicianFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("id",id);
                        bundle.putString("username",username);
                        bundle.putString("password",password);
                        bundle.putString("email",emailText.getText().toString());
                        bundle.putString("ref_id",ref_id);
                        bundle.putString("group_id", group_id);
                        nextFrag.setArguments(bundle);
                        getActivity().getFragmentManager().beginTransaction()
                                .replace(R.id.frame_container,nextFrag)
                                .addToBackStack("nextFrag")
                                .commit();
                    }else
                    {

                        HospitalFragment nextFrag= new HospitalFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("id",id);
                        bundle.putString("username",username);
                        bundle.putString("password",password);
                        bundle.putString("email",emailText.getText().toString());
                        bundle.putString("ref_id",ref_id);
                        bundle.putString("group_id", group_id);
                        nextFrag.setArguments(bundle);
                        getActivity().getFragmentManager().beginTransaction()
                                .replace(R.id.frame_container,nextFrag)
                                .addToBackStack("nextFrag")
                                .commit();
                    }
                }
                else {
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    private void forgotPassword()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_forgotpassword, null);
        builder.setView(view);
        builder.setCancelable(true);

        final EditText editText=(EditText)view.findViewById(R.id.forgotpass_edittext1);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String params=editText.getText().toString();
                if(Connectivity.checkInternet(getActivity()))
                {
                    if(editText.getText().toString().equals(""))
                    {
                        Toast.makeText(getActivity(), "Please enter email.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {

                        new ForgotPasswordTask().execute(params);
                    }
                }else
                {
                    Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    private class SignupTask extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.show();
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
                    JSONObject jsonObject1=jsonObject.getJSONObject("providers");
                    providers_list=new ArrayList<String>();
                    Iterator<String> iterator=jsonObject1.keys();
                    ArrayList<String> keys=new ArrayList<String>();
                    while(iterator.hasNext())
                    {
                        keys.add(iterator.next());
                    }
                    for(int i=0;i<jsonObject1.length();i++)
                    {
                        providers_list.add(jsonObject1.getString(keys.get(i)));
                    }
                    new StatesTask().execute();
                }
                else {
                    Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(Void... params) {
           /* String parameters="api_key=bf45c093e542f057c123ae7d6";
            String result= makeNetwowkCall(parameters,Urls.PROVIDER_LIST_URL);*/

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("api_key","bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair,Urls.PROVIDER_LIST_URL);

        }
    }
    private class StatesTask extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                if(response==200)
                {
                    JSONObject jsonObject1=jsonObject.getJSONObject("states");
                    states_list=new ArrayList<String>();
                    Iterator<String> iterator=jsonObject1.keys();
                    ArrayList<String> keys=new ArrayList<String>();
                    while(iterator.hasNext())
                    {
                        keys.add(iterator.next());
                    }
                    for(int i=0;i<jsonObject1.length();i++)
                    {
                        states_list.add(jsonObject1.getString(keys.get(i)));
                    }
                    new CountryTask().execute();
                }
                else {
                    Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... params) {

            /*String parameters="api_key=bf45c093e542f057c123ae7d6";
            String result= makeNetwowkCall(parameters,Urls.STATES_LIST_URL);
            return result;*/

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("api_key","bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair,Urls.STATES_LIST_URL);
        }
    }

    private class CountryTask extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                if(response==200)
                {
                    JSONObject jsonObject1=jsonObject.getJSONObject("countries");
                    country_list=new ArrayList<String>();Iterator<String> iterator=jsonObject1.keys();
                    ArrayList<String> keys=new ArrayList<String>();
                    while(iterator.hasNext())
                    {
                        keys.add(iterator.next());
                    }
                    for(int i=0;i<jsonObject1.length();i++)
                    {
                        country_list.add(jsonObject1.getString(keys.get(i)));
                    }
                    Intent intent=new Intent(getActivity(),SignUpActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putStringArrayList("providers",providers_list);
                    bundle.putStringArrayList("states",states_list);
                    bundle.putStringArrayList("country",country_list);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... params) {

            /*String parameters="api_key=bf45c093e542f057c123ae7d6";
            String result= makeNetwowkCall(parameters,Urls.COUNTRY_LIST_URL);
            return result;*/

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("api_key","bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair,Urls.COUNTRY_LIST_URL);
        }
    }

    private class ForgotPasswordTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                if(response==200)
                {
                    String msg=jsonObject.getString("msg");
                    Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(),"Email Address is not valid or not found in our database.",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            String parameters=params[0];
            //String result= makeNetwowkCall(parameters,Urls.FORGOT_PASSWORD_URL);

            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("email",parameters));
            return NetworkCall.makePostRequest(nameValuePair,Urls.FORGOT_PASSWORD_URL);

        }
    }


}
