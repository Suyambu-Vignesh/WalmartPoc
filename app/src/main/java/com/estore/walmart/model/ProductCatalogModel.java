package com.estore.walmart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ViewInformation;
import com.estore.walmart.core.communication.Request;
import com.estore.walmart.core.communication.RequestBuilder;
import com.estore.walmart.core.communication.ResourceManager;
import com.estore.walmart.pojo.Product;
import com.estore.walmart.pojo.ProductInfo;
import com.estore.walmart.utils.AppUtils;
import com.estore.walmart.views.fragments.ProductHomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class ProductCatalogModel implements BaseModel, Parcelable {
    public static final String TAG = ProductCatalogModel.class.getSimpleName();
    private int mPageNumber;
    private int mMaxNumberItem;
    private int mTotalNumberOfProduct;
    private String mETag;
    private List<Product> mProducts;
    private String mCommand;
    private ResourceManager mResourceManager;
    private boolean isReachedEnd;
    private int mNewNumberOfItem;

    private static ProductCatalogModel sProductCatalogModel;

    private ProductCatalogModel(int maxNumberOfItem, String command) {
        this.mMaxNumberItem = maxNumberOfItem;
        mCommand = command;
        mResourceManager = WalmartApp.getAppObjectGraph().getResourceManager();
        isReachedEnd = false;
    }

    protected ProductCatalogModel(Parcel in) {
        mPageNumber = in.readInt();
        mMaxNumberItem = in.readInt();
        mTotalNumberOfProduct = in.readInt();
        mETag = in.readString();
        mProducts = in.createTypedArrayList(Product.CREATOR);
        mCommand = in.readString();
    }

    public static final Creator<ProductCatalogModel> CREATOR = new Creator<ProductCatalogModel>() {
        @Override
        public ProductCatalogModel createFromParcel(Parcel in) {
            return new ProductCatalogModel(in);
        }

        @Override
        public ProductCatalogModel[] newArray(int size) {
            return new ProductCatalogModel[size];
        }
    };

    public static synchronized ProductCatalogModel getProductCatalogModel(int maxNumberItem, String command) {
        if (sProductCatalogModel == null) {
            sProductCatalogModel = new ProductCatalogModel(maxNumberItem, command);
        }
        return sProductCatalogModel;
    }

    public int getTotalNumberOfItem() {
        if (mProducts == null) {
            return 0;
        }
        return mProducts.size();
    }

    public void loadInitailData() {
        mPageNumber = 1;

        makeRequest(mPageNumber);
    }

    public void loadNextPageData() {
        makeRequest(mPageNumber + 1);
    }

    private void makeRequest(int pageNumber) {
        Object[] appendElement = new Object[2];
        appendElement[0] = pageNumber;
        appendElement[1] = mMaxNumberItem;

        String url = AppUtils.formatString(mCommand, appendElement);

        Request request = new RequestBuilder(url)
                .build();
        WalmartApp.getAppObjectGraph().getLogHandler().d("Suyambu", url + " Making request " + toString());
        mResourceManager.processRequest(request);
    }

    public void appendProductInfo(ProductInfo productInfo) {
        if (productInfo == null) {
            return;
        }
        mPageNumber = productInfo.getPageNumber();
        mTotalNumberOfProduct = productInfo.getTotalProducts();
        mETag = productInfo.getEtag();

        if (productInfo.getProducts() == null) {
            return;
        }

        if (mProducts == null) {
            mProducts = new ArrayList<>();
        }

        if (productInfo.getProducts() == null || productInfo.getProducts().size() == 0) {
            isReachedEnd = true;
            mNewNumberOfItem = 0;
            return;
        }
        mNewNumberOfItem = productInfo.getProducts().size();
        mProducts.addAll(productInfo.getProducts());
    }

    public int getNewItemCount() {
        return mNewNumberOfItem;
    }

    public Product getProduct(int position) {
        if (mProducts == null || mProducts.size() < position) {
            return null;
        } else {
            return mProducts.get(position);
        }
    }

    @Override
    public ViewInformation getViewOperation() {
        ViewInformation viewInformation = new ViewInformation();
        viewInformation.fragment = ProductHomeFragment.getInstance(this);
        viewInformation.addToBackStack = false;
        return viewInformation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mPageNumber);
        dest.writeInt(mMaxNumberItem);
        dest.writeInt(mTotalNumberOfProduct);
        dest.writeString(mETag);
        dest.writeTypedList(mProducts);
        dest.writeString(mCommand);
    }

    public boolean canLoadMoreItem() {
        return !isReachedEnd;
    }
}
