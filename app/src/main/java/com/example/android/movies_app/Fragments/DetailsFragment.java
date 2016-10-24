package com.example.android.movies_app.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies_app.FetchMovies;
import com.example.android.movies_app.Models.MovieContent;
import com.example.android.movies_app.Models.MovieReviews;
import com.example.android.movies_app.Models.MovieReviewsList;
import com.example.android.movies_app.Models.MovieVideoList;
import com.example.android.movies_app.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {

    private MovieContent movieContent ;
    private MovieReviewsList movieReviewses =new MovieReviewsList();
    private  MovieVideoList movieVideoList = new MovieVideoList();
    ImageView posterImageView ;
    ImageView photoImageView ;
    TextView  nameTextView ;
    TextView  dateTextView ;
    TextView  votersTextView ;
    TextView  overviewTextView;
    RatingBar ratingBar;
    TextView test ;
    TextView test2 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView=  inflater.inflate(R.layout.fragment_details, container, false);
        movieContent = (MovieContent) getActivity().getIntent().getSerializableExtra("myMovie");
        fetchReviews ();
        fetchVideos () ;

        posterImageView =(ImageView) rootView.findViewById(R.id.moviePoster);
        photoImageView =(ImageView) rootView.findViewById(R.id.moviePhoto);
        nameTextView =(TextView) rootView.findViewById(R.id.movieName);
        dateTextView =(TextView) rootView.findViewById(R.id.date);
        votersTextView =(TextView) rootView.findViewById(R.id.voters);
        overviewTextView =(TextView) rootView.findViewById(R.id.overview);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        test =(TextView) rootView.findViewById(R.id.test);
        test2 =(TextView) rootView.findViewById(R.id.test2);

        // String f = movieReviewses.results.get(0).content;


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

    public void  fetchReviews ()
    {
        String url =String.valueOf(movieContent.id)+"/reviews";
        FetchMovies fetchMovies = new FetchMovies();
        fetchMovies.setFetchMoviesCallback(
                new FetchMovies.FetchMoviesCallback() {
                    @Override
                    public void onPostExecute(Object object) {
                        movieReviewses = (MovieReviewsList) object ;
                        test.setText(movieReviewses.results.get(0).content);
                    }
                }
        );
        fetchMovies.execute(url , "reviews");
    }

    public void  fetchVideos ()
    {
        String url =String.valueOf(movieContent.id)+"/videos";
        FetchMovies fetchMovies = new FetchMovies();
        fetchMovies.setFetchMoviesCallback(
                new FetchMovies.FetchMoviesCallback() {
                    @Override
                    public void onPostExecute(Object object) {
                        movieVideoList = (MovieVideoList) object ;
                        test2.setText(movieVideoList.results.get(0).key);
                    }
                }
        );
        fetchMovies.execute(url , "videos");
    }


}
