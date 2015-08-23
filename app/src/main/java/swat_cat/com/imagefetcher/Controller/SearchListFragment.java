package swat_cat.com.imagefetcher.Controller;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import swat_cat.com.imagefetcher.models.Image;
import swat_cat.com.imagefetcher.models.ImagesManager;
import swat_cat.com.imagefetcher.R;
import swat_cat.com.imagefetcher.Utils.NetImagesLoader;

/**
 * Created by Dell on 18.08.2015.
 */
public class SearchListFragment extends ListFragment{
    public final static String TAG= SearchListFragment.class.getName();
    public final static String LIST_TYPE = TAG + "list_type";
    public final static String LAST_SEARCH_QUERY = TAG+"_last_search_query";
    public final static String QUERY = TAG+"_query";
    public final static int NET_SEARCH_LOADER_ID = 42;

    private ArrayList<Image> images = null;
    private ImageListAdapter adapter = null;
    private int dispHeight;
    private int dispWidth;
    private boolean loadingMore = false;

    @Bind(R.id.image_list_title) TextView list_title;
    @Bind(android.R.id.list) ListView listView;
    private View footer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        images = ImagesManager.getInstance(getActivity()).getSearchedImages();
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
        View footer = ((LayoutInflater)getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.footer, null, false);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if (!images.isEmpty()) {
                    Bundle args =new Bundle();
                    args.putString(QUERY,PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(LAST_SEARCH_QUERY,"cat"));
                    if((lastInScreen == totalItemCount) && !(loadingMore)){
                        if (getLoaderManager().getLoader(NET_SEARCH_LOADER_ID) != null) {
                            getLoaderManager().restartLoader(NET_SEARCH_LOADER_ID, args, new NetImagesLoaderCallbacks());
                        } else
                            getLoaderManager().initLoader(NET_SEARCH_LOADER_ID, args, new NetImagesLoaderCallbacks());
                    }
                }
            }
        });
        listView.addFooterView(footer);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list_title.setText(getString(R.string.result_for) + PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(LAST_SEARCH_QUERY, ""));
        images = ImagesManager.getInstance(getActivity()).getSearchedImages();
        adapter = new ImageListAdapter(getActivity(),R.layout.image_list_item,images,dispHeight,dispWidth);
        listView.setAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Image image = adapter.getItem(position);
        Intent intent = new Intent(getActivity(),ImageActivity.class);
        intent.putExtra(ImageFragment.IMAGE_PATH, image.getIsSaved()?"file://"+image.getUri():image.getUrl());
        intent.putExtra(ImageFragment.DOWNLOAD_TYPE,getString(R.string.searching));
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager sm =  (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchViewAction = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchViewAction.setSearchableInfo(sm.getSearchableInfo(getActivity().getComponentName()));
        searchViewAction.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle args = new Bundle();
                args.putString(QUERY, query);
                ImagesManager.httpStartIndex="1";
                ImagesManager.getInstance(getActivity()).clearSearchList();
                if (getLoaderManager().getLoader(NET_SEARCH_LOADER_ID) != null) {
                    getLoaderManager().restartLoader(NET_SEARCH_LOADER_ID, args, new NetImagesLoaderCallbacks());
                } else
                    getLoaderManager().initLoader(NET_SEARCH_LOADER_ID, args, new NetImagesLoaderCallbacks());
                list_title.setText(getString(R.string.result_for) + query);
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(LAST_SEARCH_QUERY, query).commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    private class NetImagesLoaderCallbacks implements android.support.v4.app.LoaderManager.LoaderCallbacks<ArrayList<Image>>{
        @Override
        public Loader<ArrayList<Image>> onCreateLoader(int id, Bundle args) {
            loadingMore=true;
            return  new NetImagesLoader(getActivity(),args.getString(QUERY));
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Image>> loader, ArrayList<Image> data) {
            Integer index = Integer.parseInt(ImagesManager.httpStartIndex);
            for(Image image:data){
                index++;
                Log.d(TAG, image.toString() + '\n' + "Index: " + index.toString());
            }
            ImagesManager.httpStartIndex =index.toString();
            Log.d(TAG,ImagesManager.httpStartIndex);
            ImagesManager.getInstance(getActivity()).addToSearchList(data);
            images.addAll(data);
            adapter = new ImageListAdapter(getActivity(),R.layout.image_list_item,images,dispHeight,dispWidth);
            listView.setAdapter(adapter);
            loadingMore=false;
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Image>> loader) {

        }
    }
}

