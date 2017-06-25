package com.estore.walmart.opertaions;

import com.estore.walmart.model.BaseModel;
import com.estore.walmart.presenter.BasePresenter;

/**
 * Created by Suyambu on 6/24/2017.
 */

public class UIObservable {
    private static UIObservable sInstance;

    private BasePresenter mBasePresenter;

    private UIObservable() {
    }

    public static synchronized UIObservable getInstance() {
        if (sInstance == null) {
            sInstance = new UIObservable();
        }
        return sInstance;
    }

    public void setUIObservable(BasePresenter basePresenter) {
        mBasePresenter = basePresenter;
    }

    public void notifyUI(BaseModel baseModel) {
        if (mBasePresenter == null) {
            return;
        }
        mBasePresenter.updateUI(baseModel);
    }
}
