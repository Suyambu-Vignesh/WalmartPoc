package com.estore.walmart.core.communication;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class ErrorResponse {
    public static final String NETWORK_ERROR = "Please check your Network error";
    public static final String BUSSINESS_ERROR = "Something Went bad!. Please try later.";
    public static final String NO_PRODUCT_ERROR = "No product matches your search";

    private String mError;

    public ErrorResponse(String error) {
        mError = error;
    }

    public String getError() {
        return mError;
    }
}
