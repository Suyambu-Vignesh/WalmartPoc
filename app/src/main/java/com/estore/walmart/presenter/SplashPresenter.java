package com.estore.walmart.presenter;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.model.ProductCatalogModel;
import com.estore.walmart.opertaions.SplashViewPresenterOperations;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class SplashPresenter extends BasePresenter implements SplashViewPresenterOperations.PresenterOperation {

    private ProductCatalogModel mProductCatalogModel;

    public SplashPresenter() {
        mProductCatalogModel = WalmartApp.getAppObjectGraph().getProductCatalogModel();
    }

    @Override
    public void loadData() {
        mProductCatalogModel.loadInitailData();
    }
}
