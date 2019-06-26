package com.droidapps.mvp.iconfinder.network;

import com.droidapps.mvp.iconfinder.pojo.IconPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    String BASE_URL = "https://api.iconfinder.com/v3/";

    @GET("iconsets/{category}/icons")
    Call<IconPojo> getIconList(@Path("category") String category,
                               @Query("client_id") String clientId,
                               @Query("client_secret") String clientSecret,
                               @Query("count") String count,
                               @Query("premium") String premium,
                               @Query("after") String after);

}
