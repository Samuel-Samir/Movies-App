package com.example.android.movies_app.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuel on 10/24/2016.
 */

public class MovieReviewsList implements Serializable
{
   public List<MovieReviews> results = new ArrayList<>();
    public long id ;
    public long page ;
}
