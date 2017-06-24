package com.estore.walmart.presenter;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by Suyambu on 6/23/2017.
 */

public abstract class BasePresenter {
    protected WeakReference<Activity> activityWeakReference;

    public void attach(Activity activity) {
        activityWeakReference = new WeakReference<Activity>(activity);
    }

    public void detach() {
        activityWeakReference = null;
    }
}
