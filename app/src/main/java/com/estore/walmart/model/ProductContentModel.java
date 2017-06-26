package com.estore.walmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.opertaions.UIObservable;
import com.estore.walmart.pojo.DialogInfo;
import com.estore.walmart.pojo.Product;
import com.estore.walmart.pojo.WebContent;
import com.estore.walmart.utils.AppUtils;
import com.estore.walmart.views.fragments.ProductContentFragment;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class ProductContentModel extends BaseModel implements Parcelable {
    public static String TAG = "ProductContentModel";

    private Product product;

    private ProductContentModel() {
        setId(TAG);
    }

    public ProductContentModel(Product products) {
        product = products;
    }

    @Override
    public ViewInformation getViewOperation() {
        ViewInformation viewInformation = new ViewInformation();
        viewInformation.addToBackStack = true;
        viewInformation.fragment = ProductContentFragment.getInstnace(this);
        return viewInformation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        product.writeToParcel(dest, flags);
    }

    protected ProductContentModel(Parcel in) {
        super(in);
        product = new Product(in);
    }

    public static final Parcelable.Creator<ProductContentModel> CREATOR = new Parcelable.Creator<ProductContentModel>() {
        @Override
        public ProductContentModel createFromParcel(Parcel in) {
            return new ProductContentModel(in);
        }

        @Override
        public ProductContentModel[] newArray(int size) {
            return new ProductContentModel[size];
        }
    };

    public Product getProduct() {
        return product;
    }

    public void showFullDescription() {
        WebContent webContent = new WebContent();
        webContent.setmLocalContent(AppUtils.appendHeading(product.getProductName())+product.getLongDescription());

        DialogInfo dialogInfo = new DialogInfo();
        dialogInfo.style = R.layout.layout_webview;
        dialogInfo.parameter = webContent;

        WalmartDialogModel walmartDialogModel = WalmartApp.getAppObjectGraph().getDialogModel(dialogInfo);

        UIObservable uiObservable = WalmartApp.getAppObjectGraph().getUIObservable();
        uiObservable.notifyUI(walmartDialogModel);
    }
}
