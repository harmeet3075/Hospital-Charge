package com.example.user.hospitalcharge.NavigationFragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.hospitalcharge.Main.LegalDisclaimerActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.R;

/**
 * Created by user on 21-12-2015.
 */
public class PhyDashboard extends Fragment {

    private TextView usernameTextview,emailTextview;
    private EditText nameEdit,addressEdit,emailEdit,phoneEdit,ZipEdit;
    private ImageView imageView1;
    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_phy_dashboard,viewGroup,false);
        NavigationActivity navigationActivity=(NavigationActivity)getActivity();

        navigationActivity.setHeading("DASHBOARD");
        navigationActivity.setVisibility(false);
        navigationActivity.showDrawer(false);
        navigationActivity.setBackVisibility(true);
        usernameTextview=(TextView)view.findViewById(R.id.username_textview2);
        emailTextview=(TextView)view.findViewById(R.id.username_textview3);
        nameEdit=(EditText)view.findViewById(R.id.editext1);
        imageView1=(ImageView)view.findViewById(R.id.terms_conditions);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });
        emailEdit=(EditText)view.findViewById(R.id.editext3);
        Bundle bundle1=getArguments();
        usernameTextview.setText(bundle1.getString("username"));
        emailTextview.setText(bundle1.getString("email"));
        nameEdit.setText(bundle1.getString("username"));
        emailEdit.setText(bundle1.getString("email"));
        return view;
    }
}
