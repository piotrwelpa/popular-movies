package com.example.piotrwelpa.popularmovies.data.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by piotr.welpa on 19.02.2018.
 */

public final class MoviesPreferences {
    private MoviesPreferences() {
    }

    public static final String PREF_POPULAR_ENDPOINT = "popular";
    public static final String PREF_TOP_RATED_ENDPOINT = "top_rated";

    private static final String PREF_ENDPOINT_KEY = "endpoint_key";

    public static String getPreferredEndpoint(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PREF_ENDPOINT_KEY, PREF_POPULAR_ENDPOINT);
    }

    public static void setPreferredEndpoint(Context context, String endpoint) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_ENDPOINT_KEY, endpoint);
        editor.apply();
    }

}
