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

public class AddPollutionFragment extends Fragment {

    TextInputLayout inputLayoutDueDate, inputLayoutVehicleNo;
    EditText etDueDate, etVehicleNumber, etDescription;
    String DueDate, UserId, VehicleNumber, Description;
    Button btnAddPollution;
    Calendar myCalendar;
    SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_pollution, container, false);
        mSharedPreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        UserId = mSharedPreferences.getString(Const.UserId, "0");


        inputLayoutDueDate = (TextInputLayout) view.findViewById(R.id.input_layout_DueDate);
        inputLayoutVehicleNo = (TextInputLayout) view.findViewById(R.id.input_layout_VehicleNo);

        etVehicleNumber = (EditText) view.findViewById(R.id.etVehicleNo);
        etDueDate = (EditText) view.findViewById(R.id.etDate);
        etDescription = (EditText) view.findViewById(R.id.etDescription);


        btnAddPollution = (Button) view.findViewById(R.id.btnSave);
        btnAddPollution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String VehNo = etVehicleNumber.getText().toString().toUpperCase();
                VehicleNumber = VehNo.replaceAll(" ", "");
                Log.e("Vehicle", VehicleNumber);
                DueDate = etDueDate.getText().toString();
                Description = etDescription.getText().toString();


                if (!validateVehicleNumber()) {
                    return;
                }


                if (!validateDueDate()) {
                    return;
                }

                AddPollution();

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
        etDueDate.setOnClickListener(new View.OnClickListener() {

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

        etDueDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Pollution Certificate");
    }


    public void AddPollution() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Parameter = "api_add_pollution.php?vehicleno=" + VehicleNumber + "&user_id=" + UserId + "&pollution_due_date=" + DueDate + "&desc=" + Description;
                    String Url = ApiConfig.BASE_URL + Parameter;
                    Log.e("Url", Url);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {

                                Log.e("RESPONSE", jsonObject.toString());

                                if (jsonObject.getBoolean("status")) {
                                    Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
etDescription.setText("");
                                    etDueDate.setText("");
                                    etVehicleNumber.setText("");
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


    private boolean validateDueDate() {
        if (DueDate.trim().isEmpty()) {
            inputLayoutDueDate.setError(getString(R.string.err_msg_due_date));
            requestFocus(etDueDate);
            return false;
        } else {
            inputLayoutDueDate.setErrorEnabled(false);
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