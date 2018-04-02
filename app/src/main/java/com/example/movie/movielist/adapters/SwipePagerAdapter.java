package com.example.movie.movielist.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.movie.movielist.fragments.CastFragment;
import com.example.movie.movielist.fragments.OverviewFragment;

/**
 * Created by lakshkotian on 04/03/18.
 */

public class SwipePagerAdapter extends FragmentPagerAdapter {
    public SwipePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0)
            return new OverviewFragment();
        return new CastFragment();

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Overview";
        return "Cast";
    }
}
