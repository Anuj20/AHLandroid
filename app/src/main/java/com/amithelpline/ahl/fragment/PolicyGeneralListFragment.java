package com.amithelpline.ahl.fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.adapter.PolicyAdapter;
import com.amithelpline.ahl.adapter.PolicyGeneralAdapter;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.model.Policy;
import com.amithelpline.ahl.model.PolicyGeneral;
import com.amithelpline.ahl.utils.Const;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class PolicyGeneralListFragment extends Fragment {
    RecyclerView recyclerView;
    private PolicyGeneralAdapter policyAdapter;
    private List<PolicyGeneral> policyList;
    PolicyGeneral policy;
    String UserId;
    AVLoadingIndicatorView avLoadingIndicatorView;
    SharedPreferences mSharedPreferences;
    EditText etSearch;
    String Mobile;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("General Policies");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_policylist, container, false);

        mSharedPreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        UserId = mSharedPreferences.getString(Const.UserId, "0");
        Mobile = mSharedPreferences.getString(Const.Mobile, "0");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.loader);
        policyList = new ArrayList<>();
        policyAdapter = new PolicyGeneralAdapter(getContext(), policyList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(policyAdapter);
        etSearch = (EditText) view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    policyList.clear();
                    preparePolicyData();
                } else {
                    policyAdapter.getFilter().filter(s.toString());
                }

            }
        });


        preparePolicyData();
        return view;
    }

    void preparePolicyData() {
        avLoadingIndicatorView.show();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String RegParmeter = "api_get_general_policies.php?user_id=" + UserId;
                    //String RegParmeter = "api_get_general_policies.php?mobile=" + Mobile;
                    String PolicyUrl = ApiConfig.BASE_URL + RegParmeter;
                    Log.e("Url", PolicyUrl);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {


                                Log.e("RESPONSE", jsonObject.toString());

                                if (jsonObject.getBoolean("status")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsn = jsonArray.getJSONObject(i);

                                        policy = new PolicyGeneral(jsn.getString("policy_id"),
                                                jsn.getString("vehicleno"), jsn.getString("premium_duedate"),
                                                jsn.getString("premium_amount"), jsn.getString("policy_no"), jsn.getString("policy_type"));
                                        Log.e("here","here");
                                        policyList.add(policy);
                                    }

                                    policyAdapter.notifyDataSetChanged();
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
                    }, null, PolicyUrl);

                } catch (Exception e) {

                }
            }
        });
    }
}
