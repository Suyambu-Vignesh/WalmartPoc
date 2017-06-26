package com.estore.walmart.pojo;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class ProductSort {
    public static final String PRICE_LOW_HIGH = "Price: Low to High";
    public static final String PRICE_HIGH_LOW = "Price: High to Low";
    public static final String TOP_RATED = "Top Rated";
    public static final String BEST_MATCHES = "Best Matches";

    private String mSortOrder;

    public ProductSort() {
        mSortOrder = BEST_MATCHES;
    }

    public ProductSort(String sortOrder) {
        mSortOrder = sortOrder;
    }

    public String getSortOrder() {
        return mSortOrder;
    }
}
