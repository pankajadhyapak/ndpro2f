package in.pankajadhyapak.popularmovies2.activites;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.pankajadhyapak.popularmovies2.R;
import in.pankajadhyapak.popularmovies2.adapters.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tabs)
    TabLayout mTabLayout;

    @Bind(R.id.appbar)
    AppBarLayout appbar;

    @Bind(R.id.container)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(new SectionsPagerAdapter(this, getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
