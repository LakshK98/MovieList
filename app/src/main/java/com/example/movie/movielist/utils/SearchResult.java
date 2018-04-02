package com.example.movie.movielist.utils;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.movie.movielist.database.DbContract;

import java.io.Serializable;

/**
 * Created by lakshkotian on 14/02/18.
 */

public class SearchResult implements Serializable{
    public String name;
    public String releaseDate;
    public String summary;
    public String posterPath;
    public String id;
    public String type;



    public String backdropPath;
    public String rating;
    public int genre[];
    public ContentValues getCV(){
        ContentValues cv = new ContentValues();
        cv.put(DbContract.YourList.COLUMN_NAME_DATE,releaseDate);
        cv.put(DbContract.YourList.COLUMN_NAME_POSTER_PATH,posterPath);
        cv.put(DbContract.YourList.COLUMN_NAME_TITLE,name);
        cv.put(DbContract.YourList.COLUMN_NAME_TYPE,type);
        cv.put(DbContract.YourList.COLUMN_NAME_SUMMARY,summary);
        cv.put(DbContract.YourList._ID,id);
        return cv;
    }
    public SearchResult getSearchResultFromCUursor(Cursor c,String tableName){
        SearchResult sr = new SearchResult();

        return sr;
    }
}
