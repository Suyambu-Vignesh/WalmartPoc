package com.estore.walmart.views.adapterview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.presenter.ProductHomePresenter;

/**
 * Created by Suyambu on 6/24/2017.
 */

public class ProductHomeRecyclerViewAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private static final String TAG = ProductHomeRecyclerViewAdapter.class.getSimpleName();

    private ProductHomePresenter mProductHomePresenter;

    public ProductHomeRecyclerViewAdapter(ProductHomePresenter productHomePresenter) {
        mProductHomePresenter = productHomePresenter;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mProductHomePresenter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        mProductHomePresenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        int totalItems = mProductHomePresenter.getTotalItems();
        WalmartApp.getAppObjectGraph().getLogHandler().d(TAG, ""+totalItems);
        return totalItems;
    }
}
