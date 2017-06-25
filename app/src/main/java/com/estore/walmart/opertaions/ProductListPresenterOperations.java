package com.estore.walmart.opertaions;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.estore.walmart.views.adapterview.ProductViewHolder;

/**
 * Created by Suyambu on 6/24/2017.
 */

/**
 * Interface which provides the agreement or contract between Product Home Screen and its Presenter
 */
public interface ProductListPresenterOperations {
    /**
     * Operation which the Presenter want the view to perform
     */
    interface ViewOperation extends BaseViewOperation {
        /**
         * Show or hide the floating buuton
         *
         * @param show param to show or hide floating Button.
         */
        void showScrollUpFloatingButton(boolean show);

        /**
         * Method to show the progressBar
         */
        void showProgress();


        /**
         * Method Which hides the progressView
         */
        void hideProgress();

        void notifyNewElements(int positionStart, int range);
    }

    /**
     * Operation which the Presenter has to perform on the behalf of view;
     */
    interface PresenterOperation {
        /**
         * Method which will replace the data.
         */
        void refreshData();

        /**
         * Append the new set of data.
         */
        void loadNextPageData();

        /**
         * Method which create the view holder for the recyler view.
         *
         * @param parent   viewgroup to which the view has to be added.
         * @param viewType type of view
         * @return ViewHolder created based on the type of view.
         */
        ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        /**
         * Bind the data to the view.
         *
         * @param holder   ViewHolder to which the data has to be binded.
         * @param position position of the view.
         */
        void onBindViewHolder(ProductViewHolder holder, int position);

        /**
         * Methods return the total number of items
         *
         * @return number of elements.
         */
        int getTotalItems();

        /**
         * Method which return the Layout Manager
         *
         * @return RecyclerView.LayoutManager instance
         */
        RecyclerView.LayoutManager getLayoutManager();

        /**
         * method which return the Scroll Listner
         *
         * @return instance of Scroll Listner.
         */
        RecyclerView.OnScrollListener getScrollListner();

        /**
         * Method which scroll the list view to top
         */
        void scrollToTop();
    }
}
