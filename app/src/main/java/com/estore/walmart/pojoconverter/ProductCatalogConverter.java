package com.estore.walmart.pojoconverter;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.ProductCatalogModel;
import com.estore.walmart.pojo.ProductInfo;

import org.json.JSONObject;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class ProductCatalogConverter implements BaseConverter {
    public static final String TAG = "walmart#resourcesItem";

    @Override
    public String toString() {
        return TAG;
    }

    @Override
    public BaseModel convert(JSONObject response) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.fromJson(response);

        ProductCatalogModel productCatalogModel = WalmartApp.getAppObjectGraph().getProductCatalogModel();
        productCatalogModel.appendProductInfo(productInfo);

        return productCatalogModel;
    }
}
