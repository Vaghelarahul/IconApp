package com.droidapps.mvp.iconfinder;

import com.droidapps.mvp.iconfinder.network.IconEvent;

public interface IconListPresenter {

    void onResume();

    void onPause();

    void onDestroy();

    void downloadIcon(String url);

    void fetchIconList(String lastIconId);

    void onEventMainThread(IconEvent event);

}
