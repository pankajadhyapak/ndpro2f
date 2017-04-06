package in.pankajadhyapak.popularmovies2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import in.pankajadhyapak.popularmovies2.data.MovieContract.MovieEntry;
import in.pankajadhyapak.popularmovies2.models.Movie;

public class MovieRepository {

    private static final String TAG = "MovieRepository";


    private Context mContext;

    public MovieRepository(Context mContext) {
        this.mContext = mContext;
    }

    public Movie getMovie(Integer id) {
        String sId = Integer.toString(id);
        Uri uri = MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(sId).build();
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        Movie movie = new Movie();
        if (cursor != null && cursor.moveToFirst()) {
            movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_MOVIE_ID)));
            movie.setAdult(!cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_ADULT)).equalsIgnoreCase("false"));
            movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_BACKDROP_PATH)));
            movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE)));
            movie.setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_ORIGINAL_TITLE)));
            movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_OVERVIEW)));
            movie.setPopularity(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_POPULARITY))));
            movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_POSTER_PATH)));
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_RELEASE_DATE)));
            movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_TITLE)));
            movie.setVideo(!cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_VIDEO)).equalsIgnoreCase("false"));
            movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_VOTE_AVERAGE))));
            movie.setVoteCount(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_VOTE_COUNT))));
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(MovieEntry._ID));
            cursor.close();
        }
        Log.e(TAG, "getMovie: id --> " + movie.getId() + " name --> " + movie.getTitle());
        return movie;
    }

    public long addMovie(Movie movie) {
        if (movie != null) {
            ContentValues values = new ContentValues();
            values.put(MovieEntry.COLUMN_NAME_MOVIE_ID, movie.getId());
            values.put(MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
            values.put(MovieEntry.COLUMN_NAME_BACKDROP_PATH, movie.getBackdropPath());
            values.put(MovieEntry.COLUMN_NAME_ADULT, movie.getAdult());
            values.put(MovieEntry.COLUMN_NAME_ORIGINAL_TITLE, movie.getOriginalTitle());
            values.put(MovieEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
            values.put(MovieEntry.COLUMN_NAME_POPULARITY, movie.getPopularity());
            values.put(MovieEntry.COLUMN_NAME_POSTER_PATH, movie.getPosterPath());
            values.put(MovieEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
            values.put(MovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
            values.put(MovieEntry.COLUMN_NAME_VIDEO, movie.getVideo());
            values.put(MovieEntry.COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAverage());
            values.put(MovieEntry.COLUMN_NAME_VOTE_COUNT, movie.getVoteCount());
            mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, values);
            return 1;
        }
        return 0;
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_MOVIE_ID)));
            movie.setAdult(!cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_ADULT)).equalsIgnoreCase("false"));
            movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_BACKDROP_PATH)));
            movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE)));
            movie.setOriginalTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_ORIGINAL_TITLE)));
            movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_OVERVIEW)));
            movie.setPopularity(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_POPULARITY))));
            movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_POSTER_PATH)));
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_RELEASE_DATE)));
            movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_TITLE)));
            movie.setVideo(!cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_VIDEO)).equalsIgnoreCase("false"));
            movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_VOTE_AVERAGE))));
            movie.setVoteCount(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_VOTE_COUNT))));
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(MovieEntry._ID));
            movies.add(movie);
        }
        if (cursor != null) {
            cursor.close();
        }
        return movies;
    }

    public Integer deleteMovie(Integer id) {
        String sId = Integer.toString(id);
        Uri uri = MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(sId).build();
        return mContext.getContentResolver().delete(uri, null, null);
    }

}
