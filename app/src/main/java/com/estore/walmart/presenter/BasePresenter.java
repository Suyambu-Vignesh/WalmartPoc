package com.estore.walmart.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.model.BaseModel;
import com.estore.walmart.opertaions.BaseViewOperation;
import com.estore.walmart.opertaions.ProductListPresenterOperations;
import com.estore.walmart.opertaions.UIObservable;
import com.estore.walmart.utils.WalmartAppException;

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

    public void detach(BaseViewOperation view) {
        if (this.view.equals(view)) {
            this.view = null;

            UIObservable uiObservable = WalmartApp.getAppObjectGraph().getUIObservable();
            uiObservable.setUIObservable(null);
        }
    }

    public void updateUI(BaseModel baseModel) {
        BaseViewOperation view = this.view.get();
        if (view == null) {
            return;
        }
        ViewInformation viewInformation = baseModel.getViewOperation();

        if (viewInformation == null || viewInformation.fragment == null) {
            throw new WalmartAppException(WalmartAppException.VIEW_CANNOT_BE_NULL);
        }

        view.replaceFragment(viewInformation);
    }
}
