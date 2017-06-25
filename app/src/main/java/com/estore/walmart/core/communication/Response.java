package com.estore.walmart.core.communication;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class Response<T> {
    /**
     * Happy path Response.
     */
    private T response;

    /**
     * Unexpected Response
     */
    private ErrorResponse errorResponse;

    public T getSuccessResponse() {
        return response;
    }

    public void setSuccessResponse(T response) {
        this.response = response;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public boolean isSuccess() {
        return response != null;
    }

    T createEmptySuccessResponse(Class<T> clazz) {
        T newInstance = null;
        try {
            newInstance = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return newInstance;
    }
}
