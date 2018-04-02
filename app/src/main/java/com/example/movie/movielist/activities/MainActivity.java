package com.example.movie.movielist.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.SearchResult;
import com.example.movie.movielist.adapters.PopularRecycler;
import com.example.movie.movielist.adapters.PopularSearch;
import com.example.movie.movielist.utils.Favorites;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<ArrayList<SearchResult>>,PopularRecycler.PopularClickListerer,
                    PopupMenu.OnMenuItemClickListener {

    DrawerLayout mDrawerLayout;
    RecyclerView mRecyclerView1,mRecyclerView2;
    int LOADER_ID1=1,LOADER_ID2=2;
    Intent intent;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    int flag ;
    private Parcelable recyclerViewState1,recyclerViewState2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        flag=0;
        frameLayout=findViewById(R.id.main_progressbar_frame);
        linearLayout=findViewById(R.id.main_linear_layout);
        frameLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        Favorites.FavoriteInitialize(this);

        mRecyclerView1 = findViewById(R.id.popular_movies_recycler);
        mRecyclerView2 =findViewById(R.id.popular_tv_recycler);





        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView1.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mRecyclerView2.setHasFixedSize(true);

        getSupportLoaderManager().initLoader(LOADER_ID1,null,this);
        getSupportLoaderManager().initLoader(LOADER_ID2,null,this);

        mDrawerLayout=findViewById(R.id.drawer_layout);
        NavigationView mNavigationView= findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(
               new NavigationView.OnNavigationItemSelectedListener(){


                   @Override
                   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                       item.setChecked(true);
                      mDrawerLayout.closeDrawers();
                       switch (item.getItemId()) {
                           case R.id.list_icon:

                                   Intent i = new Intent(getBaseContext(), YourList.class);
                                   startActivity(i);
                                    break;
                           case  R.id.fav_icon :
                               Intent i2 = new Intent(getBaseContext(), YourListExpanded.class);
                               i2.putExtra("id", 2);
                               i2.putExtra("name", "Favorites");
                               startActivity(i2);
                               break;
                       }
                       return true;
                   }
               }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.main_search_icon)
        {
            Intent i = new Intent(this,SearchActivity.class);
            startActivity(i);
        }
        if(item.getItemId()== android.R.id.home)
            mDrawerLayout.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<SearchResult>> onCreateLoader(int id, Bundle args) {


        if(id==LOADER_ID1)
            return new PopularSearch(this,"movie");
        else
            return new PopularSearch(this,"tv");
    }


    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<SearchResult>> loader, ArrayList<SearchResult> data) {
        int id = loader.getId();
        if(flag>0)
        {
            frameLayout.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        if(id==LOADER_ID1) {
            flag++;
            mRecyclerView1.setAdapter(new PopularRecycler(data, this, this));
        }

        else {
            flag++;
            mRecyclerView2.setAdapter(new PopularRecycler(data, this, this));
        }



    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<SearchResult>> loader) {

    }

    @Override
    public void onThreeDotClick(SearchResult sr, View view) {
        intent= new Intent(this, AddToList.class);
        intent.putExtra("MovieDetail",sr);
        PopupMenu popup = new PopupMenu(this,view);
        MenuInflater inflater = popup.getMenuInflater();

        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.three_dots_menu, popup.getMenu());
        popup.show();

    }

    @Override
    public void onViewClick(SearchResult sr) {
        Intent i= new Intent(this, ExpandedView.class);
        i.putExtra("searchResult",sr);
        startActivity(i);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_list_menu_item:

                startActivity(intent);

                return true;
            case R.id.add_to_fav_menu_item:
                SearchResult sr=(SearchResult)intent.getSerializableExtra("MovieDetail");
                Log.v("ma",sr.type+sr.name);
                if (!Favorites.favorite.contains(sr.type+sr.name))
                    Favorites.FavoriteInsert(sr);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("hello","pause");
        recyclerViewState1 = mRecyclerView1.getLayoutManager().onSaveInstanceState();
        recyclerViewState2 = mRecyclerView2.getLayoutManager().onSaveInstanceState();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("hello","resume");
        mRecyclerView1.getLayoutManager().onRestoreInstanceState(recyclerViewState1);
        mRecyclerView2.getLayoutManager().onRestoreInstanceState(recyclerViewState2);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        recyclerViewState1 = mRecyclerView1.getLayoutManager().onSaveInstanceState();
        recyclerViewState2 = mRecyclerView2.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(LOADER_ID1+"", mRecyclerView1.getLayoutManager().onSaveInstanceState());

        outState.putParcelable(LOADER_ID2+"", mRecyclerView2.getLayoutManager().onSaveInstanceState());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(LOADER_ID1+"");
            mRecyclerView1.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            savedRecyclerLayoutState =savedInstanceState.getParcelable(LOADER_ID2+"");
            mRecyclerView2.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }
}
