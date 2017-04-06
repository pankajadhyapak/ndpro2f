package in.pankajadhyapak.popularmovies2.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.pankajadhyapak.popularmovies2.R;
import in.pankajadhyapak.popularmovies2.fragments.Favourites;
import in.pankajadhyapak.popularmovies2.fragments.HighestRated;
import in.pankajadhyapak.popularmovies2.fragments.MostPopular;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
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
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.most_popular_title);
            case 1:
                return mContext.getString(R.string.highest_rated_title);
            case 2:
                return mContext.getString(R.string.favourites_title);
        }
        return null;
    }
}
