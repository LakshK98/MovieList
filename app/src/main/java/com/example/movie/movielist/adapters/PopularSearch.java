package com.example.movie.movielist.adapters;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import com.example.movie.movielist.Network.NetworkUtils;
import com.example.movie.movielist.utils.SearchResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lakshkotian on 20/03/18.
 */

public class PopularSearch extends AsyncTaskLoader<ArrayList<SearchResult>> {


    private URL url;
    private String type;
    public PopularSearch(Context context, String type) {
        super(context);

        url = NetworkUtils.buildPopularUrl(type);
        this.type=type;



    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<SearchResult> loadInBackground() {

        String result = null;
        try {
            result = NetworkUtils.getResponseFromHttpUrl(url);


        } catch (IOException e) {
            e.printStackTrace();
        }





        try {
            JSONObject searchJsonObject = new JSONObject(result);


            ArrayList<SearchResult> arrayResponse = new ArrayList<>();


            JSONArray resultsJsonArray = searchJsonObject.getJSONArray("results");

            for (int i = 0; i < resultsJsonArray.length(); i++) {
                JSONObject jPortion = (JSONObject) resultsJsonArray.get(i);
                SearchResult searchResult = new SearchResult();
                JSONArray genreArray;

                switch (type) {
                    case "movie":
                        searchResult.name = jPortion.getString("title");
                        searchResult.posterPath=jPortion.getString("poster_path");
                        searchResult.backdropPath=jPortion.getString("backdrop_path");
                        searchResult.releaseDate = jPortion.getString("release_date");
                        searchResult.summary = jPortion.getString("overview");
                        searchResult.id = jPortion.getString("id");
                        searchResult.type = type;

                        searchResult.rating =jPortion.getString("vote_average");


                        genreArray = jPortion.getJSONArray("genre_ids");
                        searchResult.genre = new int [genreArray.length()];
                        for(int j=0;j<genreArray.length();j++)
                        {
                            searchResult.genre[j] = (Integer) genreArray.get(j);
                        }

                        arrayResponse.add(searchResult);
                        break;
                    case "tv":

                        searchResult.name = jPortion.getString("name");
                        searchResult.posterPath=jPortion.getString("poster_path");
                        searchResult.backdropPath=jPortion.getString("backdrop_path");
                        searchResult.releaseDate = jPortion.getString("first_air_date");
                        searchResult.summary = jPortion.getString("overview");
                        searchResult.rating =jPortion.getString("vote_average");
                        searchResult.id = jPortion.getString("id");
                        searchResult.type = type;


                        genreArray = jPortion.getJSONArray("genre_ids");
                        searchResult.genre = new int [genreArray.length()];
                        for(int j=0;j<genreArray.length();j++)
                        {
                            searchResult.genre[j] = (Integer) genreArray.get(j);
                        }
                        arrayResponse.add(searchResult);
                        break;
                }

            }
            return arrayResponse;
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return null;
    }


}
