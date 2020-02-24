package com.example.data.rest;



import com.example.data.model.NewsResponse;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class RepoRepository {

    private static RepoRepository newsRepository;
    private static CompositeDisposable disposable;


    public static RepoRepository getInstance(){
        if (newsRepository == null){
            newsRepository = new RepoRepository();
        }
        return newsRepository;
    }

    public static CompositeDisposable getDisposable(){
        if (disposable == null){
            disposable = new CompositeDisposable();
        }
        return disposable;
    }


    private ApiHelper newsApi;

    public RepoRepository(){
        newsApi = ApiService.cteateService(ApiHelper.class);
    }

    public Single<NewsResponse> getNews(String source, String key) {
        return newsApi.getNewsList(source, key);
    }

}
