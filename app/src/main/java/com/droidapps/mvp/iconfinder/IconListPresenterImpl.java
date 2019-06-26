package com.droidapps.mvp.iconfinder;

import android.text.TextUtils;

import com.droidapps.mvp.iconfinder.lib.IconEventBus;
import com.droidapps.mvp.iconfinder.network.IconEvent;
import com.droidapps.mvp.iconfinder.ui.IconListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.droidapps.mvp.iconfinder.utils.Constants.ERROR;
import static com.droidapps.mvp.iconfinder.utils.Constants.SUCCESS;

public class IconListPresenterImpl implements IconListPresenter {

    private IconEventBus eventBus;
    private IconListView iconListView;
    private IconListModel iconListModel;

    public IconListPresenterImpl(IconListView iconListView, IconListModel iconListModel, IconEventBus eventBus) {
        this.eventBus = eventBus;
        this.iconListView = iconListView;
        this.iconListModel = iconListModel;
    }

    @Override
    public void onResume() {
        eventBus.subscribe(this);
    }

    @Override
    public void onPause() {
        eventBus.unSubscribe(this);
    }

    @Override
    public void onDestroy() {
        iconListView = null;
    }

    @Override
    public void downloadIcon(String url) {
        iconListView.showDownloadProgress();
        iconListModel.downloadIcon(url);
    }

    @Override
    public void fetchIconList(String lastIconId) {
        if (TextUtils.isEmpty(lastIconId)) {
            iconListView.showLoader();
        } else {
            iconListView.showPagingLoader();
        }
        iconListModel.fetchIconList(lastIconId);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(IconEvent event) {
        if (iconListView == null) return;

        if (event.getError() != null) {
            if (event.getError().equals(SUCCESS) || event.getError().equals(ERROR)) {

                int stringRes = -1;
                if (event.getError().equals(SUCCESS)) {
                    stringRes = R.string.download_success;
                } else if (event.getError().equals(ERROR)) {
                    stringRes = R.string.download_error;
                }
                iconListView.onDownloadProcessDone(stringRes);
                return;
            } else {
                iconListView.onError(event.getError());
            }

        } else {
            iconListView.onDataFetched(event.getList());
        }

        iconListView.hideLoader();
        iconListView.hidePagingLoader();
    }


}
