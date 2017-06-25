package com.estore.walmart.utils;

import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.TextureView;

import com.estore.walmart.BuildConfig;
import com.estore.walmart.WalmartApp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Formatter;

/**
 * Created by Suyambu on 6/23/2017.
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

    /**
     * Utils method which returns a formatted string using the specified format string and arguments.
     *
     * @param mCommand      String to be formatted
     * @param appendElement arguments to be appended.
     * @return Formatted String
     */
    public static String formatString(String mCommand, Object[] appendElement) {
        if (appendElement == null || appendElement.length == 0) {
            return mCommand;
        }

        StringBuilder stringBuilder = new StringBuilder();

        Formatter formatter = new Formatter(stringBuilder);
        formatter.format(mCommand, appendElement);

        return stringBuilder.toString();
    }

    /**
     * Apply the HMTML tag elements to create a spanned text
     *
     * @param value value to be formatted
     * @return Formatted spanned text.
     */
    public static Spanned formatText(String value) {
        if (value == null) {
            value = "";
        }
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(value);
        }
        return result;
    }

    /**
     * Convert resource Id to String
     *
     * @param resourceId resource Id for which we need to find mapped String value
     * @return Mapped String value in resource.
     */
    public static String getString(int resourceId) {
        try {
            return WalmartApp.getAppContext().getString(resourceId);
        } catch (Resources.NotFoundException e) {
            throw new WalmartAppException(WalmartAppException.NO_SUCH_RESOURCE);
        }
    }

    public static float getDimens(int resourceId) {
        try {
            return WalmartApp.getAppContext().getResources().getDimension(resourceId);
        } catch (Resources.NotFoundException e) {
            throw new WalmartAppException(WalmartAppException.NO_SUCH_RESOURCE);
        }
    }

    public static String appendBraces(String value) {
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        StringBuilder stringBuilder = new StringBuilder("(");
        stringBuilder.append(value);
        stringBuilder.append(")");
        return new String(stringBuilder);
    }
}
