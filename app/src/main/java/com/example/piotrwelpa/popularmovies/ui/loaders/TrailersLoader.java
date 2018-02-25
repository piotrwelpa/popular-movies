package com.example.piotrwelpa.popularmovies.ui.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.piotrwelpa.popularmovies.data.mapper.MovieMapper;
import com.example.piotrwelpa.popularmovies.data.model.MovieListDetails;
import com.example.piotrwelpa.popularmovies.data.model.TrailerList;
import com.example.piotrwelpa.popularmovies.utilities.NetworkUtils;

import java.io.IOException;

/**
 * Created by piotr.welpa on 20.02.2018.
 */

public class TrailersLoader extends AsyncTaskLoader<TrailerList> {
    private Double id;
    private static final String TAG = TrailersLoader.class.getSimpleName();

    public TrailersLoader(Context context, Double id) {
        super(context);
        this.id = id;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public TrailerList loadInBackground() {
        String resultFromUrl = null;
        try {
            resultFromUrl = NetworkUtils.getResponseFromHttpUrl(getContext(), id);
        } catch (IOException e) {
            Log.e(TAG, "Error in getting response from http. ");
            e.printStackTrace();
        }
        if (resultFromUrl == null) return null;

        try {
            return MovieMapper.parseTrailerJsonToTrailerList(resultFromUrl);
        } catch (IOException e) {
            Log.e(TAG, "Error while parsing json to object. ");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(TrailerList data) {
        super.deliverResult(data);
    }
}
