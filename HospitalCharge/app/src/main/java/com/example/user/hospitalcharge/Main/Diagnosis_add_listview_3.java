package com.example.user.hospitalcharge.Main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.user.hospitalcharge.Adapters.PhysicianSearch;
import com.example.user.hospitalcharge.Model.Physician.AdditionalData;
import com.example.user.hospitalcharge.R;

import java.util.ArrayList;

/**
 * Created by user on 25-01-2016.
 */
public class Diagnosis_add_listview_3  extends Activity {


    private ListView listView;
    private ImageView backButton;
    private PhysicianSearch physicianSearch;
    private ArrayList<com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures> physiciansProcedureslist_physician= new ArrayList<com.example.user.hospitalcharge.Model.Physician.PhysiciansProcedures>();
    private ArrayList<com.example.user.hospitalcharge.Model.Physician.Procedures> procedureslist_physician= new ArrayList<com.example.user.hospitalcharge.Model.Physician.Procedures>();
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_procedure_list_final);
        View view=findViewById(R.id.header);
        backButton=(ImageView)view.findViewById(R.id.imageview2_back_button);
        listView=(ListView) findViewById(R.id.list);
        Bundle bundle1=getIntent().getExtras();
        physiciansProcedureslist_physician=bundle1.getParcelableArrayList("physiciansProcedureslist_physician");
        procedureslist_physician=bundle1.getParcelableArrayList("procedureslist_physician");
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        physicianSearch=new PhysicianSearch(this,procedureslist_physician,physiciansProcedureslist_physician);
        listView.setAdapter(physicianSearch);

    }
}
