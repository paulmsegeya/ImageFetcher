package swat_cat.com.imagefetcher.Controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Dell on 22.08.2015.
 */
public class ImageActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        String path = getIntent().getStringExtra(ImageFragment.IMAGE_PATH);
        String downloadType = getIntent().getStringExtra(ImageFragment.DOWNLOAD_TYPE);
        return ImageFragment.newInstance(downloadType,path);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }


}
