package com.example.movie.movielist.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.movie.movielist.R;
import com.example.movie.movielist.database.DbContract;

/**
 * Created by lakshkotian on 09/03/18.
 */

public class YourListRecycler extends RecyclerView.Adapter<YourListRecycler.YourListViewHolder> {


    private Cursor cursor;
    private YourListClickListener yourListClickListener;
    public YourListRecycler(Cursor c,YourListClickListener ylcl){

        cursor =c;
        yourListClickListener = ylcl;
    }
    @Override
    public YourListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.your_list_recycler,parent,false);


        return new YourListViewHolder(v);
    }

    public interface YourListClickListener{
        void onClick(int listID,String name);
        void onSwipe(String listID);
    }

    @Override
    public void onBindViewHolder(YourListViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.tv.setText(cursor.getString(cursor.getColumnIndex(DbContract.YourListNames.COLUMN_LIST_NAME)));


    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class YourListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv;
        View foreground;
        public YourListViewHolder(View itemView) {
            super(itemView);
            tv =itemView.findViewById(R.id.your_list_tv);
            foreground =itemView.findViewById(R.id.list_name_foreground);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            cursor.moveToPosition(getAdapterPosition());
            int listID=cursor.getInt(cursor.getColumnIndex(DbContract.YourListNames._ID));
            String name=cursor.getString(cursor.getColumnIndex(DbContract.YourListNames.COLUMN_LIST_NAME));
            yourListClickListener.onClick(listID, name);
        }
    }

    public void removeItem(int posi){
        cursor.moveToPosition(posi);
        String listID=cursor.getString(cursor.getColumnIndex(DbContract.YourListNames._ID));
        yourListClickListener.onSwipe(listID);
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }
}
