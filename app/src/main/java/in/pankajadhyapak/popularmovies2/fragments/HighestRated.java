package in.pankajadhyapak.popularmovies2.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.pankajadhyapak.popularmovies2.App;
import in.pankajadhyapak.popularmovies2.Constants;
import in.pankajadhyapak.popularmovies2.R;
import in.pankajadhyapak.popularmovies2.adapters.MovieAdapter;
import in.pankajadhyapak.popularmovies2.api.MovieApi;
import in.pankajadhyapak.popularmovies2.api.RetroFit;
import in.pankajadhyapak.popularmovies2.models.AllMovies;
import in.pankajadhyapak.popularmovies2.models.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HighestRated extends Fragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private GridLayoutManager layout;
    private static final String MOVIE_DB_KEY = "movieList";
    private static final String TAG = MostPopular.class.getSimpleName();
    private ArrayList<Movie> allMovies = new ArrayList<>();
    private RecyclerView.Adapter mMovieAdapter;

    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 4;
    private int pageCount = 1;

    public HighestRated() {
    }

    public static HighestRated newInstance() {
        HighestRated fragment = new HighestRated();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_highest_rated, container, false);
        ButterKnife.bind(this, view);

        layout = new GridLayoutManager(getActivity(), numberOfColumns());
        recyclerView.setLayoutManager(layout);
        mMovieAdapter = new MovieAdapter(getActivity(), allMovies);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getMovies();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layout.getItemCount();
                firstVisibleItem = layout.findFirstVisibleItemPosition();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                        pageCount++;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    if(App.hasNetwork()){
                        Snackbar.make(recyclerView, "Loading Page "+pageCount, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        getMovies();
                        loading = true;
                    }else {
                        App.showNetworkError(recyclerView);
                    }

                }
            }
        });
        recyclerView.setAdapter(mMovieAdapter);
        return view;
    }

    private void getMovies() {
        Retrofit retrofit = RetroFit.getInstance();
        MovieApi api = retrofit.create(MovieApi.class);
        Call<AllMovies> call = api.getTopRatedMovies(Constants.API_KEY, pageCount+"");

        call.enqueue(new Callback<AllMovies>() {
            @Override
            public void onResponse(Call<AllMovies> call, Response<AllMovies> response) {
                Log.e(TAG, "onResponse: " + response.body().getResults().size());
                allMovies.addAll(response.body().getResults());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMovieAdapter.notifyDataSetChanged();
                    }
                });
                Log.e(TAG, "all movies size: " + allMovies.size());
            }

            @Override
            public void onFailure(Call<AllMovies> call, Throwable t) {
                Log.e("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 600;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
