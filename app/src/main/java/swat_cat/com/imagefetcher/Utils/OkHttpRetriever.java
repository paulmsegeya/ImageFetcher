package swat_cat.com.imagefetcher.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.Header;

import java.io.IOException;
import java.io.InputStream;

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
                Log.w(getClass().getSimpleName(), "Error for URL " + url+'\n'+response.body().string());
                return null;
            }
            else {
                String resullt = response.body().string();
                Log.d(getClass().getSimpleName(), "Requested data: "+resullt);
                return resullt;
            }
        } catch (IOException e) {
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
            return null;
        }
    }

    public InputStream retriveStream(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()){
                Log.w(getClass().getSimpleName(), "Error for URL " + url+'\n'+response.body().string());
                return null;
            }
            else {
                InputStream resullt = response.body().byteStream();
                Log.d(getClass().getSimpleName(), "Stream requested");
                return resullt;
            }
        } catch (IOException e) {
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
            return null;
        }
    }

    public Bitmap retrieveBitmap(String url) throws Exception {

        InputStream inputStream = null;
        try {
            inputStream = retriveStream(url);
            final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        finally {
            closeStreamQuietly(inputStream);
        }

    }

    //URL url = new URL("https://www.googleapis.com/customsearch/v1?key=" +GOOGLE_API_KEY+ "&amp;cx="
    // +ENGINE_API+ "&amp;q=" +qry+"&amp;fileType="+FILE_TYPE+"&amp;searchType="+SEARCH_TYPE+"&amp;alt=json");

   public String GCSearch(String query){
        String url = Uri.parse(Constants.BASE_URL).buildUpon()
                .appendQueryParameter("q",query)
                .appendQueryParameter("key", Constants.GOOGLE_API_KEY)
                .appendQueryParameter("cx",Constants.ENGINE_API)
                .appendQueryParameter("fileType",Constants.FILE_TYPE)
                .appendQueryParameter("searchType", Constants.SEARCH_TYPE)
                .appendQueryParameter("imgSize",Constants.IMAGE_SIZE)
                /*.appendQueryParameter("alt","json")*/.toString();
       Log.d(OkHttpRetriever.class.getSimpleName(), url);
        return doGetRequest(url);
    }

    private void closeStreamQuietly(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            // ignore exception
        }
    }
}
