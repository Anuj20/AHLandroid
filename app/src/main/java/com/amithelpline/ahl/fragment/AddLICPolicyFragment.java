package com.amithelpline.ahl.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.activity.MainActivity;
import com.amithelpline.ahl.activity.RegisterActivity;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.utils.Const;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.cert.PolicyNode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alisha on 14-10-18.
 */

public class AddLICPolicyFragment extends Fragment {

    TextInputLayout inputLayoutName, inputLayoutPolicyNo, inputLayoutPremiumAmt, inputLayoutPreDate, getInputLayoutStartDate;
    RadioGroup rdgPolicyMode;
    RadioButton rdbYearly, rdbHalfYearly, rdbQtr;
    EditText etName, etPolicyNo, etPremiumAmt, etPremiumDueDate,etPremiumStartDate;
    String Name, PolicyNo, PremiumAmt, PremiumDate, UserId, PremiumMode;
    Button btnAddPolicy;
    Calendar myCalendar;
    SharedPreferences mSharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addpolicy, container, false);
        mSharedPreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);

        UserId = mSharedPreferences.getString(Const.UserId, "0");

        inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_Name);
        inputLayoutPolicyNo = (TextInputLayout) view.findViewById(R.id.input_layout_PolicyNo);
        inputLayoutPremiumAmt = (TextInputLayout) view.findViewById(R.id.input_layout_PremiumAmt);
        inputLayoutPreDate = (TextInputLayout) view.findViewById(R.id.input_layout_DueDate);
         getInputLayoutStartDate= (TextInputLayout) view.findViewById(R.id.input_layout_StartDate);

        etName = (EditText) view.findViewById(R.id.etName);
        etPolicyNo = (EditText) view.findViewById(R.id.etPolicyNo);
        etPremiumAmt = (EditText) view.findViewById(R.id.etPremiumAmount);
        etPremiumDueDate = (EditText) view.findViewById(R.id.etPremiumDueDate);
        etPremiumStartDate = (EditText)view.findViewById(R.id.input_layout_StartDate);
        // etVehicleType = (EditText) view.findViewById(R.id.etVehicle);

        rdbYearly = (RadioButton) view.findViewById(R.id.rdbYearly);
        rdbHalfYearly = (RadioButton) view.findViewById(R.id.rdbHalfYearly);
        rdbQtr = (RadioButton) view.findViewById(R.id.rdbQtr);

        rdgPolicyMode = (RadioGroup) view.findViewById(R.id.rdgPolicyMode);
        rdgPolicyMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if (i == R.id.rdbQtr) {
                    PremiumMode = "3";
                } else if (i == R.id.rdbHalfYearly) {
                    PremiumMode = "6";
                } else if (i == R.id.rdbYearly) {
                    PremiumMode = "12";
                }


            }
        });
        PremiumMode = "12";

        etName.setVisibility(EditText.VISIBLE);
        inputLayoutName.setVisibility(TextInputLayout.VISIBLE);


        btnAddPolicy = (Button) view.findViewById(R.id.btnSave);
        btnAddPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = etName.getText().toString();
                PolicyNo = etPolicyNo.getText().toString();
                PremiumDate = etPremiumDueDate.getText().toString();
                PremiumAmt = etPremiumAmt.getText().toString();

                Log.e("PolicyNo", PolicyNo);

                if (!validatePolicyNo()) {
                    return;
                }
                if (!validatePremiumAmt()) {
                    return;
                }
                if (!validatePremiumDueDate()) {
                    return;
                }

                if (!validateName()) {
                    return;

                }


                AddPolicy();

            }
        });

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        etPremiumDueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        return view;

    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etPremiumDueDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Policy");
    }


    public void AddPolicy() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String PolicyParameter = "api_add_lic_policy.php?name=" + Name + "&user_id=" + UserId + "&policy_no=" + PolicyNo + "&premium_amount=" + PremiumAmt + "&premium_due_date=" + PremiumDate + "&premium_mode=" + PremiumMode;
                    String AddPolicyUrl = ApiConfig.BASE_URL + PolicyParameter;
                    Log.e("Url", AddPolicyUrl);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {

                                Log.e("RESPONSE", jsonObject.toString());

                                if (jsonObject.getBoolean("status")) {
                                    Toast.makeText(getActivity(), "Policy details saved", Toast.LENGTH_SHORT).show();
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
        etPremiumDueDate.setText("");
        etPremiumAmt.setText("");
        etPolicyNo.setText("");

    }

    private boolean validateName() {
        if (Name.trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(etName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePolicyNo() {
        if (PolicyNo.trim().isEmpty()) {
            inputLayoutPolicyNo.setError(getString(R.string.err_msg_policy_no));
            requestFocus(etPolicyNo);
            return false;
        } else {
            inputLayoutPolicyNo.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePremiumAmt() {
        if (PremiumAmt.trim().isEmpty()) {
            inputLayoutPremiumAmt.setError(getString(R.string.err_msg_policy_amt));
            requestFocus(etPremiumAmt);
            return false;
        } else {
            inputLayoutPremiumAmt.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePremiumDueDate() {
        if (PremiumDate.trim().isEmpty()) {
            inputLayoutPreDate.setError(getString(R.string.err_msg_policy_due_date));
            requestFocus(etPremiumDueDate);
            return false;
        } else {
            inputLayoutPreDate.setErrorEnabled(false);
        }

        return true;
    }




    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}