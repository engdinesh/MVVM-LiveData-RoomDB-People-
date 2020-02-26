package com.example.data.rest;



import com.example.data.model.WeatherResponse;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class RepoRepository {

    private static RepoRepository repoRepository;
    private static CompositeDisposable disposable;


    public static RepoRepository getInstance(){
        if (repoRepository == null){
            repoRepository = new RepoRepository();
        }
        return repoRepository;
    }

    public static CompositeDisposable getDisposable(){
        if (disposable == null){
            disposable = new CompositeDisposable();
        }
        return disposable;
    }


    private ApiHelper apiHelper;

    public RepoRepository(){
        apiHelper = ApiService.cteateService(ApiHelper.class);
    }

    public Single<WeatherResponse> getWeather(String lat, String lon, String appid) {
        return apiHelper.getWeather(lat, lon, appid);
    }


}
