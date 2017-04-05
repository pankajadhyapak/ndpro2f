package in.pankajadhyapak.popularmovies2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import in.pankajadhyapak.popularmovies2.data.MovieContract.MovieEntry;
import in.pankajadhyapak.popularmovies2.models.Movie;

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                    MovieEntry._ID + " INTEGER PRIMARY KEY," +
                    MovieEntry.COLUMN_NAME_POSTER_PATH +" VARCHAR(50) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_ADULT +" VARCHAR(50) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_OVERVIEW +" VARCHAR(200) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_RELEASE_DATE +" VARCHAR(50) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_MOVIE_ID +" DOUBLE NOT NULL,"+
                    MovieEntry.COLUMN_NAME_ORIGINAL_TITLE +" VARCHAR(50) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE +" VARCHAR(50) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_TITLE +" VARCHAR(50) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_BACKDROP_PATH +" VARCHAR(50) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_POPULARITY +" VARCHAR(50) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_VOTE_COUNT +" DOUBLE NOT NULL,"+
                    MovieEntry.COLUMN_NAME_VIDEO +" VARCHAR(50) NOT NULL,"+
                    MovieEntry.COLUMN_NAME_VOTE_AVERAGE +" VARCHAR(50) NOT NULL)";


                    //    CREATE TABLE FavouriteMovies(
                    //            poster_path VARCHAR(50) NOT NULL,
                    //    adult VARCHAR(50) NOT NULL,
                    //    overview VARCHAR(200) NOT NULL,
                    //    release_date VARCHAR(50) NOT NULL,
                    //    movie_id DOUBLE NOT NULL,
                    //    original_title VARCHAR(50) NOT NULL,
                    //    original_language VARCHAR(50) NOT NULL,
                    //    title VARCHAR(50) NOT NULL,
                    //    backdrop_path VARCHAR(50) NOT NULL,
                    //    popularity VARCHAR(50) NOT NULL,
                    //    vote_count DOUBLE NOT NULL,
                    //    video VARCHAR(50) NOT NULL,
                    //    vote_average VARCHAR(50) NOT NULL,
                    //    PRIMARY KEY (poster_path))

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
    private static final String TAG = "MovieDbHelper";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Movie getMovie(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = MovieEntry.COLUMN_NAME_MOVIE_ID + " = ?";
        String[] selectionArgs = { id.toString() };
        Cursor cursor = db.query(
                MovieEntry.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
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
        cursor.close();
        return movie;

    }

    public long addMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        if(movie != null){
            // Create a new map of values, where column names are the keys
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
            // Insert the new row, returning the primary key value of the new row
            return db.insert(MovieEntry.TABLE_NAME, null, values);
        }
        return 0;
    }

    public ArrayList<Movie> getAllMovies(){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(MovieEntry.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        while (cursor.moveToNext()) {
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
        cursor.close();
        return movies;
    }

    public Boolean movieExists(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = MovieEntry.COLUMN_NAME_MOVIE_ID + " = ?";
        String[] selectionArgs = { id.toString() };
        Cursor cursor = db.query(
                MovieEntry.TABLE_NAME,                     // The table to query
                null,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        final boolean b = cursor.getCount() > 0;
        cursor.close();
        return b;
    }

    public Integer deleteMovie(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = MovieEntry.COLUMN_NAME_MOVIE_ID + " = ?";
        String[] selectionArgs = { id.toString() };
        return db.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
    }
}