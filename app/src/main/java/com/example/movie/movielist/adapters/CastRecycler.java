package com.example.movie.movielist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movie.movielist.utils.Constants;
import com.example.movie.movielist.R;
import com.example.movie.movielist.utils.Cast;

import java.util.ArrayList;

/**
 * Created by lakshkotian on 07/03/18.
 */

public class CastRecycler extends RecyclerView.Adapter<CastRecycler.CastViewHolder> {


    ArrayList<Cast> castList;
    Context mContext;

    public CastRecycler(ArrayList<Cast> cl, Context con){
        castList =cl;
        mContext=con;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cast_card_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        CastViewHolder viewHolder = new CastViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        Cast cast = castList.get(position);
        holder.tv2.setText(cast.name);
        holder.tv1.setText(cast.realName);


        Glide.with(mContext).load(Constants.IMAGE_BASE_URL_342+cast.posterPath).asBitmap().centerCrop().into(holder.img);

    }


    @Override
    public int getItemCount() {
        return castList.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder{
        TextView tv1;
        TextView tv2;
        ImageView img;


        public CastViewHolder(View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.cast_textView1);
            tv2=itemView.findViewById(R.id.cast_textView2);
            img=itemView.findViewById(R.id.cast_img);
        }
    }
}
