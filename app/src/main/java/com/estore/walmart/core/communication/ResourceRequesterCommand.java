package com.estore.walmart.core.communication;

import android.text.TextUtils;

import com.estore.walmart.utils.AppUtils;
import com.estore.walmart.BuildConfig;
import com.estore.walmart.utils.WalmartAppException;

import java.net.URL;

/**
 * Created by Suyambu on 6/22/2017.
 */

public class ResourceRequesterCommand implements ResourceRequester.ResourceCommandInfo {
    private ResourceRequester mResourceRequester;
    private Request mRequest;
    private ResourceManager manager;

    public ResourceRequesterCommand() {
        mResourceRequester = new ResourceRequester(this);
        manager = ResourceManager.getInstance();
    }

    /**
     * Method Which return the Runnable command which makes Network call
     *
     * @return Command which makes network call.
     */
    public Runnable getCommand() {
        return mResourceRequester;
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public URL getUrl() {
        if (mRequest == null || TextUtils.isEmpty(mRequest.getCommand())) {
            throw new WalmartAppException(WalmartAppException.URI_CANNOT_BE_NULL);
        }
        return AppUtils.createUrl(mRequest.getCommand());
    }

    @Override
    public int getTimeOut() {
        return BuildConfig.TIME_OUT;
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }

    @Override
    public void processResponse(String response) {
    }
}
