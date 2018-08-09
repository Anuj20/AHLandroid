package com.amithelpline.ahl.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.adapter.PolicyAdapter;
import com.amithelpline.ahl.adapter.YouTubeAdapter;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.model.Policy;
import com.amithelpline.ahl.model.Youtube;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 29-08-2017.
 */

public class YoutubeListFragment extends Fragment {

    RecyclerView recyclerView;
    private YouTubeAdapter youTubeAdapter;
    private List<Youtube> youtubeList;
    Youtube youtube;
     AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youtubelist, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.loader);
        youtubeList = new ArrayList<>();
        youTubeAdapter = new YouTubeAdapter(getContext(), youtubeList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(youTubeAdapter);
        prepareYoutubeData();
        return view;
    }
    void prepareYoutubeData() {
        avLoadingIndicatorView.show();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    String PolicyUrl = ApiConfig.GET_YOUTUBE_VIDEOS;
                    Log.e("Url", PolicyUrl);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {


                                Log.e("RESPONSE", jsonObject.toString());


                                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsn = jsonArray.getJSONObject(i);
                                        JSONObject jsnSnippet=jsn.getJSONObject("snippet");
                                        JSONObject jsnThumb=jsnSnippet.getJSONObject("thumbnails");
                                        JSONObject jsnThumbdefault=jsnThumb.getJSONObject("default");
                                        JSONObject jsnResourceId=jsnSnippet.getJSONObject("resourceId");


                                        youtube = new Youtube(jsnSnippet.getString("title"), jsnThumbdefault.getString("url"), jsnResourceId.getString("videoId"));

                                        youtubeList.add(youtube);
                                    }

                                    youTubeAdapter.notifyDataSetChanged();
                                    avLoadingIndicatorView.hide();
/*
                                } else {
                                    avLoadingIndicatorView.hide();
                                    Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                                }*/


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(String message) {
                            avLoadingIndicatorView.hide();
                            Toast.makeText(getActivity(), "Oops something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }, null, PolicyUrl);

                } catch (Exception e) {

                }
            }
        });
    }
}

