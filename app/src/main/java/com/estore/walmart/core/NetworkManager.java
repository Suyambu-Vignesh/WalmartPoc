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

    public void registerNetworkObserver(Context context,ConnectivityReceiver.onConnectionStatusChanged listener) {
        if (context != null) {
            context.registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            connectivityReceiver.setOnConnectionChanged(listener);
        }
    }

    public void unRegisterNetworkObserver(Context context,ConnectivityReceiver.onConnectionStatusChanged listener) {
        if (context != null) {
            context.unregisterReceiver(connectivityReceiver);
            connectivityReceiver.removeConnectionChanged(listener);
        }
    }
}
