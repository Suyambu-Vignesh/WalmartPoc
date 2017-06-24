package com.estore.walmart.model;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.communication.Request;
import com.estore.walmart.core.communication.RequestBuilder;
import com.estore.walmart.core.communication.ResourceManager;
import com.estore.walmart.core.communication.Response;
import com.estore.walmart.core.communication.ResponseCallback;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class ProductCatalogModel implements ResponseCallback<Response> {
    private int mPageNumber;
    private int mMaxNumberItem;
    private String mCommand;
    private ResourceManager mResourceManager;

    private static ProductCatalogModel sProductCatalogModel;

    private ProductCatalogModel(int maxNumberOfItem, String command) {
        this.mMaxNumberItem = maxNumberOfItem;
        mCommand = command;
        mResourceManager = WalmartApp.getAppObjectGraph().getResourceManager();
    }

    public static synchronized ProductCatalogModel getProductCatalogModel(int maxNumberItem, String command) {
        if (sProductCatalogModel == null) {
            sProductCatalogModel = new ProductCatalogModel(maxNumberItem, command);
        }
        return sProductCatalogModel;
    }


    public void loadInitailData() {
        mPageNumber = 1;

        Request request = new RequestBuilder(mCommand)
                .setResponseCallBack(this)
                .build();

    }

    @Override
    public void notify(Response response) {

    }
}
