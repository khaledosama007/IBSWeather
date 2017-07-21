package com.ibsweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.filter;

/**
 * Created by khaled on 20/07/17.
 */

public class CityAdapter extends ArrayAdapter<CityWeather> implements Filterable {
    Context context;
    ArrayList<CityWeather> objects;
    CityFilter filter;

    public CityAdapter(@NonNull Context context, @NonNull ArrayList<CityWeather> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CityWeather city = getItem(position);
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.city_list_item, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.item_city_name);
            holder.humid = (TextView) convertView.findViewById(R.id.item_humidity);
            holder.minTemp = (TextView) convertView.findViewById(R.id.item_min_temp);
            holder.maxTemp = (TextView) convertView.findViewById(R.id.item_max_temp);
            holder.temp = (TextView) convertView.findViewById(R.id.item_temperature);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.name.setText(city.getCityName());
        holder.humid.setText(String.valueOf(city.getHumidity()));
        holder.maxTemp.setText(String.valueOf(city.getMaxTemp()));
        holder.minTemp.setText(String.valueOf(city.getMinTemp()));
        holder.temp.setText(String.valueOf(city.getCurrentTemp()));
        return convertView;
    }

    @Nullable
    @Override
    public CityWeather getItem(int position) {
        return objects.get(position);
    }
    @Override
    public Filter getFilter() {
        if(filter == null)
        {
            filter=new CityFilter();
        }
        return filter;
    }
    class CityFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            String query = charSequence.toString().toLowerCase();
            ArrayList<CityWeather> resultsObjects = new ArrayList<>();
            if(query != null && query.length() >0){
                for(int i=0 ; i< WeatherUtil.getData().cityWeathers.size() ; i++ ){
                    if(WeatherUtil.getData().cityWeathers.get(i).getCityName().toLowerCase().contains(query)){
                        resultsObjects.add(WeatherUtil.getData().cityWeathers.get(i));
                    }
                }
                results.count = resultsObjects.size();
                results.values = resultsObjects;
            }
            else {
                //get the original data
                results.count = WeatherUtil.getData().cityWeathers.size();
                results.values = WeatherUtil.getData().cityWeathers;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            objects = (ArrayList<CityWeather>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}


class Holder {
    TextView name, humid, minTemp, maxTemp, temp;
}