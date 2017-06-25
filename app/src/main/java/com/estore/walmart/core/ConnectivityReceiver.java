package com.estore.walmart.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class ConnectivityReceiver extends BroadcastReceiver {
    private static final int ONLINE = 1;
    private static final int OFFLINE = 2;

    private static int mConnectionStatus = -1;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        //Toast.makeText(context,"Connection Changed",Toast.LENGTH_SHORT).show();
        if (mOnConnectionStatusChanged.size() == 0) {
            return;
        }

        boolean isOnline = isOnline(context);

        int newConnectionStatus = isOnline ? ONLINE : OFFLINE;

        if (newConnectionStatus == mConnectionStatus) {
            return;
        }

        mConnectionStatus = newConnectionStatus;

        for (onConnectionStatusChanged connectionStatusChanged : mOnConnectionStatusChanged) {
            connectionStatusChanged.onConnectionChanged(isOnline);
        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    //**********************************************************************************************

    private static List<onConnectionStatusChanged> mOnConnectionStatusChanged = new ArrayList<>();

    public static interface onConnectionStatusChanged {
        public void onConnectionChanged(boolean isOnline);
    }

    public static void setOnConnectionChanged(onConnectionStatusChanged onConnectionStatusChanged) {
        if (onConnectionStatusChanged == null) {
            return;
        }
        mOnConnectionStatusChanged.add(onConnectionStatusChanged);
    }

    public static boolean removeConnectionChanged(onConnectionStatusChanged onConnectionStatusChanged) {
        return (mOnConnectionStatusChanged.size() != 0 && onConnectionStatusChanged != null &&
                mOnConnectionStatusChanged.remove(onConnectionStatusChanged));
    }
}

