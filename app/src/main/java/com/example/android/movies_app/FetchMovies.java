package com.example.android.movies_app;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.movies_app.Models.MoviesList;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.android.movies_app.PopularFragment.moviesListResult;

/**
 * Created by samuel on 10/21/2016.
 */

public class FetchMovies extends AsyncTask <String ,Void,MoviesList> {

    private final String LOG_TAG = FetchMovies.class.getName();


    public interface FetchMoviesCallback
    {
        public  void onPostExecute(MoviesList moviesList);
    }
    private  FetchMoviesCallback fetchMoviesCallback;
    public  void setFetchMoviesCallback (FetchMoviesCallback fetchMoviesCallback)
    {
        this.fetchMoviesCallback =fetchMoviesCallback;
    }
    @Override
    protected MoviesList doInBackground(String... params) {
        MoviesList moviesList = new MoviesList();
        if (params.length == 0) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            final String BASE_URL = "http://api.themoviedb.org/3/movie/";
            final String API_KEY = "api_key";
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(params[0])
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
            Gson gson = new Gson() ;
            moviesList  = gson.fromJson(buffer.toString() ,MoviesList.class);
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
        return moviesList;
    }

    @Override
    protected void onPostExecute(MoviesList moviesList) {
        moviesListResult = moviesList ;

      /*  if (fetchMoviesCallback !=null)
        {
            fetchMoviesCallback.onPostExecute(moviesList);
        }*/
    }
}