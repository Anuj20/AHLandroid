package com.amithelpline.ahl.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.api.ApiConfig;
import com.amithelpline.ahl.api.ApiConnection;
import com.amithelpline.ahl.api.OnApiResponseListener;
import com.amithelpline.ahl.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import static com.amithelpline.ahl.utils.Const.Email;

/**
 * Created by Neeraj on 14-03-2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout inputLayoutMobile, inputLayoutPassword;
    Button btnLogin ;
    EditText etMobile, etPassword;
    String Mobile, Password, UserId;
    TextView tvForget, btnRegister;

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        tvForget = (TextView) findViewById(R.id.tvForgot);
        tvForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                Mobile = etMobile.getText().toString();
                Password = etPassword.getText().toString();
                if (!validateMobile()) {
                    return;
                }
                if (!validatePassword()) {
                    return;
                }
                getLogin();


                break;

            case R.id.btnRegister:
                Intent reg = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(reg);
                finish();
                break;

            case R.id.tvForgot:
                Intent forgot = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgot);
                finish();
                break;

        }
    }

    public void getLogin() {


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String LoginParmeter = "api_login.php?reg_id=jdjdjjdjdjd&mobile=" + Mobile + "&password=" + Password;
                    String LoginUrl = ApiConfig.BASE_URL + LoginParmeter;
                    Log.e("Url", LoginUrl);
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
                                    editor.putString(Const.Mobile, jsn.getString("mobile"));
                                    editor.putString(Const.Email, jsn.getString("email"));
                                    editor.putString(Const.Name, jsn.getString("name"));
                                    editor.apply();
                                    sharedpreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
                                    UserId = sharedpreferences.getString(Const.UserId, "0");
                                    Log.e("UserId", UserId);
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(LoginActivity.this, "Oops something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }, null, LoginUrl);

                } catch (Exception e) {

                }
            }
        });


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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
