package com.example.piotrwelpa.popularmovies.ui.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.model.Movie;
import com.example.piotrwelpa.popularmovies.data.model.Review;
import com.example.piotrwelpa.popularmovies.data.model.ReviewList;
import com.example.piotrwelpa.popularmovies.data.model.Trailer;
import com.example.piotrwelpa.popularmovies.data.model.TrailerList;
import com.example.piotrwelpa.popularmovies.ui.adapters.ReviewListAdapter;
import com.example.piotrwelpa.popularmovies.ui.adapters.TrailerListAdapter;
import com.example.piotrwelpa.popularmovies.ui.loaders.ReviewLoader;
import com.example.piotrwelpa.popularmovies.ui.loaders.TrailersLoader;
import com.example.piotrwelpa.popularmovies.utilities.NetworkUtils;

import java.net.MalformedURLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    private int mId = 0;
    private int mReviewId = 999;
    private List<Trailer> trailerData;
    private List<Review> reviewData;
    private Double movieId;
    private TrailerListAdapter mTrailerAdapter;
    private ReviewListAdapter mReviewAdapter;
    private static final String YT_BASE_APP_URL = "vnd.youtube:";

    @BindView(R.id.original_title_tv)
    TextView mOriginalTitle;
    @BindView(R.id.mini_poster_iv)
    ImageView mPosterImageThumbnail;
    @BindView(R.id.year_tv)
    TextView mYearView;
    @BindView(R.id.user_rating_tv)
    TextView mUserRating;
    @BindView(R.id.overview_tv)
    TextView mOverView;
    @BindView(R.id.trailers_rv)
    RecyclerView mTrailersRv;
    @BindView(R.id.reviews_rv)
    RecyclerView mReviewsRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.MOVIE_KEY);
        mTrailersRv.setLayoutManager(new LinearLayoutManager(this));
        mReviewsRv.setLayoutManager(new LinearLayoutManager(this));
        movieId = movie.getId();

        setTitle("Movie details");
        populateUI(movie);
    }

    private void populateUI(Movie movie) {
        mOriginalTitle.setText(movie.getOriginalTitle());
        movie.loadImage(mPosterImageThumbnail, movie.getPosterPath());
        Log.d("IMAGE PATH: ", NetworkUtils.getImageUrl(movie.getPosterPath()));
        mYearView.setText(movie.getReleaseDate());
        mUserRating.setText(movie.getVoteAverage());
        mOverView.setText(movie.getOverview());
        loadTrailerData();
        loadReviewData();
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
                            String ytUrl = null;
                            if (trailer.getSite().equals("YouTube")) {
                                try {
                                    ytUrl = NetworkUtils.getYoutubeUrl(trailer.getKey());
                                } catch (MalformedURLException e) {
                                    Log.e(TAG, " Error while getting YouTube URL");
                                    String message = "Trailer cannot be viewed at this moment";
                                    Toast.makeText(MovieDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                                if (ytUrl != null){
                                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getYtAppUrl(trailer.getKey())));
                                    Intent siteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ytUrl));

                                    PackageManager packageManager = getPackageManager();
                                    List<ResolveInfo> activitiesApp = packageManager.queryIntentActivities(appIntent,
                                            PackageManager.MATCH_DEFAULT_ONLY);
                                    List<ResolveInfo> activitiesWeb = packageManager.queryIntentActivities(appIntent,
                                            PackageManager.MATCH_DEFAULT_ONLY);
                                    if (activitiesApp.size() > 0){
                                        startActivity(appIntent);
                                    } else if (activitiesWeb.size() >0){
                                        startActivity(siteIntent);
                                    } else{
                                        String message = "You don't have app to open this trailer. Sorry";
                                        Toast.makeText(MovieDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            } else {
                                String message = "This trailer is designed to display at " + trailer.getSite() +
                                        " which is not supported yet. Sorry";
                                Toast.makeText(MovieDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mTrailersRv.setAdapter(mTrailerAdapter);
                    mTrailerAdapter.notifyDataSetChanged();
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

                    mReviewAdapter = new ReviewListAdapter(reviewData);
                    mReviewsRv.setAdapter(mReviewAdapter);
                    mReviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLoaderReset(Loader<ReviewList> loader) {

            }
        };
        getSupportLoaderManager().initLoader(mReviewId, null, callback);
    }

    private String getYtAppUrl(String key){
        return YT_BASE_APP_URL + key;
    }
}

