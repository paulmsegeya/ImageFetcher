package swat_cat.com.imagefetcher.Utils;

import android.content.Context;

import java.util.ArrayList;

import swat_cat.com.imagefetcher.models.Image;

/**
 * Created by Dell on 23.08.2015.
 */
public class DbImagesLoader extends DataLoader<ArrayList<Image>> {

    private Context context;

    public DbImagesLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public ArrayList<Image> loadInBackground() {
        return new DataBaseUtils(context).getFavoriteImages();
    }
}
