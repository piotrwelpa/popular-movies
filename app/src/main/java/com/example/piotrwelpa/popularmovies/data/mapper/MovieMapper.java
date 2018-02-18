package com.example.piotrwelpa.popularmovies.data.mapper;

import android.util.Log;

import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.model.MovieListDetails;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by piotr.welpa on 18.02.2018.
 */

public final class MovieMapper {
    /* TOP OBJECT TAGS */
    private String TOP_PAGE = "page";
    private String TOP_TOTAL_RESULT = "total_results";
    private String TOP_TOTAL_PAGES = "total_pages";
    private String TOP_RESULTS = "results";

    /* MOVIE TAGS */
    private String ADULT = "adult";
    private String BACKDROP_PATH = "backdrop_path";
    private String GENRE_IDS = "genre_ids";
    private String ID = "id";
    private String ORIGINAL_LANGUAGE = "original_language";
    private String ORIGINAL_TITLE = "original_title";
    private String OVERVIEW = "overview";
    private String POPULARITY = "popularity";
    private String POSTER_PATH = "poster_path";
    private String RELASE_DATE = "release_date";
    private String TITLE = "title";
    private String VIDEO = "video";
    private String VOTE_AVERAGE = "vote_average";
    private String VOTE_COUNT = "vote_count";

    public final MovieListDetails parseJsonToMovieList(String jsonString){
        Map jsonMap = new Gson().fromJson(jsonString, Map.class);

        MovieListDetails movieListDetails = new MovieListDetails();

        /* Init list parametes */
        movieListDetails.setPage((Integer) jsonMap.get(TOP_PAGE));
        movieListDetails.setToatlPages((Integer) jsonMap.get(TOP_TOTAL_PAGES));
        movieListDetails.setTotalResult((Integer) jsonMap.get(TOP_TOTAL_RESULT));

        ArrayList<Movie> movieArrayList = new ArrayList<>();

        ArrayList<Map> list = (ArrayList<Map>) jsonMap.get(TOP_RESULTS);

        for (Map item: list){
            Movie movie = new Movie();
            movie.setId((Integer) item.get(ID));
            movie.setAdult((Boolean) item.get(ADULT));
            movie.setBackdropPath((String) item.get(BACKDROP_PATH));
            movie.setGenreIds((List<Integer>) item.get(GENRE_IDS));
            movie.setOriginalTitle((String) item.get(ORIGINAL_TITLE));
            movie.setOverview((String) item.get(OVERVIEW));
            movie.setPopularity((Float) item.get(POPULARITY));
            movie.setPosterPath((String) item.get(POSTER_PATH));
            movie.setReleaseDate((String) item.get(RELASE_DATE));
            movie.setTitle((String) item.get(TITLE));
            movie.setVideo((Boolean) item.get(VIDEO));
            movie.setVoteAverage((Float) item.get(VOTE_AVERAGE));
            movie.setVoteCount((Integer) item.get(VOTE_COUNT));
            movie.setOriginalLanguage((String) item.get(ORIGINAL_LANGUAGE));
            movieArrayList.add(movie);
        }

        movieListDetails.setResults(movieArrayList);

        return movieListDetails;
    }
}
