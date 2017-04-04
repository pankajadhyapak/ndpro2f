package in.pankajadhyapak.popularmovies2.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.pankajadhyapak.popularmovies2.fragments.Favourites;
import in.pankajadhyapak.popularmovies2.fragments.HighestRated;
import in.pankajadhyapak.popularmovies2.fragments.MostPopular;
import in.pankajadhyapak.popularmovies2.fragments.PlaceholderFragment;

/**
 * Created by pankaj on 04/04/17.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return MostPopular.newInstance();
            case 1:
                return HighestRated.newInstance();
            case 2:
                return Favourites.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Most Popular";
            case 1:
                return "Highest Rated";
            case 2:
                return "Favourites";
        }
        return null;
    }
}
