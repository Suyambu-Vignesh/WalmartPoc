package com.estore.walmart.core.log;

/**
 * Created by Suyambu on 6/22/2017.
 */

public interface LogHandler {

    void e(String var1, String var2);

    void e(String var1, String var2, Throwable var3);

    void d(String var1, String var2);

    void d(String var1, String var2, Throwable var3);

    void v(String var1, String var2);

    void v(String var1, String var2, Throwable var3);

    void w(String var1, String var2);

    void w(String var1, String var2, Throwable var3);

    void i(String var1, String var2);

    void i(String var1, String var2, Throwable var3);
}
