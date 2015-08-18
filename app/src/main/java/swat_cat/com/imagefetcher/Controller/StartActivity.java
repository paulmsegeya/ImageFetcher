package swat_cat.com.imagefetcher.Controller;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import swat_cat.com.imagefetcher.R;

public class StartActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new StartFragment();
    }
}
