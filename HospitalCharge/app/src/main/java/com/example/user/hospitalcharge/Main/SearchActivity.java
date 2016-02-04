package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.user.hospitalcharge.Adapters.SearchAdapter;
import com.example.user.hospitalcharge.R;

/**
 * Created by user on 28-12-2015.
 */
public class SearchActivity extends Activity {

    private ListView listView;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_search_custom);
        listView=(ListView)findViewById(R.id.search_listview);
        //SearchAdapter searchAdapter=new SearchAdapter(this);
        //listView.setAdapter(searchAdapter);
    }
}
