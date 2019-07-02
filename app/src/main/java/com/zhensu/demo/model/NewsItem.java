package com.zhensu.demo.model;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class NewsItem implements Serializable {

    public NewsItem(String uuid, String title, String type, String link, String summary, long publishedAt, MainImage mainImage) {
        this.uuid = uuid;
        this.title = title;
        this.type = type;
        this.link = link;
        this.summary = summary;
        this.publishedAt = publishedAt;
        this.mainImage = mainImage;
    }

    public final String uuid;
    public final String title;
    public final String type;
    public final String link;
    public final String summary;
    @Json(name = "published_at")
    public final long publishedAt;
    @Json(name = "main_image")
    public final MainImage mainImage;

    public static class MainImage implements Serializable {

        public MainImage(String originalUrl, int originalWidth, int originalHeight) {
            this.originalUrl = originalUrl;
            this.originalWidth = originalWidth;
            this.originalHeight = originalHeight;
        }

        @Json(name = "original_url")
        public final String originalUrl;
        @Json(name = "original_width")
        public final int originalWidth;
        @Json(name = "original_height")
        public final int originalHeight;
    }
}
