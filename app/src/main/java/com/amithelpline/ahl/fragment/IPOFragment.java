package com.amithelpline.ahl.fragment;

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
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.model.Ipo;
import com.amithelpline.ahl.utils.Const;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 02-06-2017.
 */

public class IPOFragment extends Fragment {
    RecyclerView recyclerView;
    private IpoAdapter ipoAdapter;
    private List<Ipo> ipoList;
    Ipo ipo;
    String UserId;
    AVLoadingIndicatorView avLoadingIndicatorView;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("IPO");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ipo, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.loader);
        ipoList = new ArrayList<>();
        ipoAdapter = new IpoAdapter(getContext(), ipoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ipoAdapter);


        if (Const.isNetworkAvailable(getActivity())) {
            prepareIPOData();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.failed_connect_network), Toast.LENGTH_SHORT).show();

        }
        return view;
    }

    void prepareIPOData() {
        avLoadingIndicatorView.show();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Parameter = "api_get_ipo.php";
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
                                        ipo = new Ipo(jsn.getString("name"),
                                                jsn.getString("minamount"), jsn.getString("opendate"), jsn.getString("lastdate"),
                                                jsn.getString("priceband"), jsn.getString("lotsize"));

                                        ipoList.add(ipo);
                                    }

                                    ipoAdapter.notifyDataSetChanged();
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
