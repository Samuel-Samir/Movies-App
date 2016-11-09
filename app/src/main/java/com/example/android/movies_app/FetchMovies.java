
package com.example.android.movies_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.movies_app.Models.MovieReviewsList;
import com.example.android.movies_app.Models.MovieVideoList;
import com.example.android.movies_app.Models.MoviesList;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by samuel on 10/21/2016.
 */

public class FetchMovies extends AsyncTask <String ,Void,Object> {

    private final String LOG_TAG = FetchMovies.class.getName();
    private Activity activity ;
    private ProgressDialog dialog ;

    public  FetchMovies (Activity activity )
    {
        this.activity=activity;
        dialog = new ProgressDialog(activity);
    }


//////////////////////////////////////////////////////////////////////////

    public interface FetchMoviesCallback
    {
        public  void onPostExecute(Object Object);
    }
    private  FetchMoviesCallback fetchMoviesCallback;
    public  void setFetchMoviesCallback (FetchMoviesCallback fetchMoviesCallback)
    {
        this.fetchMoviesCallback =fetchMoviesCallback;
    }
 ////////////////////////////////////////////////////////////////////////////





    public Object movieParse (String jsonString ,String parsingType)
    {
        MoviesList moviesList = new MoviesList();
        MovieReviewsList movieReviewses = new MovieReviewsList() ;
        MovieVideoList movieVideoList =new MovieVideoList();
        Gson gson = new Gson() ;
        if (parsingType.equals("movie"))
        {
            moviesList = gson.fromJson(jsonString, MoviesList.class);
            return moviesList;
        }
        else if (parsingType.equals("reviews"))
        {
            movieReviewses = gson.fromJson(jsonString, MovieReviewsList.class);
            return movieReviewses;
        }

        else if(parsingType.equals("videos"))
        {
            movieVideoList = gson.fromJson(jsonString, MovieVideoList.class);
            return movieVideoList;
        }
            return null;
    }
    @Override
    protected Object doInBackground(String... params) {

        Object resultObject = new Object();
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            final String BASE_URL = "http://api.themoviedb.org/3/movie/";
            final String API_KEY = "api_key";
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendEncodedPath(params[0])
                    .appendQueryParameter(API_KEY, BuildConfig.OPEN_WEATHER_MAP_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            resultObject  =movieParse(buffer.toString() ,params[1]);


        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        }
        finally
        {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return resultObject;
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("loading...");
        this.dialog.show();
        super.onPreExecute();
    }




    @Override
    protected void onPostExecute(Object object) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (fetchMoviesCallback !=null)
        {
            fetchMoviesCallback.onPostExecute(object);
        }
    }

}

