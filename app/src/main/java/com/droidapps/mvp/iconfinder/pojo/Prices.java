package com.droidapps.mvp.iconfinder.pojo;

import com.google.gson.annotations.SerializedName;

public class Prices {

    @SerializedName("price")
    private String price;

    @SerializedName("currency")
    private String currency;

    public String getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
