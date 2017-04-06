package in.pankajadhyapak.popularmovies2.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.pankajadhyapak.popularmovies2.App;
import in.pankajadhyapak.popularmovies2.Constants;
import in.pankajadhyapak.popularmovies2.R;
import in.pankajadhyapak.popularmovies2.adapters.ReviewAdapter;
import in.pankajadhyapak.popularmovies2.adapters.TrailerAdapter;
import in.pankajadhyapak.popularmovies2.api.MovieApi;
import in.pankajadhyapak.popularmovies2.api.RetroFit;
import in.pankajadhyapak.popularmovies2.data.MovieRepository;
import in.pankajadhyapak.popularmovies2.models.AllReviews;
import in.pankajadhyapak.popularmovies2.models.AllTrailers;
import in.pankajadhyapak.popularmovies2.models.Movie;
import in.pankajadhyapak.popularmovies2.models.Review;
import in.pankajadhyapak.popularmovies2.models.Trailer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static in.pankajadhyapak.popularmovies2.Constants.MOVIE_DETAIL;
import static in.pankajadhyapak.popularmovies2.Constants.TMDB_IMAGE_W500;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = "MovieDetails";


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.summary)
    TextView summary;

    @Bind(R.id.ratingValue)
    TextView ratingValue;

    @Bind(R.id.releaseValue)
    TextView releaseValue;

    @Bind(R.id.trailer_rv)
    RecyclerView trailerRv;

    @Bind(R.id.review_rv)
    RecyclerView reviewRv;

    private ArrayList<Trailer> allTrailer = new ArrayList<>();
    private ArrayList<Review> allReviews = new ArrayList<>();
    private RecyclerView.Adapter mTrailerAdapter;
    private RecyclerView.Adapter mReviewAdapter;
    private Movie movie;
    private MovieRepository mMovieRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(MOVIE_DETAIL);
        mMovieRepository = new MovieRepository(this);
        Log.e(TAG, "onCreate: " + movie.getTitle());

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(movie.getTitle());
            ab.setDisplayHomeAsUpEnabled(true);
        }

        setFabIcon(movie.getId());

        if (movie.getPosterPath() != null) {
            String posterUrl = TMDB_IMAGE_W500 + movie.getPosterPath();
            Picasso.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.empty_photo)
                    .error(R.drawable.empty_photo)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.empty_photo);
        }

        ratingValue.setText(String.format("Rating : %s", movie.getVoteAverage()));
        summary.setText(movie.getOverview());
        releaseValue.setText(String.format("Release : %s", movie.getReleaseDate()));

        trailerRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mTrailerAdapter = new TrailerAdapter(this, allTrailer);
        trailerRv.setAdapter(mTrailerAdapter);

        reviewRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mReviewAdapter = new ReviewAdapter(this, allReviews);
        reviewRv.setAdapter(mReviewAdapter);

        if (App.hasNetwork()) {
            getTrailers(movie.getId());
            getReviews(movie.getId());
        } else {
            App.showNetworkError(reviewRv);
        }

    }

    private void setFabIcon(Integer id) {
        if (mMovieRepository.getMovie(id).getId() != null) {
            Log.e(TAG, "not fav " + id);
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
        } else {
            Log.e(TAG, "fav " + id);
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
        }
    }

    private void getReviews(Integer id) {

        Retrofit retrofit = RetroFit.getInstance();
        MovieApi api = retrofit.create(MovieApi.class);
        Call<AllReviews> call = api.getReviews(id, Constants.API_KEY);
        call.enqueue(new Callback<AllReviews>() {
            @Override
            public void onResponse(Call<AllReviews> call, final Response<AllReviews> response) {
                Log.e(TAG, "onResponse: " + response.body().getResults().size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allReviews.clear();
                        allReviews.addAll(response.body().getResults());
                        mReviewAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<AllReviews> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void getTrailers(Integer id) {

        Retrofit retrofit = RetroFit.getInstance();
        MovieApi api = retrofit.create(MovieApi.class);
        Call<AllTrailers> call = api.getTrailers(id, Constants.API_KEY);
        call.enqueue(new Callback<AllTrailers>() {
            @Override
            public void onResponse(Call<AllTrailers> call, final Response<AllTrailers> response) {
                Log.e(TAG, "onResponse: " + response.body().getResults().size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allTrailer.clear();
                        allTrailer.addAll(response.body().getResults());
                        mTrailerAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(Call<AllTrailers> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @OnClick(R.id.fab)
    public void onViewClicked(View v) {
        if (mMovieRepository.getMovie(movie.getId()).getId() != null) {
            int deletedRows = mMovieRepository.deleteMovie(movie.getId());
            Log.e(TAG, "deleted movie: " + deletedRows);
            if (deletedRows > 0) {
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border));
                Snackbar.make(v, R.string.fav_removed_msg, Snackbar.LENGTH_SHORT).show();
            }
        } else {
            long newRowId = mMovieRepository.addMovie(movie);
            if (newRowId > 0) {
                Log.e(TAG, "onViewClicked: " + newRowId);
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite));
                Snackbar.make(v, R.string.fav_movie_added, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(v, R.string.movie_add_failed, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
