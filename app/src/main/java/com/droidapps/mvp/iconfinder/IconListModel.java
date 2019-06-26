package com.droidapps.mvp.iconfinder;

public interface IconListModel {

    void fetchIconList(String lastIconId);

    void downloadIcon(String url);
}
