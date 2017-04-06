package in.pankajadhyapak.popularmovies2.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.pankajadhyapak.popularmovies2.R;
import in.pankajadhyapak.popularmovies2.adapters.MovieAdapter;
import in.pankajadhyapak.popularmovies2.data.MovieRepository;
import in.pankajadhyapak.popularmovies2.models.Movie;

public class Favourites extends Fragment {

    private static final String TAG = MostPopular.class.getSimpleName();

    private GridLayoutManager layout;
    private ArrayList<Movie> allMovies = new ArrayList<>();
    private RecyclerView.Adapter mMovieAdapter;
    private MovieRepository mMovieRepository;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    public Favourites() {
    }

    public static Favourites newInstance() {
        Favourites fragment = new Favourites();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        ButterKnife.bind(this, view);
        mMovieRepository = new MovieRepository(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        allMovies.clear();
        allMovies = mMovieRepository.getAllMovies();
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns()));
        mMovieAdapter = new MovieAdapter(getActivity(), allMovies);
        recyclerView.setAdapter(mMovieAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
