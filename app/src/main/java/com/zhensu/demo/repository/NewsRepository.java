package com.zhensu.demo.repository;

import com.zhensu.demo.model.NewsItem;
import com.zhensu.demo.model.NewsResponse;
import com.zhensu.demo.network.Api;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class NewsRepository {

    private static NewsRepository sInstance;
    private static final String BASE_URL = "https://mobile-homerun-yql.media.yahoo.com";

    public static NewsRepository getInstance() {
        if (sInstance == null) {
            synchronized (NewsRepository.class) {
                if (sInstance == null) {
                    sInstance = new NewsRepository();
                }
            }
        }
        return sInstance;
    }

    private NewsRepository() {/*no-op*/}

    private OkHttpClient buildHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        return httpClient.build();
    }

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildHttpClient())
                .build();
    }

    public Single<List<NewsItem>> getNews() {
        return buildRetrofit()
                .create(Api.class)
                .getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<NewsResponse, List<NewsItem>>() {
                    @Override
                    public List<NewsItem> apply(NewsResponse newsResponse) throws Exception {
                        if (newsResponse != null && newsResponse.items != null) {
                            return newsResponse.items.list;
                        }
                        return null;
                    }
                });
    }


}
