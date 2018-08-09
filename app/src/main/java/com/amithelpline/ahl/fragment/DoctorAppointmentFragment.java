package com.amithelpline.ahl.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.utils.Const;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Neeraj on 22-03-2017.
 */

public class DoctorAppointmentFragment extends Fragment {

    TextInputLayout inputLayoutName, inputLayoutAddress, inputLayoutCity, inputLayoutDate, inputLayoutTime;
    EditText etName, etAddress, etCity, etDate, etTime;
    String Name, Address, City, appDate, appTime, UserId, DoctorId = "1";
    Button btnSave;
    Calendar myCalendar;
    Spinner spnDoctor;

    List<String> namesList = new ArrayList<String>();
    SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_appointment, container, false);

        mSharedPreferences = getActivity().getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);

        UserId = mSharedPreferences.getString(Const.UserId, "0");

        spnDoctor = (Spinner) view.findViewById(R.id.spnDocotor);
        getDoctors();

        inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_Name);
        inputLayoutAddress = (TextInputLayout) view.findViewById(R.id.input_layout_Address);
        inputLayoutCity = (TextInputLayout) view.findViewById(R.id.input_layout_City);
        inputLayoutDate = (TextInputLayout) view.findViewById(R.id.input_layout_Date);
        inputLayoutTime = (TextInputLayout) view.findViewById(R.id.input_layout_Time);
        etName = (EditText) view.findViewById(R.id.etName);
        etAddress = (EditText) view.findViewById(R.id.etAddress);
        etCity = (EditText) view.findViewById(R.id.etCity);
        etDate = (EditText) view.findViewById(R.id.etDate);
        etTime = (EditText) view.findViewById(R.id.etTime);


        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = etName.getText().toString();
                Address = etAddress.getText().toString();
                City = etCity.getText().toString();
                appDate = etDate.getText().toString();
                appTime = etTime.getText().toString();
                AddAppointment();
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
        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time time = new Time(selectedHour, selectedMinute, 0);

                        //little h uses 12 hour format and big H uses 24 hour format
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mma");

                        //format takes in a Date, and Time is a sublcass of Date
                        String s = simpleDateFormat.format(time);
                        // toTime.setText(s);
                        etTime.setText(s);

                    }
                }, hour, minute, false);//Yes 12 hour time
                mTimePicker.setTitle("Select Appointment Time");
                mTimePicker.show();

            }
        });


        return view;

    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Appointment");
    }


    public void AddAppointment() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Parameter = "api_add_appointment.php?name=" + Name + "&address=" + Address + "&city=" + City + "&appdate=" + appDate + "&apptime=" + appTime + "&userid=" + UserId + "&doctorid=" + DoctorId;
                    String Url = ApiConfig.BASE_URL + Parameter;
                    Log.e("Url", Url);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {


                                Log.e("RESPONSE", jsonObject.toString());

                                if (jsonObject.getBoolean("status")) {

                                    Toast.makeText(getActivity(), "Appointment request send", Toast.LENGTH_SHORT).show();
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
                    }, null, Url);

                } catch (Exception e) {

                }
            }
        });


    }

    public void getDoctors() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Parameter = "api_get_doctors.php";
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

                                        namesList.add(jsn.getString("name"));
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, namesList);

                                    spnDoctor.setAdapter(adapter);
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

    void ClearFields() {
        etTime.setText("");
        etAddress.setText("");
        etCity.setText("");
        etDate.setText("");
        etName.setText("");
    }
}