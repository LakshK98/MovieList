package com.example.movie.movielist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.ExpandedSearchResults;

import java.text.DateFormatSymbols;

/**
 * Created by lakshkotian on 04/03/18.
 */

public class OverviewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.overview_fragment,container,false);

        ExpandedSearchResults esr=(ExpandedSearchResults)getActivity()
                                .getIntent().getSerializableExtra("esr");

        TextView tv2 = v.findViewById(R.id.overview_textview2);

        TextView tv3 = v.findViewById(R.id.overview_textview3);

        TextView tv4 = v.findViewById(R.id.overview_textview4);

        TextView tv5 = v.findViewById(R.id.overview_textview5);

        TextView tv6 = v.findViewById(R.id.overview_textview6);


        TextView tv8 = v.findViewById(R.id.overview_textview8);

        TextView tv9 = v.findViewById(R.id.overview_rating_tv);

        String rating=esr.rating+"/10";
        tv9.setText(rating);

        tv2.setText(esr.summary);



        if(esr.type.equals("tv")) {
            tv5.setText(R.string.first_air_date);
            tv3.setVisibility(View.GONE);
            tv4.setVisibility(View.GONE);
        }
        else
        {
            tv4.setText(esr.director);

        }
        String date ,month,yr;
        yr=esr.releaseDate.substring(0,4);
        month=getMonthForInt(Integer.parseInt(esr.releaseDate.substring(5,7)));
        date =esr.releaseDate.substring(8);
        String txt=date+" "+month+" "+ yr;
        tv6.setText(txt);


        if(esr.genre!=null) {
            for (int i = 0; i < esr.genre.length; i++) {

                tv8.append(esr.genre[i]);
                if(i!=esr.genre.length-1)
                    tv8.append(", ");
            }
        }
        return v;
    }
    String getMonthForInt(int m) {
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11 ) {
            month = months[m];
        }
        return month;
    }
}
