package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Adapters.VideoAdapter;
import com.example.user.hospitalcharge.Adapters.YouTubeContent;
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

import java.util.ArrayList;

/**
 * Created by user on 20-01-2016.
 */
public class VideoActivity extends Activity {

    private ImageView backButton;
    private ImageView imageView;
    private WebView webView;
    private ListView listView;
    private ArrayList<String> urls_list=new ArrayList<String>();
    private ArrayList<String> title_list=new ArrayList<String>();
    private ProgressDialog progressDialog;
    private Context context;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_videos_activity);
        listView=(ListView)findViewById(R.id.videos_list);
        View view=findViewById(R.id.header);
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView=(ImageView)findViewById(R.id.disclaimer);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final YouTubeContent.YouTubeVideo video = YouTubeContent.ITEMS.get(position);

                final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(VideoActivity.this);
                if (result != YouTubeInitializationResult.SUCCESS) {
                    //If there are any issues we can show an error dialog.
                    result.getErrorDialog(VideoActivity.this, 0).show();
                }else
                {
                    final Intent actIntent = new Intent(getApplicationContext(), YouTubeActivity.class);
                    actIntent.putExtra(YouTubeActivity.KEY_VIDEO_ID, video.id);
                    startActivity(actIntent);
                }
            }
        });
        home();
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
            /*progressDialog=new ProgressDialog(getApplicationContext(),ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("please wait a moment...");
            progressDialog.setCancelable(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressDialog.dismiss();
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
                    VideoAdapter videoAdapter=new VideoAdapter(getApplicationContext());
                    listView.setAdapter(videoAdapter);
                    //webView.loadUrl(urls_list.get(0));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
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
}
