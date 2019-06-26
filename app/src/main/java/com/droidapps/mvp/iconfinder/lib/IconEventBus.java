package com.droidapps.mvp.iconfinder.lib;

import com.droidapps.mvp.iconfinder.network.IconEvent;

public interface IconEventBus {

    void subscribe(Object object);

    void unSubscribe(Object object);

    void post(IconEvent event);
}
