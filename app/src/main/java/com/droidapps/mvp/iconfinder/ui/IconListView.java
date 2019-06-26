package com.droidapps.mvp.iconfinder.ui;

import com.droidapps.mvp.iconfinder.pojo.IconData;

import java.util.List;

public interface IconListView {

    void showLoader();

    void hideLoader();

    void showList();

    void hideList();

    void showPagingLoader();

    void hidePagingLoader();

    void showDownloadProgress();

    void hideDownloadProgress();

    void onError(String error);

    void onDownloadProcessDone(int messageRes);

    void onDataFetched(List<IconData> iconList);

}
