package com.zhensu.demo.model;

import com.squareup.moshi.Json;

import java.util.List;

public class NewsResponse {
    public final Items items;

    public NewsResponse(Items items) {
        this.items = items;
    }

    public static class Items {

        @Json(name = "result")
        public final List<NewsItem> list;

        public Items(List<NewsItem> list) {
            this.list = list;
        }
    }
}
