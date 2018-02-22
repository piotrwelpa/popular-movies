package com.example.piotrwelpa.popularmovies.ui.activities;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.databinding.ActivityMovieDetailBinding;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_KEY);

        setTitle("Movie details");
        populateUI(movie);
    }

    private void populateUI(Movie movie) {
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        binding.setMovie(movie);
    }
}
