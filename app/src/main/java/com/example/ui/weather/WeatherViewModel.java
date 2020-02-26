package com.example.ui.weather;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data.model.LoginUser;
import com.example.data.model.WeatherResponse;
import com.example.data.rest.RepoRepository;
import com.example.data.utility.GPSTracker;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<WeatherResponse> mutableLiveData;
    private RepoRepository newsRepository;
    private CompositeDisposable disposable;
    Context context;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        String lat="",lon="";
        GPSTracker gpsTracker=new GPSTracker(context);
        if (gpsTracker.getLatitude()==0.0 || gpsTracker.getLongitude()==0.0)
        {
            lat="12.9716";
            lon="77.5946";
        }
        else
        {
             lat=String.valueOf(gpsTracker.getLatitude());
             lon=String.valueOf(gpsTracker.getLongitude());
        }

        newsRepository = RepoRepository.getInstance();
        disposable= RepoRepository.getDisposable();
        disposable.add(newsRepository.getWeather(lat, lon,"749ae6058b6fb17eb9a43a2342853a34").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<WeatherResponse>() {
                    @Override
                    public void onSuccess(WeatherResponse weatherResponse) {

                        mutableLiveData.setValue(weatherResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));

    }

    public LiveData<WeatherResponse> getWeather(Context context) {
        this.context=context;
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;

    }

}
