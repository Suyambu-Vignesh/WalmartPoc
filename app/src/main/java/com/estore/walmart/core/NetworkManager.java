package com.estore.walmart.core;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.estore.walmart.WalmartApp;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class NetworkManager {

    private static NetworkManager sInstance;
    private ConnectivityReceiver connectivityReceiver;

    private NetworkManager() {
        connectivityReceiver = WalmartApp.getAppObjectGraph().getConnectivityReceiver();
    }

    public synchronized static NetworkManager getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkManager();
        }
        return sInstance;
    }

    public boolean isOnline(Context context) {
        return connectivityReceiver.isOnline(context);
    }

    public void registerNetworkObserver(Context context) {
        if (context != null) {
            context.registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    public void unRegisterNetworkObserver(Context context) {
        if (context != null) {
            context.unregisterReceiver(connectivityReceiver);
        }
    }
}
