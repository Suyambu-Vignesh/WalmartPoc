package com.estore.walmart.presenter;

import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.ProductContentModel;
import com.estore.walmart.opertaions.ProductContentPresenterOperation;
import com.estore.walmart.pojo.Product;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class ProductContentPresenter extends BasePresenter implements ProductContentPresenterOperation.PresenterOperation {
    ProductContentModel mProductContentModel;

    private ProductContentPresenter() {
    }

    public ProductContentPresenter(ProductContentModel productContentModel) {
        mProductContentModel = productContentModel;
    }

    @Override
    public void updateUI(BaseModel baseModel) {
        super.updateUI(baseModel);
    }

    @Override
    public Product getProduct() {
        return mProductContentModel.getProduct();
    }

    @Override
    public void showFullDescription() {
        mProductContentModel.showFullDescription();
    }
}
