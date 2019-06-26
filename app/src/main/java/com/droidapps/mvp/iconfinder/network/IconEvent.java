package com.droidapps.mvp.iconfinder.network;

import com.droidapps.mvp.iconfinder.pojo.IconData;

import java.util.List;

public class IconEvent {

    private String error;
    private String downloadMessage;
    private List<IconData> list;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<IconData> getList() {
        return list;
    }

    public String getDownloadMessage() {
        return downloadMessage;
    }

    public void setDownloadMessage(String downloadMessage) {
        this.downloadMessage = downloadMessage;
    }

    public void setList(List<IconData> list) {
        this.list = list;
    }
}
