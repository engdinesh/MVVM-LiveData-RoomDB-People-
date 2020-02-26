package com.example.data.rest;



import com.example.data.model.WeatherResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHelper {

    @GET("weather?units=metric")
    Single<WeatherResponse> getWeather(@Query("lat") String lat,
                                       @Query("lon") String lon,
                                       @Query("appid") String appId);

}
