package com.example.user.hospitalcharge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.hospitalcharge.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 23-12-2015.
 */
public class VideoAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener{


    private Context mContext;
    private Map<View, YouTubeThumbnailLoader> mLoaders;
    private int[] images={R.drawable.screen1,R.drawable.screen2,R.drawable.screen3,R.drawable.screen4,
            R.drawable.screen5,R.drawable.screen6};

    public VideoAdapter(final Context context) {
        mContext = context;
        mLoaders = new HashMap<>();
    }

    @Override
    public int getCount() {
        return YouTubeContent.ITEMS.size();
    }

    @Override
    public Object getItem(int position) {
        return YouTubeContent.ITEMS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View currentRow = convertView;
        VideoHolder holder;

        //The item at the current position
        final YouTubeContent.YouTubeVideo item = YouTubeContent.ITEMS.get(position);

        if (currentRow == null) {
            //Create the row
            final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            currentRow = inflater.inflate(R.layout.layout_videos_custom_list, parent, false);

            //Create the video holder
            holder = new VideoHolder();

            //Set the title
            holder.title = (TextView) currentRow.findViewById(R.id.textView_title);
            holder.imageView=(ImageView)currentRow.findViewById(R.id.imageview1);
            holder.title.setText(item.title);
            holder.imageView.setImageResource(images[position]);
            //Initialise the thumbnail
           /* holder.thumb = (YouTubeThumbnailView) currentRow.findViewById(R.id.thumb);

            holder.thumb.setTag(item.id);
            holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);*/
            currentRow.setTag(holder);
        } else {
            //Create it again
            holder = (VideoHolder) currentRow.getTag();
            //final YouTubeThumbnailLoader loader = mLoaders.get(holder.thumb);

            //Set the title
            if (item != null) {
                holder.title.setText(item.title);
                holder.imageView.setImageResource(images[position]);
                /*if (loader == null) {
                    //Loader is currently initialising
                    holder.thumb.setTag(item.id);
                    holder.thumb.initialize(mContext.getString(R.string.DEVELOPER_KEY), this);

                } else {
                    //The loader is already initialised
                    loader.setVideo(item.id);

                }*/
            }
        }
        return currentRow;
    }


    @Override
    public void onInitializationSuccess(YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
        mLoaders.put(view, loader);
        loader.setVideo((String) view.getTag());
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView thumbnailView, YouTubeInitializationResult errorReason) {
        final String errorMessage = errorReason.toString();
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }


    static class VideoHolder {
        YouTubeThumbnailView thumb;
        TextView title;
        ImageView imageView;
    }


}
