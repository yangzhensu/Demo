package com.zhensu.demo.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhensu.demo.R;
import com.zhensu.demo.adapter.NewsAdapter;
import com.zhensu.demo.model.NewsItem;
import com.zhensu.demo.viewmodel.NewsViewModel;

import java.util.List;

public class NewsListFragment extends Fragment {

    private static final String TAG = NewsListFragment.class.getSimpleName();

    private RecyclerView mNewsList;
    private NewsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean loading;
    private NewsViewModel mNewsViewModel;
    private Observer<List<NewsItem>> mNewsObserver;
    private boolean mMore;

    public NewsListFragment() {
        loading = true;
    }

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init news list
        mNewsList = view.findViewById(R.id.fragment_news_news_list);
        mNewsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            getNews(true);
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });
        mLayoutManager = new LinearLayoutManager(getContext());
        mNewsList.setLayoutManager(mLayoutManager);
        mAdapter = new NewsAdapter();
        mNewsList.setAdapter(mAdapter);
        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        mNewsObserver = newsItems -> {
            if (newsItems != null) {
                mAdapter.updateNews(newsItems, mMore);
            }
        };

        getNews(false);
    }

    private void getNews(boolean more) {
        mMore = more;
        mNewsViewModel.newsLiveData.observe(this, mNewsObserver);
        mNewsViewModel.getNews();
    }

}
