package com.estore.walmart.opertaions;

import com.estore.walmart.pojo.Product;

import java.io.Serializable;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class DialogPresenterOperation {

    /**
     * Operation which the Presenter want the view to perform
     */
    public interface ViewOperation extends BaseViewOperation {
    }

    /**
     * Operation which the Presenter has to perform on the behalf of view;
     */
    public interface PresenterOperation {
        int getViewType();

        Serializable getExtraParams();
    }

}
