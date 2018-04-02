package com.example.movie.movielist.fragments;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movie.movielist.R;
import com.example.movie.movielist.activities.ExpandedView;
import com.example.movie.movielist.adapters.CastRecycler;

/**
 * Created by lakshkotian on 04/03/18.
 */

public class CastFragment extends  Fragment{
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                View v = inflater.inflate(R.layout.cast_fragment,container,false);

        RecyclerView castRv=  v.findViewById(R.id.cast_recycler);
        int numberOfColumns = 3;
        castRv.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        ExpandedView activity = (ExpandedView) getActivity();
        CastRecycler adapter =
                new CastRecycler(activity.passDataOverviewFragment(),getActivity());
        castRv.setAdapter(adapter);
                return v;

    }
}
