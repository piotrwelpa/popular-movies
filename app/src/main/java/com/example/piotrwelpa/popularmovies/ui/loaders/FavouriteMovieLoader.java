package com.example.piotrwelpa.popularmovies.ui.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.example.piotrwelpa.popularmovies.data.database.MovieContract;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.model.MovieListDetails;

import java.util.ArrayList;
import java.util.List;


public class FavouriteMovieLoader extends AsyncTaskLoader<MovieListDetails> {


    public FavouriteMovieLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public MovieListDetails loadInBackground() {
        Cursor moviesCursor = getAllMovieFromDb();
        MovieListDetails movieListDetails = new MovieListDetails();
        List<Movie> movieList = new ArrayList<>();

        if (moviesCursor.moveToFirst()){
            do{
                int index;
                Movie movie = new Movie();

                index = moviesCursor.getColumnIndexOrThrow(MovieContract.MovieEntry._ID);
                movie.setId(moviesCursor.getDouble(index));

                index = moviesCursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_TITLE);
                movie.setTitle(moviesCursor.getString(index));

                index = moviesCursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
                movie.setPosterPath(moviesCursor.getString(index));

                index = moviesCursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE);
                movie.setOriginalTitle(moviesCursor.getString(index));

                index = moviesCursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_OVERVIEW);
                movie.setOverview(moviesCursor.getString(index));

                index = moviesCursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_USER_RATING);
                movie.setVoteAverage(moviesCursor.getDouble(index));

                index = moviesCursor.getColumnIndexOrThrow(MovieContract.MovieEntry.COLUMN_YEAR);
                movie.setReleaseDate(moviesCursor.getString(index));


                movieList.add(movie);
            }while (moviesCursor.moveToNext());
            movieListDetails.setResults(movieList);
            return movieListDetails;
        }
        return null;
    }

    @Override
    public void deliverResult(MovieListDetails data) {
        super.deliverResult(data);
    }

    private Cursor getAllMovieFromDb(){
        return getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                new String[]{MovieContract.MovieEntry._ID,
                             MovieContract.MovieEntry.COLUMN_TITLE,
                             MovieContract.MovieEntry.COLUMN_POSTER_PATH,
                             MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE,
                             MovieContract.MovieEntry.COLUMN_OVERVIEW,
                             MovieContract.MovieEntry.COLUMN_USER_RATING,
                             MovieContract.MovieEntry.COLUMN_YEAR},
                null,
                null,
                null);
    }
}
