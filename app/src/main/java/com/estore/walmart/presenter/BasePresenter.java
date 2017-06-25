package com.estore.walmart.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.model.BaseModel;
import com.estore.walmart.opertaions.BaseViewOperation;
import com.estore.walmart.opertaions.UIObservable;

import java.lang.ref.WeakReference;

/**
 * Created by Suyambu on 6/23/2017.
 */

public abstract class BasePresenter {
    protected WeakReference<BaseViewOperation> view;

    public void attach(BaseViewOperation view) {
        this.view = new WeakReference<BaseViewOperation>(view);
        UIObservable uiObservable = WalmartApp.getAppObjectGraph().getUIObservable();
        uiObservable.setUIObservable(this);
    }

    public void detach() {
        view = null;

        UIObservable uiObservable = WalmartApp.getAppObjectGraph().getUIObservable();
        uiObservable.setUIObservable(null);
    }

    public abstract void updateUI(BaseModel baseModel);
}
