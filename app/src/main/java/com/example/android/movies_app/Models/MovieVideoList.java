package com.example.android.movies_app.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuel on 10/24/2016.
 */

public class MovieVideoList implements Serializable{
    public long id;
    public List <MovieVideo> results = new ArrayList<>();
}

