package com.example.movie.movielist.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.SearchResult;
import com.example.movie.movielist.adapters.YourListRecycler;
import com.example.movie.movielist.database.DbContract;
import com.example.movie.movielist.database.DbHelper;

public class AddToList extends AppCompatActivity implements YourListRecycler.YourListClickListener{

    RecyclerView aRecyclerView;
    YourListRecycler adapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);

        getSupportActionBar().setTitle(R.string.add_to_list);

        DbHelper dbHelper= new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor c =db.query(DbContract.YourListNames.TABLE_NAME,null,
                null,null,null,null,null);

        aRecyclerView= findViewById(R.id.add_to_list_recycler);
        adapter = new YourListRecycler(c,this);
        aRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        aRecyclerView.setHasFixedSize(true);
        aRecyclerView.setAdapter(adapter);



    }
    @Override
    public void onResume() {
        super.onResume();
        Cursor c =db.query(DbContract.YourListNames.TABLE_NAME,null,
                null,null,null,null,null);

        adapter.swapCursor(c);
    }

    public void onClick(View v)
    {
        Intent i =  new Intent(this,NameYourList.class);
        startActivity(i);

    }

    @Override
    public void onClick(int listID,String name) {

        finish();
        SearchResult sr =(SearchResult)getIntent().getSerializableExtra("MovieDetail");

        Cursor c = db.query(DbContract.YourList.TABLE_NAME
                ,null,DbContract.YourList.COLUMN_NAME_TYPE+"=? and "
                        +DbContract.YourList.COLUMN_NAME_TITLE+"=? and "
        +DbContract.YourList.COLUMN_NAME_ID_OF_NAME+"=?",new String[]{sr.type,sr.name,listID+""},null,null,null,null);

        if(c.getCount()==0) {
            ContentValues cv = sr.getCV();
            cv.put(DbContract.YourList.COLUMN_NAME_ID_OF_NAME, listID);

            db.insert(DbContract.YourList.TABLE_NAME, null, cv);
        }
        c.close();
        db.close();
    }

    @Override
    public void onSwipe(String listID) {

    }
}
