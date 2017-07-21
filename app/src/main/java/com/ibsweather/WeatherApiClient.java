package com.ibsweather;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by khaled on 18/07/17.
 * Retrofit client
 */

public interface WeatherApiClient {
    @GET("/data/2.5/box/city")
    Call<WeatherData> getWeatherData(@QueryMap Map<String , String> map);
}
