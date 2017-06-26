package com.estore.walmart.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.model.WalmartDialogModel;
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
        backStackWorker();
        return rootView;
    }

    private void backStackWorker() {
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        updateTitle();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();

        WalmartApp.getAppObjectGraph().getLogHandler().d(toString(), "On Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        WalmartApp.getAppObjectGraph().getLogHandler().d(toString(), "On Pause");
    }

    @Override
    public void onStart() {
        super.onStart();
        WalmartApp.getAppObjectGraph().getLogHandler().d(toString(), "On Start");
    }

    @Override
    public void onStop() {
        super.onStop();

        WalmartApp.getAppObjectGraph().getLogHandler().d(toString(), "On Stop");
    }

    protected abstract int getLayoutRes();

    protected abstract void initFragment(View rootView);

    public void updateTitle() {
    }

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

    protected void setTitle(String title) {
        if (mActionBar == null || title == null) {
            return;
        }
        mActionBar.setTitle(title);
    }

    public void replaceFragment(ViewInformation viewInformation) {
        if (viewInformation == null || viewInformation.fragment == null) {
            throw new WalmartAppException(WalmartAppException.VIEW_CANNOT_BE_NULL);
        }

        Activity activity = getActivity();
        if (activity == null) {
            return;
        }
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();

        if (viewInformation.viewType == ViewInformation.VIEW_TYPE_FRAGMENT) {
            fragmentTransaction.add(R.id.view_root, viewInformation.fragment);
            if (viewInformation.addToBackStack) {
                fragmentTransaction.addToBackStack(viewInformation.fragment.toString());
            }

            fragmentTransaction.setCustomAnimations(R.anim.enter,
                    R.anim.exit,
                    R.anim.re_enter,
                    R.anim.re_exit);
            fragmentTransaction.commit();
        } else {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag(WalmartDialogModel.TAG);
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            ((DialogFragment) viewInformation.fragment).show(ft, WalmartDialogModel.TAG);
        }
    }
}
