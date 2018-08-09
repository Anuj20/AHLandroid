package com.amithelpline.ahl.fragment;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
 * Created by Neeraj on 28-08-2017.
 */

public class ProfileFragment extends Fragment {


    TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutMobile, inputLayoutPassword;
    EditText etName, etEmail, etMobile, etPassword;
    String Name, Email, Mobile, Password,UserId;
    Button btnUpdate;
    SharedPreferences sharedpreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Profile");
        inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout)  view.findViewById(R.id.input_layout_email);
        inputLayoutMobile = (TextInputLayout)  view.findViewById(R.id.input_layout_mobile);
        inputLayoutPassword = (TextInputLayout)  view.findViewById(R.id.input_layout_password);

        etName = (EditText)  view.findViewById(R.id.etName);
        etEmail = (EditText)  view.findViewById(R.id.etEmail);
        etMobile = (EditText)  view.findViewById(R.id.etMobile);
        etPassword = (EditText)  view.findViewById(R.id.etPassword);
        sharedpreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        UserId = sharedpreferences.getString(Const.UserId, "0");
        getProfile();
    }

    public void getProfile() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Parameter = "api_get_profile.php?userid=" + UserId;
                    String Url = ApiConfig.BASE_URL + Parameter;
                    Log.e("Url", Url);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {


                                Log.e("RESPONSE", jsonObject.toString());
                                if (jsonObject.getBoolean("status")) {
                                    JSONObject jsn = jsonObject.getJSONObject("data");
                                    etEmail.setText(jsn.getString("email"));
                                    etMobile.setText(jsn.getString("mobile"));
                                    etName.setText(jsn.getString("name"));
                                    etPassword.setText(jsn.getString("password"));

                                } else {
                                    Toast.makeText(getActivity(),"Something went wrong.Please try again", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(getActivity(),"Something went wrong.Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }, null, Url);

                } catch (Exception e) {

                }
            }
        });


    }

}
