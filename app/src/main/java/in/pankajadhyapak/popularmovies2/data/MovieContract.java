package in.pankajadhyapak.popularmovies2.data;

import android.provider.BaseColumns;

/**
 * Created by pankaj on 05/04/17.
 */

public final class MovieContract {

        private MovieContract() {}

        public static class MovieEntry implements BaseColumns {

            public static final String TABLE_NAME = "FavouriteMovies";

            public static final String COLUMN_NAME_POSTER_PATH = "poster_path";
            public static final String COLUMN_NAME_ADULT = "adult";
            public static final String COLUMN_NAME_OVERVIEW = "overview";
            public static final String COLUMN_NAME_RELEASE_DATE = "release_date";
            public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
            public static final String COLUMN_NAME_ORIGINAL_TITLE = "original_title";
            public static final String COLUMN_NAME_ORIGINAL_LANGUAGE = "original_language";
            public static final String COLUMN_NAME_TITLE = "title";
            public static final String COLUMN_NAME_BACKDROP_PATH = "backdrop_path";
            public static final String COLUMN_NAME_POPULARITY = "popularity";
            public static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
            public static final String COLUMN_NAME_VIDEO = "video";
            public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_average";
        }
}
