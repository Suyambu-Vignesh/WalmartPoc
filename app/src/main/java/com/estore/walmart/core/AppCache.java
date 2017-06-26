package com.estore.walmart.core;

import com.estore.walmart.pojo.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class AppCache {

    public static AppCache sInstance;

    private AppCache() {
    }

    public static AppCache getInstance() {
        if (sInstance == null) {
            sInstance = new AppCache();
        }

        return sInstance;
    }

    private List<Product> mListOfProduct;

    public void appendProduct(List<Product> productList) {
        if (productList == null) {
            return;
        }

        if (mListOfProduct == null) {
            mListOfProduct = new ArrayList<>();
        }

        mListOfProduct.addAll(productList);
    }


    public List<Product> getProducts() {
        if (mListOfProduct == null) {
            mListOfProduct = new ArrayList<>();
        }
        return mListOfProduct;
    }
}
