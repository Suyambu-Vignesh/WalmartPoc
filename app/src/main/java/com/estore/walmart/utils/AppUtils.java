package com.estore.walmart.utils;

import com.estore.walmart.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Suyambu on 6/22/2017.
 */

public class AppUtils {

    /**
     * Helper Method to frame the URL from input command;
     *
     * @param command the command which wil be appended base url;
     * @return the Url which framed by appending command to url
     */
    public static URL createUrl(String command) {
        URL url = null;
        StringBuilder stringBuilder = new StringBuilder(BuildConfig.appBaseUrl);
        stringBuilder.append(command);
        try {
            url = new URL(stringBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
