package com.estore.walmart.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estore.walmart.R;
import com.estore.walmart.utils.WalmartAppException;

/**
 * Created by Suyambu on 6/23/2017.
 */

public abstract class BaseFragment extends Fragment {
    private ActionBar mActionBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutRes(), container, false);
        Activity activity = getActivity();

        if (activity != null) {
            mActionBar = ((AppCompatActivity) activity).getSupportActionBar();
        }

        initFragment(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected abstract int getLayoutRes();

    protected abstract void initFragment(View rootView);

    protected boolean isFullView() {
        return false;
    }

    protected ActionBar getActionBar() {
        return mActionBar;
    }

    protected void showActionBar() {
        if (mActionBar != null) {
            mActionBar.show();
        }
    }

    protected void hideActionBar() {
        if (mActionBar != null) {
            mActionBar.hide();
        }
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment == null) {
            throw new WalmartAppException(WalmartAppException.VIEW_CANNOT_BE_NULL);
        }

        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.view_root, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.toString());
        }
        fragmentTransaction.commit();
    }
}
