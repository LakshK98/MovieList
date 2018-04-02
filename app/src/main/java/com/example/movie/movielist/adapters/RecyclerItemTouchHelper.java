package com.example.movie.movielist.adapters;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Created by lakshkotian on 25/03/18.
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;
    String id;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener,String i) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
        id =i;
    }


    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
         View foregroundView ;
         if(id.equals("YourListExpanded"))
         foregroundView= ((YourListExpandedRecycler.YourListExpandedViewHolder) viewHolder).foreground;
        else
             foregroundView= ((YourListRecycler.YourListViewHolder) viewHolder).foreground;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        View foregroundView ;
        if(id.equals("YourListExpanded"))
            foregroundView= ((YourListExpandedRecycler.YourListExpandedViewHolder) viewHolder).foreground;
        else
            foregroundView= ((YourListRecycler.YourListViewHolder) viewHolder).foreground;

        getDefaultUIUtil().clearView(foregroundView);
    }
    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}