package com.amithelpline.ahl.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by Neeraj on 10-03-2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutMobile, inputLayoutPassword, inputLayoutConfirmPass;
    EditText etName, etEmail, etMobile, etPassword, etConfirmPass;
    String Name, Email, Mobile, Password, ConfirmPass, UserId;
    Button btnRegister, btnLogin;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    void init() {
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutConfirmPass = (TextInputLayout) findViewById(R.id.input_layout_confirmpassword);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPass = (EditText) findViewById(R.id.etConfirmPassword);


        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnRegister) {

            Name = etName.getText().toString();
            Email = etEmail.getText().toString();
            Mobile = etMobile.getText().toString();
            Password = etPassword.getText().toString();
            ConfirmPass = etConfirmPass.getText().toString();

            if (!validateName()) {
                return;
            }
            if (!validateEmail()) {
                return;
            }
            if (!validateMobile()) {
                return;
            }
            if (!validatePassword()) {
                return;
            }
            if (!validateConfirmPassword()) {
                return;
            }

            getRegister();
        } else if (view.getId() == R.id.btnLogin) {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }

    }


    public void getRegister() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String RegParmeter = "api_register.php?reg_id=jdjdjjdjdjd&email=" + Email + " &password=" + Password + "&mobile=" + Mobile + "&name=" + Name;
                    String AdsUrl = ApiConfig.BASE_URL + RegParmeter;
                    Log.e("Url", AdsUrl);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {


                                Log.e("RESPONSE", jsonObject.toString());

                                if (jsonObject.getBoolean("status")) {
                                    JSONObject jsn = jsonObject.getJSONObject("data");
                                    UserId = jsn.getString("user_id");
                                    sharedpreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(Const.UserId, UserId);
                                    editor.putString(Const.Mobile, Mobile);
                                    editor.putString(Const.Email, jsn.getString("email"));
                                    editor.putString(Const.Name, jsn.getString("name"));
                                    editor.apply();
                                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    Toast.makeText(RegisterActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(RegisterActivity.this, "Oops something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }, null, AdsUrl);

                } catch (Exception e) {

                }
            }
        });


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

    private boolean validateEmail() {
        if (Email.trim().isEmpty() || !isValidEmaillId(Email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(etEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {
        if (Mobile.trim().isEmpty() || (Mobile.length() != 10)) {
            inputLayoutMobile.setError(getString(R.string.err_msg_mobile));
            requestFocus(etMobile);
            return false;
        } else {
            inputLayoutMobile.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (Password.trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(etPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmPassword() {
        if (ConfirmPass.trim().isEmpty() || (!TextUtils.equals(Password, ConfirmPass))) {
            inputLayoutConfirmPass.setError(getString(R.string.err_msg_password));
            requestFocus(etConfirmPass);
            return false;
        } else {
            inputLayoutConfirmPass.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


}

