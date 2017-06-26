package com.estore.walmart.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Display;
import android.view.TextureView;
import android.view.WindowManager;

import com.estore.walmart.BuildConfig;
import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;

import org.xml.sax.XMLReader;

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
            result = Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY, null, new UlTagHandler());
        } else {
            result = Html.fromHtml(value, null, new UlTagHandler());
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

    public static String frameRatting(int reviewCount) {
        if (reviewCount == 0) {
            return WalmartApp.getAppContext().getString(R.string.no_rating_yet);
        } else if (reviewCount == 1) {
            return WalmartApp.getAppContext().getString(R.string.one_rating);
        }
        StringBuilder builder = new StringBuilder();
        builder.append(reviewCount);
        builder.append(" ");
        builder.append(WalmartApp.getAppContext().getString(R.string.rating));
        return new String(builder);
    }

    public static String emptyString() {
        return "";
    }

    public static String getEmptyDollar() {
        return WalmartApp.getAppContext().getString(R.string.zero_dollar);
    }

    public static int getColor(int id) {
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = WalmartApp.getAppContext().getColor(id);
        } else {
            color = WalmartApp.getAppContext().getResources().getColor(id);
        }
        return color;
    }

    public static double getWidth() {
        WindowManager manager = (WindowManager) WalmartApp.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static double getHeight() {
        WindowManager manager = (WindowManager) WalmartApp.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int percentOf(double width, int percentage) {
        return (int) (width * percentage) / 100;
    }

    public static String appendHeading(String productName) {
        if (TextUtils.isEmpty(productName)) {
            return productName;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>");
        stringBuilder.append(productName);
        stringBuilder.append("</b><br/><br/>");

        return productName;
    }

    public static void launchSettings(Context context) {
        if (context == null) {
            return;
        }
        context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }

    static class UlTagHandler implements Html.TagHandler {
        @Override
        public void handleTag(boolean opening, String tag, Editable output,
                              XMLReader xmlReader) {
            if (tag.equals("ul") && !opening) output.append("\n");
            if (tag.equals("li") && opening) output.append("\n\t-  ");
            if (tag.equals("p") && opening) output.append("\n");
        }
    }
}
