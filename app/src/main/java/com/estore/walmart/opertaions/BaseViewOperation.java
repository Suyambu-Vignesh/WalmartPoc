package com.estore.walmart.opertaions;

import com.estore.walmart.core.ViewInformation;

/**
 * Created by Suyambu on 6/24/2017.
 */

public interface BaseViewOperation {
    /**
     * Method which help to replace the fragment.
     *
     * @param viewInformation info about the new view
     */
    void replaceFragment(ViewInformation viewInformation);
}
