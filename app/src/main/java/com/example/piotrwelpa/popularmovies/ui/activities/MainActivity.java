package com.example.piotrwelpa.popularmovies.ui.activities;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.model.MovieListDetails;
import com.example.piotrwelpa.popularmovies.data.preferences.MoviesPreferences;
import com.example.piotrwelpa.popularmovies.ui.adapters.MovieListAdapter;
import com.example.piotrwelpa.popularmovies.ui.loaders.MovieLoader;
import com.example.piotrwelpa.popularmovies.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NUMBER_OF_COLUMNS = 2;
    MovieListAdapter mMovieAdapter;
    RecyclerView mRecyclerView;
    List<Movie> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.posters_image_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));


        LoaderManager.LoaderCallbacks<MovieListDetails> callback
                = new LoaderManager.LoaderCallbacks<MovieListDetails>() {
            @Override
            public Loader<MovieListDetails> onCreateLoader(int id, Bundle args) {
                return new MovieLoader(MainActivity.this);
            }

            @Override
            public void onLoadFinished(Loader<MovieListDetails> loader, MovieListDetails data) {
                mData = data.getResults();
                mMovieAdapter = new MovieListAdapter(MainActivity.this, mData);
                mRecyclerView.setAdapter(mMovieAdapter);
            }

            @Override
            public void onLoaderReset(Loader<MovieListDetails> loader) {

            }
        };
        getSupportLoaderManager().initLoader(0,null, callback);


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
