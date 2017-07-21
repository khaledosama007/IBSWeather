package com.ibsweather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    // Cooordinates of egypt rectangle on map
    // as left-bottom and right upper
    private final double LEFT_LAT = 22.089095;
    private final double BOTTOM_LON = 25.043481;
    private final double RIGHT_LAT = 31.967622;
    private final double UPPER_LON = 34.744091;
    private MapView mMapView;
    private GoogleMap mGMap;
    private LatLngBounds egyptBounds =
            new LatLngBounds(new LatLng(LEFT_LAT, BOTTOM_LON), new LatLng(RIGHT_LAT, UPPER_LON));
    private WeatherUtil util = new WeatherUtil();
    private WeatherData data = new WeatherData();

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) root.findViewById(R.id.map);
        mMapView.getMapAsync(this);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        MapsInitializer.initialize(this.getActivity());
        return root;
    }

    @Override
    public void onStart() {
        mMapView.onStart();
        super.onStart();
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGMap = googleMap;
        CameraUpdate cameraUpdate =
                CameraUpdateFactory.newLatLngBounds(egyptBounds, 4);
        mGMap.animateCamera(cameraUpdate);
        mGMap.setOnMarkerClickListener(this);
        fetchWeatherData();
        //data = WeatherUtil.getData();
        //addMarkers();
    }

    /**
     * fetch data from the API and store it in the singleton object
     */
    public void fetchWeatherData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create(WeatherUtil.getGson()))
                .build();
        WeatherApiClient weatherApiClient = retrofit.create(WeatherApiClient.class);
        Map<String, String> params = new HashMap<>();
        params.put("bbox", "25.043481,22.089095,34.744091,31.967622,12");
        params.put("appid", getString(R.string.Weather_API_Key));
        weatherApiClient.getWeatherData(params).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherUtil.setData(response.body());
                data = response.body();
                addMarkers();
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Toast.makeText(getActivity(), "Please make sure you are connected to the internet", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void addMarkers() {
        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        ImageView image = (ImageView) marker.findViewById(R.id.num_img);
        //image.setImageResource();
        //avoid creating objects in the loop
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Canvas canvas = new Canvas();
        for (int i = 0; i < data.cityWeathers.size(); i++) {
            LatLng tempPlace = new LatLng(data.cityWeathers.get(i).getLat(), data.cityWeathers.get(i).getLon());
            if (data.cityWeathers.get(i).getCurrentTemp() <= 15) {
            } else if (data.cityWeathers.get(i).getCurrentTemp() > 15 && data.cityWeathers.get(i).getCurrentTemp() < 33) {
            } else {
            }
            numTxt.setText(String.valueOf(data.cityWeathers.get(i).getCurrentTemp()));
            (getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            marker.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
            marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
            marker.buildDrawingCache();
            Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bitmap);
            marker.draw(canvas);
            MarkerOptions options = new MarkerOptions();
            options.position(tempPlace)
                    .title(String.valueOf(data.cityWeathers.get(i).getCityName()));
            options.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            mGMap.addMarker(options);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String cityName = marker.getTitle();
        for (CityWeather temp : data.cityWeathers) {
            if (temp.getCityName().equals(cityName)) {
                Intent intent = new Intent(getActivity(), CityDetailsActivity.class);
                intent.putExtra("city", temp);
                startActivity(intent);
            }
        }
        return false;
    }
}
