package com.estore.walmart.views.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.model.ProductCatalogModel;
import com.estore.walmart.opertaions.ProductListPresenterOperations;
import com.estore.walmart.presenter.ProductHomePresenter;
import com.estore.walmart.utils.AppUtils;
import com.estore.walmart.views.adapterview.ProductHomeRecyclerViewAdapter;

/**
 * Created by Suyambu on 6/24/2017.
 */

public class ProductHomeFragment extends BaseFragment implements ProductListPresenterOperations.ViewOperation, View.OnClickListener, MenuItem.OnMenuItemClickListener {

    private ProductHomePresenter mProductHomePresenter;
    private ProductHomeRecyclerViewAdapter mProductHomeRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mScrollUpButton;
    private View mProgressView;

    public static ProductHomeFragment getInstance(ProductCatalogModel productCatalogModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductCatalogModel.TAG, productCatalogModel);

        ProductHomeFragment productHomeFragment = new ProductHomeFragment();
        productHomeFragment.setArguments(bundle);

        return productHomeFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mProductHomePresenter.attach(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mProductHomePresenter.detach(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_product_home;
    }

    @Override
    protected void initFragment(View rootView) {
        showActionBar();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        mScrollUpButton = (FloatingActionButton) rootView.findViewById(R.id.floating_button);
        mScrollUpButton.setOnClickListener(this);
        mScrollUpButton.setTranslationY(AppUtils.getDimens(R.dimen.Y_trans));
        mScrollUpButton.setEnabled(false);
        mScrollUpButton.setVisibility(View.INVISIBLE);

        setTitle(getString(R.string.fragment_home_title));

        mProgressView = rootView.findViewById(R.id.layout_progress);
        mProgressView.setAlpha(0);
        mProgressView.setTranslationY(AppUtils.getDimens(R.dimen.Y_trans));

        mProductHomePresenter = WalmartApp.getAppObjectGraph().getProductHomePresenter(
                (ProductCatalogModel) getArguments().getParcelable(ProductCatalogModel.TAG)
        );

        mProductHomeRecyclerViewAdapter = WalmartApp.getAppObjectGraph().getHomeRecylerViewAdapter(mProductHomePresenter);


        mRecyclerView.setLayoutManager(mProductHomePresenter.getLayoutManager());
        mRecyclerView.addOnScrollListener(mProductHomePresenter.getScrollListner());
        mRecyclerView.setAdapter(mProductHomeRecyclerViewAdapter);
        mRecyclerView.scrollToPosition(0);

        setHasOptionsMenu(true);
    }

    @Override
    public void updateTitle() {
        if (getActivity() == null) {
            return;
        }
        setTitle(getString(R.string.fragment_home_title));
        mProductHomePresenter.attach(this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //menu.findItem(R.id.sort).setOnMenuItemClickListener(this);
    }

    @Override
    public void showScrollUpFloatingButton(boolean show) {
        if (show) {
            showScrollUpButton();
        } else {
            hideScrollUpButton();
        }
    }

    private void showScrollUpButton() {
        if (mScrollUpButton.isEnabled()) {
            return;
        }

        mScrollUpButton.setEnabled(true);
        mScrollUpButton.animate().setStartDelay(200).translationY(0).setDuration(500).setListener(null).start();
        mScrollUpButton.setVisibility(View.VISIBLE);
    }

    private void hideScrollUpButton() {
        if (!mScrollUpButton.isEnabled()) {
            return;
        }
        mScrollUpButton.setEnabled(false);
        mScrollUpButton.animate().
                translationY(AppUtils.getDimens(R.dimen.Y_trans)).
                setDuration(500).
                setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mScrollUpButton.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    @Override
    public void showProgress() {
        if (mProgressView.getAlpha() != 0) {
            return;
        }

        mProgressView.animate().translationY(0).alpha(1).start();
    }

    @Override
    public void hideProgress() {
        if (mProgressView.getAlpha() == 0) {
            return;
        }

        mProgressView.animate().translationY(AppUtils.getDimens(R.dimen.Y_trans)).alpha(0).start();
    }

    @Override
    public void notifyNewElements(int positionChanged, int itemCount) {
        if (mProductHomeRecyclerViewAdapter != null) {
            synchronized (mProductHomeRecyclerViewAdapter) {
                mProductHomeRecyclerViewAdapter.notifyItemRangeChanged(positionChanged, itemCount);
            }
        }
        hideProgress();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.floating_button:
                mRecyclerView.scrollToPosition(0);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.sort) {
            return true;
        }
        return false;
    }
}
