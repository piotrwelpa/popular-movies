package com.example.piotrwelpa.popularmovies.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.mapper.MovieMapper;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.model.MovieListDetails;
import com.example.piotrwelpa.popularmovies.data.model.Trailer;
import com.example.piotrwelpa.popularmovies.data.model.TrailerList;
import com.example.piotrwelpa.popularmovies.databinding.ActivityMovieDetailBinding;
import com.example.piotrwelpa.popularmovies.ui.adapters.MovieListAdapter;
import com.example.piotrwelpa.popularmovies.ui.adapters.TrailerListAdapter;
import com.example.piotrwelpa.popularmovies.ui.loaders.MovieLoader;
import com.example.piotrwelpa.popularmovies.ui.loaders.TrailersLoader;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private int mId = 0;
    private List<Trailer> mData;
    private Double movieId;
    private RecyclerView mRecyclerView;
    private TrailerListAdapter mTrailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_KEY);
        mRecyclerView = findViewById(R.id.trailers_lv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MovieDetailActivity.this));
        movieId = movie.getId();
        setTitle("Movie details");
        populateUI(movie);

//        List<Trailer> list = new ArrayList<>();
//
//        Trailer trailer = new Trailer();
//        trailer.setName("Test1");
//        list.add(trailer);
//        trailer = new Trailer();
//        trailer.setName("Test2");
//        list.add(trailer);
//
//        mListView.setAdapter(new TrailerListAdapter(this, list));

//
//        String cars[] = {"Mercedes", "Fiat", "Ferrari", "Aston Martin", "Lamborghini", "Skoda", "Volkswagen", "Audi", "Citroen"};
//
//        ArrayList<String> carL = new ArrayList<String>();
//        carL.addAll( Arrays.asList(cars) );
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.trailer_item, carL);
//
//        mListView.setAdapter(adapter);

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

                    mTrailerAdapter = new TrailerListAdapter(mData, new TrailerListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Trailer trailer) {
                            Toast.makeText(MovieDetailActivity.this, "TEST", Toast.LENGTH_SHORT).show();
                        }
                    });

                    mRecyclerView.setAdapter(mTrailerAdapter);

                    mRecyclerView.invalidate();
                }
            }

            @Override
            public void onLoaderReset(Loader<TrailerList> loader) {

            }
        };
        getSupportLoaderManager().initLoader(mId, null, callback);
    }
}

