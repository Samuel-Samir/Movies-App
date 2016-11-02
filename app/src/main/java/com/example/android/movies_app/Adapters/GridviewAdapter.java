package com.example.android.movies_app.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.android.movies_app.Activities.MovieDetailsActivity;
import com.example.android.movies_app.Models.MovieContent;
import com.example.android.movies_app.Models.MoviesList;
import com.example.android.movies_app.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;



/**
 * Created by samuel on 10/21/2016.
 */

public class GridviewAdapter extends  RecyclerView.Adapter<GridviewAdapter.ViewHolder> {

    private  final Activity myActivity;
    private  MoviesList moviesListResult =new MoviesList();
    private ProgressBar progressBar ;

    public GridviewAdapter(Activity myActivity, MoviesList moviesListResult ,ProgressBar progressBar)
    {
        this.myActivity = myActivity;
        this.moviesListResult = moviesListResult;
        this.progressBar=progressBar ;
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
        Picasso.with(myActivity).load(baseimagUrl+imagUrl).placeholder(R.drawable.holder).into( holder.imageView,
                new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

                    @Override
                    public void onError() {

                    }
                });


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