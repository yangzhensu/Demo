package com.zhensu.demo.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhensu.demo.R;
import com.zhensu.demo.model.NewsItem;

public class NewsActivity extends AppCompatActivity {

    private static final String NEWS_ITEM = "news_item";

    public static void launchNewsActivity(Context context, NewsItem newsItem) {
        Intent intent = new Intent(context, NewsActivity.class);
        intent.putExtra(NEWS_ITEM, newsItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        attachNewsDetailFragment();
    }

    private void attachNewsDetailFragment() {
        NewsItem item = null;
        if (getIntent() != null) {
            item = (NewsItem) getIntent().getSerializableExtra(NEWS_ITEM);
        }
        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.activity_news) == null) {
            fm.beginTransaction()
                    .add(R.id.activity_news, NewsDetailFragment.newInstance(item))
                    .commit();
        }
    }
}
