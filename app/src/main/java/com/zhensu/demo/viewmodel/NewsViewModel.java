package com.zhensu.demo.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zhensu.demo.model.NewsItem;
import com.zhensu.demo.repository.NewsRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsViewModel extends ViewModel {

    public final MutableLiveData<List<NewsItem>> newsLiveData = new MutableLiveData<>();
    private final NewsRepository mNewsRepository = NewsRepository.getInstance();
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void getNews() {
        mCompositeDisposable.add(mNewsRepository
                .getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItems -> {
                    newsLiveData.postValue(newsItems);
                }, throwable -> {
                    /*no-op*/
                })
        );
    }

}
