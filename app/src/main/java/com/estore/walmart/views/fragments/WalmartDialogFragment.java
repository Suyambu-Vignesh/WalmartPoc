package com.estore.walmart.views.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.model.WalmartDialogModel;
import com.estore.walmart.pojo.DialogInfo;
import com.estore.walmart.pojo.WebContent;
import com.estore.walmart.presenter.WalmartDialogPresenter;
import com.estore.walmart.utils.AppUtils;
import com.estore.walmart.utils.WalmartAppException;

import java.io.Serializable;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class WalmartDialogFragment extends DialogFragment {
    private Context mContext;
    WalmartDialogPresenter mWalmartDialogPresenter;

    private int mViewType;

    private WebView mWebView;


    public static Fragment getInstance(WalmartDialogModel walmartDialogModel) {
        WalmartDialogFragment walmartDialogFragment = new WalmartDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(WalmartDialogModel.TAG, walmartDialogModel);

        walmartDialogFragment.setArguments(bundle);
        return walmartDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWalmartDialogPresenter = WalmartApp.getAppObjectGraph().getWalmartDialogPresenter(
                (WalmartDialogModel) getArguments().getParcelable(WalmartDialogModel.TAG)
        );

        mViewType = mWalmartDialogPresenter.getViewType();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mViewType == R.layout.layout_webview) {
            getDialog().getWindow().setLayout(
                    AppUtils.percentOf(AppUtils.getWidth(), 90),
                    AppUtils.percentOf(AppUtils.getHeight(), 90));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mViewType == -1) {
            throw new WalmartAppException(WalmartAppException.VIEW_CANNOT_BE_NULL);
        }
        View rootView = inflater.inflate(mViewType, container, false);
        populateView(mViewType, rootView);
        return rootView;
    }

    private void populateView(int viewType, View rootView) {
        // todo will  move to switch if more than three case..
        if (mViewType == R.layout.layout_webview) {
            Serializable extraParameter = mWalmartDialogPresenter.getExtraParams();

            WebContent dialogInfo = (WebContent) extraParameter;

            mWebView = (WebView) rootView.findViewById(R.id.view_webview);
            if (dialogInfo.isLocalData()) {
                mWebView.loadData(dialogInfo.getLocalContent(), "text/html", "UTF-8");
                mWebView.getSettings().setDefaultFontSize(17);
            } else {
                mWebView.loadUrl(dialogInfo.getUrl());
            }
        }
    }
}
