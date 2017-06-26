package com.estore.walmart.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.estore.walmart.R;
import com.estore.walmart.core.ConnectivityReceiver;
import com.estore.walmart.core.NetworkManager;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.model.BaseModel;
import com.estore.walmart.presenter.ErrorPresenter;
import com.estore.walmart.utils.AppUtils;

/**
 * Created by Suyambu on 6/26/2017.
 */

public class ErrorFragment extends BaseFragment implements ConnectivityReceiver.onConnectionStatusChanged, View.OnClickListener {

    TextView mText;
    TextView mOpenSettings;
    boolean isNetworkError;
    ErrorPresenter errorPresenter;

    public static Fragment getInstance(BaseModel baseModel) {
        ErrorFragment errorFragment = new ErrorFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BaseModel.TAG, baseModel);

        errorFragment.setArguments(bundle);
        return errorFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.layout_error;
    }

    @Override
    protected void initFragment(View rootView) {
        hideActionBar();
        errorPresenter = new ErrorPresenter((BaseModel) getArguments().getParcelable(BaseModel.TAG));
        mText = (TextView) rootView.findViewById(R.id.text_message);
        mText.setText(errorPresenter.getMessage());
        mOpenSettings = (TextView) rootView.findViewById(R.id.text_open_settings);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (errorPresenter.isNetworkError()) {
            mOpenSettings.setVisibility(View.VISIBLE);
            mOpenSettings.setOnClickListener(this);
            NetworkManager.getInstance().registerNetworkObserver(getActivity(), this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (errorPresenter.isNetworkError()) {
            NetworkManager.getInstance().unRegisterNetworkObserver(getActivity(), this);
        }
    }

    @Override
    public void onConnectionChanged(boolean isOnline) {
        if (isOnline) {
            ViewInformation viewInformation = new ViewInformation();
            viewInformation.addToBackStack = false;
            viewInformation.fragment = new SplashFragment();
            replaceFragment(viewInformation);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_open_settings) {
            AppUtils.launchSettings(getContext());
        }
    }
}
