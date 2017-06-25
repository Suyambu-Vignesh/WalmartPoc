package com.estore.walmart.core.communication;

/**
 * Created by Suyambu on 6/23/2017.
 */

public interface ResponseCallback<T> {
    public void notify(Response<T> t);
}
