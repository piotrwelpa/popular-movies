package com.example.piotrwelpa.popularmovies.ui.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.piotrwelpa.popularmovies.data.mapper.MovieMapper;
import com.example.piotrwelpa.popularmovies.data.model.ReviewList;
import com.example.piotrwelpa.popularmovies.utilities.NetworkUtils;

import java.io.IOException;

public class ReviewLoader extends AsyncTaskLoader<ReviewList> {
    private Double id;
    private static final String TAG = ReviewLoader.class.getSimpleName();

    public ReviewLoader(Context context, Double id) {
        super(context);
        this.id = id;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public ReviewList loadInBackground() {
        String resultFromUrl = null;
        try {
            resultFromUrl = NetworkUtils.getResponseFromHttpUrl(getContext(), id, NetworkUtils.REVIEW);
        } catch (IOException e) {
            Log.e(TAG, "Error in getting response from http. ");
            e.printStackTrace();
        }
        if (resultFromUrl == null) return null;

        try {

            return MovieMapper.parseReviewJsonToReviewList(resultFromUrl);
        } catch (IOException e) {
            Log.e(TAG, "Error while parsing json to object. ");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(ReviewList data) {
        super.deliverResult(data);
    }
}
