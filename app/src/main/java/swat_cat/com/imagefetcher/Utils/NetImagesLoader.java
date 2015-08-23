package swat_cat.com.imagefetcher.Utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import swat_cat.com.imagefetcher.models.Image;

/**
 * Created by Dell on 21.08.2015.
 */
public class NetImagesLoader extends DataLoader<ArrayList<Image>> {

    private static final String TAG = NetImagesLoader.class.getSimpleName();

    private Context context;
    private OkHttpRetriever retriever;
    private String query;

    public NetImagesLoader(Context context, String query) {
        super(context);
        this.query = query;
        retriever = new OkHttpRetriever();
    }

    @Override
    public ArrayList<Image> loadInBackground() {
        Log.d(TAG, "Loader initiated");
        return JsonToImageParser.parseJson(retriever.GCSearch(query));
    }
}
