package com.droidapps.mvp.iconfinder.di;

import com.droidapps.mvp.iconfinder.ui.IconListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = IconModule.class)
public interface IconComponent {
    void inject(IconListActivity activity);
}
