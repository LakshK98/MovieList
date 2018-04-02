package com.example.movie.movielist.database;

import android.provider.BaseColumns;

/**
 * Created by lakshkotian on 11/03/18.
 */

public final class DbContract {

    //declaring a private constructor prevents someone from instantiating an object of this class
    private DbContract() {}

    public static class YourListNames implements BaseColumns{
        public static final String TABLE_NAME = "yourListName";
        public static final String COLUMN_LIST_NAME = "listName";
        public static final String COLUMN_DESCRIPTION = "description";


    }
    public static class YourList implements BaseColumns{
        public static final String TABLE_NAME = "yourList";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TYPE ="type";
        public static final String COLUMN_NAME_DATE = "releaseDate";
        public static final String COLUMN_NAME_SUMMARY= "summary";
        public static final String COLUMN_NAME_ID_OF_NAME= "nameID";
        public static final String COLUMN_NAME_POSTER_PATH= "posterPath";
    }
    public static class Favorite implements BaseColumns{

        public static final String TABLE_NAME = "Favorite";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_TYPE ="type";
        public static final String COLUMN_NAME_DATE = "releaseDate";
        public static final String COLUMN_NAME_SUMMARY= "summary";
        public static final String COLUMN_NAME_POSTER_PATH= "posterPath";

    }

}
