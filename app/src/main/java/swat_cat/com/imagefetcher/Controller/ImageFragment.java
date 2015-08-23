package swat_cat.com.imagefetcher.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Dell on 22.08.2015.
 */
public class ImageFragment extends Fragment {
    private static final String TAG = ImageFragment.class.getSimpleName();
    private static final String DOWNLOAD_TYPE = TAG+"_download_type";
    private static final String IMAGE_PATH = TAG+"_image_path";

    public static Fragment newInstance(String downloadType, String path){
        Bundle args = new Bundle();
        args.putString(DOWNLOAD_TYPE,downloadType);
        args.putString(IMAGE_PATH, path);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
