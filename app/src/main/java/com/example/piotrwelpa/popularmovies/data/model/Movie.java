package com.example.piotrwelpa.popularmovies.data.model;

/**
 * Created by piotr.welpa on 18.02.2018.
 */

public class Movie {
    private int id;
    private int voteCount;
    private boolean video;
    private float vote_average;
    private String title;
    private float popularity;
    private String posterPath;
    private String originalTitle;
    private int[] genreIds;
    private String backdropPath;
    private boolean adult;
    private String overwier;
    private String releaseDate;

    public String getImageUrl(){
        return "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    }
}

