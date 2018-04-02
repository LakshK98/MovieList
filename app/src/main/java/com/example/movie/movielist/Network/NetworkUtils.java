package com.example.movie.movielist.Network;

/**
 * Created by lakshkotian on 14/02/18.
 */

import android.net.Uri;
import com.example.movie.movielist.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {






    public static URL buildUrl(String query) {
        Uri builtUri = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendPath("search").appendPath("multi")
                .appendQueryParameter("api_key", Constants.API_KEY)
                .appendQueryParameter("query", query)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    public static URL buildPopularUrl(String type){

        Uri builtUri = Uri.parse(Constants.BASE_URL).buildUpon().appendPath(type).appendPath("popular")
                .appendQueryParameter("api_key", Constants.API_KEY)
                .appendQueryParameter("language","en-US")
                .appendQueryParameter("page","1").build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildExpandedUrl(String id,String type) {

        Uri builtUri = Uri.parse(Constants.BASE_URL).buildUpon().appendPath(type).appendPath(id)
                .appendQueryParameter("api_key", Constants.API_KEY)
                .appendQueryParameter("append_to_response", "credits")
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException {



        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {

                return scanner.next();

            } else {
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally {
            urlConnection.disconnect();
        }
    }
}



