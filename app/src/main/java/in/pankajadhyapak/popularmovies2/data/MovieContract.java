package in.pankajadhyapak.popularmovies2.data;

import android.provider.BaseColumns;

/**
 * Created by pankaj on 05/04/17.
 */

public final class MovieContract {

        private MovieContract() {}

        public static class MovieEntry implements BaseColumns {
            public static final String TABLE_NAME = "favs";
            public static final String COLUMN_NAME_TITLE = "title";
            public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        }
}
