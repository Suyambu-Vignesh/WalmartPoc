package com.estore.walmart.core.log;

import android.util.Log;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class DebugLogHandler implements LogHandler {
    @Override
    public void e(String var1, String var2) {
        Log.d(var1, var2);
    }

    @Override
    public void e(String var1, String var2, Throwable var3) {
        Log.e(var1, var2, var3);
    }

    @Override
    public void d(String var1, String var2) {
        Log.d(var1, var2);
    }

    @Override
    public void d(String var1, String var2, Throwable var3) {
        Log.d(var1, var2, var3);
    }

    @Override
    public void v(String var1, String var2) {
        Log.v(var1, var2);
    }

    @Override
    public void v(String var1, String var2, Throwable var3) {
        Log.v(var1, var2, var3);
    }

    @Override
    public void w(String var1, String var2) {
        Log.w(var1, var2);
    }

    @Override
    public void w(String var1, String var2, Throwable var3) {
        Log.w(var1, var2, var3);
    }

    @Override
    public void i(String var1, String var2) {
        Log.i(var1, var2);
    }

    @Override
    public void i(String var1, String var2, Throwable var3) {
        Log.i(var1, var2, var3);
    }
}
