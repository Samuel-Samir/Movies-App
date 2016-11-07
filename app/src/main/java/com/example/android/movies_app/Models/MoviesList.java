package com.example.android.movies_app.Models;

import android.graphics.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuel on 10/21/2016.
 */

public class MoviesList implements Serializable {

     public List <MovieContent> results =new ArrayList<MovieContent>();
      public int page ;


}
