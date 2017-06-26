package com.estore.walmart.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class DialogInfo implements Parcelable {
    public static final int STYLE_WEB_VIEW = 0;

    /**
     * Style of the The Dialog view
     */
    public int style;

    /**
     * extra parameter from which help to render the view
     */
    public Serializable parameter;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(style);
        dest.writeSerializable(parameter);
    }

    public DialogInfo(){
    }

    public DialogInfo(Parcel in) {
        style = in.readInt();
        parameter = in.readSerializable();
    }

    public static final Creator<DialogInfo> CREATOR = new Creator<DialogInfo>() {
        @Override
        public DialogInfo createFromParcel(Parcel in) {
            return new DialogInfo(in);
        }

        @Override
        public DialogInfo[] newArray(int size) {
            return new DialogInfo[size];
        }
    };
}
