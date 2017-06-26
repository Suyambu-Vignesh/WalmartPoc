package com.estore.walmart.presenter;

import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.ProductDetailModel;
import com.estore.walmart.opertaions.ProductContentPresenterOperation;
import com.estore.walmart.opertaions.ProductDetailPresenterOperation;
import com.estore.walmart.pojo.Product;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class ProductDetailPresenter extends BasePresenter implements ProductDetailPresenterOperation.PresenterOperation {
    ProductDetailModel mProductDetailModel;

    private ProductDetailPresenter() {
    }

    public ProductDetailPresenter(ProductDetailModel productDetailModel) {
        mProductDetailModel = productDetailModel;
    }

    @Override
    public void updateUI(BaseModel baseModel) {
        super.updateUI(baseModel);
    }

    @Override
    public void shareItem() {
    }

    @Override
    public int getTotalElements() {
        return mProductDetailModel.getTotalElements();
    }

    @Override
    public void setSelectedItem(int value) {
        mProductDetailModel.setSelectedItem(value);
    }

    @Override
    public int getSelectedItem() {
        return mProductDetailModel.getSelectedItem();
    }

    @Override
    public Product getProductAt(int index) {
        return mProductDetailModel.getProductAt(index);
    }
}
