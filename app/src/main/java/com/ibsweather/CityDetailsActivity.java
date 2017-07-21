package com.ibsweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class CityDetailsActivity extends AppCompatActivity {

    private CityWeather city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        Intent intent = getIntent();
        city = (CityWeather) intent.getSerializableExtra("city");
        setUpViews();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        getWindow().setLayout( Integer.valueOf((int) (width*0.9)), Integer.valueOf((int) (height*0.8)));

    }
    public void setUpViews(){
        TextView name = (TextView)findViewById(R.id.details_city_name);
        name.setText(city.getCityName());
        TextView temp = (TextView)findViewById(R.id.details_current_temp);
        temp.setText(String.valueOf(city.getCurrentTemp()));
        TextView minTemp = (TextView)findViewById(R.id.details_min_temp);
        minTemp.setText(String.valueOf(city.getMinTemp()));
        TextView maxTemp = (TextView)findViewById(R.id.details_max_temp);
        maxTemp.setText(String.valueOf(city.getMaxTemp()));
        TextView humidity = (TextView)findViewById(R.id.details_humidity);
        humidity.setText(String.valueOf(city.getHumidity()));
        TextView windSpeed = (TextView)findViewById(R.id.details_wind_speed);
        windSpeed.setText(String.valueOf(city.getWindSpeed()));
        TextView windDegree = (TextView)findViewById(R.id.details_wind_degree);
        windDegree.setText(String.valueOf(city.getWindDegree()));
        TextView pressure = (TextView)findViewById(R.id.details_pressure);
        pressure.setText(String.valueOf(city.getPressure()));
        TextView descripttion = (TextView)findViewById(R.id.details_description);
        descripttion.setText(city.getDescription());
    }
}
