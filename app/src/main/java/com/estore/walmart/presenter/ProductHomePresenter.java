package com.estore.walmart.presenter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.ProductCatalogModel;
import com.estore.walmart.opertaions.ProductListPresenterOperations;
import com.estore.walmart.utils.WalmartAppException;
import com.estore.walmart.views.adapterview.ProductViewHolder;

/**
 * Created by Suyambu on 6/24/2017.
 */

public class ProductHomePresenter extends BasePresenter implements ProductListPresenterOperations.PresenterOperation {
    private ProductCatalogModel mProductCatalogModel;
    private LinearLayoutManager mLayoutManager;

    public ProductHomePresenter(ProductCatalogModel productCatalogModel) {
        mProductCatalogModel = productCatalogModel;

        mLayoutManager = (LinearLayoutManager) WalmartApp.getAppObjectGraph().getLayoutManager();
    }

    @Override
    public void updateUI(BaseModel baseModel) {
        if (baseModel instanceof ProductCatalogModel) {
            ProductListPresenterOperations.ViewOperation viewOperation = getView();
            if (viewOperation == null) {
                return;
            }
            viewOperation.notifyNewElements(
                    mProductCatalogModel.getTotalNumberOfItem()-mProductCatalogModel.getNewItemCount(),
                    mProductCatalogModel.getNewItemCount()
            );
        } else {
            ProductListPresenterOperations.ViewOperation view = getView();
            if (view == null) {
                return;
            }
            ViewInformation viewInformation = baseModel.getViewOperation();

            if (viewInformation == null || viewInformation.fragment == null) {
                throw new WalmartAppException(WalmartAppException.VIEW_CANNOT_BE_NULL);
            }

            view.replaceFragment(viewInformation.fragment, viewInformation.addToBackStack);
        }
    }

    @Override
    public void refreshData() {
        mProductCatalogModel.loadInitailData();
    }

    @Override
    public void loadNextPageData() {
        mProductCatalogModel.loadNextPageData();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new ProductViewHolder(inflater.inflate(R.layout.view_product_card, parent, false));
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.updateView(mProductCatalogModel.getProduct(position));
    }

    @Override
    public int getTotalItems() {
        return mProductCatalogModel.getTotalNumberOfItem();
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public RecyclerView.OnScrollListener getScrollListner() {
        return recyclerViewOnScrollListener;
    }

    @Override
    public void scrollToTop() {
        if (mLayoutManager == null) {
            return;
        }
        mLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    private ProductListPresenterOperations.ViewOperation getView() {
        if (view == null) {
            return null;
        }
        return (ProductListPresenterOperations.ViewOperation) view.get();
    }


    //_____________________________________________________________________________________________

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

            ProductListPresenterOperations.ViewOperation view = getView();
            if (view == null) {
                return;
            }

            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                if (mProductCatalogModel.canLoadMoreItem()) {
                    view.showProgress();
                    mProductCatalogModel.loadNextPageData();
                }
            }


            view.showScrollUpFloatingButton(firstVisibleItemPosition != 0);
        }
    };
}
