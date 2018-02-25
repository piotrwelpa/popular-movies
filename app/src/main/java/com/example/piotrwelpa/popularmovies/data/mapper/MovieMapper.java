package com.example.piotrwelpa.popularmovies.data.mapper;

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
    private static final String TOP_PAGE = "page";
    private static final String TOP_TOTAL_RESULT = "total_results";
    private static final String TOP_TOTAL_PAGES = "total_pages";
    private static final String TOP_RESULTS = "results";

    /* MOVIE TAGS */
    private static final String ADULT = "adult";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String GENRE_IDS = "genre_ids";
    private static final String ID = "id";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String RELEASE_DATE = "release_date";
    private static final String TITLE = "title";
    private static final String VIDEO = "video";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String VOTE_COUNT = "vote_count";

    public static MovieListDetails parseJsonToMovieList(String jsonString) {
        Map jsonMap = new Gson().fromJson(jsonString, Map.class);

        MovieListDetails movieListDetails = new MovieListDetails();

        /* Init list parameters */
        movieListDetails.setPage((Double) jsonMap.get(TOP_PAGE));
        movieListDetails.setTotalPages((Double) jsonMap.get(TOP_TOTAL_PAGES));
        movieListDetails.setTotalResult((Double) jsonMap.get(TOP_TOTAL_RESULT));

        ArrayList<Movie> movieArrayList = new ArrayList<>();

        ArrayList<Map> list = (ArrayList<Map>) jsonMap.get(TOP_RESULTS);

        for (Map item : list) {
            Movie movie = new Movie();
            movie.setId((Double) item.get(ID));
            movie.setAdult((Boolean) item.get(ADULT));
            movie.setBackdropPath((String) item.get(BACKDROP_PATH));
            movie.setGenreIds((List<Integer>) item.get(GENRE_IDS));
            movie.setOriginalTitle((String) item.get(ORIGINAL_TITLE));
            movie.setOverview((String) item.get(OVERVIEW));
            movie.setPopularity((Double) item.get(POPULARITY));
            movie.setPosterPath((String) item.get(POSTER_PATH));
            movie.setReleaseDate((String) item.get(RELEASE_DATE));
            movie.setTitle((String) item.get(TITLE));
            movie.setVideo((Boolean) item.get(VIDEO));
            movie.setVoteAverage((Double) item.get(VOTE_AVERAGE));
            movie.setVoteCount((Double) item.get(VOTE_COUNT));
            movie.setOriginalLanguage((String) item.get(ORIGINAL_LANGUAGE));
            movieArrayList.add(movie);
        }

        movieListDetails.setResults(movieArrayList);

        return movieListDetails;
    }
}
