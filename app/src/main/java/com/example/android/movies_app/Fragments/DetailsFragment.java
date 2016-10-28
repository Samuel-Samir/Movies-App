package com.example.android.movies_app.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies_app.DataProvider;
import com.example.android.movies_app.ExpandableListAdapter;
import com.example.android.movies_app.FetchMovies;
import com.example.android.movies_app.Models.MovieContent;
import com.example.android.movies_app.Models.MovieReviews;
import com.example.android.movies_app.Models.MovieReviewsList;
import com.example.android.movies_app.Models.MovieVideoList;
import com.example.android.movies_app.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsFragment extends Fragment {

    private MovieContent movieContent ;
    private MovieReviewsList movieReviewses =new MovieReviewsList();
    private MovieVideoList movieVideoList = new MovieVideoList();
    private ShareActionProvider mShareActionProvider;
    private ExpandableListView trailers_exp_list ;
    private ExpandableListView reviews_exp_list;
    private ExpandableListAdapter trailersExpAdapter ;
    private ExpandableListAdapter reviewsExpAdapter ;

    private List <String> expandableListKey ;
    private HashMap <String ,String> expandableListHashMap ;



    ImageView posterImageView ;
    ImageView photoImageView ;
    TextView  nameTextView ;
    TextView  dateTextView ;
    TextView  votersTextView ;
    TextView  overviewTextView;
    RatingBar ratingBar;
    TextView reviewsNotAvailable;
    TextView trailersNotAvailable;

    public  DetailsFragment ()
    {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView=  inflater.inflate(R.layout.fragment_details, container, false);
        movieContent = (MovieContent) getActivity().getIntent().getSerializableExtra("myMovie");
        expandableListHashMap =  DataProvider.getInfo();
        expandableListKey = new ArrayList<String>(expandableListHashMap.keySet());

        fetchReviews ();
        fetchVideos () ;

        posterImageView =(ImageView) rootView.findViewById(R.id.moviePoster);
        photoImageView =(ImageView) rootView.findViewById(R.id.moviePhoto);
        nameTextView =(TextView) rootView.findViewById(R.id.movieName);
        dateTextView =(TextView) rootView.findViewById(R.id.date);
        votersTextView =(TextView) rootView.findViewById(R.id.voters);
        overviewTextView =(TextView) rootView.findViewById(R.id.overview);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        trailersNotAvailable = (TextView) rootView.findViewById(R.id.trailersNotAvailable);
        reviewsNotAvailable = (TextView) rootView.findViewById(R.id.reviewsNotAvailable);
        reviews_exp_list = (ExpandableListView) rootView.findViewById(R.id.reviews_exp_list);
        trailers_exp_list = (ExpandableListView) rootView.findViewById(R.id.trailers_exp_list);
        trailersExpAdapter = new ExpandableListAdapter(getActivity() ,expandableListHashMap ,expandableListKey);
        reviewsExpAdapter = new ExpandableListAdapter(getActivity() ,expandableListHashMap ,expandableListKey);
        reviews_exp_list.setAdapter(reviewsExpAdapter);
        trailers_exp_list.setAdapter(trailersExpAdapter);


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
        Picasso.with(getActivity()).load(baseimagUrl+imagUrl).resize(400,300).placeholder(R.drawable.holder).into(posterImageView);
        nameTextView.setText(movieContent.original_title);
        dateTextView.setText( "Date : "+movieContent.release_date);
        votersTextView.setText("Voters : "+String.valueOf(movieContent.vote_count));
        imagUrl = movieContent.poster_path;
        Picasso.with(getActivity()).load(baseimagUrl+imagUrl).placeholder(R.drawable.holder).into(photoImageView);
        overviewTextView.setText(movieContent.overview);
        ratingBar.setRating((float) movieContent.vote_average/2);
        Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#049e47"), PorterDuff.Mode.SRC_ATOP);

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
                        if(movieReviewses.results.size()!=0)
                        {
                            reviewsNotAvailable.setVisibility(View.GONE);

                        }
                        else
                        reviews_exp_list.setVisibility(View.GONE);

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
                        if(movieVideoList.results.size()!=0)
                        {
                            trailersNotAvailable.setVisibility(View.GONE);
                           /* expandableListKey.clear();
                            expandableListHashMap.clear();
                            for (int i=0 ;i<movieVideoList.results.size() ;i++)
                            {
                                expandableListHashMap.put(movieVideoList.results.get(i).name ,movieVideoList.results.get(i).key);
                                expandableListKey.add(movieVideoList.results.get(i).name);
                            }

                            trailersExpAdapter = new ExpandableListAdapter(getActivity() ,expandableListHashMap ,expandableListKey);
                            trailers_exp_list.setAdapter(trailersExpAdapter);*/

                        }
                       else
                        trailers_exp_list.setVisibility(View.GONE);
                    }
                }
        );
        fetchMovies.execute(url , "videos");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.share_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(createShareForecastIntent());

    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "sasa");
        return shareIntent;
    }


}
