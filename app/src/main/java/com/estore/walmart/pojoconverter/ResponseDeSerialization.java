package com.estore.walmart.pojoconverter;

import android.text.TextUtils;

import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.BussinessErrorModel;
import com.estore.walmart.pojo.BasePojo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class ResponseDeSerialization {
    private Map<String, Class<? extends BaseConverter>> mResponseMapping = new HashMap<>();


    public ResponseDeSerialization() {
        mResponseMapping = new HashMap<>();

        mResponseMapping.put(ProductCatalogConverter.TAG, ProductCatalogConverter.class);
    }

    public BaseModel parseResponse(String response) {
        BussinessErrorModel bussinessErrorModel = new BussinessErrorModel();
        if (TextUtils.isEmpty(response)) {
            return bussinessErrorModel;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has(BasePojo.KIND)) {
                String key = jsonObject.getString(BasePojo.KIND);
                if (key != null && mResponseMapping.containsKey(key)) {
                    return (mResponseMapping.get(key).newInstance()).convert(jsonObject);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return bussinessErrorModel;
    }
}
