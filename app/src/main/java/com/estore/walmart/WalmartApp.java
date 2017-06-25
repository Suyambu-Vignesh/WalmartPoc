package com.estore.walmart;

import android.app.Application;
import android.content.Context;

import com.estore.walmart.core.communication.ModuleInjection;
import com.estore.walmart.core.dependencyinjection.DebugModuleInjection;
import com.estore.walmart.core.dependencyinjection.ReleaseModuleInjection;


/**
 * Created by Suyambu on 6/23/2017.
 */

public class WalmartApp extends Application {

    private static Context appContext;

    private ModuleInjection mModuleInjection;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    //-------------------------------------------------------------------------------------------------
    /*
    Return the Context of the Application
     */
    public static Context getAppContext() {
        return appContext;
    }

    public static ModuleInjection getAppObjectGraph() {
        return ((WalmartApp) (appContext)).getModuleInjection();
    }

    private ModuleInjection getModuleInjection() {
        if (mModuleInjection == null) {
            mModuleInjection = BuildConfig.isProduction ? new ReleaseModuleInjection() : new DebugModuleInjection();
        }
        return mModuleInjection;
    }
}
