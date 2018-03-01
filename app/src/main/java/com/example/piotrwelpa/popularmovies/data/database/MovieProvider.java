package com.example.piotrwelpa.popularmovies.data.database;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.piotrwelpa.popularmovies.R;

public class MovieProvider extends ContentProvider {

    public static final int CODE_MOVIE = 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mMovieDbHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContract.PATH_MOVIE, CODE_MOVIE);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mMovieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case CODE_MOVIE:
                cursor = mMovieDbHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException(getContext().getString(R.string.not_implemented));
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowsInsertedCount;
        Uri insertedUri = null;

        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE:
                rowsInsertedCount = (int) mMovieDbHelper.getWritableDatabase().insert(
                        MovieContract.MovieEntry.TABLE_NAME,
                        null,
                        values);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsInsertedCount > 0){
            insertedUri = ContentUris.withAppendedId(uri, rowsInsertedCount);
            getContext().getContentResolver().notifyChange(insertedUri, null);
        }
        return insertedUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsDeletedCount;

        if(selection == null){
            selection = "1";
        }

        switch (sUriMatcher.match(uri)){
            case CODE_MOVIE:
                rowsDeletedCount = mMovieDbHelper.getWritableDatabase().delete(
                        MovieContract.MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeletedCount != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeletedCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new RuntimeException(getContext().getString(R.string.not_implemented));
    }
}
