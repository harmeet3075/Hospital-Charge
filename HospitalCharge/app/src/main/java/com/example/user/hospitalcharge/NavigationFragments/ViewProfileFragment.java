package com.example.user.hospitalcharge.NavigationFragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.hospitalcharge.Main.LegalDisclaimerActivity;
import com.example.user.hospitalcharge.Main.NavigationActivity;
import com.example.user.hospitalcharge.R;

/**
 * Created by user on 27-01-2016.
 */
public class ViewProfileFragment extends Fragment {

    private TextView usernameTextview,emailTextview;
    private Button dashBoardButton,editProfileButton,passButton,diagButton;
    NavigationActivity navigationActivity;
    private String ref_id,group_id;
    private ImageView imageView1;

    public View onCreateView(LayoutInflater layoutInflater,ViewGroup viewGroup,Bundle bundle)
    {
        View view=layoutInflater.inflate(R.layout.layout_welcome_note,viewGroup,false);
        View includedView=view.findViewById(R.id.included_dash);
        navigationActivity=(NavigationActivity)getActivity();
        navigationActivity.setHeading("MY ACCOUNT");
        navigationActivity.setVisibility(false);
        navigationActivity.setBackVisibility(true);
        navigationActivity.showDrawer(false);
        usernameTextview=(TextView)view.findViewById(R.id.username_textview2);
        emailTextview=(TextView)view.findViewById(R.id.username_textview3);
        dashBoardButton=(Button)includedView.findViewById(R.id.button1);
        editProfileButton=(Button)includedView.findViewById(R.id.button2);
        imageView1=(ImageView)includedView.findViewById(R.id.terms_conditions);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LegalDisclaimerActivity.class);
                startActivity(intent);
            }
        });

        diagButton=(Button)includedView.findViewById(R.id.button4);
        passButton=(Button)includedView.findViewById(R.id.button3);
        Bundle bundle1=getArguments();
        usernameTextview.setText(bundle1.getString("username"));
        emailTextview.setText(bundle1.getString("email"));
        ref_id=bundle1.getString("ref_id");
        group_id=bundle1.getString("group_id");
        dashBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard();
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
        diagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diagnosis();
            }
        });
        dashBoardButton.setOnTouchListener(new View.OnTouchListener() {
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
        editProfileButton.setOnTouchListener(new View.OnTouchListener() {
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
        passButton.setOnTouchListener(new View.OnTouchListener() {
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
        diagButton.setOnTouchListener(new View.OnTouchListener() {
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
        return view;
    }


    private void dashboard()
    {
        PhyDashboard nextFrag= new PhyDashboard();
        Bundle bundle=new Bundle();
        bundle.putString("ref_id",ref_id);
        bundle.putString("group_id",group_id);
        bundle.putString("username",usernameTextview.getText().toString());
        bundle.putString("email",emailTextview.getText().toString());
        nextFrag.setArguments(bundle);
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.frame_container,nextFrag)
                .addToBackStack(null)
                .commit();
    }

    private void editProfile()
    {
        PhysicianEditProfile nextFrag= new PhysicianEditProfile();
        Bundle bundle=new Bundle();
        bundle.putString("ref_id",ref_id);
        bundle.putString("group_id",group_id);
        nextFrag.setArguments(bundle);
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.frame_container,nextFrag)
                .addToBackStack(null)
                .commit();
    }

    private void changePassword()
    {
        PhyChangePassword nextFrag= new PhyChangePassword();
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.frame_container,nextFrag)
                .addToBackStack(null)
                .commit();
    }

    private void diagnosis()
    {
        PhyDiagnosis nextFrag= new PhyDiagnosis();
        Bundle bundle=new Bundle();
        bundle.putString("ref_id",ref_id);
        bundle.putString("group_id",group_id);
        nextFrag.setArguments(bundle);
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.frame_container,nextFrag)
                .addToBackStack(null)
                .commit();
    }

    public void onStop()
    {
        super.onStop();
        navigationActivity.showDrawer(true);
    }
}
