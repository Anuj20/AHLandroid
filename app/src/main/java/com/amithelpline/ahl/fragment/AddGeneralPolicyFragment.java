package com.amithelpline.ahl.fragment;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.utils.Const;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Neeraj on 22-03-2017.
 */

public class AddGeneralPolicyFragment extends Fragment {

    TextInputLayout inputLayoutPolicyNo, inputLayoutPremiumAmt, inputLayoutPreDate, inputLayoutVehicleNo;
    EditText etPolicyNo, etPremiumAmt, etPremiumDueDate, etVehicleNumber;
    MaterialBetterSpinner spnVehicleType;
    String PolicyNo, PremiumAmt, PremiumDate, UserId, PolicyType, VehicleNumber, PolicyTypeId;
    Button btnAddPolicy;
    Calendar myCalendar;
    SharedPreferences mSharedPreferences;

    String[] SPINNER_DATA = {"Motor Insurance", "Health Insurance", "Personal Accident"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addgeneral_policy, container, false);
        mSharedPreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);

        UserId = mSharedPreferences.getString(Const.UserId, "0");
        spnVehicleType = (MaterialBetterSpinner) view.findViewById(R.id.material_spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, SPINNER_DATA);

        spnVehicleType.setAdapter(adapter);

        inputLayoutPolicyNo = (TextInputLayout) view.findViewById(R.id.input_layout_PolicyNo);
        inputLayoutPremiumAmt = (TextInputLayout) view.findViewById(R.id.input_layout_PremiumAmt);
        inputLayoutPreDate = (TextInputLayout) view.findViewById(R.id.input_layout_DueDate);
        // inputLayoutVehicleType = (TextInputLayout) view.findViewById(R.id.input_layout_Vehicle);
        inputLayoutVehicleNo = (TextInputLayout) view.findViewById(R.id.input_layout_VehicleNo);

        etPolicyNo = (EditText) view.findViewById(R.id.etPolicyNo);
        etPremiumAmt = (EditText) view.findViewById(R.id.etPremiumAmount);
        etPremiumDueDate = (EditText) view.findViewById(R.id.etPremiumDueDate);
        // etVehicleType = (EditText) view.findViewById(R.id.etVehicle);

        etVehicleNumber = (EditText) view.findViewById(R.id.etVehicleNo);


        btnAddPolicy = (Button) view.findViewById(R.id.btnSave);
        btnAddPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                String VehNo = etVehicleNumber.getText().toString().toUpperCase();
                PolicyType = spnVehicleType.getText().toString();
                VehicleNumber = VehNo.replaceAll(" ", "");


                if (!validateVehicle()) {
                    return;
                }
                if (!validateVehicleNumber()) {
                    return;
                }

                if (TextUtils.equals(PolicyType, "Motor Insurance")) {
                    PolicyTypeId = "1";
                } else if (TextUtils.equals(PolicyType, "Health Insurance")) {
                    PolicyTypeId = "2";
                } else if (TextUtils.equals(PolicyType, "Personal Accident")) {
                    PolicyTypeId = "3";
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
        etPremiumDueDate.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        spnVehicleType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etVehicleNumber.setHint("");
                if (TextUtils.equals("Health Insurance", spnVehicleType.getText().toString()) || TextUtils.equals("Personal Accident", spnVehicleType.getText().toString()) || TextUtils.equals("Misc. Insurance", spnVehicleType.getText().toString())) {
                    etVehicleNumber.setHint("Name");
                } else {
                    etVehicleNumber.setHint("Vehicle Number");
                }
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
        getActivity().setTitle("Add General Policy");
    }


    public void AddPolicy() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String PolicyParameter = "api_add_general_policy.php?&vehicleno=" + VehicleNumber + "&user_id=" + UserId + "&policy_no=" + PolicyNo + "&premium_amount=" + PremiumAmt + "&premium_due_date=" + PremiumDate + "&policy_type=" + PolicyTypeId;
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

        etPremiumDueDate.setText("");
        etPremiumAmt.setText("");
        etPolicyNo.setText("");
        etVehicleNumber.setText("");
        spnVehicleType.setText("");
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

    private boolean validateVehicle() {
        if (PolicyType.trim().isEmpty()) {
            spnVehicleType.setError(getString(R.string.err_msg_select_vehicle));
            requestFocus(spnVehicleType);
            return false;
        }

        return true;
    }

    private boolean validateVehicleNumber() {
        if (VehicleNumber.trim().isEmpty()) {
            inputLayoutVehicleNo.setError(getString(R.string.err_msg_vehicle_number));
            requestFocus(etVehicleNumber);
            return false;
        } else {
            inputLayoutVehicleNo.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}