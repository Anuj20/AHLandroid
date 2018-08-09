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
import com.amithelpline.ahl.adapter.AppointmentAdapter;
import com.amithelpline.ahl.adapter.CurrencyAdapter;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.model.Appointment;
import com.amithelpline.ahl.model.Currency;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neeraj on 14-03-2017.
 */

public class CurrencyFragment extends Fragment {
    RecyclerView recyclerView;
    CurrencyAdapter currencyAdapter;
    List<Currency> currencyList;
    Currency currency;
    AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.loader);
        currencyList = new ArrayList<>();
        currencyAdapter = new CurrencyAdapter(getContext(), currencyList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(currencyAdapter);
        getCurency();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Currency Rates");
    }

    public void getCurency() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //  String PolicyParameter = "api_add_policy.php?name=" + Name + "&vehicle=" + Vehicle + "&vehicleno=" + VehicleNumber + "&user_id=" + UserId + "&policy_no=" + PolicyNo + "&premium_amount=" + PremiumAmt + "&premium_due_date=" + PremiumDate + "&premium_mode=" + PremiumMode + "&policy_type=" + PolicyType;
                    String AddPolicyUrl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDINR%22,%22AUDINR%22,%22EURINR%22,%22NZDINR%22,%22GBPINR%22,%22CADINR%22,%22AEDINR%22,%22THBINR%22)&env=store://datatables.org/alltableswithkeys&format=json";
                    Log.e("Url", AddPolicyUrl);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {

                                Log.e("RESPONSE", jsonObject.toString());
                                JSONObject jsn = jsonObject.getJSONObject("query");
                                JSONObject jsn2 = jsn.getJSONObject("results");
                                JSONArray jsonArray = jsn2.getJSONArray("rate");
                                Log.e("Data",""+jsonArray.length());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsn3=jsonArray.getJSONObject(i);
                                    Log.e("Array",jsn3.getString("Name"));
                                    currency = new Currency(jsn3.getString("Name"),
                                            jsn3.getString("Rate"));
                                    currencyList.add(currency);
                                }

                                currencyAdapter.notifyDataSetChanged();
                                avLoadingIndicatorView.hide();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(getActivity(), "Oops something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }, null, AddPolicyUrl);

                } catch (Exception e) {

                }
            }
        });


    }
}
