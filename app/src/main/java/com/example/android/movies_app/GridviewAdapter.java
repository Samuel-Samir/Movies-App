package com.example.android.movies_app;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movies_app.Models.MoviesList;

/**
 * Created by samuel on 10/21/2016.
 */

public class GridviewAdapter extends  RecyclerView.Adapter<GridviewAdapter.ViewHolder> {

    private  final Activity myActivity;
    private final Integer[] gridElements ;
    private final MoviesList moviesListResult ;

    public GridviewAdapter(Activity myActivity, Integer[] gridElements, MoviesList moviesListResult)
    {
        this.myActivity = myActivity;
        this.gridElements = gridElements;
        this.moviesListResult = moviesListResult;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View rootView =myActivity.getLayoutInflater().inflate(R.layout.grid_item,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(gridElements[position]);
    }

    @Override
    public int getItemCount() {
        return gridElements.length;
    }
//******************************************************************************************//
    public class ViewHolder extends RecyclerView.ViewHolder
   {
       ImageView imageView ;
       public ViewHolder (View itemView)
       {
          super(itemView);
           imageView = (ImageView) itemView.findViewById(R.id.image_item);
       }
   }
}
