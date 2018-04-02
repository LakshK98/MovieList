package com.example.movie.movielist.activities;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;

import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;

import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.SearchResult;
import com.example.movie.movielist.adapters.AsyncSearch;
import com.example.movie.movielist.adapters.SearchRecycler;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<SearchResult>>,
        SearchRecycler.SearchRecyclerClickListener {
    int LOADER_ID=1;
    LoaderCallbacks callback = this;
    RecyclerView search_rv;
    Bundle bundle = new Bundle();
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.search);
        setContentView(R.layout.activity_search);

        pb = findViewById(R.id.search_progressBar_1);

        pb.setVisibility(View.INVISIBLE);
        search_rv=findViewById(R.id.search_recycler);
        search_rv.setLayoutManager(new LinearLayoutManager(this));
        search_rv.setHasFixedSize(true);
        search_rv.setAdapter(new SearchRecycler(null,this,this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.expandActionView();
        SearchView searchView =(SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                bundle.putString("key",s);

                if(s.length()>0) {
                    LoaderManager loaderManager = getSupportLoaderManager();
                    Loader loader = loaderManager.getLoader(LOADER_ID);
                    if (loader != null) {
                        loaderManager.destroyLoader(LOADER_ID);
                    }
                    loaderManager.restartLoader(LOADER_ID, bundle, callback);
                }
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<SearchResult>> onCreateLoader(int id, Bundle args) {

        pb.setVisibility(View.VISIBLE);
        search_rv.setVisibility(View.INVISIBLE);
        return new AsyncSearch(this,args.getString("key"));
    }


    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<SearchResult>> loader, ArrayList<SearchResult> data) {
        pb.setVisibility(View.INVISIBLE);
        search_rv.setVisibility(View.VISIBLE);
        search_rv.setAdapter(new SearchRecycler(data,this,this));




    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<SearchResult>> loader) {

    }

    @Override
    public void onClick(SearchResult sr) {
        Intent i= new Intent(this, ExpandedView.class);
        i.putExtra("searchResult",sr);
        startActivity(i);

    }
}
