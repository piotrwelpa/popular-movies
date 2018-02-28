package com.example.piotrwelpa.popularmovies.data.database;

import android.net.Uri;
import android.provider.BaseColumns;

class MovieContract {
    private static final String CONTENT_AUTHORITY = "com.example.piotrwelpa.popularmovies";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        static final String TABLE_NAME = "movie";
        static final String COLUMN_TITLE = "title";

    }
}
