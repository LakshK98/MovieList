package com.example.movie.movielist.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movie.movielist.R;
import com.example.movie.movielist.adapters.RecyclerItemTouchHelper;
import com.example.movie.movielist.adapters.YourListExpandedRecycler;
import com.example.movie.movielist.adapters.YourListRecycler;
import com.example.movie.movielist.database.DbContract;
import com.example.movie.movielist.database.DbHelper;

/**
 * Created by lakshkotian on 22/03/18.
 */

public class YourList extends AppCompatActivity implements YourListRecycler.YourListClickListener
        ,RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    RecyclerView ylRecyclerView;
    YourListRecycler adapter;
    SQLiteDatabase db;

    @Nullable
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_list);
        getSupportActionBar().setTitle(R.string.your_list);
        FloatingActionButton fab = findViewById(R.id.your_list_fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), NameYourList.class);
                startActivity(i);
            }
        });


        DbHelper dbHelper = new DbHelper(this);
        db = dbHelper.getReadableDatabase();
        Cursor c = db.query(DbContract.YourListNames.TABLE_NAME, null,
                null, null, null, null, null);

        ylRecyclerView = findViewById(R.id.your_list_recycler);
        adapter = new YourListRecycler(c, this);
        ylRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ylRecyclerView.setHasFixedSize(true);
        ylRecyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback
                = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this,"YourList");
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(ylRecyclerView);

    }


    @Override
    public void onResume() {
        super.onResume();
        Cursor c =db.query(DbContract.YourListNames.TABLE_NAME,null,
                null,null,null,null,null);

        adapter.swapCursor(c);
    }

    @Override
    public void onClick(int listID,String name) {
        Intent i = new Intent(this, YourListExpanded.class);
        i.putExtra("listID",listID);
        i.putExtra("name",name);
        i.putExtra("id",1);
        startActivity(i);
    }

    @Override
    public void onSwipe(String listID) {
        db.delete(DbContract.YourListNames.TABLE_NAME,DbContract.YourListNames._ID+"=?",new String[]{listID});
        db.delete(DbContract.YourList.TABLE_NAME,DbContract.YourList.COLUMN_NAME_ID_OF_NAME+"=?",new String[]{listID});
        onResume();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        adapter.removeItem(viewHolder.getAdapterPosition());
     }

}

