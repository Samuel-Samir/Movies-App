package com.example.android.movies_app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movies_app.Activities.MovieDetailsActivity;
import com.example.android.movies_app.Models.MovieContent;
import com.example.android.movies_app.Models.MoviesList;
import com.squareup.picasso.Picasso;



/**
 * Created by samuel on 10/21/2016.
 */

public class GridviewAdapter extends  RecyclerView.Adapter<GridviewAdapter.ViewHolder> {

    private  final Activity myActivity;
    private  MoviesList moviesListResult =new MoviesList();

    public GridviewAdapter(Activity myActivity, MoviesList moviesListResult)
    {
        this.myActivity = myActivity;
        this.moviesListResult = moviesListResult;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View rootView =myActivity.getLayoutInflater().inflate(R.layout.grid_item,parent,false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MovieContent movieContent =  moviesListResult.results.get(position) ;
        String baseimagUrl ="http://image.tmdb.org/t/p/w342/";
        String imagUrl = movieContent.poster_path;
        Picasso.with(myActivity).load(baseimagUrl+imagUrl).into( holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(myActivity, MovieDetailsActivity.class).putExtra("myMovie" ,movieContent);
                myActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesListResult.results.size();
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
