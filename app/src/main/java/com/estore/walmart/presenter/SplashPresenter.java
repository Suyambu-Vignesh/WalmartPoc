package com.estore.walmart.presenter;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.ProductCatalogModel;
import com.estore.walmart.opertaions.SplashViewPresenterOperations;
import com.estore.walmart.utils.WalmartAppException;

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

    @Override
    public void updateUI(BaseModel baseModel) {
        SplashViewPresenterOperations.ViewOperation view = getView();
        if (view == null) {
            return;
        }
        ViewInformation viewInformation = baseModel.getViewOperation();

        if (viewInformation == null || viewInformation.fragment == null) {
            throw new WalmartAppException(WalmartAppException.VIEW_CANNOT_BE_NULL);
        }

        view.replaceFragment(viewInformation.fragment, viewInformation.addToBackStack);
    }

    private SplashViewPresenterOperations.ViewOperation getView() {
        if (view == null) {
            return null;
        }
        return (SplashViewPresenterOperations.ViewOperation) view.get();
    }
}
