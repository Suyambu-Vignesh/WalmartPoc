package com.estore.walmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.pojo.Product;
import com.estore.walmart.views.fragments.ProductDetailFragment;

import java.util.List;

/**
 * Created by Suyambu on 6/25/2017.
 */

/***
 * Model which act as a data Layer for Detail Page.
 */
public class ProductDetailModel extends BaseModel implements Parcelable {
    public static String TAG = "ProductDetailModel";

    private int mSelectedPosition;
    private List<Product> mProducts;

    private ProductDetailModel() {
        setId(TAG);
    }

    public ProductDetailModel(int selectedPosition, List<Product> products) {
        mSelectedPosition = selectedPosition;
        this.mProducts = products;
    }

    @Override
    public ViewInformation getViewOperation() {
        ViewInformation viewInformation = new ViewInformation();
        viewInformation.addToBackStack = true;
        viewInformation.fragment = ProductDetailFragment.getInstnace(this);
        return viewInformation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mSelectedPosition);
        dest.writeTypedList(mProducts);
    }

    protected ProductDetailModel(Parcel in) {
        super(in);
        mSelectedPosition = in.readInt();
        mProducts = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Creator<ProductDetailModel> CREATOR = new Creator<ProductDetailModel>() {
        @Override
        public ProductDetailModel createFromParcel(Parcel in) {
            return new ProductDetailModel(in);
        }

        @Override
        public ProductDetailModel[] newArray(int size) {
            return new ProductDetailModel[size];
        }
    };

    public int getTotalElements() {
        if (mProducts == null) {
            return 0;
        }
        return mProducts.size();
    }

    public int getSelectedItem() {
        return mSelectedPosition;
    }

    public void setSelectedItem(int position) {
        mSelectedPosition = position;
    }

    public Product getProductAt(int index) {
        if (mProducts == null || index < 0 || index >= mProducts.size()) {
            return null;
        }

        return mProducts.get(index);
    }
}
