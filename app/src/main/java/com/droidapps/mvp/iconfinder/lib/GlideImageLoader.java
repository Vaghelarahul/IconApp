package com.droidapps.mvp.iconfinder.lib;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;

public class GlideImageLoader implements ImageLoader {

    private RequestManager requestManager;

    public GlideImageLoader(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    public void loadImage(ImageView view, String url) {
        requestManager
                .load(url)
                .into(view);
    }
}
