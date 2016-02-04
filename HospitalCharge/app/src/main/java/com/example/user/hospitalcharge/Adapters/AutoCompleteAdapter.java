package com.example.user.hospitalcharge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.user.hospitalcharge.Holders.AutoHolder;
import com.example.user.hospitalcharge.Holders.NavHolder;
import com.example.user.hospitalcharge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29-01-2016.
 */
public class AutoCompleteAdapter  extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<String> diagnosisList=new ArrayList<String>();
    private ArrayList<String> diagnosisList_original=new ArrayList<String>();
    private ArrayList<String> diagnosisList_filter=new ArrayList<String>();
    private ItemFilter mFilter = new ItemFilter();

    public AutoCompleteAdapter(Context context,ArrayList<String> diagnosisList)
    {
        this.context=context;
        this.diagnosisList=diagnosisList;
        diagnosisList_original=diagnosisList;
        diagnosisList_filter=diagnosisList;
    }
    @Override
    public int getCount() {
        return diagnosisList_filter.size();
    }

    @Override
    public Object getItem(int i) {
        return diagnosisList_filter.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        AutoHolder holder=null;
        if(convertView==null)
        {
            holder=new AutoHolder();
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.layout_custom_spinner1_items,null);
            holder.textView=(TextView)convertView.findViewById(R.id.title);
            holder.textView.setText(diagnosisList_filter.get(i));
            convertView.setTag(holder);
        }
        else {
            holder=(AutoHolder)convertView.getTag();
            holder.textView.setText(diagnosisList_filter.get(i));
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }







    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            Filter.FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {


                results.values = diagnosisList_original;
                results.count = diagnosisList_original.size();
            }
            else {
                final List<String> list = diagnosisList_original;

                int count = list.size();
                final ArrayList<String> nlist = new ArrayList<String>(count);

                String filterableString;

                for (int i = 0; i < count; i++) {

                    filterableString = list.get(i);

                    if (filterableString.toLowerCase().startsWith(filterString)) {
                        nlist.add(filterableString);
                    }
                }

                results.values = nlist;
                results.count = nlist.size();
            }


            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.count == 0) {
                notifyDataSetInvalidated();
            } else {
                diagnosisList_filter = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        }

    }
}
