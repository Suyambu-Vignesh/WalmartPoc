package com.estore.walmart.core.communication;

/**
 * Created by Suyambu on 6/22/2017.
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

    public RequestBuilder(String command, String requestType) {
        mRequest = new Request();
        mRequest.setCommand(command);
        mRequest.setRequestType(requestType);
    }

    public RequestBuilder setResponseCallBack(ResponseCallback<Response> responseCallback) {
        mRequest.setResponseCallback(responseCallback);
        return this;
    }

    public Request build() {
        return mRequest;
    }
}
