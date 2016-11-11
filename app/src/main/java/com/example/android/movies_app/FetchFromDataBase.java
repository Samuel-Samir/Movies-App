package com.example.android.movies_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.movies_app.Database.MovieContract;
import com.example.android.movies_app.Models.MovieContent;
import com.example.android.movies_app.Models.MovieReviews;
import com.example.android.movies_app.Models.MovieReviewsList;
import com.example.android.movies_app.Models.MovieVideo;
import com.example.android.movies_app.Models.MovieVideoList;
import com.example.android.movies_app.Models.MoviesList;


/**
 * Created by samuel on 11/9/2016.
 */

public class FetchFromDataBase extends AsyncTask<String ,Void,Object> {


    private final String LOG_TAG = FetchFromDataBase.class.getName();
    private Activity activity ;
    private ProgressDialog dialog ;

    public  FetchFromDataBase  (Activity activity )
    {
        this.activity=activity;
        dialog = new ProgressDialog(activity);
    }


//////////////////////////////////////////////////////////////////////////

    public interface FetchFromDBCallback
    {
        public  void onPostExecute(Object object);
    }
    private  FetchFromDBCallback fetchFromDBCallback ;
    public  void setFetchMoviesDBCallback ( FetchFromDBCallback fetchFromDBCallback)
    {
        this.fetchFromDBCallback =fetchFromDBCallback;
    }
    ////////////////////////////////////////////////////////////////////////////


    @Override
    protected Object doInBackground(String... params) {


            if (params[0].equals("movie"))
            {
                MoviesList MoviesListDB ;
                Uri moviesUri = MovieContract.MovieEntry.CONTENT_URI;
                Cursor cursor = activity.getContentResolver().query(moviesUri , null , null , null , null);
                MoviesListDB = getMoviesFromCursor(cursor);
                //TODO
                cursor.close();
                return MoviesListDB;
            }
           else if (params[0].equals("videos"))
            {

                Uri trailersUri = Uri.parse(MovieContract.TrailerEntry.CONTENT_URI.toString() +"/"+params[1]);
                Cursor cursor = activity.getContentResolver().query(trailersUri , null , null , null , null);
                MovieVideoList movieVideoList = getTrailersFromCursor(cursor);
                cursor.close();
                return movieVideoList ;
            }
            else if (params[0].equals("reviews"))
            {
                Uri reviewWithIdUri =Uri.parse(MovieContract.ReviewEntry.CONTENT_URI.toString()+"/"+params[1]);
                Cursor cursor = activity.getContentResolver().query(reviewWithIdUri,null,null,null,null);
                MovieReviewsList movieReviewsList = getReviewFromCursor(cursor);
                cursor.close();
                return movieReviewsList;
            }


        return  null;
    }
    private MoviesList getMoviesFromCursor(Cursor cursor) {

        int movieIdIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID);
        int movieTitleIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
        int posterUrlIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_URL);
        int backdropUrlIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BAVKDROP_URL);
        int movieRatingIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING);
        int moviedescriptionIndex =  cursor.getColumnIndex("description");
        int movievoteCountIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_COUNT);
        int movieDateIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_REALEASE_DATE);
        String names [] = cursor.getColumnNames() ;

        MoviesList favMoviesList = new MoviesList() ;
        while (cursor.moveToNext())
        {
            MovieContent movieContent  =  new MovieContent();
            movieContent.id = cursor.getInt(movieIdIndex) ;
            movieContent.original_title = cursor.getString(movieTitleIndex) ;
            movieContent.poster_path = cursor.getString(posterUrlIndex) ;
            movieContent.backdrop_path =  cursor.getString(backdropUrlIndex) ;
            movieContent.vote_average = cursor.getDouble(movieRatingIndex) ;
            movieContent.overview = cursor.getString(moviedescriptionIndex) ;
            movieContent.vote_count = cursor.getInt(movievoteCountIndex) ;
            movieContent.release_date =  cursor.getString(movieDateIndex) ;
            favMoviesList.results.add(movieContent);
        }
        cursor.close();


        return  favMoviesList ;
    }

    private MovieReviewsList getReviewFromCursor(Cursor cursor) {

        int reviewAuthorIdx = cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_AUTHOR);
        int reviewContentIdx = cursor.getColumnIndex(MovieContract.ReviewEntry.COLUMN_CONTENT);
        MovieReviewsList movieReviewsList = new MovieReviewsList() ;
        while (cursor.moveToNext()){
            MovieReviews movieReviews = new MovieReviews() ;
            movieReviews.author = cursor.getString(reviewAuthorIdx) ;
            movieReviews.content =  cursor.getString(reviewContentIdx) ;
            movieReviewsList .results.add(movieReviews);
        }
        cursor.close();

        return movieReviewsList;
    }

    private MovieVideoList getTrailersFromCursor(Cursor cursor) {

       int trailerKeyIndex = cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_KEY);
       int trailerNameIndex = cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_NAME);

        MovieVideoList movieVideoList = new MovieVideoList() ;
        while (cursor.moveToNext()){
            MovieVideo video = new MovieVideo() ;
            video.key = cursor.getString(trailerKeyIndex) ;
            video.name = cursor.getString(trailerNameIndex) ;
            movieVideoList.results.add(video) ;
        }
        cursor.close();

        return movieVideoList;
    }




    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("loading...");
//        this.dialog.show();
        super.onPreExecute();    }

    @Override
    protected void onPostExecute(Object object) {
      /*  if (dialog.isShowing()) {
            dialog.dismiss();
        }*/
        if (fetchFromDBCallback !=null)
        {
            fetchFromDBCallback.onPostExecute(object);
        }
    }
}
