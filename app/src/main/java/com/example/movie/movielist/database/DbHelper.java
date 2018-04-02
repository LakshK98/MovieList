package com.example.movie.movielist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by lakshkotian on 11/03/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "yourList.db";

    private static final int DATABASE_VERSION = 2;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_Your_LIST_NAMES_TABLE =
                "CREATE TABLE " + DbContract.YourListNames.TABLE_NAME +"(" +
                        DbContract.YourListNames._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DbContract.YourListNames.COLUMN_DESCRIPTION + " TEXT," +
                        DbContract.YourListNames.COLUMN_LIST_NAME + " TEXT );";

        final String SQL_CREATE_Your_LIST_TABLE =
                "CREATE TABLE " + DbContract.YourList.TABLE_NAME +"(" +
                        DbContract.YourList._ID +" INTEGER NOT NULL, " +
                        DbContract.YourList.COLUMN_NAME_TYPE + " TEXT NOT NULL," +
                        DbContract.YourList.COLUMN_NAME_TITLE + " TEXT, " +
                        DbContract.YourList.COLUMN_NAME_DATE + " TEXT, " +
                        DbContract.YourList.COLUMN_NAME_SUMMARY + " TEXT, " +
                        DbContract.YourList.COLUMN_NAME_POSTER_PATH + " TEXT, " +
                        DbContract.YourList.COLUMN_NAME_ID_OF_NAME + " TEXT, " +
                        "FOREIGN KEY(" + DbContract.YourList.COLUMN_NAME_ID_OF_NAME +")"+
                            " REFERENCES "+ DbContract.YourListNames.TABLE_NAME + "(" +
                                DbContract.YourListNames._ID+")," +
                        "PRIMARY KEY(" + DbContract.YourList._ID +","
                            + DbContract.YourList.COLUMN_NAME_TYPE + ") );";

        final String SQL_CREATE_FAV_TABLE =
                "CREATE TABLE " + DbContract.Favorite.TABLE_NAME +"(" +
                        DbContract.YourList._ID +" INTEGER NOT NULL, " +
                        DbContract.Favorite.COLUMN_NAME_TYPE + " TEXT NOT NULL," +
                        DbContract.Favorite.COLUMN_NAME_TITLE + " TEXT, " +
                        DbContract.Favorite.COLUMN_NAME_DATE + " TEXT, " +
                        DbContract.Favorite.COLUMN_NAME_SUMMARY + " TEXT, " +
                        DbContract.Favorite.COLUMN_NAME_POSTER_PATH + " TEXT, " +
                        "PRIMARY KEY(" + DbContract.YourList._ID +","
                        + DbContract.YourList.COLUMN_NAME_TYPE + ") );";


        Log.v("sql query",SQL_CREATE_FAV_TABLE);

        sqLiteDatabase.execSQL(SQL_CREATE_FAV_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_Your_LIST_NAMES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_Your_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL_DROP_YOUR_LIST = "DROP TABLE " + DbContract.YourList.TABLE_NAME;
        final String SQL_DROP_YOUR_LIST_NAMES = "DROP TABLE " + DbContract.YourListNames.TABLE_NAME;
        sqLiteDatabase.execSQL(SQL_DROP_YOUR_LIST);
        sqLiteDatabase.execSQL(SQL_DROP_YOUR_LIST_NAMES);

        onCreate(sqLiteDatabase);

    }



}
