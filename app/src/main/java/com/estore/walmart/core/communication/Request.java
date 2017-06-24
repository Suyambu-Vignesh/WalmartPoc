package com.estore.walmart.core.communication;

import java.util.UUID;

/**
 * Created by Suyambu on 6/22/2017.
 */

public class Request {
    public static final String CONNECTION_KEEP_LIVE = "Keep-Alive";
    public static final String CONTENT_JSON = "application/json;charset=UTF-8";

    public static final String REQUEST_POST = "POST";
    public static final String REQUEST_GET = "GET";

    private String requestType;
    private UUID mId;
    private ResponseCallback<Response> responseCallback;
    private String command;
    private String contentType;

    public Request() {
        mId = UUID.randomUUID();
    }

    public String getRequestType() {
        return requestType;
    }

    public String getConnectionState() {
        return CONNECTION_KEEP_LIVE;
    }

    public String getContentType() {
        return contentType == null ? CONTENT_JSON : contentType;
    }

    public UUID getId() {
        return mId;
    }

    public String getCommand() {
        return command;
    }

    public ResponseCallback<Response> getResponseCallback() {
        return responseCallback;
    }

    public void setResponseCallback(ResponseCallback<Response> responseCallback) {
        this.responseCallback = responseCallback;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
}
