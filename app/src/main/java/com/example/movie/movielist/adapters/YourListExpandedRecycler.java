package com.example.movie.movielist.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movie.movielist.utils.Constants;
import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.SearchResult;
import com.example.movie.movielist.database.DbContract;

/**
 * Created by lakshkotian on 15/03/18.
 */

public class YourListExpandedRecycler extends RecyclerView.Adapter<YourListExpandedRecycler.YourListExpandedViewHolder>{
    private YourListExpandedClickListener yleListener;
    private Cursor cursor;
    private Context mContext;
    private int parent;
    public YourListExpandedRecycler (Cursor c, Context con, YourListExpandedClickListener cl,int p){
        mContext = con;
        cursor = c;
        yleListener = cl;
        parent=p;
    }

    public interface YourListExpandedClickListener
    {
        void onClick( SearchResult sr);
        void onSwipe(String type ,String name);
        void onSwipe(String name ,String id,String listID);
    }

    @Override
    public YourListExpandedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.your_list_card_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        YourListExpandedViewHolder viewHolder = new YourListExpandedViewHolder((view));


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(YourListExpandedViewHolder holder, int position) {
        cursor.moveToPosition(position);

        String posterPath =cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_POSTER_PATH));
        Glide.with(mContext).load(Constants.IMAGE_BASE_URL_500+posterPath).asBitmap().fitCenter().into(holder.img);

        holder.title.setText(cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_TITLE)));

        String releaseDate = cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_DATE));
        holder.yr.setText("");
        if(releaseDate.length()>0)
            holder.yr.append(releaseDate.substring(0,4));

        holder.summary.setText(cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_SUMMARY)));

        String type=cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_TITLE));
        if(type.equals("tv"))
            holder.type.setText(R.string.tv_show);
        else
            holder.type.setText(R.string.movie);



    }


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class YourListExpandedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView summary;
        ImageView img;
        View foreground;
        TextView yr,type;
        public YourListExpandedViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_title);
            summary = itemView.findViewById( R.id.list_summary);
            img = itemView.findViewById(R.id.list_image);
            foreground = itemView.findViewById(R.id.list_foreground);
            yr= itemView.findViewById(R.id.list_yr_tv);
            type= itemView.findViewById(R.id.list_type_tv);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int adapterPosition=getAdapterPosition();
            cursor.moveToPosition(adapterPosition);
            SearchResult sr = new SearchResult();
            sr.name=cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_TITLE));
            sr.summary=cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_SUMMARY));
            sr.posterPath=cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_POSTER_PATH));
            sr.id=cursor.getString(cursor.getColumnIndex(DbContract.YourList._ID));
            sr.type=cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_TYPE));
            sr.releaseDate=cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_DATE));

            yleListener.onClick(sr);

        }
    }
    public void removeItem(int posi)
    {

        cursor.moveToPosition(posi);
        String name =cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_TITLE));
        String id =cursor.getString(cursor.getColumnIndex(DbContract.YourList._ID));
        String type =cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_TYPE));
        if(parent==1)
        {

            String listID =cursor.getString(cursor.getColumnIndex(DbContract.YourList.COLUMN_NAME_ID_OF_NAME));
            yleListener.onSwipe(name,id,listID);
        }
        else
        {
            yleListener.onSwipe(type,name);
        }
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }


}
