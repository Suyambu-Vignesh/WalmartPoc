package com.estore.walmart.core;


import android.app.Fragment;

/**
 * Created by Suyambu on 6/24/2017.
 */

public class ViewInformation {
    public static final int VIEW_TYPE_FRAGMENT = 1;
    public static final int VIEW_TYPE_DIALOG = 2;

    public ViewInformation() {
        viewType = VIEW_TYPE_FRAGMENT;
    }

    /**
     * The Fragment which has to shown
     */
    public Fragment fragment;

    /**
     * Flag indicates the fragment need to push to back stack.
     */
    public boolean addToBackStack;

    /**
     * Value Indicates Fragment, Dialog or any Other View
     */
    public int viewType;
}
