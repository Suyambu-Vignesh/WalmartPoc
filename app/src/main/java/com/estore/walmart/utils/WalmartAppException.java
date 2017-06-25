package com.estore.walmart.utils;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class WalmartAppException extends RuntimeException {
    public static final String EXCEPTION_RESOURCE_COMMAND_NULL = "Resource Command cannot be null";
    public static final String RESOURCE_CANNOT_BE_NULL = "Resource Cannot be null";
    public static final String URI_CANNOT_BE_NULL = "Url cannot be empty or null";
    public static final String VIEW_CANNOT_BE_NULL = "View is null. A view cannot be null";
    public static final String NO_SUCH_RESOURCE = "No Such resource found !!!";

    private WalmartAppException() {
    }

    public WalmartAppException(String message) {
        super(message);
    }
}
