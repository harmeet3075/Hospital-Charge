package com.example.user.hospitalcharge.NavigationFragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Adapters.VideoAdapter;
import com.example.user.hospitalcharge.Adapters.YouTubeContent;
import com.example.user.hospitalcharge.Main.CustomLightboxActivity;
import com.example.user.hospitalcharge.Main.LegalDisclaimerActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.Main.NetworkCall;
import com.example.user.hospitalcharge.Main.YouTubeActivity;
import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

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

/**
 * Created by user on 12/8/2015.
 */
public class VideosFragment extends Fragment {

    private WebView webView;
    private ListView listView;
    private ArrayList<String> urls_list=new ArrayList<String>();
    private ArrayList<String> title_list=new ArrayList<String>();
    private ProgressDialog progressDialog;
    private Context context;
    private ImageView imageView;
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_videos_frag,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("VIDEO GALLERY");
        navigationActivity.setBackVisibility(true);
        navigationActivity.setVisibility(false);
        navigationActivity.showDrawer(false);
        context=getActivity().getApplicationContext();
        listView=(ListView)view.findViewById(R.id.videos_list);
        imageView=(ImageView)view.findViewById(R.id.disclaimer);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final YouTubeContent.YouTubeVideo video = YouTubeContent.ITEMS.get(position);

                final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(getActivity());
                if (result != YouTubeInitializationResult.SUCCESS) {
                    //If there are any issues we can show an error dialog.
                    result.getErrorDialog(getActivity(), 0).show();
                }else
                {
                    /*startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                            getString(R.string.DEVELOPER_KEY), video.id,0,true,true));*/

                    final Intent actIntent = new Intent(getActivity(), YouTubeActivity.class);
                    actIntent.putExtra(YouTubeActivity.KEY_VIDEO_ID, video.id);
                    startActivity(actIntent);
                }
                /*final Intent lightboxIntent = new Intent(getActivity(), CustomLightboxActivity.class);
                lightboxIntent.putExtra(CustomLightboxActivity.KEY_VIDEO_ID, video.id);
                startActivity(lightboxIntent);*/
                /*startActivity(YouTubeStandalonePlayer.createVideoIntent(getActivity(),
                        getString(R.string.DEVELOPER_KEY), video.id, 0, true, true));*/


            }
        });
        //final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(getActivity());

        /*if (result != YouTubeInitializationResult.SUCCESS) {
            //If there are any issues we can show an error dialog.
            result.getErrorDialog(getActivity(), 0).show();
        }*/
        home();
        return view;


    }


    private void home()
    {
        String parameters="bf45c093e542f057c123ae7d6";
        new HomeTask().execute(parameters);
    }

    private class HomeTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(false);
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

                    JSONArray jsonArray=jsonObject.getJSONArray("video");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        JSONObject jsonObject2=jsonObject1.getJSONObject("YoutubeVideo");
                        urls_list.add(jsonObject2.getString("link"));
                        title_list.add(jsonObject2.getString("title"));
                        //YouTubeContent.addItem(new YouTubeContent.YouTubeVideo(urls_list.get(i).toString(),title_list.get(i).toString()));
                    }
                    VideoAdapter videoAdapter=new VideoAdapter(getActivity());
                    listView.setAdapter(videoAdapter);
                    //webView.loadUrl(urls_list.get(0));
                }
                else {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected String doInBackground(String... params) {
            String parameters=params[0];
           /* String result= makeNetwowkCall(parameters, Urls.VIDEO_URL);
            return result;*/
            ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("api_key", parameters));
            return NetworkCall.makePostRequest(nameValuePair, Urls.VIDEO_URL);
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
