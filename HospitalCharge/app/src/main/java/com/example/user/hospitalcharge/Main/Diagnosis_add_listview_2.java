package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.user.hospitalcharge.Adapters.AdditionalSearch;
import com.example.user.hospitalcharge.Model.Diagnosis_AdditionalServicies.AddServiciesData;
import com.example.user.hospitalcharge.Model.Physician.AdditionalData;
import com.example.user.hospitalcharge.R;

import java.util.ArrayList;

/**
 * Created by user on 25-01-2016.
 */
public class Diagnosis_add_listview_2 extends Activity{


    private ListView listView;
    private ImageView backButton;
    private ArrayList<AdditionalData> additionalDatas=null;
    private com.example.user.hospitalcharge.Adapters.ProcedureAdditional procedureAdditional;
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_procedure_list_final);
        View view=findViewById(R.id.header);
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        listView=(ListView) findViewById(R.id.list);
        Bundle bundle1=getIntent().getExtras();
        additionalDatas=bundle1.getParcelableArrayList("list");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        procedureAdditional=new com.example.user.hospitalcharge.Adapters.ProcedureAdditional(this,additionalDatas);
        listView.setAdapter(procedureAdditional);

    }
}
