
package com.estore.walmart.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.estore.walmart.utils.AppUtils;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Product implements PojoParser, Parcelable {
    private static String KEY_PRODUCT_ID = "productId";
    private static String KEY_PRODUCT_NMAE = "productName";
    private static String KEY_SHORT_DESCRIPTION = "shortDescription";
    private static String KEY_LONG_DESCRIPTION = "longDescription";
    private static String KEY_PRICE = "price";
    private static String KEY_PRODUCT_IMAGE = "productImage";
    private static String KEY_REVIEW_RATING = "reviewRating";
    private static String KEY_REVIEW_COUNT = "reviewCount";
    private static String KEY_IN_STOCK = "inStock";

    private String productId;
    private String productName;
    private String shortDescription;
    private String longDescription;
    private String price;
    private String productImage;
    private Double reviewRating;
    private Integer reviewCount;
    private Boolean inStock;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Product() {
    }

    public Product(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        shortDescription = in.readString();
        longDescription = in.readString();
        price = in.readString();
        productImage = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName == null ? AppUtils.emptyString() : productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription == null ? AppUtils.emptyString() : shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return TextUtils.isEmpty(price) ? AppUtils.getEmptyDollar() : price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Double getReviewRating() {
        DecimalFormat df = new DecimalFormat("#.0");
        return new Double(df.format(reviewRating));
    }

    public void setReviewRating(Double reviewRating) {
        this.reviewRating = reviewRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    //-------------------------------------------------------------------------------------------------
    @Override
    public JSONObject toJson() {
        // // TODO: 6/23/2017 need to implement the Object to JSON.
        return null;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        productId = jsonObject.optString(KEY_PRODUCT_ID);
        productName = jsonObject.optString(KEY_PRODUCT_NMAE);
        shortDescription = jsonObject.optString(KEY_SHORT_DESCRIPTION);
        longDescription = jsonObject.optString(KEY_LONG_DESCRIPTION);
        price = jsonObject.optString(KEY_PRICE);
        productImage = jsonObject.optString(KEY_PRODUCT_IMAGE);
        reviewRating = jsonObject.optDouble(KEY_REVIEW_RATING);
        reviewCount = jsonObject.optInt(KEY_REVIEW_COUNT);
        inStock = jsonObject.optBoolean(KEY_IN_STOCK);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productId);
        dest.writeString(productName);
        dest.writeString(shortDescription);
        dest.writeString(longDescription);
        dest.writeString(price);
        dest.writeString(productImage);
    }
}
