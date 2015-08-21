package swat_cat.com.imagefetcher.Utils;

import android.content.Context;

import java.util.ArrayList;

import swat_cat.com.imagefetcher.Model.Image;

/**
 * Created by Dell on 21.08.2015.
 */
public class NetImagesLoader extends DataLoader<ArrayList<Image>> {

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
        return JsonToImageParser.parseJson(retriever.GCSearch(query));
    }
}
