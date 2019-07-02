package com.zhensu.demo.network;

import com.zhensu.demo.model.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {

    @GET("/v2/homerun/newsfeed")
    public Single<NewsResponse> getNews();
}
