package com.estore.walmart.views.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.opertaions.SplashViewPresenterOperations;
import com.estore.walmart.presenter.SplashPresenter;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class SplashFragment extends BaseFragment implements SplashViewPresenterOperations.ViewOperation {
    private SplashPresenter mSplashPresenter;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_splash;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSplashPresenter.attach(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        mSplashPresenter.detach(this);
    }

    @Override
    protected void initFragment(View rootView) {
        hideActionBar();
        mSplashPresenter = WalmartApp.getAppObjectGraph().getSplashPresenter();
        mSplashPresenter.loadData();
    }

    @Override
    public void replaceFragment(ViewInformation viewInformation) {
        super.replaceFragment(viewInformation);
    }
}
