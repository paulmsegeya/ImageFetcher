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
public class AsyncRemoveFromFavorites extends AsyncTask<Image, Void, Void > {
    private Context context;
    private Image image;

    public AsyncRemoveFromFavorites(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected Void doInBackground(Image... params) {
        image = params[0];
        new FileUtils().removeImageFile(Uri.parse(image.getUri()));
        new DataBaseUtils(context).removeFromFavorites(image);
        image.setIsSaved(false);
        ImagesManager.getInstance(context).deleteFromFavorites(image);
        Log.d(AsyncAddThumbnailToFavorites.class.getSimpleName(), "Image moved from favorites");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Image - " + image.getTitle() + " removed from favorites", Toast.LENGTH_SHORT).show();
    }
}
