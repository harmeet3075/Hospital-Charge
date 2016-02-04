package com.example.user.hospitalcharge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.hospitalcharge.Holders.NavHolder;
import com.example.user.hospitalcharge.R;

/**
 * Created by user on 12/8/2015.
 */
public class NavDrawerAdapter extends BaseAdapter {
    private Context context;
    private String[] names={"SEARCH","VIEW PROFILE","ABOUT US","HEALTHCARE PROVIDERS","VIDEO GALLERY","CONTACT US","LOGIN","LOGOUT"};
    private Integer[] logos={R.drawable.search_icon,R.drawable.about_us_icon,
    R.drawable.health_care_icon,R.drawable.videos_icon,R.drawable.contact_us_icon,
            R.drawable.login_in_icon};
    public NavDrawerAdapter(Context context)
    {
        this.context=context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        NavHolder holder=null;
        if(convertView==null)
        {
            holder=new NavHolder();
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.layout_custom_nav_list,null);
            //holder.imageView=(ImageView)convertView.findViewById(R.id.icon);
            holder.textView=(TextView)convertView.findViewById(R.id.title);
            holder.textView.setText(names[position]);
            //holder.imageView.setImageResource(logos[position]);
            convertView.setTag(holder);
        }
        else {
            holder=(NavHolder)convertView.getTag();
            holder.textView.setText(names[position]);
            //holder.imageView.setImageResource(logos[position]);
        }
        return convertView;
    }
}
