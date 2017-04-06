package in.pankajadhyapak.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import in.pankajadhyapak.popularmovies2.data.MovieContract.MovieEntry;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "MovieDbHelper";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "movies.db";

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

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;

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
}