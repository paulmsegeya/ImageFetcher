package swat_cat.com.imagefetcher.Controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import swat_cat.com.imagefetcher.Model.Image;
import swat_cat.com.imagefetcher.R;
import swat_cat.com.imagefetcher.Utils.JsonToImageParser;
import swat_cat.com.imagefetcher.Utils.NetImagesLoader;
import swat_cat.com.imagefetcher.Utils.OkHttpRetriever;


public class StartActivity extends AppCompatActivity {
    private static final String TAG = StartActivity.class.getName();

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.pager) ViewPager viewPager;

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
        FragmentManager fm = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return StartFragment.newInstance(getString(R.string.searching));
                    case 1:
                        return StartFragment.newInstance(getString(R.string.favorites));
                    default: return null;
                }
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private class NetImagesLoaderCallbacks implements LoaderManager.LoaderCallbacks<ArrayList<Image>>{
        @Override
        public Loader<ArrayList<Image>> onCreateLoader(int id, Bundle args) {
            return  new NetImagesLoader(getApplicationContext(),"squirrel");
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Image>> loader, ArrayList<Image> data) {
            for(Image image:data){
                Log.d(TAG,image.toString()+'\n');
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Image>> loader) {

        }
    }
}
