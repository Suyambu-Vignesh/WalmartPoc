package com.estore.walmart.pojo;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class WebContent implements Serializable {
    /**
     * Url from which the webview has to load the data.
     */
    private String mUrl;

    /**
     * local html content using which the view will render
     */
    private String mLocalContent;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public void setmLocalContent(String content) {
        mLocalContent = content;
    }

    public String getLocalContent() {
        return mLocalContent;
    }

    public boolean isLocalData() {
        return TextUtils.isEmpty(mUrl);
    }
}
