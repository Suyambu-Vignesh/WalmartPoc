package com.estore.walmart.pojoconverter;

import com.estore.walmart.model.BaseModel;

import org.json.JSONObject;

/**
 * Created by Suyambu on 6/23/2017.
 */

public interface BaseConverter {
    public BaseModel convert(JSONObject response);
}
