package com.ibsweather;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by khaled on 18/07/17.
 * Singleton object to store and access weather data across
 * the application
 */

public class WeatherUtil {
    private static WeatherData data = null;
    private static Gson gson = createGson() ;
    private static Gson createGson(){
        if(gson == null){
            gson = new GsonBuilder().registerTypeAdapter(WeatherData.class , new WeatherDataDeserializer()).create();
        }
        if(data == null){
            data = new WeatherData();
        }
        return gson;
    }
    public static Gson getGson(){
      return createGson();
    }
    public static WeatherData getData(){
        return data;
    }
    public static void setData(WeatherData d){
        data.cityWeathers = new ArrayList<>();
        data = d;
        data.cityWeathers = d.cityWeathers;

    }



}
