package com.ibsweather;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

public class CityListFragment extends Fragment implements SearchView.OnQueryTextListener  {

    private ListView cityList;
    private CityAdapter adapter;
    private ArrayList<CityWeather> data;
    private SearchView searchView;
    public CityListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     *
     * @param isVisibleToUser
     *  used to detect if the fragment in visible to user
     *  or not , if visible populate the adapter with the data
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            data = WeatherUtil.getData().cityWeathers;
            adapter.clear();
            adapter.addAll(data);
           adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_list, container, false);
        cityList = (ListView) view.findViewById(R.id.city_list);
        searchView = (SearchView) view.findViewById(R.id.search_view);
        data = new ArrayList<>();
        adapter = new CityAdapter(getActivity(), data);
        cityList.setAdapter(adapter);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailsIntent = new Intent(getActivity() , CityDetailsActivity.class);
                detailsIntent.putExtra("city" , adapter.getItem(i));
                startActivity(detailsIntent);
            }
        });
        searchView.setOnQueryTextListener(this);
        return view;
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        adapter.getFilter().filter(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.getFilter().filter(s);
        return true;
    }



}
