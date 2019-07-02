package com.zhensu.demo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhensu.demo.R;
import com.zhensu.demo.model.NewsItem;
import com.zhensu.demo.util.StringUtils;
import com.zhensu.demo.view.NewsActivity;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<NewsItem> newsList;

    public NewsAdapter() {
        newsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int pos) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int pos) {
        newsViewHolder.bind(newsList.get(pos));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void updateNews(List<NewsItem> newsItems, boolean more) {
        if (more) {
            newsList.addAll(newsItems);
            notifyItemRangeInserted(newsList.size(), newsItems.size());
        } else {
            newsList.clear();
            newsList.addAll(newsItems);
            notifyDataSetChanged();
        }
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mainImage;
        TextView title;
        TextView date;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImage = itemView.findViewById(R.id.card_news_main_image);
            title = itemView.findViewById(R.id.card_news_title);
            date = itemView.findViewById(R.id.card_news_publish_date);
        }

        void bind(NewsItem item) {
            if (item != null) {
                if (item.mainImage != null) {
                    Glide.with(itemView)
                            .load(item.mainImage.originalUrl)
                            .into(mainImage);
                }
                title.setText(item.title);
                date.setText(StringUtils.parseDate(item.publishedAt));
                itemView.setTag(item);
                itemView.setOnClickListener(this);
            } else {
                itemView.setOnClickListener(null);
            }
        }

        @Override
        public void onClick(View v) {
            NewsActivity.launchNewsActivity(v.getContext(), (NewsItem) v.getTag());
        }
    }
}
