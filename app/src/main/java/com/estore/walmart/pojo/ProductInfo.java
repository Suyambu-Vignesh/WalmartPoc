
package com.estore.walmart.pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductInfo extends BasePojo {
    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCT = "products";
    private static final String KEY_TOTAL_PRODUCTS = "totalProducts";
    private static final String KEY_PAGE_NUMBER = "pageNumber";
    private static final String KEY_PAGE_SIZE = "pageSize";
    private static final String KEY_ETAG = "etag";

    private String id;
    private List<Product> products = null;
    private Integer totalProducts;
    private Integer pageNumber;
    private Integer pageSize;
    private String etag;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
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
        JSONObject jsonObject = super.toJson();
        // // TODO: 6/23/2017 need to implement the Object to JSON.
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        id = jsonObject.optString(KEY_ID);

        totalProducts = jsonObject.optInt(KEY_TOTAL_PRODUCTS);

        pageNumber = jsonObject.optInt(KEY_PAGE_NUMBER);

        pageSize = jsonObject.optInt(KEY_PAGE_SIZE);

        etag = jsonObject.optString(KEY_ETAG);

        JSONArray jsonArray = jsonObject.optJSONArray(KEY_PRODUCT);
        if (jsonArray != null && jsonArray.length() > 0) {
            parseProducts(jsonArray);
        }

        super.fromJson(jsonObject);
    }

    /**
     * Helper method to form array of Products rom JSON
     *
     * @param jsonArray from which list of products need to be formed.
     */
    private void parseProducts(JSONArray jsonArray) {
        int index = -1;
        products = new ArrayList<>();
        while (++index < jsonArray.length()) {
            JSONObject productJson = jsonArray.optJSONObject(index);
            if (productJson != null) {
                Product product = new Product();
                product.fromJson(productJson);
                products.add(product);
            }
        }
    }
}
