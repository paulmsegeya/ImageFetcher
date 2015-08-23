package swat_cat.com.imagefetcher.Utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import swat_cat.com.imagefetcher.models.Image;
import swat_cat.com.imagefetcher.models.ImagesManager;

/**
 * Created by Dell on 22.08.2015.
 */
public class AsyncAddThumbnailToFavorites extends AsyncTask<Image, Void, Void >{
    private Context context;
    private Image image;

    public AsyncAddThumbnailToFavorites(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected Void doInBackground(Image... params) {
        image = params[0];
        Log.d(AsyncAddThumbnailToFavorites.class.getSimpleName(), image.getUrl());
        String uri = new FileUtils().saveImage(image.getTitle(), image.getUrl());
        image.setUri(uri);
        image.setIsSaved(true);
        long id = new DataBaseUtils(context).addToFavorites(image);
        image.setId(id);
        ImagesManager.getInstance(context).addToFavorite(image);
        Log.d(AsyncAddThumbnailToFavorites.class.getSimpleName(),"Image added to favorites");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context,"Image - "+image.getTitle()+" added to favorites",Toast.LENGTH_SHORT).show();
    }
}
