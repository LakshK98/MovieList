package com.example.movie.movielist.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.SearchResult;
import com.example.movie.movielist.adapters.RecyclerItemTouchHelper;
import com.example.movie.movielist.adapters.YourListExpandedRecycler;
import com.example.movie.movielist.database.DbContract;
import com.example.movie.movielist.database.DbHelper;
import com.example.movie.movielist.utils.Favorites;

public class YourListExpanded extends AppCompatActivity
        implements YourListExpandedRecycler.YourListExpandedClickListener,RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    SQLiteDatabase db;
    RecyclerView yleRV;
    YourListExpandedRecycler yAdapter;
    int id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        yleRV=findViewById(R.id.search_recycler);
        yleRV.setLayoutManager(new LinearLayoutManager(this));
        yleRV.setHasFixedSize(true);
        ProgressBar pb = findViewById(R.id.search_progressBar_1);

        DbHelper dbHelper= new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        yleRV.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
        Cursor c ;

        id =getIntent().getIntExtra("id",1);
        if(id==1) {
            int listID = getIntent().getIntExtra("listID", 1);
            c = db.query(DbContract.YourList.TABLE_NAME, null,
                    DbContract.YourList.COLUMN_NAME_ID_OF_NAME + " = '" + listID + "'",
                    null, null, null, null);
        }
        else
            c =db.query(DbContract.Favorite.TABLE_NAME,null,null
                ,null,null,null,null);



         yAdapter = new YourListExpandedRecycler(c,this,this,id);


        yleRV.setAdapter(yAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback
                = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this,"YourListExpanded");
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(yleRV);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onClick(SearchResult sr) {
        Intent i= new Intent(this,ExpandedView.class);
        i.putExtra("searchResult",sr);
        startActivity(i);

    }
    public void onSwipe(String type,String name)
    {
        Favorites.FavoriteDelete(type,name);
    }
    public void onSwipe(String name,String movieId,String listID)
    {
        String whereClause= DbContract.YourList._ID+"=? and "
                +DbContract.YourList.COLUMN_NAME_TITLE+"=? and "+DbContract.YourList.COLUMN_NAME_ID_OF_NAME+"=?";
        String[] whereArgs= {movieId,name,listID};
        db.delete(DbContract.YourList.TABLE_NAME,whereClause,whereArgs);

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        yAdapter.removeItem(viewHolder.getAdapterPosition());
        Cursor c;
        if(id==1)
            c =db.query(DbContract.YourList.TABLE_NAME,null,
                null,null,null,null,null);
        else
            c=db.query(DbContract.Favorite.TABLE_NAME,null,
                    null,null,null,null,null);
                yAdapter.swapCursor(c);

    }
}
