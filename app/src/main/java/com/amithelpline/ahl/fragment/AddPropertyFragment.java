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

public class AddPropertyFragment extends Fragment {

    EditText etName, etAddress, etAddress1, etCity, etSize, etRent;
    String Name, Address, Address1, City, Size, Rent, UserId;
    Button btnSave;
    SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addproperty, container, false);
        mSharedPreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);

        UserId = mSharedPreferences.getString(Const.UserId, "0");
        etName = (EditText) view.findViewById(R.id.etName);
        etAddress = (EditText) view.findViewById(R.id.etAddress);
        etAddress1 = (EditText) view.findViewById(R.id.etAddress1);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etSize = (EditText) view.findViewById(R.id.etSize);
        etRent = (EditText) view.findViewById(R.id.etRent);

        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = etName.getText().toString();
                Address = etAddress.getText().toString();
                Address1 = etAddress1.getText().toString();
                City = etCity.getText().toString();
                Size = etSize.getText().toString();
                Rent = etRent.getText().toString();
                SaveProperty();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add Property");
    }

    public void SaveProperty() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String PropertyParameter = "api_add_property.php?name=" + Name + "&address=" + Address1 + "&address1=" + Address1 + "&city=" + City + "&size=" + Size + "&rent=" + Rent + "&userid=" + UserId;
                    String AddPolicyUrl = ApiConfig.BASE_URL + PropertyParameter;
                    Log.e("Url", AddPolicyUrl);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {

                                Log.e("RESPONSE", jsonObject.toString());

                                if (jsonObject.getBoolean("status")) {
                                    Toast.makeText(getActivity(), "Property details saved", Toast.LENGTH_SHORT).show();
                                    ClearFields();
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
                    }, null, AddPolicyUrl);

                } catch (Exception e) {
                }
            }
        });


    }

    void ClearFields() {
        etName.setText("");
        etAddress.setText("");
        etAddress1.setText("");
        etCity.setText("");
        etSize.setText("");
        etRent.setText("");
    }
}
