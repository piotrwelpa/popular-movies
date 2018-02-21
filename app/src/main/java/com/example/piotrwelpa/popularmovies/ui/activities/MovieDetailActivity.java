package com.example.piotrwelpa.popularmovies.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView textView = (TextView) findViewById(R.id.textView);
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        textView.setText(movie.getOriginalTitle());
    }
}
