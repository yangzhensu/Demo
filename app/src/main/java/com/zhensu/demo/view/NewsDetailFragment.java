package com.zhensu.demo.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhensu.demo.R;
import com.zhensu.demo.model.NewsItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailFragment extends Fragment {

    private static final String NEWS_ITEM = "news_item";

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    public static NewsDetailFragment newInstance(NewsItem item) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(NEWS_ITEM, item);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

}
