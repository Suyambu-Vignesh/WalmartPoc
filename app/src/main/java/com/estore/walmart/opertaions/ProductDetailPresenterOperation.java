package com.estore.walmart.opertaions;

/**
 * Created by suyam on 6/25/2017.
 */

import com.estore.walmart.pojo.Product;

/**
 * Plan of action between Product detail view and its preseter.
 */
public class ProductDetailPresenterOperation {

    /**
     * Operation which the Presenter want the view to perform
     */
    public interface ViewOperation extends BaseViewOperation {
    }

    /**
     * Operation which the Presenter has to perform on the behalf of view;
     */
    public interface PresenterOperation {
        /**
         * Method which preovides Product object
         */
        Product getProductAt(int index);

        /**
         * Method which perform load the initial items.
         */
        void shareItem();

        /**
         * Method which return the total number of elements
         * @return
         */
        int getTotalElements();

        /**
         * set the selected Product
         */
        void setSelectedItem(int value);

        /**
         * get the selected Item
         * @return
         */
        int getSelectedItem();
    }

}
