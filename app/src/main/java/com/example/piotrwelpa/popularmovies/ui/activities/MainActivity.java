package com.example.piotrwelpa.popularmovies.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.preferences.MoviesPreferences;
import com.example.piotrwelpa.popularmovies.ui.adapters.MovieListAdapter;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_set_pref_popular:
                MoviesPreferences.setPreferedEndpoint(this, MoviesPreferences.PREF_POPULAR_ENDPOINT);
                return true;
            case R.id.action_set_pref_top_rated:
                MoviesPreferences.setPreferedEndpoint(this, MoviesPreferences.PREF_TOP_RATED_ENDPOINT);
                return true;
        }
        return false;
    }
}
