package com.droidapps.mvp.iconfinder.di;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.droidapps.mvp.iconfinder.IconListModel;
import com.droidapps.mvp.iconfinder.IconListModelImpl;
import com.droidapps.mvp.iconfinder.IconListPresenter;
import com.droidapps.mvp.iconfinder.IconListPresenterImpl;
import com.droidapps.mvp.iconfinder.lib.GlideImageLoader;
import com.droidapps.mvp.iconfinder.lib.IconEventBus;
import com.droidapps.mvp.iconfinder.lib.IconListEventBus;
import com.droidapps.mvp.iconfinder.lib.ImageLoader;
import com.droidapps.mvp.iconfinder.network.RetrofitService;
import com.droidapps.mvp.iconfinder.pojo.IconData;
import com.droidapps.mvp.iconfinder.ui.IconListView;
import com.droidapps.mvp.iconfinder.ui.adapter.DownloadListener;
import com.droidapps.mvp.iconfinder.ui.adapter.IconListAdapter;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.droidapps.mvp.iconfinder.network.RetrofitService.BASE_URL;

@Module
public class IconModule {

    private Context context;
    private IconListView iconListView;
    private DownloadListener downloadListener;

    public IconModule(Context context, IconListView iconListView, DownloadListener downloadListener) {
        this.context = context;
        this.iconListView = iconListView;
        this.downloadListener = downloadListener;
    }

    @Provides
    @Singleton
    GridLayoutManager getLayoutManager(Context context) {
        return new GridLayoutManager(context, 2);
    }

    @Provides
    @Singleton
    IconListAdapter getIconListAdapter(List<IconData> list, ImageLoader imageLoader, DownloadListener downloadListener) {
        return new IconListAdapter(list, imageLoader, downloadListener);
    }

    @Provides
    @Singleton
    List<IconData> getIconDataList() {
        return new ArrayList<>();
    }

    @Provides
    @Singleton
    DownloadListener getDownloadListener() {
        return this.downloadListener;
    }


    @Provides
    @Singleton
    IconListPresenter getIconListPresenter(IconListView iconListView, IconListModel iconListModel, IconEventBus eventBus) {
        return new IconListPresenterImpl(iconListView, iconListModel, eventBus);
    }

    @Provides
    @Singleton
    IconListView getIconListView() {
        return this.iconListView;
    }

    @Provides
    @Singleton
    IconListModel getIconListModel(RetrofitService retrofitService, IconEventBus eventBus, File imageFilePath) {
        return new IconListModelImpl(retrofitService, eventBus, imageFilePath);
    }

    @Provides
    @Singleton
    RetrofitService getRetrofitService() {
        Retrofit client = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        return client.create(RetrofitService.class);
    }



    @Provides
    @Singleton
    ImageLoader getGlideImageLoader(RequestManager requestManager) {
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager getRequestManager(Context context) {
        return Glide.with(context);
    }

    @Provides
    @Singleton
    IconEventBus getIconEventBus(EventBus eventBus) {
        return new IconListEventBus(eventBus);
    }

    @Provides
    @Singleton
    EventBus getEvenBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    Context getContext() {
        return this.context;
    }

    @Provides
    @Singleton
    File getFilePath() {
        File imageFilePath = new File(Environment.getExternalStorageDirectory(), "IconFinder");
        if (!imageFilePath.exists()) {
            try {
                imageFilePath = new File(Environment.getExternalStorageDirectory() + "/IconFinder");
                imageFilePath.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imageFilePath;
    }

}
