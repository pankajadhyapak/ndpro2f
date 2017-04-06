package in.pankajadhyapak.popularmovies2.data;

import android.net.Uri;
import android.provider.BaseColumns;

final class MovieContract {

    static final String AUTHORITY = "in.pankajadhyapak.popularmovies2";
    static final String PATH_MOVIES = "movies";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    private MovieContract() {
    }

    static class MovieEntry implements BaseColumns {

        static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        static final String TABLE_NAME = "FavouriteMovies";

        static final String COLUMN_NAME_POSTER_PATH = "poster_path";
        static final String COLUMN_NAME_ADULT = "adult";
        static final String COLUMN_NAME_OVERVIEW = "overview";
        static final String COLUMN_NAME_RELEASE_DATE = "release_date";
        static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        static final String COLUMN_NAME_ORIGINAL_TITLE = "original_title";
        static final String COLUMN_NAME_ORIGINAL_LANGUAGE = "original_language";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
        static final String COLUMN_NAME_POPULARITY = "popularity";
        static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
        static final String COLUMN_NAME_VIDEO = "video";
        static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
    }
}
