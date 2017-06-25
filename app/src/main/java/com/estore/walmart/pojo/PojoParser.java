package com.estore.walmart.pojo;

import org.json.JSONObject;

/**
 * Created by Suyambu on 6/23/2017.
 */

public interface PojoParser {


    /**
     * Method which converts the Pojo to JSON Object
     * @return return the framed {@link JSONObject} instance
     */
    public abstract JSONObject toJson();

    /**
     * Method which convert JSON to POJO.
     * @param jsonObject Josn object which need to be parsed.
     */
    public abstract void fromJson(JSONObject jsonObject);
}
