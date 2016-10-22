package com.example.android.movies_app.Models;

import java.io.Serializable;

/**
 * Created by samuel on 10/21/2016.
 */

public class MovieContent implements Serializable {
    public  String poster_path ;
    public boolean adult ;
    public String overview;
    public String release_date ;
    public long [] genre_ids ;
    public long id ;
    public String original_title;
    public String original_language;
    public String title;
    public String backdrop_path;
    public double popularity ;
    public long vote_count ;
    public boolean video;
    public double vote_average ;
}
