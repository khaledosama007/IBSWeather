package com.ibsweather;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by khaled on 18/07/17.
 */

public class WeatherDataDeserializer implements JsonDeserializer<WeatherData> {
    @Override
    public WeatherData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ArrayList<CityWeather> cityWeathers = new ArrayList<>();
        JsonObject response = json.getAsJsonObject();
        JsonArray list = response.getAsJsonArray("list");
        for(int i=0 ; i<list.size() ; i++){
            CityWeather model = new CityWeather();
            JsonObject city = (JsonObject)list.get(i);
            model.setCityName(city.get("name").getAsString());
            model.setLon(city.getAsJsonObject("coord").get("Lon").getAsFloat());
            model.setLat(city.getAsJsonObject("coord").get("Lat").getAsFloat());
            model.setCurrentTemp(city.getAsJsonObject("main").get("temp").getAsInt());
            model.setMinTemp(city.getAsJsonObject("main").get("temp_min").getAsInt());
            model.setMaxTemp(city.getAsJsonObject("main").get("temp_max").getAsInt());
            model.setPressure(city.getAsJsonObject("main").get("pressure").getAsFloat());
            model.setHumidity(city.getAsJsonObject("main").get("humidity").getAsInt());
            model.setWindSpeed(city.getAsJsonObject("wind").get("speed").getAsFloat());
            model.setWindDegree(city.getAsJsonObject("wind").get("deg").getAsFloat());
            JsonArray weather = city.getAsJsonArray("weather");
            JsonObject description = (JsonObject) weather.get(0);
            model.setDescription(description.get("description").getAsString());
            cityWeathers.add(model);
        }
        WeatherData weatherData = new WeatherData();
        weatherData.cityWeathers = cityWeathers;
        return weatherData;
    }
}
