package com.estore.walmart.opertaions;

/**
 * Created by suyam on 6/23/2017.
 */

import android.support.v4.app.Fragment;

/**
 * Interface which provides the agreement or contract between Splash Screen and its Presenter
 */
public interface SplashViewPresenterOperations {

    /*
    Operation which the Presenter want the view to perform
     */
    interface ViewOperation {
        void replaceFragment(Fragment fragment, boolean addToBackStack);
    }

    /*
    Operation which the Presenter has to perform on the behalf of view;
     */
    interface PresenterOperation {
        void loadData();
    }
}
