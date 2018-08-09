package com.amithelpline.ahl.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

/**
 * Created by Neeraj on 26-08-2017.
 */

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout inputLayoutEmail, inputLayoutMobile, inputLayoutPassword, inputLayoutConfirmPass;
    Button btnForgot, btnLogin;
    EditText etEmail, etMobile, etPassword, etConfirmPass;
    String Email, Mobile, Password, ConfirmPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_passowrd);
        init();
    }

    void init() {

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutConfirmPass = (TextInputLayout) findViewById(R.id.input_layout_confirmpassword);


        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPass = (EditText) findViewById(R.id.etConfirmPassword);


        btnForgot = (Button) findViewById(R.id.btnForgot);
        btnForgot.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnForgot:

                Email = etEmail.getText().toString();
                Mobile = etMobile.getText().toString();
                Password = etPassword.getText().toString();
                ConfirmPass = etConfirmPass.getText().toString();


                if ((!validateEmail()) &&(!validateMobile()) ) {
                    return;
                }
                if (!validatePassword()) {
                    return;
                }
                if (!validateConfirmPassword()) {
                    return;
                }
                if ((validateMobile()) || (validateEmail())) {
                    getForgotPassword();
                }
                break;

            case R.id.btnLogin:
                Intent login = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
                break;
        }
    }

    public void getForgotPassword() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Parameter = "api_change_password.php?email=" + Email + "&mobile=" + Mobile + "&password=" + Password;
                    String Url = ApiConfig.BASE_URL + Parameter;
                    Log.e("Url", Url);
                    new ApiConnection().connect(new OnApiResponseListener() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {


                                Log.e("RESPONSE", jsonObject.toString());
                                if (jsonObject.getBoolean("status")) {
                                    Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                      Toast.makeText(ForgotPasswordActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(ForgotPasswordActivity.this, "Oops something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }, null, Url);

                } catch (Exception e) {

                }
            }
        });


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