package swat_cat.com.imagefetcher.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import swat_cat.com.imagefetcher.R;

/**
 * Created by Dell on 22.08.2015.
 */
public class ImageFragment extends Fragment {
    private static final String TAG = ImageFragment.class.getSimpleName();
    protected static final String IMAGE_PATH = TAG+"_image_path";
    protected static final String DOWNLOAD_TYPE = TAG+"download_type";
    private String path;

    @Bind(R.id.big_image_view)ImageView imageView;

    public static Fragment newInstance(String downloadType, String path){
        Bundle args = new Bundle();
        args.putString(IMAGE_PATH, path);
        args.putString(DOWNLOAD_TYPE,downloadType);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString(IMAGE_PATH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_fragment,container,false);
        ButterKnife.bind(this,view);
        Picasso.with(getActivity())
                .load(path)
                .placeholder(R.drawable.swat_cat)
                .error(R.drawable.no_data)
                .into(imageView);
        return view;
    }
}
