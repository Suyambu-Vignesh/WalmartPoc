package com.estore.walmart.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.model.ProductDetailModel;
import com.estore.walmart.opertaions.ProductDetailPresenterOperation;
import com.estore.walmart.presenter.ProductDetailPresenter;
import com.estore.walmart.views.adapterview.ProductDetailAdapter;

/**
 * Created by Suyambu on 6/25/2017.
 */

/**
 * This Fragment Holds an array of Detail content of Product.
 */
public class ProductDetailFragment extends BaseFragment implements ViewPager.OnPageChangeListener,
        ProductDetailPresenterOperation.ViewOperation {
    private ViewPager mViewPager;
    ProductDetailPresenter mProductDetailPresenter;

    public static Fragment getInstnace(ProductDetailModel productDetailModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductDetailModel.TAG, productDetailModel);

        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        productDetailFragment.setArguments(bundle);

        return productDetailFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onStart() {
        super.onStart();
        mProductDetailPresenter.attach(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mProductDetailPresenter.detach(this);
    }

    @Override
    protected void initFragment(View rootView) {
        mProductDetailPresenter = WalmartApp.getAppObjectGraph().getProductDetailPresenter(
                (ProductDetailModel) getArguments().getParcelable(ProductDetailModel.TAG)
        );
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ProductDetailAdapter(
                getActivity().getSupportFragmentManager(),
                mProductDetailPresenter
        ));
        mViewPager.setCurrentItem(mProductDetailPresenter.getSelectedItem());
        mViewPager.addOnPageChangeListener(this);

    }

    @Override
    public void updateTitle() {
        if (getActivity() == null) {
            return;
        }
        setTitle(getString(R.string.fragment_item_detail));
        mProductDetailPresenter.attach(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mProductDetailPresenter.setSelectedItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
