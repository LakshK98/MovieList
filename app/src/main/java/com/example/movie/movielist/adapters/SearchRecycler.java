package com.example.movie.movielist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.SearchResult;

import java.util.ArrayList;


/**
 * Created by lakshkotian on 16/02/18.
 */

public class SearchRecycler extends RecyclerView.Adapter<SearchRecycler.SearchViewHolder> {

    private SearchRecyclerClickListener sRCListener;
    private ArrayList<SearchResult> array_result;
    private Context mContext;
    public SearchRecycler (ArrayList<SearchResult> array_result,Context con,SearchRecyclerClickListener cl){
        mContext = con;
        this.array_result=array_result;
        sRCListener =cl;

    }

    public interface SearchRecyclerClickListener
    {
         void onClick(SearchResult sr);
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.search_card_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

       View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        SearchViewHolder viewHolder = new SearchViewHolder(view);



        return viewHolder;
    }


    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        SearchResult sr ;
        sr= array_result.get(position);
        String IMAGE_BASE_URL="https://image.tmdb.org/t/p/w342/";
        holder.title.setText(sr.name);
        Glide.with(mContext).load(IMAGE_BASE_URL+sr.posterPath).asBitmap().centerCrop().into(holder.img);
        if(sr.type.equals("tv"))
            holder.type.setText("TV Show");
        else
            holder.type.setText("Movie");

        holder.yr.setText("");
        if(sr.releaseDate.length()>0)
            holder.yr.append(sr.releaseDate.substring(0,4));

        holder.summary.setText(sr.summary);
    }

    @Override
    public int getItemCount() {
        if(array_result!=null)
            return array_result.size();
        return 0;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView title;
    TextView summary;
    ImageView img;
    TextView type;
    TextView yr;
        public SearchViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.search_title);
            summary = itemView.findViewById( R.id.search_summary);
            img = itemView.findViewById(R.id.search_image);

            type = itemView.findViewById(R.id.search_type_tv);
            yr= itemView.findViewById(R.id.search_yr_tv);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int adapterPosition=getAdapterPosition();
            sRCListener.onClick(array_result.get(adapterPosition));

        }
    }
}
