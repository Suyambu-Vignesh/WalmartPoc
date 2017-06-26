package com.estore.walmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.estore.walmart.core.ViewInformation;

/**
 * Created by Suyambu on 6/23/2017.
 */

public abstract class BaseModel implements Parcelable {
    public static final String TAG = "BaseModel";
    protected String id;

    protected BaseModel() {
    }

    protected BaseModel(String id) {
        this.id = id;
    }

    public BaseModel(Parcel in) {
        id = in.readString();
    }

    public abstract ViewInformation getViewOperation();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }
}
