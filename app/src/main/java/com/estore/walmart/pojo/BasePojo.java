package com.estore.walmart.pojo;

import org.json.JSONObject;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class BasePojo implements PojoParser {

    public static final String KIND = "kind";
    public static final String STATUS = "status";

    protected Integer status;
    protected String kind;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public JSONObject toJson() {
        return null;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        kind = jsonObject.optString(KIND);

        status = jsonObject.optInt(STATUS);
    }
}
