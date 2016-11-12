package com.example.android.movies_app.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies_app.Activities.MainActivity;
import com.example.android.movies_app.Adapters.ExpandableListAdapter;
import com.example.android.movies_app.Adapters.GridviewAdapter;
import com.example.android.movies_app.Database.MovieContract;
import com.example.android.movies_app.FetchFromDataBase;
import com.example.android.movies_app.FetchMovies;
import com.example.android.movies_app.Models.MovieContent;
import com.example.android.movies_app.Models.MovieReviewsList;
import com.example.android.movies_app.Models.MovieVideoList;
import com.example.android.movies_app.Models.MoviesList;
import com.example.android.movies_app.R;
import com.example.android.movies_app.Utilities;
import com.squareup.picasso.Picasso;

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
    private List <String> expandableListKey  ;
    private HashMap <String ,String> expandableListHashMap  ;
    private MenuItem menuItem ;

    LinearLayout linearLayout ;
    ScrollView scrollView;
    ImageView posterImageView ;
    ImageView photoImageView ;
    TextView  nameTextView ;
    TextView  dateTextView ;
    TextView  votersTextView ;
    TextView  overviewTextView;
    RatingBar ratingBar;
    TextView reviewsNotAvailable;
    TextView trailersNotAvailable;
    Button addToFavoritButton;

    public  DetailsFragment ()
    {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView=  inflater.inflate(R.layout.fragment_details, container, false);
        getActivity().setTitle("Movie Details");
        if (MainActivity.mTwoPane==true)
        {

            getActivity().setTitle(  Utilities.getOrderName(getActivity()));

        }
        if (savedInstanceState==null)
        {
            Bundle arguments = getArguments();
            if (arguments!=null)
            {
                movieContent =(MovieContent) arguments.getSerializable("myMovie");
            }
            else
            {
                movieContent = (MovieContent) getActivity().getIntent().getSerializableExtra("myMovie");

            }


            if (Utilities.getOrder(getActivity()).equals("favorit") && movieContent!=null)
            {
                findViews(rootView);
                setMovieContent();
                fetchVideosFromDB ();
            }
            else
            {
                if (movieContent != null) {
                    findViews(rootView);
                    setMovieContent();
                    if (Utilities.checkInternetConnection(getActivity())) {
                        fetchVideos();
                    } else
                        Utilities.connectionAlart(getActivity());
                }
            }
        }

        else if (savedInstanceState!=null)
        {
            movieContent = (MovieContent) savedInstanceState.getSerializable("myMovie") ;
            movieReviewses = (MovieReviewsList)  savedInstanceState.getSerializable("movieReviewses");
            movieVideoList = (MovieVideoList) savedInstanceState.getSerializable("movieVideoList") ;
            findViews(rootView);
            setMovieContent();
            if (movieVideoList.results.size()!=0)
                setTrailersExpList (movieVideoList);

            if (movieReviewses.results.size()!=0)
                setReviewsExpList (movieReviewses);

        }

        if(movieContent==null)
        {
            scrollView = (ScrollView) rootView.findViewById(R.id.scroll);

            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.no_select);
            scrollView.setBackground(drawable);
            linearLayout = (LinearLayout) rootView.findViewById(R.id.no_selected);
            linearLayout.setVisibility(LinearLayout.GONE);
        }

        return rootView ;
    }


    public void findViews (View rootView)
    {
        posterImageView =(ImageView) rootView.findViewById(R.id.moviePoster);
        photoImageView =(ImageView) rootView.findViewById(R.id.moviePhoto);
        nameTextView =(TextView) rootView.findViewById(R.id.movieName);
        dateTextView =(TextView) rootView.findViewById(R.id.date);
        votersTextView =(TextView) rootView.findViewById(R.id.voters);
        overviewTextView =(TextView) rootView.findViewById(R.id.overview);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        addToFavoritButton = (Button) rootView.findViewById(R.id.addToFavorit);
        trailersNotAvailable = (TextView) rootView.findViewById(R.id.trailersNotAvailable);
        reviewsNotAvailable = (TextView) rootView.findViewById(R.id.reviewsNotAvailable);
        reviews_exp_list = (ExpandableListView) rootView.findViewById(R.id.reviews_exp_list);
        trailers_exp_list = (ExpandableListView) rootView.findViewById(R.id.trailers_exp_list);

    }

    public void setMovieContent()
    {

        if (Utilities.checkInsertedInDatabase(getActivity() , movieContent.id))
        {
            addToFavoritButton.setText("Remove From Favorites");
            addToFavoritButton.setBackgroundColor(Color.parseColor("#fc2e40"));
        }
        else{
            addToFavoritButton.setText("Add To Favorites");
            addToFavoritButton.setBackgroundColor(Color.parseColor("#049e47"));
        }
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

        addToFavoritButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteClick ();
            }
        });
    }
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("myMovie" , movieContent);
        outState.putSerializable("movieReviewses" , movieReviewses);
        outState.putSerializable("movieVideoList" , movieVideoList);

    }

    public void setTrailersExpList (MovieVideoList movieVideoList2)
    {
        trailersNotAvailable.setVisibility(View.GONE);
        expandableListKey = new ArrayList<String>();
        expandableListHashMap = new HashMap<String, String>();
        for (int i=0 ;i<movieVideoList2.results.size() ;i++)
        {
            expandableListHashMap.put(movieVideoList2.results.get(i).name ,movieVideoList2.results.get(i).key);
            expandableListKey.add(movieVideoList2.results.get(i).name);
        }

        trailersExpAdapter = new ExpandableListAdapter(getActivity() ,expandableListHashMap ,expandableListKey ,true);
        trailers_exp_list.setAdapter(trailersExpAdapter);
        trailers_exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Utilities.setListViewHeight(parent, groupPosition);
                return false;
            }
        });

    }
    public void  fetchVideos ()
    {
        String url =String.valueOf(movieContent.id)+"/videos";
        FetchMovies fetchMovies = new FetchMovies(getActivity());
        fetchMovies.setFetchMoviesCallback(
                new FetchMovies.FetchMoviesCallback() {
                    @Override
                    public void onPostExecute(Object object) {
                        movieVideoList = (MovieVideoList) object ;
                        if(movieVideoList.results.size()!=0)
                        {
                            setTrailersExpList (movieVideoList);
                        }
                        else
                            trailers_exp_list.setVisibility(View.GONE);
                        fetchReviews();

                    }
                }
        );
        fetchMovies.execute(url , "videos");
    }

    private void fetchVideosFromDB() {

        FetchFromDataBase fetchFromDataBase = new FetchFromDataBase(getActivity());
        fetchFromDataBase.setFetchMoviesDBCallback(new FetchFromDataBase.FetchFromDBCallback() {
            @Override
            public void onPostExecute(Object Object) {

                movieVideoList = (MovieVideoList) Object ;
                if(movieVideoList.results.size()!=0)
                {
                    setTrailersExpList (movieVideoList);
                }
                else
                    trailers_exp_list.setVisibility(View.GONE);
                fetchReviewsFromDB ();

            }
        });
        fetchFromDataBase.execute("videos" ,String.valueOf(movieContent.id ) ) ;
    }

    private void fetchReviewsFromDB() {
        FetchFromDataBase fetchFromDataBase = new FetchFromDataBase(getActivity());
        fetchFromDataBase.setFetchMoviesDBCallback(new FetchFromDataBase.FetchFromDBCallback() {
            @Override
            public void onPostExecute(Object Object) {

                movieReviewses = (MovieReviewsList) Object ;
                if(movieReviewses.results.size()!=0)
                {
                    setReviewsExpList (movieReviewses);
                  //  setShareActionProvider ();
                }
                else
                    reviews_exp_list.setVisibility(View.GONE);

            }
        });
        fetchFromDataBase.execute("reviews" ,String.valueOf(movieContent.id ) ) ;
    }


    public void setReviewsExpList (MovieReviewsList movieReviewses2)
    {
        reviewsNotAvailable.setVisibility(View.GONE);
        expandableListKey = new ArrayList<String>();
        expandableListHashMap = new HashMap<String, String>();
        for (int i =0 ;i<movieReviewses2.results.size() ;i++)
        {
            expandableListHashMap.put(movieReviewses2.results.get(i).author ,movieReviewses2.results.get(i).content);
            expandableListKey.add(movieReviewses2.results.get(i).author);
        }
        reviewsExpAdapter = new ExpandableListAdapter(getActivity() ,expandableListHashMap ,expandableListKey,false);
        reviews_exp_list.setAdapter(reviewsExpAdapter);
        reviews_exp_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Utilities.setListViewHeight(parent, groupPosition);
                return false;
            }
        });

    }

    public void  fetchReviews ()
    {
        String url =String.valueOf(movieContent.id)+"/reviews";
        FetchMovies fetchMovies = new FetchMovies(getActivity());
        fetchMovies.setFetchMoviesCallback(
                new FetchMovies.FetchMoviesCallback() {
                    @Override
                    public void onPostExecute(Object object) {
                        movieReviewses = (MovieReviewsList) object ;
                        if(movieReviewses.results.size()!=0)
                        {
                            setReviewsExpList (movieReviewses);
                            setShareActionProvider ();
                        }
                        else
                            reviews_exp_list.setVisibility(View.GONE);

                    }
                }
        );
        fetchMovies.execute(url , "reviews");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.share_menu, menu);

        menuItem = menu.findItem(R.id.action_share);

    }

    private void setShareActionProvider ()
    {
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        mShareActionProvider.setShareIntent(createShareForecastIntent());
    }
    private Intent createShareForecastIntent() {
        String movieName = "Movie Name : " + movieContent.original_title  ;
        String key="";
        if (movieVideoList.results.size()>=0)
        {
             key = movieVideoList.results.get(0).key;

        }
        String url = "https://www.youtube.com/watch?v="+ key;
        String sharedString = "#Movie_App \n"+movieName+"\nMovie Official Trailers : "+url+"\n" ;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, sharedString);
        return shareIntent;
    }



    public void onFavoriteClick()
    {
        if (addToFavoritButton.getText().toString().equals("Add To Favorites")) {
            ContentValues reviewValues[] = new ContentValues[movieReviewses.results.size()];
            for (int i = 0; i < movieReviewses.results.size(); i++) {
                ContentValues tempVal = new ContentValues();
                tempVal.put(MovieContract.ReviewEntry.COLUMN_AUTHOR, movieReviewses.results.get(i).author);
                tempVal.put(MovieContract.ReviewEntry.COLUMN_CONTENT, movieReviewses.results.get(i).content);
                tempVal.put(MovieContract.ReviewEntry.COLUMN_MOVIE_ID, movieContent.id);
                reviewValues[i] = tempVal;
            }
            ContentValues trailersValues[] = new ContentValues[movieVideoList.results.size()];
            for (int i = 0; i < movieVideoList.results.size(); i++) {
                ContentValues tempVal = new ContentValues();
                tempVal.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, movieContent.id);
                tempVal.put(MovieContract.TrailerEntry.COLUMN_KEY, movieVideoList.results.get(i).key);
                tempVal.put(MovieContract.TrailerEntry.COLUMN_NAME, movieVideoList.results.get(i).name);

                trailersValues[i] = tempVal;
            }
            ContentValues movieVals = new ContentValues();
            movieVals.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieContent.id);
            movieVals.put(MovieContract.MovieEntry.COLUMN_TITLE, movieContent.original_title);
            movieVals.put(MovieContract.MovieEntry.COLUMN_DESCRIPTION, movieContent.overview);
            movieVals.put(MovieContract.MovieEntry.COLUMN_POSTER_URL, movieContent.poster_path);
            movieVals.put(MovieContract.MovieEntry.COLUMN_RATING, movieContent.vote_average);
            movieVals.put(MovieContract.MovieEntry.COLUMN_REALEASE_DATE, movieContent.release_date);
            movieVals.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, movieContent.vote_count);
            movieVals.put(MovieContract.MovieEntry.COLUMN_BAVKDROP_URL, movieContent.backdrop_path);

            Uri returnUriMovie = getActivity().getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, movieVals);
            int returnTrailerInserted = 0, returnReviewInserted = 0;
            if (trailersValues.length != 0) {
                returnTrailerInserted = getActivity().getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI,
                        trailersValues);
            }
            if (reviewValues.length != 0) {
                returnReviewInserted = getActivity().getContentResolver().bulkInsert(MovieContract.ReviewEntry.CONTENT_URI,
                        reviewValues);
            }
            if (returnUriMovie != null) {
                addToFavoritButton.setText("Remove From Favorites");
                addToFavoritButton.setBackgroundColor(Color.parseColor("#fc2e40"));
                Toast.makeText(getActivity() ,"Added To Favorites" ,Toast.LENGTH_SHORT).show();
            }
        }
        else {
            int trailersDeleted = getActivity().getContentResolver().delete(MovieContract.TrailerEntry.CONTENT_URI ,
                    MovieContract.TrailerEntry.COLUMN_MOVIE_ID +" = ? " ,new String[]{String.valueOf(movieContent.id)} );
            int reviewDeleted =  getActivity().getContentResolver().delete(MovieContract.ReviewEntry.CONTENT_URI ,
                    MovieContract.ReviewEntry.COLUMN_MOVIE_ID +" = ? " ,new String[]{String.valueOf(movieContent.id)} );
            int moviesDeleted =getActivity().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI ,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID+" = ? ",new String[]{String.valueOf(movieContent.id)} );
            if(moviesDeleted!=0){
                addToFavoritButton.setText("Add To Favorites");
                addToFavoritButton.setBackgroundColor(Color.parseColor("#049e47"));
                Toast.makeText(getActivity() ,"Removed From Favorites" ,Toast.LENGTH_SHORT).show();


            }
        }

    }


}
