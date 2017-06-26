package com.estore.walmart.views.adapterview;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.estore.walmart.model.ProductContentModel;
import com.estore.walmart.presenter.ProductDetailPresenter;
import com.estore.walmart.views.fragments.ProductContentFragment;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class ProductDetailAdapter extends FragmentStatePagerAdapter {
    ProductDetailPresenter mProductDetailPresenter;

    public ProductDetailAdapter(FragmentManager fm, ProductDetailPresenter productDetailPresenter) {
        super(fm);
        mProductDetailPresenter = productDetailPresenter;
    }

    @Override
    public Fragment getItem(int i) {
        ProductContentModel productContentModel = new ProductContentModel(mProductDetailPresenter.getProductAt(i));
        return ProductContentFragment.getInstnace(productContentModel);
    }

    @Override
    public int getCount() {
        return mProductDetailPresenter.getTotalElements();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
