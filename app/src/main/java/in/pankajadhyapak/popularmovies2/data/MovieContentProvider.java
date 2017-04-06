package in.pankajadhyapak.popularmovies2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import in.pankajadhyapak.popularmovies2.data.MovieContract.MovieEntry;

public class MovieContentProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;

    private static final String TAG = "MovieContentProvider";

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieDbHelper mDbHelper;

    public MovieContentProvider() {
    }

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return false;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        String id = uri.getPathSegments().get(1);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String mSelection = MovieEntry.COLUMN_NAME_MOVIE_ID + " = ?";
        String[] mSelectionArgs = {id};
        return db.delete(MovieEntry.TABLE_NAME, mSelection, mSelectionArgs);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return "Movies";
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri = null;
        switch (match) {
            case MOVIES:
                long id = db.insert(MovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row" + uri);
                }
                break;
            case MOVIE_WITH_ID:
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        switch (match) {
            case MOVIES:
                cursor = db.query(
                        MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                cursor = db.query(
                        MovieEntry.TABLE_NAME,
                        projection, MovieEntry.COLUMN_NAME_MOVIE_ID + " = ?",
                        new String[]{id},
                        null,
                        null,
                        sortOrder
                );
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        //Not required in this application use case
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
