package com.example.movie.movielist.activities;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.movie.movielist.R;
import com.example.movie.movielist.database.DbContract;
import com.example.movie.movielist.database.DbHelper;

public class NameYourList extends AppCompatActivity {
    EditText et1 ,et2;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_your_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.new_list);
        et1 = findViewById(R.id.name_your_list_editText);
        et2 = findViewById(R.id.name_your_list_editText2);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.name_your_list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            finish();
            return false;
        }
        if(et1.getText().toString().trim().equals(""))
            return false;

        DbHelper dbHelper = new DbHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DbContract.YourListNames.COLUMN_LIST_NAME,et1.getText().toString());
        cv.put(DbContract.YourListNames.COLUMN_DESCRIPTION,et2.getText().toString());
        db.insert(DbContract.YourListNames.TABLE_NAME,null,cv);

        finish();
        return super.onOptionsItemSelected(item);
    }
}
