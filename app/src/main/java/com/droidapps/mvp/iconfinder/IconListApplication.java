package com.droidapps.mvp.iconfinder;

import android.app.Application;
import android.content.Context;

import com.droidapps.mvp.iconfinder.di.DaggerIconComponent;
import com.droidapps.mvp.iconfinder.di.IconComponent;
import com.droidapps.mvp.iconfinder.di.IconModule;
import com.droidapps.mvp.iconfinder.ui.IconListView;
import com.droidapps.mvp.iconfinder.ui.adapter.DownloadListener;

public class IconListApplication extends Application {

    public IconComponent getIconComponent(Context context, IconListView iconListView, DownloadListener downloadListener) {
        return DaggerIconComponent.builder()
                .iconModule(new IconModule(context, iconListView, downloadListener))
                .build();
    }
}
