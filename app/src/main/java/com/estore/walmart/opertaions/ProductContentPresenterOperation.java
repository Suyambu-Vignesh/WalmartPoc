package com.estore.walmart.opertaions;

/**
 * Created by Suyambu on 6/25/2017.
 */

import com.estore.walmart.pojo.Product;

/**
 * Plan of action between Product view and its preseter.
 */
public class ProductContentPresenterOperation {

    /**
     * Operation which the Presenter want the view to perform
     */
    public interface ViewOperation extends BaseViewOperation {
        void refreshUI(Product product);
    }

    /**
     * Operation which the Presenter has to perform on the behalf of view;
     */
    public interface PresenterOperation {

        /**
         * Method which preovides Product object
         */
        Product getProduct();

        /**
         * get the full description.
         */
        void showFullDescription();
    }

}
