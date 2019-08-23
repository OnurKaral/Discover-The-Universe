package com.example.jsonparse;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Adapter extends FragmentPagerAdapter {

    private int numOfTabs;


    Adapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new YakinFragment();
                case 1:
                    return new TumuFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }
