package com.estore.walmart.views;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.views.fragments.SplashFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SplashFragment splashFragment = WalmartApp.getAppObjectGraph().getSplashFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.view_root, splashFragment).commit();
    }
}
