package com.droidapps.mvp.iconfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.droidapps.mvp.iconfinder.lib.IconEventBus;
import com.droidapps.mvp.iconfinder.network.IconEvent;
import com.droidapps.mvp.iconfinder.network.RetrofitService;
import com.droidapps.mvp.iconfinder.pojo.IconPojo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.droidapps.mvp.iconfinder.utils.Constants.ERROR;
import static com.droidapps.mvp.iconfinder.utils.Constants.SUCCESS;

public class IconListModelImpl implements IconListModel {

    private static final String TAG = "IconListModelImpl";

    private static final String category = "diversity-v2-0-volume-01";
    private static final String count = "15";
    private static final String premium = "0";

    private IconEvent event = new IconEvent();
    private RetrofitService retrofitService;
    private IconEventBus eventBus;
    private File imageFilePath;

    private Call<IconPojo> call;

    public IconListModelImpl(RetrofitService retrofitService, IconEventBus eventBus, File imageFilePath) {
        this.eventBus = eventBus;
        this.imageFilePath = imageFilePath;
        this.retrofitService = retrofitService;
    }

    @Override
    public void fetchIconList(String lastIconId) {

        if (call != null) call.cancel();

        call = retrofitService.getIconList(category, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, count, premium, lastIconId);
        call.enqueue(new Callback<IconPojo>() {

            @Override
            public void onResponse(Call<IconPojo> call, Response<IconPojo> response) {
                IconPojo data = response.body();
                if (response.isSuccessful()) {
                    if (data == null || data.getList() == null) {
                        event.setError("No icon found for specified category.");
                    } else {
                        event.setList(data.getList());
                    }
                } else {
                    event.setError(data.getMessage());
                }
                eventBus.post(event);
            }

            @Override
            public void onFailure(Call<IconPojo> call, Throwable t) {
                event.setError("Unable to fetch icons");
                eventBus.post(event);
            }
        });
    }

    @Override
    public void downloadIcon(String url) {

        OkHttpClient client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .url(url).build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                saveToStorage(bitmap);
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                event.setError(ERROR);
                eventBus.post(event);
            }
        });
    }

    private void saveToStorage(Bitmap bitmap) {

        File file = new File(imageFilePath, "icon_" + System.currentTimeMillis() + ".png");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            event.setError(SUCCESS);
            eventBus.post(event);
        }
    }
}
