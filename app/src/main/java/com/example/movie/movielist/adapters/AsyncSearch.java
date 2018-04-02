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
 * Created by lakshkotian on 15/02/18.
 */

public class AsyncSearch extends AsyncTaskLoader<ArrayList<SearchResult>> {
    private URL url;
    public AsyncSearch(Context context,String query) {
        super(context);
        url = NetworkUtils.buildUrl(query);



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


            switch (jPortion.getString("media_type")) {
                case "movie":
                    searchResult.name = jPortion.getString("title");
                    searchResult.posterPath=jPortion.getString("poster_path");
                    searchResult.releaseDate = jPortion.getString("release_date");
                    searchResult.summary = jPortion.getString("overview");
                    searchResult.id = jPortion.getString("id");
                    searchResult.type = jPortion.getString("media_type");
                    arrayResponse.add(searchResult);
                    break;
                case "tv":

                    searchResult.name = jPortion.getString("name");
                    searchResult.posterPath=jPortion.getString("poster_path");
                    searchResult.releaseDate = jPortion.getString("first_air_date");
                    searchResult.summary = jPortion.getString("overview");
                    searchResult.id = jPortion.getString("id");
                    searchResult.type = jPortion.getString("media_type");
                    arrayResponse.add(searchResult);
                    break;
                case "person":
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
