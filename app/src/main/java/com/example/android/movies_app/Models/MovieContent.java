package com.example.android.movies_app.Models;

import java.io.Serializable;

/**
 * Created by samuel on 10/21/2016.
 */

public class MovieContent implements Serializable {
    public String poster_path ;
    public String overview;
    public String release_date ;
    public int id ;
    public String original_title;
    public String title;
    public String backdrop_path;
    public long vote_count ;
    public double vote_average ;
}
