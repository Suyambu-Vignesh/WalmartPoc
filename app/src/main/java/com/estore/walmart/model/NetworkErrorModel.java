package com.estore.walmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.views.fragments.ErrorFragment;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class NetworkErrorModel extends BaseModel implements Parcelable {
    public static final String TAG = "NetworkErrorModel";

    public String message;

    public NetworkErrorModel(String id) {
        super(id);
        message = WalmartApp.getAppContext().getResources().getString(R.string.network_error);
    }

    @Override
    public ViewInformation getViewOperation() {
        ViewInformation viewInformation = new ViewInformation();
        viewInformation.fragment = ErrorFragment.getInstance(this);
        viewInformation.addToBackStack = false;
        return viewInformation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected NetworkErrorModel(Parcel in) {
        super(in);
    }

    public static final Creator<NetworkErrorModel> CREATOR = new Creator<NetworkErrorModel>() {
        @Override
        public NetworkErrorModel createFromParcel(Parcel in) {
            return new NetworkErrorModel(in);
        }

        @Override
        public NetworkErrorModel[] newArray(int size) {
            return new NetworkErrorModel[size];
        }
    };

    public String getMessage() {
        return message;
    }
}
