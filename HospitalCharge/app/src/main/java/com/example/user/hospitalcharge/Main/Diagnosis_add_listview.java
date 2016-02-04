package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.user.hospitalcharge.Adapters.AdditionalSearch;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.AddServiciesData;
import com.example.user.hospitalcharge.R;

import java.util.ArrayList;

/**
 * Created by user on 25-01-2016.
 */
public class Diagnosis_add_listview extends Activity {

    private ListView listView;
    private ImageView backButton;
    private AdditionalSearch additionalSearch;
    private ArrayList<AddServiciesData> addServiciesDatas;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_diag_add_listview);
        View view=findViewById(R.id.header);
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        listView=(ListView) findViewById(R.id.list);
        Bundle bundle1=getIntent().getExtras();
        addServiciesDatas=bundle1.getParcelableArrayList("list");
        additionalSearch=new AdditionalSearch(this,addServiciesDatas);
        listView.setAdapter(additionalSearch);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
