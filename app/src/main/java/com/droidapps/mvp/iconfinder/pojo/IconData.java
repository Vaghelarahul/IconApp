package com.droidapps.mvp.iconfinder.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IconData {

    @SerializedName("icon_id")
    @Expose
    private String iconId;

    @SerializedName("is_premium")
    private boolean isPremium;

    @SerializedName("prices")
    @Expose
    private List<Prices> prices;

    @SerializedName("raster_sizes")
    private List<IconUrls> iconUrls;

    @SerializedName("tags")
    private List<String> tagNames;

    private String iconName;

    public String getIconName() {
        return tagNames.get(1);
    }

    public String getIconId() {
        return iconId;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public String getPrice() {
        return prices.get(0).getPrice();
    }

    public IconUrls getIconUrls() {
        return iconUrls.get(4);
    }

}
