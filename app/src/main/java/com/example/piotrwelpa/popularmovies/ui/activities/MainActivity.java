package com.example.piotrwelpa.popularmovies.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.ui.adapters.MovieListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NUMBER_OF_COLUMNS = 2;
    MovieListAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.posters_image_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));
        List<Movie> data = new ArrayList<>();
        data.add(new Movie());
        data.add(new Movie());
        data.add(new Movie());
        data.add(new Movie());
        data.add(new Movie());
        data.add(new Movie());
        movieAdapter = new MovieListAdapter(this, data);
        recyclerView.setAdapter(movieAdapter);

    }
}
