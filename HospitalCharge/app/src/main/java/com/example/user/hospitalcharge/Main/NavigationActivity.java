package com.example.user.hospitalcharge.Main;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.Adapters.NavDrawerAdapter;
import com.example.user.hospitalcharge.NavigationFragments.AboutUsFragment;
import com.example.user.hospitalcharge.NavigationFragments.ContactUsFragment;
import com.example.user.hospitalcharge.NavigationFragments.HealthFragment;
import com.example.user.hospitalcharge.NavigationFragments.HomeFragment;
import com.example.user.hospitalcharge.NavigationFragments.LoginFragment;
import com.example.user.hospitalcharge.NavigationFragments.VideosFragment;
import com.example.user.hospitalcharge.NavigationFragments.ViewProfile2Fragment;
import com.example.user.hospitalcharge.NavigationFragments.ViewProfileFragment;
import com.example.user.hospitalcharge.R;
import com.example.user.hospitalcharge.Url.Urls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 12/8/2015.
 */
public class NavigationActivity extends ActionBarActivity{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView imageView1,backButton,logoButton;
    private TextView heading_textview;
    private ArrayList<String> states_list=new ArrayList<String>();
    private ArrayList<String> city_list=new ArrayList<String>();
    private ArrayList<String> diagnosisList=new ArrayList<String>();
    private ArrayList<String> diagnosis_Id_List=new ArrayList<String>();
    private ArrayList<String> procedureList=new ArrayList<String>();
    private ArrayList<String> procedure_Id_List=new ArrayList<String>();
    private ArrayList<String> physicianList=new ArrayList<String>();
    private ArrayList<String> physician_Id_List=new ArrayList<String>();
    SharedPreferences sharedpreferences;
    public String loginPreferences="loginPrefs";
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_drawer);
        String parameters = "api_key=bf45c093e542f057c123ae7d6";
        //new GetDiagList().execute(parameters);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.layout_listview_header, null);
        logoButton=(ImageView)header.findViewById(R.id.logoButton);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().hide();
        View view=findViewById(R.id.nav_header);
        imageView1 = (ImageView) view.findViewById(R.id.nav_imageview1);
        backButton=(ImageView) view.findViewById(R.id.imageview2_back_button);
        heading_textview=(TextView)view.findViewById(R.id.heading_textview);
        setBackVisibility(false);
        Bundle bundle1=getIntent().getExtras();
        sharedpreferences= getSharedPreferences(loginPreferences, Context.MODE_PRIVATE);
        states_list=bundle1.getStringArrayList("states_list");
        city_list=bundle1.getStringArrayList("city_list");
        diagnosisList=bundle1.getStringArrayList("diagnosis_list");
        procedureList=bundle1.getStringArrayList("procedure_list");
        physicianList=bundle1.getStringArrayList("physician_list");
        diagnosis_Id_List=bundle1.getStringArrayList("diagnosis_id");
        procedure_Id_List=bundle1.getStringArrayList("procedure_id");
        physician_Id_List=bundle1.getStringArrayList("physician_id");
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    return;
                }
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        NavDrawerAdapter adapter=new NavDrawerAdapter(this);
        mDrawerList.addHeaderView(header, null, false);
        mDrawerList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,null,0,0
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (bundle == null) {
            displayView(1);
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        logoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    //return;
                }
            }
        });
    }
    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if (mDrawerToggle.onOptionsItemSelected(item)) {
           return true;
       }
       switch (item.getItemId()) {
           default:
               return super.onOptionsItemSelected(item);
       }
   }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 1:
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("states_list",states_list);
                bundle.putStringArrayList("city_list",city_list);
                bundle.putStringArrayList("diagnosis_list",diagnosisList);
                bundle.putStringArrayList("procedure_list",procedureList);
                bundle.putStringArrayList("physician_list",physicianList);
                bundle.putStringArrayList("diagnosis_id",diagnosis_Id_List);
                bundle.putStringArrayList("procedure_id",procedure_Id_List);
                bundle.putStringArrayList("physician_id",physician_Id_List);
                fragment = new HomeFragment();
                fragment.setArguments(bundle);
                break;
            case 3:
                fragment = new AboutUsFragment();
                break;
            case 4:
                fragment = new HealthFragment();
                break;
            case 5:
                fragment = new VideosFragment();
                break;
            case 6:
                fragment = new ContactUsFragment();
                break;
            case 7:
                fragment = new LoginFragment();
                break;
            case 2:
                String email=sharedpreferences.getString("email","");
                String password=sharedpreferences.getString("password", "");
                String id=sharedpreferences.getString("id", "");
                String username=sharedpreferences.getString("username", "");
                String ref_id=sharedpreferences.getString("ref_id", "");
                String group_id=sharedpreferences.getString("group_id", "");
                //Toast.makeText(getApplicationContext(),group_id,Toast.LENGTH_SHORT).show();
                if(group_id.equals("2"))
                {
                    Bundle bundle1=new Bundle();
                    bundle1.putString("id",id);
                    bundle1.putString("username",username);
                    bundle1.putString("password",password);
                    bundle1.putString("email",email);
                    bundle1.putString("ref_id",ref_id);
                    bundle1.putString("group_id", group_id);
                    fragment=new ViewProfileFragment();
                    fragment.setArguments(bundle1);
                }if(group_id.equals("3"))
                {
                    Bundle bundle1=new Bundle();
                    bundle1.putString("id",id);
                    bundle1.putString("username",username);
                    bundle1.putString("password",password);
                    bundle1.putString("email",email);
                    bundle1.putString("ref_id",ref_id);
                    bundle1.putString("group_id", group_id);
                    fragment=new ViewProfile2Fragment();
                    fragment.setArguments(bundle1);
                }if(group_id.equals("")) {

                Toast.makeText(getApplicationContext(),"please login to view your profile.",Toast.LENGTH_SHORT).show();
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    //return;
                }

                }

                break;
            case 8:
                SharedPreferences.Editor editor1 = sharedpreferences.edit();
                editor1.putString("id","");
                editor1.putString("username","");
                editor1.putString("password","");
                editor1.putString("email","");
                editor1.putString("ref_id","");
                editor1.putString("group_id","");
                editor1.commit();
                Toast.makeText(getApplicationContext(),"Logout successfully.",Toast.LENGTH_SHORT).show();
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    //return;
                }
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setHeading(String text)
    {
        heading_textview.setText(text);
    }


    public void onBackPressed(){

        int var=2;
        int count = getFragmentManager().getBackStackEntryCount();
        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            //return;
        }
        //Toast.makeText(getApplicationContext(),String.valueOf(count),Toast.LENGTH_SHORT).show();
        if(count>0)
        {
            getFragmentManager().popBackStack();
        }
        if(count==0)
        {

            Bundle bundle=new Bundle();
            bundle.putStringArrayList("states_list",states_list);
            bundle.putStringArrayList("city_list",city_list);
            bundle.putStringArrayList("diagnosis_list",diagnosisList);
            bundle.putStringArrayList("procedure_list",procedureList);
            bundle.putStringArrayList("physician_list",physicianList);
            bundle.putStringArrayList("diagnosis_id",diagnosis_Id_List);
            bundle.putStringArrayList("procedure_id",procedure_Id_List);
            bundle.putStringArrayList("physician_id",physician_Id_List);
            Fragment fragment = new HomeFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
            /*var--;
            Toast.makeText(getApplicationContext(),String.valueOf(var),Toast.LENGTH_SHORT).show();*/
            /*if(var==0)
            {
                super.onBackPressed();
            }*/
        }


        /*else
        {
                new AlertDialog.Builder(this)

                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            //super.onBackPressed();
        }*/
    }
    public void setVisibility(boolean flag) {
        if (flag) {
            imageView1.setVisibility(View.VISIBLE);
        } else {
            imageView1.setVisibility(View.INVISIBLE);
        }
    }

    public void setBackVisibility(boolean flag)
    {
        if (flag) {
            backButton.setVisibility(View.VISIBLE);
        } else {
            backButton.setVisibility(View.INVISIBLE);
        }
    }

    public void showDrawer(boolean flag)
    {
        if(flag)
        {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }else
        {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

}
