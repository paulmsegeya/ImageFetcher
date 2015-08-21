package swat_cat.com.imagefetcher.Controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
        String result = null;
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
                return new OkHttpRetriever().GCSearch("squirel");
            }

            @Override
            protected void onPostExecute(String s) {
                //Log.d(TAG, s);
                ArrayList<Image> images = JsonToImageParser.parseJson(s);
                for(Image image : images){
                    Log.d(TAG, image.toString());
                }
            }
        }.execute();
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
    }
}
