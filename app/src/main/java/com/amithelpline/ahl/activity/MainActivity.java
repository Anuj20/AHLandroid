package com.amithelpline.ahl.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amithelpline.ahl.R;
import com.amithelpline.ahl.fragment.AddGeneralPolicyFragment;
import com.amithelpline.ahl.fragment.AddLICPolicyFragment;
import com.amithelpline.ahl.fragment.AddPollutionFragment;
import com.amithelpline.ahl.fragment.AddPropertyFragment;
import com.amithelpline.ahl.fragment.ApplyFragment;
import com.amithelpline.ahl.fragment.AppointmentListFragment;
import com.amithelpline.ahl.fragment.ContactUsFragment;
import com.amithelpline.ahl.fragment.CurrencyFragment;
import com.amithelpline.ahl.fragment.DoctorAppointmentFragment;
import com.amithelpline.ahl.fragment.HomeFragment;
import com.amithelpline.ahl.fragment.IPOFragment;
import com.amithelpline.ahl.fragment.PolicyGeneralListFragment;
import com.amithelpline.ahl.fragment.PolicyListFragment;
import com.amithelpline.ahl.fragment.PollutionListFragment;
import com.amithelpline.ahl.fragment.ProfileFragment;
import com.amithelpline.ahl.fragment.PropertyListFragment;
import com.amithelpline.ahl.fragment.YoutubeListFragment;
import com.amithelpline.ahl.utils.Const;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnGridMenuClickListener {
    public HomeFragment mHomeFragment;
    private AdView mAdView;

    @Override
    public void onGridMenuClick(int position) {

        Log.e("POS", "POS" + position);

        if (position == 0) {
            PolicyListFragment fragment = new PolicyListFragment();
            setFragment(fragment);
        } else if (position == 1) {
            PropertyListFragment fragment = new PropertyListFragment();
            setFragment(fragment);
        } else if (position == 2) {
            IPOFragment fragment = new IPOFragment();
            setFragment(fragment);
        } else if (position == 3) {
            YoutubeListFragment fragment = new YoutubeListFragment();
            setFragment(fragment);
        } else if (position == 4) {
            ApplyFragment fragment = new ApplyFragment();
            setFragment(fragment);
        } else if (position == 5) {
            ContactUsFragment fragment = new ContactUsFragment();
            setFragment(fragment);
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment).commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MobileAds.initialize(this, "ca-app-pub-5057880724201581~2555112164");
        mAdView = (AdView) findViewById(R.id.adView);
        //.addTestDevice("07A2BBA833B555F7B7AB34144CE6BB00")
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initDashboardFragment();


    }

    private void initDashboardFragment() {
        mHomeFragment = new HomeFragment();
        mHomeFragment.setOnGridMenuClickListener(this);

        setFragment(mHomeFragment);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mHomeFragment = new HomeFragment();
            mHomeFragment.setOnGridMenuClickListener(this);
            setFragment(mHomeFragment);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_logout) {
            SharedPreferences mSharedPreferences = getSharedPreferences(Const.SHAREDPREFERENCE, MODE_PRIVATE);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.clear();
            editor.apply();
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if (id == R.id.action_profile) {
            Fragment fragment = new ProfileFragment();
            setFragment(fragment);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_AddLICPolicy) {
            fragment = new AddLICPolicyFragment();
        } else if (id == R.id.nav_listLICPolicy) {
            fragment = new PolicyListFragment();
        } else if (id == R.id.nav_listGeneralPolicy) {
            fragment = new PolicyGeneralListFragment();
        } else if (id == R.id.nav_AddGeneralPolicy) {
            fragment = new AddGeneralPolicyFragment();
        } else if (id == R.id.nav_contact) {
            fragment = new ContactUsFragment();

        } else if (id == R.id.nav_apply) {
            fragment = new ApplyFragment();

        } else if (id == R.id.nav_AddProperty) {
            fragment = new AddPropertyFragment();
        } else if (id == R.id.nav_listProperty) {
            fragment = new PropertyListFragment();

        } else if (id == R.id.nav_AddApp) {
            fragment = new DoctorAppointmentFragment();
        } else if (id == R.id.nav_viewAppiontment) {
            fragment = new AppointmentListFragment();
        } else if (id == R.id.nav_currency) {
            fragment = new CurrencyFragment();
        } else if (id == R.id.nav_ipo) {
            fragment = new IPOFragment();
        } else if (id == R.id.nav_AddPollution) {
            fragment = new AddPollutionFragment();
        } else if (id == R.id.nav_listPollution) {
            fragment = new PollutionListFragment();
        } else if (id == R.id.nav_share) {
            fragment = null;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hey, download this app!\n https://play.google.com/store/apps/details?id=com.amithelpline.ahl");
            startActivity(shareIntent);
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
