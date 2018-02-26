package com.example.piotrwelpa.popularmovies.ui.activities;

import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.model.Review;
import com.example.piotrwelpa.popularmovies.data.model.ReviewList;
import com.example.piotrwelpa.popularmovies.data.model.Trailer;
import com.example.piotrwelpa.popularmovies.data.model.TrailerList;
import com.example.piotrwelpa.popularmovies.databinding.ActivityMovieDetailBinding;
import com.example.piotrwelpa.popularmovies.ui.adapters.TrailerListAdapter;
import com.example.piotrwelpa.popularmovies.ui.loaders.ReviewLoader;
import com.example.piotrwelpa.popularmovies.ui.loaders.TrailersLoader;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    private int mId = 0;
    private int mReviewId = 999;
    private List<Trailer> trailerData;
    private List<Review> reviewData;
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
        Log.d("ID!!!!!!!!!!!: ", String.valueOf(movieId));
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

        loadTrailerData();
        loadReviewData();

    }

    private void populateUI(Movie movie) {
        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        binding.setMovie(movie);
        loadTrailerData();
    }

    private void loadTrailerData() {
        LoaderManager.LoaderCallbacks<TrailerList> callback
                = new LoaderManager.LoaderCallbacks<TrailerList>() {
            @Override
            public Loader<TrailerList> onCreateLoader(int id, Bundle args) {
                return new TrailersLoader(MovieDetailActivity.this, movieId);
            }

            @Override
            public void onLoadFinished(Loader<TrailerList> loader, TrailerList data) {
                if (data != null) {
                    trailerData = data.getResults();

                    mTrailerAdapter = new TrailerListAdapter(trailerData, new TrailerListAdapter.OnItemClickListener() {
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

    private void loadReviewData() {
        LoaderManager.LoaderCallbacks<ReviewList> callback
                = new LoaderManager.LoaderCallbacks<ReviewList>() {
            @Override
            public Loader<ReviewList> onCreateLoader(int id, Bundle args) {
                return new ReviewLoader(MovieDetailActivity.this, movieId);
            }

            @Override
            public void onLoadFinished(Loader<ReviewList> loader, ReviewList data) {
                if (data != null) {
                    reviewData = data.getResults();
                    Log.d("REVIEWDATA: ", reviewData.get(0).toString());
                }
            }

            @Override
            public void onLoaderReset(Loader<ReviewList> loader) {

            }
        };
        getSupportLoaderManager().initLoader(mReviewId, null, callback);
    }
}

