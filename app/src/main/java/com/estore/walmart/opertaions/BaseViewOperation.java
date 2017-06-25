package com.estore.walmart.opertaions;

import android.support.v4.app.Fragment;

/**
 * Created by Suyambu on 6/24/2017.
 */

public interface BaseViewOperation {
    /**
     * Method which help to replace the fragment.
     *
     * @param fragment       Fragment which has to appear at top
     * @param addToBackStack flag which idicates the current fragment need to add to back stack or not
     */
    void replaceFragment(Fragment fragment, boolean addToBackStack);
}
