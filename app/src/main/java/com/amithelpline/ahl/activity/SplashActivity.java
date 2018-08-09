package com.amithelpline.ahl.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.utils.Const;

/**
 * Created by Neeraj on 10-03-2017.
 */

public class SplashActivity extends AppCompatActivity {
    int splahTime = 2000;
    private int currentApiVersion;
    SharedPreferences mSharedPreferences;
    String UserId;
    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
        UserId = mSharedPreferences.getString(Const.UserId, "0");
        //progressBar= (ProgressBar) findViewById(R.id.progress_start);
        Log.e("UserId",UserId);


/*Thread method in order to hold screen according to time*/
        Thread splash_thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(splahTime);
                    }
                } catch (InterruptedException e) {
                } finally {
                    onSplashStop();
                }
            }
        };

        splash_thread.start();


    }


  /*  private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);

        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);

        iv.clearAnimation();

        iv.startAnimation(anim);


    }/*


    /*method used to redirect to home screen or login screen acc to user id*/
    public void onSplashStop() {

        if (TextUtils.equals(UserId, "0")) {
            Intent dashboard = new Intent(SplashActivity.this, LoginActivity.class);
            dashboard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(dashboard);
            //progressBar.setVisibility(View.INVISIBLE);
            finish();
        } else {
            Intent main = new Intent(SplashActivity.this, MainActivity.class);
            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main);
            //progressBar.setVisibility(View.INVISIBLE);
            finish();
        }


    }

    /*Hide the bottom bar*/
    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /*method hide bottom bar*/
    void HideBottomBar() {
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }


    }


}

