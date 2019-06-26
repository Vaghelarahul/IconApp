package com.droidapps.mvp.iconfinder.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IconPojo {

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("icons")
    @Expose
    List<IconData> list;

    public String getMessage() {
        return message == null ? "Error fetching icons" : message;
    }

    public List<IconData> getList() {
        return list;
    }
}
