package com.example.piotrwelpa.popularmovies.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.model.MovieListDetails;
import com.example.piotrwelpa.popularmovies.data.preferences.MoviesPreferences;
import com.example.piotrwelpa.popularmovies.ui.adapters.MovieListAdapter;
import com.example.piotrwelpa.popularmovies.ui.loaders.MovieLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String MOVIE_KEY = "movie";
    private MovieListAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private List<Movie> mData;
    private int mId = 0;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.posters_image_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));

        if (isOnline()) {
            initView();
        } else {
            Toast.makeText(this, "Network connection is disabled. Please enable it.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        LoaderManager.LoaderCallbacks<MovieListDetails> callback
                = new LoaderManager.LoaderCallbacks<MovieListDetails>() {
            @Override
            public Loader<MovieListDetails> onCreateLoader(int id, Bundle args) {
                return new MovieLoader(MainActivity.this);
            }

            @Override
            public void onLoadFinished(Loader<MovieListDetails> loader, MovieListDetails data) {
                mData = data.getResults();
                mMovieAdapter = new MovieListAdapter(mData, new MovieListAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(Movie movie) {
                        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                        intent.putExtra(MOVIE_KEY, movie);
                        startActivity(intent);
                    }
                });
                mRecyclerView.setAdapter(mMovieAdapter);
                mRecyclerView.invalidate();
                if (mBundleRecyclerViewState != null) {
                    Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
                }
            }

            @Override
            public void onLoaderReset(Loader<MovieListDetails> loader) {

            }
        };
        getSupportLoaderManager().initLoader(mId, null, callback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_set_pref_popular:
                if (!MoviesPreferences.getPreferredEndpoint(this).equals(MoviesPreferences.PREF_POPULAR_ENDPOINT)) {
                    MoviesPreferences.setPreferredEndpoint(this, MoviesPreferences.PREF_POPULAR_ENDPOINT);
                    mId++;
                    if (isOnline()) {
                        initView();
                    } else {
                        Toast.makeText(this, "Network connection is disabled. Please enable it and refresh.", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            case R.id.action_set_pref_top_rated:
                if (!MoviesPreferences.getPreferredEndpoint(this).equals(MoviesPreferences.PREF_TOP_RATED_ENDPOINT)) {
                    MoviesPreferences.setPreferredEndpoint(this, MoviesPreferences.PREF_TOP_RATED_ENDPOINT);
                    mId++;
                    if (isOnline()) {
                        initView();
                    } else {
                        Toast.makeText(this, "Network connection is disabled. Please enable it and refresh.", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            case R.id.action_refresh:
                if (isOnline()) {
                    initView();
                } else {
                    Toast.makeText(this, "Network connection is disabled. Please enable it and refresh.", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }
}
