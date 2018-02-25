package com.example.piotrwelpa.popularmovies.utilities;

import android.content.Context;
import android.net.Uri;

import com.example.piotrwelpa.popularmovies.R;
import com.example.piotrwelpa.popularmovies.data.preferences.MoviesPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * Created by piotr.welpa on 18.02.2018.
 */

public final class NetworkUtils {
    /* Movie list URL  */
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie";
    private static final String POPULAR_ENDPOINT = "popular";
    private static final String TOP_RATED_ENDPOINT = "top_rated";
    private static final String API_KEY_PREFIX = "api_key";

    /* Image Url */
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    private static final String IMAGE_DEFAULT_SIZE = "w185";

    /* Trailer Url*/
    private static final String TRAILER_VIDEO = "videos";

    private static URL getUrl(Context context) throws MalformedURLException {
        String endpoint = MoviesPreferences.getPreferredEndpoint(context);

        if (endpoint.equals(POPULAR_ENDPOINT)) {
            return buildPopularUrl(context);
        } else if (endpoint.equals(TOP_RATED_ENDPOINT)) {
            return buildTopRatedUrl(context);
        }
        return null;
    }

    private static URL getUrl(Context context, Double id) throws MalformedURLException {
        return buildTrailerUrl(context, id);
    }

    private static URL buildPopularUrl(Context context) throws MalformedURLException {
        Uri movieUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(POPULAR_ENDPOINT)
                .appendQueryParameter(API_KEY_PREFIX, context.getString(R.string.API_KEY))
                .build();

        return new URL(movieUri.toString());
    }

    private static URL buildTopRatedUrl(Context context) throws MalformedURLException {
        Uri movieUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(TOP_RATED_ENDPOINT)
                .appendQueryParameter(API_KEY_PREFIX, context.getString(R.string.API_KEY))
                .build();

        return new URL(movieUri.toString());
    }

    private static URL buildTrailerUrl(Context context, Double id) throws MalformedURLException {


        Uri trailerUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(String.valueOf(id).split("\\.")[0])
                .appendPath(TRAILER_VIDEO)
                .appendQueryParameter(API_KEY_PREFIX, context.getString(R.string.API_KEY))
                .build();
        return new URL(trailerUri.toString());
    }

    public static String getResponseFromHttpUrl(Context context, Double id) throws IOException {
        URL url = null;

        if (context != null) {
            url = getUrl(context);
            if (id != null){
                url = getUrl(context, id);
            }
        }

        if (url == null) {
            return null;
        }
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getImageUrl(String imageUrl) {
        Uri uri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_DEFAULT_SIZE).build();
        return uri.toString() + imageUrl;
    }

}
