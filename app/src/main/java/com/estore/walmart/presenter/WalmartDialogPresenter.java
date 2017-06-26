package com.estore.walmart.presenter;

import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.WalmartDialogModel;
import com.estore.walmart.opertaions.DialogPresenterOperation;

import java.io.Serializable;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class WalmartDialogPresenter extends BasePresenter implements DialogPresenterOperation.PresenterOperation {

    WalmartDialogModel mWalmartDialogModel;

    public WalmartDialogPresenter(WalmartDialogModel walmartDialogModel) {
        mWalmartDialogModel = walmartDialogModel;
    }

    @Override
    public void updateUI(BaseModel baseModel) {
        updateUI(baseModel);
    }

    @Override
    public int getViewType() {
        return mWalmartDialogModel.getViewType();
    }

    @Override
    public Serializable getExtraParams() {
        return mWalmartDialogModel.getExtraParams();
    }
}
