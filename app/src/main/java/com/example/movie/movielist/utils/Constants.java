package com.example.movie.movielist.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.movie.movielist.database.DbContract;
import com.example.movie.movielist.database.DbHelper;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by lakshkotian on 23/03/18.
 */

public class Constants {

    public static final String IMAGE_BASE_URL_780="https://image.tmdb.org/t/p/w780/";
    public static final String IMAGE_BASE_URL_342="https://image.tmdb.org/t/p/w342/";
    public static final String IMAGE_BASE_URL_500="https://image.tmdb.org/t/p/w500/";
    public final static String BASE_URL =
            "https://api.themoviedb.org/3";
    public final static String API_KEY = "93f8982a020d16ac6d936dfa538bf95a";

    public static HashMap<Integer,String> genreMap;
    public static HashSet<String>favorite;


    static {

        genreMap = new HashMap<Integer, String>(){{
            put(28,"Action");
            put(12,"Adventure");
            put(16,"Animation");
            put(35,"Comedy");
            put(80,"Crime");
            put(99,"Documentary");
            put(18,"Drama");
            put(10751,"Family");
            put(14,"Fantasy");
            put(36,"History");
            put(27,"Horror");
            put(10402,"Music");
            put(9648,"Mystery");
            put(10749,"Romance");
            put( 878,"Science Fiction");
            put(10770,"TV Movie");
            put(53,"Thriller");
            put(10752,"War");
            put(37,"Western");
            put(10759,"Action & Adventure");
             put(10762,"Kids");
            put(9648,"Mystery");
            put(10763,"News");
            put(10764,"Reality");
            put(10765,"Sci-Fi & Fantasy");
            put(10766,"Soap");
            put(10767,"Talk");
            put(10768,"War & Politics");
        }};



    }

    public static void FavoriteInitialize(Context con){
        SQLiteDatabase db;
        DbHelper dbHelper= new DbHelper(con);
        db = dbHelper.getReadableDatabase();
        String columns[]= new String[]{DbContract.Favorite.COLUMN_NAME_TYPE,DbContract.Favorite.COLUMN_NAME_TITLE};
        Cursor c =db.query(DbContract.Favorite.TABLE_NAME,columns,
                null,null,null,null,null);
        while(c.moveToNext())
        {
            favorite.add(c.getString(c.getColumnIndex(DbContract.Favorite.COLUMN_NAME_TYPE)
                    +c.getColumnIndex(DbContract.Favorite.COLUMN_NAME_TITLE)));

        }
        c.close();
    }



}