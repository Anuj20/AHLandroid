package com.amithelpline.ahl.fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Neeraj on 14-03-2017.
 */

public class ApplyFragment extends Fragment {
    private RadioGroup radioGroup;
    int Apply = 1;
    Button btnApply;
    String Name, Email, UserId, Mobile;
    SharedPreferences mSharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Apply Now");
        btnApply = (Button) view.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedPreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
                UserId = mSharedPreferences.getString(Const.UserId, "0");
                Mobile = mSharedPreferences.getString(Const.Mobile, "0");
                Email = mSharedPreferences.getString(Const.Email, "0");
                Name = mSharedPreferences.getString(Const.Name, "0");
                if (Const.isNetworkAvailable(getActivity())) {
                    AddQuery();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.failed_connect_network), Toast.LENGTH_SHORT).show();

                }

            }
        });
        radioGroup = (RadioGroup) view.findViewById(R.id.rgApply);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.rdbLic) {
                    Apply = 1;
                } else if (checkedId == R.id.rdbMotor) {
                    Apply = 2;
                } else if (checkedId == R.id.rdbHealth) {
                    Apply = 3;
                } else if (checkedId == R.id.rdbMutual) {
                    Apply = 6;
                } else if (checkedId == R.id.rdbHomeLoan) {
                    Apply = 8;
                } else if (checkedId == R.id.rdbCarLoan) {
                    Apply = 9;
                } else if (checkedId == R.id.rdbTour) {
                    Apply = 10;
                } else if (checkedId == R.id.rdbIpo) {
                    Apply = 7;
                }

            }


        });

    }

    public void AddQuery() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Parameter = "api_add_query.php?name=" + Name + "&email=" + Email + "&mobile=" + Mobile + "&user_id=" + UserId + "&product_id=" + Apply;
                    String Url = ApiConfig.BASE_URL + Parameter;
                    Log.e("Url", Url);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {

                                Log.e("RESPONSE", jsonObject.toString());

                                if (jsonObject.getBoolean("status")) {
                                    Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getActivity(), jsonObject.getString("error_msg"), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(getActivity(), "Oops something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }, null, Url);

                } catch (Exception e) {

                }
            }
        });


    }

}
