package com.example.piotrwelpa.popularmovies.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.mapper.MovieMapper;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.model.MovieListDetails;
import com.example.piotrwelpa.popularmovies.data.model.Trailer;
import com.example.piotrwelpa.popularmovies.data.model.TrailerList;
import com.example.piotrwelpa.popularmovies.databinding.ActivityMovieDetailBinding;
import com.example.piotrwelpa.popularmovies.ui.adapters.MovieListAdapter;
import com.example.piotrwelpa.popularmovies.ui.loaders.MovieLoader;
import com.example.piotrwelpa.popularmovies.ui.loaders.TrailersLoader;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private int mId = 0;
    private List<Trailer> mData;
    private Double movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_KEY);
        movieId = movie.getId();
        setTitle("Movie details");
        populateUI(movie);

        loadAdditionalData();

    }

    private void populateUI(Movie movie) {
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        binding.setMovie(movie);
    }

    private void loadAdditionalData() {
        LoaderManager.LoaderCallbacks<TrailerList> callback
                = new LoaderManager.LoaderCallbacks<TrailerList>() {
            @Override
            public Loader<TrailerList> onCreateLoader(int id, Bundle args) {
                return new TrailersLoader(MovieDetailActivity.this, movieId);
            }

            @Override
            public void onLoadFinished(Loader<TrailerList> loader, TrailerList data) {
                if (data != null) {
                    mData = data.getResults();
                    Log.d("MovieDetails: ", mData.get(0).getName());
                }
            }

            @Override
            public void onLoaderReset(Loader<TrailerList> loader) {

            }
        };
        getSupportLoaderManager().initLoader(mId, null, callback);
    }
}

