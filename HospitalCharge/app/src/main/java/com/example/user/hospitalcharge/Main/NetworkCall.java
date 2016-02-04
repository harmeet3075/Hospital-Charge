package com.example.user.hospitalcharge.Main;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 13-01-2016.
 */
public class NetworkCall {

    public static String makePostRequest(ArrayList<NameValuePair> paramsList,String url) {

        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost(url);
        //Post Data
        List<NameValuePair> nameValuePair = paramsList;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder=new StringBuilder();
        try {
            HttpResponse response = httpClient.execute(httpPost);
            Log.d("Http Post Response:", response.toString());
            StatusLine statusLine=response.getStatusLine();
            int statusCode=statusLine.getStatusCode();
            if(statusCode==200)
            {
                HttpEntity httpEntity=response.getEntity();
                BufferedInputStream bufferedInputStream=new BufferedInputStream(httpEntity.getContent());
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(bufferedInputStream));
                String data=null;
                while((data=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(data);
                }
            }
        } catch (Exception e) {
            // Log exception
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
