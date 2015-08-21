package swat_cat.com.imagefetcher.Utils;

import android.net.Uri;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Dell on 20.08.2015.
 */
public class OkHttpRetriever {

    private OkHttpClient client;

    public OkHttpRetriever() {
        client = new OkHttpClient();
    }

    private String doGetRequest(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()){
                Log.w(getClass().getSimpleName(), "Error for URL " + url);
                return null;
            }
            else {
                return response.body().string();
            }
        } catch (IOException e) {
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
            return null;
        }
    }

    //URL url = new URL("https://www.googleapis.com/customsearch/v1?key=" +GOOGLE_API_KEY+ "&amp;cx="
    // +ENGINE_API+ "&amp;q=" +qry+"&amp;fileType="+FILE_TYPE+"&amp;searchType="+SEARCH_TYPE+"&amp;alt=json");

   private String GCSearch(String query){
        String url = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendQueryParameter("key", Constants.GOOGLE_API_KEY)
                .appendQueryParameter("cx",Constants.ENGINE_API)
                .appendQueryParameter("q",query)
                .appendQueryParameter("fileType",Constants.FILE_TYPE)
                .appendQueryParameter("searchType", Constants.SEARCH_TYPE)
                .appendQueryParameter("alt","json").toString();

    }
}
