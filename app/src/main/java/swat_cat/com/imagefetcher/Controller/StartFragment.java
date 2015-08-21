package swat_cat.com.imagefetcher.Controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import swat_cat.com.imagefetcher.Model.Image;
import swat_cat.com.imagefetcher.Model.ImagesManager;
import swat_cat.com.imagefetcher.R;
import swat_cat.com.imagefetcher.Utils.OkHttpRetriever;

/**
 * Created by Dell on 18.08.2015.
 */
public class StartFragment extends ListFragment {
    public final static String TAG= StartFragment.class.getName();
    public final static String LIST_TYPE = TAG + "list_type";
    public final static String LAST_SEARCH_QUERY = TAG+"_last_search_query";

    private String list_title_str;
    private ArrayList<Image> images;

    @Bind(R.id.image_list_title) TextView list_title;
    @Bind(android.R.id.list) ListView listView;

    public static Fragment newInstance(String list_type){
        Bundle args = new Bundle();
        args.putString(LIST_TYPE, list_type);
        StartFragment fragment = new StartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        list_title_str = getArguments().getString(LIST_TYPE);
        if(list_title_str.equals(getResources().getString(R.string.searching))){
            images = ImagesManager.getInstance(getActivity()).getSearchedImages();
        }
        if(list_title_str.equals(getResources().getString(R.string.favorites))){
            images = ImagesManager.getInstance(getActivity()).getFaivoriteImages();
        }
        ImageListAdapter adapter = new ImageListAdapter(getActivity(),R.layout.image_list_item,images);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_list_fragment,container,false);
        ButterKnife.bind(this,view);
        list_title.setText(PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getString(LAST_SEARCH_QUERY,""));
        return view;
    }

}
