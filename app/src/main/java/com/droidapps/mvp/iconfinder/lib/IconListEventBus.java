package com.droidapps.mvp.iconfinder.lib;

import com.droidapps.mvp.iconfinder.network.IconEvent;

import org.greenrobot.eventbus.EventBus;

public class IconListEventBus implements IconEventBus {

    private EventBus eventBus;

    public IconListEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void subscribe(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unSubscribe(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(IconEvent event) {
        eventBus.post(event);
    }
}
