package com.amithelpline.ahl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by Neeraj on 18-08-2017.
 */

public class YoutubeFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView youTubeView;
    private static final int RECOVERY_REQUEST = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youtube, container, false);
        youTubeView = (YouTubePlayerView) view.findViewById(R.id.youtube_view);
        youTubeView.initialize("AIzaSyCjDrz6lE-E_UHm2dKHYBtuRmrB-djT__E", this);
        return view;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

           youTubePlayer.cueVideo("fhWaJi1Hsfo");

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
