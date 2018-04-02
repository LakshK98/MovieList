package com.example.movie.movielist.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.movie.movielist.database.DbContract;
import com.example.movie.movielist.database.DbHelper;

import java.util.HashSet;

/**
 * Created by lakshkotian on 24/03/18.
 */

public class Favorites {
    public static HashSet<String> favorite= new HashSet<>();

    static SQLiteDatabase db;
    static DbHelper dbHelper;
    public static void FavoriteInitialize(Context con){

        dbHelper= new DbHelper(con);
        db = dbHelper.getWritableDatabase();
        String columns[]= new String[]{DbContract.Favorite.COLUMN_NAME_TYPE,DbContract.Favorite.COLUMN_NAME_TITLE};
        Cursor c =db.query(DbContract.Favorite.TABLE_NAME,columns,
                null,null,null,null,null);
        favorite= new HashSet<>();

        while(c.moveToNext())
        {
            favorite.add(c.getString(c.getColumnIndex(DbContract.Favorite.COLUMN_NAME_TYPE))
                    +c.getString(c.getColumnIndex(DbContract.Favorite.COLUMN_NAME_TITLE)));
            Log.v("io",c.getString(c.getColumnIndex(DbContract.Favorite.COLUMN_NAME_TYPE))
                    +c.getString(c.getColumnIndex(DbContract.Favorite.COLUMN_NAME_TITLE)));
        }
        c.close();
    }

    public static void FavoriteInsert( SearchResult sr){
        Log.v("comp",favorite.toString());
        ContentValues cv = sr.getCV();
        db.insert(DbContract.Favorite.TABLE_NAME,null,cv);
        favorite.add(sr.type+sr.name);

    }

    public static void FavoriteDelete(String type,String name){
        db.delete(DbContract.Favorite.TABLE_NAME
                ,DbContract.Favorite.COLUMN_NAME_TYPE+"=? and "+DbContract.Favorite.COLUMN_NAME_TITLE+"=?"
                ,new String[]{type,name});
        favorite.remove(type+name);

    }



}
