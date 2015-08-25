package swat_cat.com.imagefetcher.Controller;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import swat_cat.com.imagefetcher.R;
import swat_cat.com.imagefetcher.models.ImagesManager;


public class StartActivity extends AppCompatActivity {
    private static final String TAG = StartActivity.class.getName();

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.pager) ViewPager viewPager;

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ArrayList<Fragment> fragments = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        ButterKnife.bind(this);
        //getSupportLoaderManager().initLoader(0,null, new NetImagesLoaderCallbacks());
        setSupportActionBar(toolbar);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.searching));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.favorites));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragments = new ArrayList<>();
        fragments.add(new SearchListFragment());
        fragments.add(new FavoritesListFragment());
        final MyPagerAdapter adapter = new MyPagerAdapter(fm);

        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class MyPagerAdapter extends SmartFragmentStatePagerAdapter{

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return new SearchListFragment();
                    case 1:
                        return new FavoritesListFragment();
                    default:return null;
                }
                //return fragments.get(position);
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            Fragment currentItem = getItem(position);
            if (currentItem != null) {
                currentItem.setMenuVisibility(true);
                currentItem.setUserVisibleHint(true);
            }
        }
    }
}
