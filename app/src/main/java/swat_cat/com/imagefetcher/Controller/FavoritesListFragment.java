package swat_cat.com.imagefetcher.Controller;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import swat_cat.com.imagefetcher.R;
import swat_cat.com.imagefetcher.Utils.DbImagesLoader;
import swat_cat.com.imagefetcher.Utils.NetImagesLoader;
import swat_cat.com.imagefetcher.models.Image;
import swat_cat.com.imagefetcher.models.ImagesManager;

/**
 * Created by Dell on 22.08.2015.
 */
public class FavoritesListFragment extends ListFragment implements Updateble {
    public final static String TAG= FavoritesListFragment.class.getName();

    private ArrayList<Image> images = null;
    private ImageListAdapter adapter = null;
    private int dispHeight;
    private int dispWidth;

    @Bind(R.id.image_list_title)
    TextView list_title;
    @Bind(android.R.id.list)
    ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        images = ImagesManager.getInstance(getActivity()).getFaivoriteImages();
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        dispWidth = (int)(config.screenWidthDp * dm.density);
        dispHeight = dispWidth * dm.heightPixels / dm.widthPixels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_list_fragment,container,false);
        ButterKnife.bind(this, view);
        list_title.setText(getString(R.string.favorites));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        images = ImagesManager.getInstance(getActivity()).getFaivoriteImages();
        adapter = new ImageListAdapter(getActivity(),R.layout.image_list_item,images,dispHeight,dispWidth);
        listView.setAdapter(adapter);
    }

    public class DbImagesLoaderCallbacks implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Image>> {

        @Override
        public Loader<ArrayList<Image>> onCreateLoader(int id, Bundle args) {
            return  new DbImagesLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Image>> loader, ArrayList<Image> data) {
            for (Image image : data) {
                Log.d(DbImagesLoaderCallbacks.class.getSimpleName(), image.toString() + '\n');
            }
            ImagesManager.getInstance(getActivity()).setFaivoriteImages(data);
            images=ImagesManager.getInstance(getActivity()).getFaivoriteImages();
            adapter = new ImageListAdapter(getActivity(),R.layout.image_list_item,images,dispHeight,dispWidth);
            listView.setAdapter(adapter);
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Image>> loader) {
        }
    }

    @Override
    public void update() {
        images = ImagesManager.getInstance(getActivity()).getFaivoriteImages();
        adapter = new ImageListAdapter(getActivity(),R.layout.image_list_item,images,dispHeight,dispWidth);
        listView.setAdapter(adapter);
    }
}
