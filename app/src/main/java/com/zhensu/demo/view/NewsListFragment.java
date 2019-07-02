package com.zhensu.demo.view;


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
import com.zhensu.demo.network.NetworkManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment {

    private static final String TAG = NewsListFragment.class.getSimpleName();

    private RecyclerView mNewsList;
    private NewsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private CompositeDisposable disposable;
    private boolean loading;

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
                if(dy > 0) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
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

        disposable = new CompositeDisposable();
        getNews(false);
    }

    private void getNews(boolean more) {
        disposable.add(NetworkManager.getInstance()
                .getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsResponse -> {
                    if (newsResponse != null && newsResponse.items != null && newsResponse.items.list != null) {
                        List<NewsItem> newsItems = newsResponse.items.list;
                        if (more) {
                            mAdapter.addNews(newsItems);
                        } else {
                            mAdapter.updateNews(newsItems);
                        }
                    }
                }, throwable -> {
                    Log.e(TAG, "getNews: ", throwable);
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
