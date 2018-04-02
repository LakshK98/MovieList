package com.example.movie.movielist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.movie.movielist.utils.Constants;
import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.SearchResult;
import com.example.movie.movielist.adapters.AsyncExpandedSearch;
import com.example.movie.movielist.adapters.SwipePagerAdapter;
import com.example.movie.movielist.utils.Cast;
import com.example.movie.movielist.utils.ExpandedSearchResults;
import com.example.movie.movielist.utils.Favorites;
import com.github.clans.fab.FloatingActionMenu;
import java.util.ArrayList;

public class ExpandedView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ExpandedSearchResults> {

    SwipePagerAdapter sPAdapter;
    ViewPager mViewPager;
    ExpandedSearchResults expandedSearchResults;
    SearchResult sr;
    int LOADER_ID=1;
    ImageView img;
    MenuItem favFilled,favOutline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_view);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                 findViewById(R.id.collapse_toolbar);


         TabLayout tabLayout= findViewById(R.id.tabs);
         tabLayout.addTab(tabLayout.newTab().setText(R.string.overview));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.cast));

         sr =(SearchResult)getIntent().getSerializableExtra("searchResult");



        expandedSearchResults = new ExpandedSearchResults(sr);

        sPAdapter = new SwipePagerAdapter(
                getSupportFragmentManager());

        mViewPager =findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(mViewPager);




        img = findViewById(R.id.collapsing_img);


        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        collapsingToolbar.setTitle(expandedSearchResults.name);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));


        getSupportLoaderManager().initLoader(LOADER_ID,null,this);


        final Intent i = new Intent(this, AddToList.class);
        i.putExtra("MovieDetail",sr);
        final com.github.clans.fab.FloatingActionButton fab1= findViewById(R.id.fab_add_to_list);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionMenu fabMenu= findViewById(R.id.floating_action_menu);

                fabMenu.close(true);
                startActivity(i);
            }
        });
        CoordinatorLayout coordinatorLayout = findViewById(R.id.main_content);
        coordinatorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionMenu fabMenu= findViewById(R.id.floating_action_menu);
                fabMenu.close(true);            }
        });

    }
    public ArrayList<Cast> passDataOverviewFragment(){

        return expandedSearchResults.castList;
    }

    @Override
    public Loader<ExpandedSearchResults> onCreateLoader(int id, Bundle args) {
        return new AsyncExpandedSearch(this,expandedSearchResults);
    }

    @Override
    public void onLoadFinished(Loader<ExpandedSearchResults> loader, ExpandedSearchResults data) {

        getIntent().putExtra("esr",expandedSearchResults);
        mViewPager.setAdapter(sPAdapter);
        Glide.with(this).load(Constants.IMAGE_BASE_URL_780+expandedSearchResults.backdropPath).asBitmap().centerCrop().into(img);

    }

    @Override
    public void onLoaderReset(Loader<ExpandedSearchResults> loader) {

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.expanded_view_menu,menu);
        favFilled=menu.findItem(R.id.fav_filled);
        favOutline=menu.findItem(R.id.fav_outline);
        if(Favorites.favorite.contains(sr.type+sr.name))
        {
            favOutline.setVisible(false);
            favFilled.setVisible(true);
        }
        else
        {
            favOutline.setVisible(true);
            favFilled.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.fav_outline:

                favOutline.setVisible(false);
                favFilled.setVisible(true);
                Favorites.FavoriteInsert(sr);
                break;
            case R.id.fav_filled:

                favOutline.setVisible(true);
                favFilled.setVisible(false);
                Favorites.FavoriteDelete(expandedSearchResults.type,expandedSearchResults.name);

                break;


        }
        return true;
    }
}
