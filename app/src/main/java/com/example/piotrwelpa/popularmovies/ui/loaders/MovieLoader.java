package com.example.piotrwelpa.popularmovies.ui.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.piotrwelpa.popularmovies.data.mapper.MovieMapper;
import com.example.piotrwelpa.popularmovies.data.model.MovieListDetails;
import com.example.piotrwelpa.popularmovies.utilities.NetworkUtils;

import java.io.IOException;

/**
 * Created by piotr.welpa on 20.02.2018.
 */

public class MovieLoader extends AsyncTaskLoader<MovieListDetails> {


    public MovieLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public MovieListDetails loadInBackground() {
        String resultFromUrl = null;
        try {
            resultFromUrl = NetworkUtils.getResponseFromHttpUrl(getContext(), null, null);
        } catch (IOException e) {
            Log.e("MovieLoader", "Error in getting response from http. ");
            e.printStackTrace();
        }
        if (resultFromUrl == null) return null;

        return MovieMapper.parseJsonToMovieList(resultFromUrl);
    }

    @Override
    public void deliverResult(MovieListDetails data) {
        super.deliverResult(data);
    }
}
