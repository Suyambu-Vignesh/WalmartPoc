package com.estore.walmart.model;

import android.os.Parcel;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.views.fragments.ErrorFragment;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class BussinessErrorModel extends BaseModel {
    public static final String TAG = "BussinessErrorModel";
    private String message;

    private BussinessErrorModel() {
    }

    public BussinessErrorModel(String id) {
        super(id);
        message = WalmartApp.getAppContext().getResources().getString(R.string.bussiness_error);
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
        dest.writeString(id);
    }

    protected BussinessErrorModel(Parcel in) {
        id = in.readString();
    }

    public static final Creator<BussinessErrorModel> CREATOR = new Creator<BussinessErrorModel>() {
        @Override
        public BussinessErrorModel createFromParcel(Parcel in) {
            return new BussinessErrorModel(in);
        }

        @Override
        public BussinessErrorModel[] newArray(int size) {
            return new BussinessErrorModel[size];
        }
    };

    public String getMessage() {
        return message;
    }
}
