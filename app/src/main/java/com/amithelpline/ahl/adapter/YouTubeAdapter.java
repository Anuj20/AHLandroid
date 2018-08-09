package com.amithelpline.ahl.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.activity.YoutubeActivity;
import com.amithelpline.ahl.model.Property;
import com.amithelpline.ahl.model.Youtube;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Neeraj on 29-08-2017.
 */

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Youtube> youtubeList;
    Youtube youtube;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        ImageView ivThumbnail;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    youtube=youtubeList.get(getAdapterPosition());
                    Intent i = new Intent(mContext, YoutubeActivity.class);
                    i.putExtra("videoid", youtube.getVideoId());
                    i.putExtra("title", youtube.getTitle());
                    mContext.startActivity(i);
                }
            });



        }
    }


    public YouTubeAdapter(Context mContext, List<Youtube> youtubeList) {
        this.mContext = mContext;
        this.youtubeList = youtubeList;
    }

    @Override
    public YouTubeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_youtube, parent, false);

        return new YouTubeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final YouTubeAdapter.MyViewHolder holder, int position) {
        youtube = youtubeList.get(position);

        Picasso.with(mContext)
                .load(youtube.getUrl()).fit().placeholder(R.drawable.ahllogo).error(R.drawable.ahllogo)
                .into(holder.ivThumbnail);

        holder.tvTitle.setText(youtube.getTitle());


    }

    @Override
    public int getItemCount() {
        return youtubeList.size();
    }
}
