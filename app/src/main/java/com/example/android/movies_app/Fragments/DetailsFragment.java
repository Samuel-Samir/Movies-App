package com.example.android.movies_app.Fragments;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies_app.Models.MovieContent;
import com.example.android.movies_app.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailsFragment extends Fragment {

    private MovieContent movieContent ;
    ImageView posterImageView ;
    ImageView photoImageView ;
    TextView  nameTextView ;
    TextView  dateTextView ;
    TextView  votersTextView ;
    TextView  overviewTextView;
    RatingBar ratingBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView=  inflater.inflate(R.layout.fragment_details, container, false);

        movieContent = (MovieContent) getActivity().getIntent().getSerializableExtra("myMovie");
        posterImageView =(ImageView) rootView.findViewById(R.id.moviePoster);
        photoImageView =(ImageView) rootView.findViewById(R.id.moviePhoto);
        nameTextView =(TextView) rootView.findViewById(R.id.movieName);
        dateTextView =(TextView) rootView.findViewById(R.id.date);
        votersTextView =(TextView) rootView.findViewById(R.id.voters);
        overviewTextView =(TextView) rootView.findViewById(R.id.overview);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);


        if (movieContent!=null)
        {
            setMovieContent() ;
        }

        return rootView ;
    }

    public void setMovieContent()
    {
        String baseimagUrl ="http://image.tmdb.org/t/p/w320/";
        String imagUrl = movieContent.backdrop_path;
        Picasso.with(getActivity()).load(baseimagUrl+imagUrl).into(posterImageView);
        nameTextView.setText(movieContent.original_title);
        dateTextView.setText( "Date : "+movieContent.release_date);
        votersTextView.setText("Voters : "+String.valueOf(movieContent.vote_count));
        imagUrl = movieContent.poster_path;
        Picasso.with(getActivity()).load(baseimagUrl+imagUrl).into(photoImageView);
        overviewTextView.setText(movieContent.overview);
       ratingBar.setRating((float) movieContent.vote_average/2);

    }


}
