package com.estore.walmart.presenter;

import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.BussinessErrorModel;
import com.estore.walmart.model.NetworkErrorModel;
import com.estore.walmart.utils.AppUtils;

/**
 * Created by suyam on 6/26/2017.
 */

public class ErrorPresenter extends BasePresenter {

    BaseModel mBaseModel;

    public ErrorPresenter(BaseModel baseModel) {
        mBaseModel = baseModel;
    }

    public String getMessage() {
        if (mBaseModel instanceof NetworkErrorModel) {
            return ((NetworkErrorModel) mBaseModel).getMessage();
        } else if (mBaseModel instanceof BussinessErrorModel) {
            return ((BussinessErrorModel) mBaseModel).getMessage();
        } else {
            return AppUtils.emptyString();
        }
    }

    public boolean isNetworkError() {
        return (mBaseModel != null && mBaseModel instanceof NetworkErrorModel);
    }

}
