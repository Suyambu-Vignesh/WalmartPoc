package com.estore.walmart.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.pojo.DialogInfo;
import com.estore.walmart.pojo.WebContent;
import com.estore.walmart.views.fragments.WalmartDialogFragment;

import java.io.Serializable;

/**
 * Created by suyam on 6/25/2017.
 */

public class WalmartDialogModel extends BaseModel {
    public static final String TAG = WalmartDialogModel.class.getSimpleName();

    private DialogInfo dialogInfo;

    public WalmartDialogModel(DialogInfo dialogInfo) {
        this.dialogInfo = dialogInfo;
    }

    @Override
    public ViewInformation getViewOperation() {
        ViewInformation viewInformation = new ViewInformation();
        viewInformation.fragment = WalmartDialogFragment.getInstance(this);
        viewInformation.viewType = ViewInformation.VIEW_TYPE_DIALOG;

        return viewInformation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dialogInfo.writeToParcel(dest, flags);
    }

    protected WalmartDialogModel(Parcel in) {
        super(in);
        dialogInfo = new DialogInfo(in);
    }

    public static final Parcelable.Creator<WalmartDialogModel> CREATOR = new Parcelable.Creator<WalmartDialogModel>() {
        @Override
        public WalmartDialogModel createFromParcel(Parcel in) {
            return new WalmartDialogModel(in);
        }

        @Override
        public WalmartDialogModel[] newArray(int size) {
            return new WalmartDialogModel[size];
        }
    };

    public Serializable getExtraParams() {
        return dialogInfo.parameter;
    }

    public int getViewType() {
        return dialogInfo.style;
    }
}
