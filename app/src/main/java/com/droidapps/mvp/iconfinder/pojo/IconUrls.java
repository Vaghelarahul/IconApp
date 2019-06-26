package com.droidapps.mvp.iconfinder.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IconUrls {

    @SerializedName("formats")
    private List<Formats> formatsList;

    public String getPreviewUrl() {
        return formatsList != null ? formatsList.get(0).getPreviewUrl() : "";
    }

    public String getDownloadUrl() {
        return formatsList != null ? formatsList.get(0).getDownloadUrl() : "";
    }

    public class Formats {

        @SerializedName("preview_url")
        private String previewUrl;

        @SerializedName("download_url")
        private String downloadUrl;

        public String getPreviewUrl() {
            return previewUrl;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }
    }

}
