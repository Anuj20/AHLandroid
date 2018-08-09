package com.amithelpline.ahl.fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.amithelpline.ahl.adapter.IpoAdapter;
import com.amithelpline.ahl.adapter.PollutionAdapter;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.model.Ipo;
import com.amithelpline.ahl.model.Pollution;
import com.amithelpline.ahl.utils.Const;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Neeraj on 02-06-2017.
 */

public class PollutionListFragment extends Fragment {
    RecyclerView recyclerView;
    private PollutionAdapter pollutionAdapter;
    private List<Pollution> pollutionList;
    Pollution pollution;
    String UserId;
    AVLoadingIndicatorView avLoadingIndicatorView;
    SharedPreferences mSharedPreferences;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Pollution Certificates");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pollutionlist, container, false);

        mSharedPreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        UserId = mSharedPreferences.getString(Const.UserId, "0");

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.loader);
        pollutionList = new ArrayList<>();
        pollutionAdapter = new PollutionAdapter(getContext(), pollutionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pollutionAdapter);


        if (Const.isNetworkAvailable(getActivity())) {
            preparePollutionData();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.failed_connect_network), Toast.LENGTH_SHORT).show();

        }
        return view;
    }

    void preparePollutionData() {
        avLoadingIndicatorView.show();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Parameter = "api_get_pollution.php?user_id=" + UserId;
                    String Url = ApiConfig.BASE_URL + Parameter;
                    Log.e("Url", Url);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {


                                Log.e("RESPONSE", jsonObject.toString());

                                if (jsonObject.getBoolean("status")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsn = jsonArray.getJSONObject(i);
                                        pollution = new Pollution(jsn.getString("vehicleno"),
                                                jsn.getString("duedate"),jsn.getString("desc"));

                                        pollutionList.add(pollution);
                                    }

                                    pollutionAdapter.notifyDataSetChanged();
                                    avLoadingIndicatorView.hide();

                                } else {
                                    avLoadingIndicatorView.hide();
                                    Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(String message) {
                            avLoadingIndicatorView.hide();
                            Toast.makeText(getActivity(), "Oops something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }, null, Url);

                } catch (Exception e) {

                }
            }
        });
    }
}
