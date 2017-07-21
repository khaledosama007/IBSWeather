package com.ibsweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellIdentityLte;
import android.view.View;
import android.widget.TableLayout;
import android.support.design.widget.TabLayout;

public class MainActivity extends AppCompatActivity {
    FragmentStatePagerAdapter adapter;
    TabLayout mTabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Map"));
        mTabLayout.addTab(mTabLayout.newTab().setText("City List"));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
         viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new PagerAdapter(getSupportFragmentManager() , 2);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        viewPager.setAdapter(adapter);
    }
    class PagerAdapter extends FragmentStatePagerAdapter {

        private int NUM_OF_PAGES = 2;

        public PagerAdapter(FragmentManager fm , int num) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new MapFragment();
                case 1:
                    return new CityListFragment();
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            return NUM_OF_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0 :
                    return "Map";
                case 1 :
                    return "City List";
                default:
                    return " ";
            }
        }
    }

}
