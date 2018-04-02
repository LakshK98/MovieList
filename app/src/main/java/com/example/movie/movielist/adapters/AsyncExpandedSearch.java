package com.example.movie.movielist.adapters;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.example.movie.movielist.Network.NetworkUtils;
import com.example.movie.movielist.utils.Cast;
import com.example.movie.movielist.utils.ExpandedSearchResults;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;

/**
 * Created by lakshkotian on 05/03/18.
 */

public class AsyncExpandedSearch extends AsyncTaskLoader<ExpandedSearchResults> {

    URL url;
    ExpandedSearchResults expandedSearchResults;
    public AsyncExpandedSearch(Context context,ExpandedSearchResults esr) {
        super(context);
        expandedSearchResults=esr;
        url=NetworkUtils.buildExpandedUrl(esr.id,esr.type);

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ExpandedSearchResults loadInBackground() {
        String result = null;
        try {
            result = NetworkUtils.getResponseFromHttpUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            JSONObject searchJsonObject = new JSONObject(result);

            expandedSearchResults.rating = searchJsonObject.getString("vote_average");

            expandedSearchResults.backdropPath=searchJsonObject.getString("backdrop_path");

            JSONArray resultsJsonArray = searchJsonObject.getJSONArray("genres");
            int arrayLength = resultsJsonArray.length();

            expandedSearchResults.genre = new String[arrayLength];
            for (int i = 0; i < arrayLength; i++)
            {
                JSONObject jPortion = (JSONObject) resultsJsonArray.get(i);

                expandedSearchResults.genre[i]=jPortion.getString("name");

            }
            JSONObject credits = (JSONObject)searchJsonObject.get("credits");

            resultsJsonArray = credits.getJSONArray("cast");

            arrayLength = resultsJsonArray.length();

            for (int i = 0; i < arrayLength; i++)
            {
                JSONObject jPortion = (JSONObject) resultsJsonArray.get(i);
                Cast cast = new Cast();
                cast.name = jPortion.getString("character");
                cast.id = jPortion.getString("credit_id");
                cast.realName = jPortion.getString("name");
                cast.posterPath = jPortion.getString("profile_path");
                expandedSearchResults.castList.add(cast);
            }

            resultsJsonArray = credits.getJSONArray("crew");

            arrayLength = resultsJsonArray.length();

            for (int i = 0; i < arrayLength; i++)
            {
                JSONObject jPortion = (JSONObject) resultsJsonArray.get(i);
                if(jPortion.getString("job").equals("Director"))
                {
                    expandedSearchResults.director=jPortion.getString("name");
                    break;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        return expandedSearchResults;

    }
}
