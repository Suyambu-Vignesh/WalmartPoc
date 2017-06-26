package com.estore.walmart.presenter;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.NetworkErrorModel;
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
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    @Override
    public void updateUI(BaseModel baseModel) {
        if (baseModel instanceof ProductCatalogModel) {
            ProductListPresenterOperations.ViewOperation viewOperation = getView();
            if (viewOperation == null) {
                return;
            }
            viewOperation.notifyNewElements(
                    mProductCatalogModel.getTotalNumberOfItem() - mProductCatalogModel.getNewItemCount(),
                    mProductCatalogModel.getNewItemCount()
            );
        } else if (baseModel instanceof NetworkErrorModel) {
            Toast.makeText(
                    WalmartApp.getAppContext(),
                    WalmartApp.getAppContext().getResources().getString(R.string.network_error),
                    Toast.LENGTH_SHORT
            ).show();

            ProductListPresenterOperations.ViewOperation viewOperation = getView();
            if (viewOperation == null) {
                return;
            }
            viewOperation.hideProgress();

        } else {
            super.updateUI(baseModel);
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

        return new ProductViewHolder(inflater.inflate(R.layout.view_product_card, parent, false), this);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.updateView(mProductCatalogModel.getProduct(position), position);
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

    public void showSortOptions() {
        ProductListPresenterOperations.ViewOperation view = getView();

        if (view == null) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(((Fragment) view).getContext());
        builder.setTitle(R.string.sort);
        //builder.set
    }

    @Override
    public void onProductSelected(int itemPosition) {
        mProductCatalogModel.loadDetailPage(itemPosition);
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


            //view.showScrollUpFloatingButton(firstVisibleItemPosition != 0);
        }
    };
}
