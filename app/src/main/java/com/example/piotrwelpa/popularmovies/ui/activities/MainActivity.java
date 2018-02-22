package com.example.piotrwelpa.popularmovies.ui.activities;

import android.content.Intent;
import android.os.Parcelable;
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

import java.util.List;

public class MainActivity extends AppCompatActivity{

    public static final int NUMBER_OF_COLUMNS = 2;
    public static final String MOVIE_KEY = "movie";
    MovieListAdapter mMovieAdapter;
    RecyclerView mRecyclerView;
    List<Movie> mData;
    int mId = 0;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.posters_image_rv);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COLUMNS));

        initView();
    }

    public void initView(){
        LoaderManager.LoaderCallbacks<MovieListDetails> callback
                = new LoaderManager.LoaderCallbacks<MovieListDetails>() {
            @Override
            public Loader<MovieListDetails> onCreateLoader(int id, Bundle args) {
                return new MovieLoader(MainActivity.this);
            }

            @Override
            public void onLoadFinished(Loader<MovieListDetails> loader, MovieListDetails data) {
                mData = data.getResults();
                mMovieAdapter = new MovieListAdapter(mData, new MovieListAdapter.OnItemClickListener(){

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
        getSupportLoaderManager().initLoader(mId,null, callback);
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
                if (!MoviesPreferences.getPreferedEndpoint(this).equals(MoviesPreferences.PREF_POPULAR_ENDPOINT)) {
                    MoviesPreferences.setPreferedEndpoint(this, MoviesPreferences.PREF_POPULAR_ENDPOINT);
                    mId++;
                    initView();
                }
                return true;
            case R.id.action_set_pref_top_rated:
                if (!MoviesPreferences.getPreferedEndpoint(this).equals(MoviesPreferences.PREF_TOP_RATED_ENDPOINT)){
                    MoviesPreferences.setPreferedEndpoint(this, MoviesPreferences.PREF_TOP_RATED_ENDPOINT);
                    mId++;
                    initView();
                }
                return true;
        }
        return false;
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

}
