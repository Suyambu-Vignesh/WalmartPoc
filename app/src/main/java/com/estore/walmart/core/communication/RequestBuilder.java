package com.estore.walmart.core.communication;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class RequestBuilder {
    private Request mRequest;

    private RequestBuilder() {
    }

    public RequestBuilder(String command) {
        mRequest = new Request();
        mRequest.setCommand(command);
        mRequest.setRequestType(Request.REQUEST_GET);
    }

    public RequestBuilder setRequestType(String requestType) {
        mRequest.setRequestType(requestType);
        return this;
    }

    public Request build() {
        return mRequest;
    }
}
