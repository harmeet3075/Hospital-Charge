package com.example.user.hospitalcharge.NavigationFragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Main.Connectivity;
import com.example.user.hospitalcharge.Main.LegalDisclaimerActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
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

/**
 * Created by user on 12/8/2015.
 */
public class AboutUsFragment extends Fragment {

    private WebView webView;
    private ProgressDialog progressDialog;
    private ImageView imageView1;
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_aboutus_frag,viewGroup,false);

        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("ABOUT US");
        navigationActivity.setBackVisibility(true);
        navigationActivity.setVisibility(false);
        navigationActivity.showDrawer(false);
        if(Connectivity.checkInternet(getActivity()))
        {
            home();
        }else
        {
            Toast.makeText(getActivity(),"Please check your internet connection and try again.",Toast.LENGTH_SHORT).show();
        }
        webView=(WebView)view.findViewById(R.id.about_webview);
        imageView1=(ImageView)view.findViewById(R.id.terms_conditions);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });
        //webView.getSettings().setJavaScriptEnabled(true);
        return view;
    }

    private void home()
    {
        String parameters="data[Page][title]=about-us"+"&api_key=bf45c093e542f057c123ae7d6";
        new HomeTask().execute(parameters);
    }

    private class HomeTask extends AsyncTask<String,Void,String>
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
            try {
                JSONObject jsonObject=new JSONObject(s);
                int response=jsonObject.getInt("response");
                if(response==200)
                {
                    String text = "<html><body style=\"text-align:justify\"> %s </body></Html>";
                    String data=jsonObject.getString("content");
                    //Toast.makeText(getActivity().getApplicationContext(),data,Toast.LENGTH_SHORT).show();

                    webView.loadData(data,"text/html",null);
                    //webView.loadData(String.format(text,data),"text/html",null);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
           /* String parameters=params[0];
            String result= makeNetwowkCall(parameters, Urls.CMS_URL);*/

            //// TODO: 13-01-2016 changes.
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("data[Page][title]", "about-us"));
            nameValuePair.add(new BasicNameValuePair("api_key", "bf45c093e542f057c123ae7d6"));
            return NetworkCall.makePostRequest(nameValuePair,  Urls.CMS_URL);

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
}
