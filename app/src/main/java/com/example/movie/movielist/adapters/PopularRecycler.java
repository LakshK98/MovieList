package com.example.movie.movielist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movie.movielist.utils.Constants;
import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.SearchResult;

import java.util.ArrayList;

/**
 * Created by lakshkotian on 19/03/18.
 */

public class PopularRecycler extends RecyclerView.Adapter<PopularRecycler.PopularViewHolder> {

    private Context mContext;
     private ArrayList<SearchResult> array_result;
     private PopularClickListerer clickListener;
    public PopularRecycler (ArrayList<SearchResult> array_result, Context con,PopularClickListerer pcl){
        mContext = con;
        this.array_result=array_result;
        clickListener =pcl;

    }

    public interface PopularClickListerer{
        void onThreeDotClick(SearchResult sr,View view);
        void onViewClick(SearchResult sr);
    }

    @Override
    public PopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        int layoutIdForListItem = R.layout.popular_card_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        PopularViewHolder viewHolder = new PopularViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PopularViewHolder holder, int position) {
        SearchResult sr ;
        sr= array_result.get(position);
        holder.title.setText(sr.name);

        Glide.with(mContext).load(Constants.IMAGE_BASE_URL_780+sr.backdropPath).asBitmap().centerCrop().into(holder.back);
        Glide.with(mContext).load(Constants.IMAGE_BASE_URL_342+sr.posterPath).asBitmap().centerCrop().into(holder.poster);

        holder.rating.setText(sr.rating);
        holder.genre.setText("");
        if(sr.genre!=null) {
            for (int i = 0; i < sr.genre.length; i++) {

                holder.genre.append(Constants.genreMap.get(sr.genre[i]));
                if(i!=sr.genre.length-1)
                    holder.genre.append(", ");
            }
        }
    }

    @Override
    public int getItemCount() {
        return array_result.size();
    }

    class PopularViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        ImageView back,poster;
        TextView title,genre,rating;
        ImageButton threeDots;

        public PopularViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            threeDots=itemView.findViewById(R.id.popular_three_dots);
            threeDots.setOnClickListener(this);
            back=itemView.findViewById(R.id.popular_back);
            poster=itemView.findViewById(R.id.popular_poster);
            title=itemView.findViewById(R.id.popular_title);
            rating=itemView.findViewById(R.id.popular_rating);
            genre = itemView.findViewById(R.id.popular_genre);
        }

        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.popular_three_dots)
                clickListener.onThreeDotClick(array_result.get(getAdapterPosition()),view);
            else
                clickListener.onViewClick(array_result.get(getAdapterPosition()));
        }
    }
}
