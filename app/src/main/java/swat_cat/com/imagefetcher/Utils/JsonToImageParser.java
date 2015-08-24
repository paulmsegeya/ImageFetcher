package swat_cat.com.imagefetcher.Utils;

import android.content.Context;
import android.util.Log;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.ArrayList;

import swat_cat.com.imagefetcher.models.Image;
import swat_cat.com.imagefetcher.models.ImagesManager;

/**
 * Created by Dell on 21.08.2015.
 */
public class JsonToImageParser {

    private static final String TAG = JsonToImageParser.class.getSimpleName();

    public static ArrayList<Image> parseJson(String json){
        ArrayList<Image> images = new ArrayList<>();
        if(json==null||json.isEmpty()){
            return images;
        }
        ReadContext cntx = JsonPath.parse(json);
        ArrayList<String> titles = cntx.read(Constants.JSON_PATH_BASE + "snippet");
        ArrayList<String> urls = cntx.read(Constants.JSON_PATH_BASE+"link");
        ArrayList<String> thumbUrls = cntx.read(Constants.JSON_PATH_BASE+"image."+"thumbnailLink");
        ArrayList<Integer> imageHeights = cntx.read(Constants.JSON_PATH_BASE+"image."+"height");
        ArrayList<Integer> imageWidths = cntx.read(Constants.JSON_PATH_BASE+"image."+"width");
        ArrayList<Integer> thumbHeights = cntx.read(Constants.JSON_PATH_BASE+"image."+"thumbnailHeight");
        ArrayList<Integer> thumbWidths = cntx.read(Constants.JSON_PATH_BASE+"image."+"thumbnailWidth");
        for (int i = 0; i<titles.size(); i++){
            images.add(new Image(
                    null,
                    titles.get(i),
                    urls.get(i),
                    thumbUrls.get(i),
                    imageHeights.get(i),
                    imageWidths.get(i),
                    thumbHeights.get(i),
                    thumbWidths.get(i),
                    null,
                    false));
        }
        if(!images.isEmpty()){
            Log.d(TAG, "Parsed data:");
            for(Image image: images){
                Log.d(TAG, image.toString());
            }
        }
        else {
            Log.e(TAG, "JSON string wasn't parsed");
        }
        /*JsonFactory factory = new JsonFactory();
        String title = null;
        String url = null;
        String thumbUrl = null;
        int imageHeight = 0;
        int imageWidth = 0;
        int thumbHeight = 0;
        int thumbWidth = 0;
        try {
            JsonParser parser = factory.createJsonParser(json);
            while (parser.nextToken()!= JsonToken.END_OBJECT){
                String token = parser.getCurrentName();
                if("items".equals(token)){
                    parser.nextToken();
                    Log.d(TAG, "Items array fonud");
                    while(parser.nextToken()!=JsonToken.END_ARRAY){
                        while(parser.nextToken()!=JsonToken.END_OBJECT){
                            String innerToken = parser.getCurrentName();
                            if("snippet".equals(innerToken)){
                                title = parser.getText();
                                Log.d(TAG,"snippet tag was found :" + title);
                            }
                            if("link".equals(innerToken)){
                                url = parser.getText();
                            }
                            if("thumbnailLink".equals(innerToken)){
                                thumbUrl=parser.getText();
                            }
                            if("height".equals(innerToken)){
                                imageHeight = parser.getIntValue();
                            }
                            if("width".equals(innerToken)){
                                imageWidth = parser.getIntValue();
                            }
                            if("thumbnailHeight".equals(innerToken)){
                                thumbHeight = parser.getIntValue();
                            }
                            if("thumbnailWidth".equals(innerToken)){
                                thumbWidth = parser.getIntValue();
                            }
                            images.add(new Image(title, url, thumbUrl, imageHeight, imageWidth, thumbHeight, thumbWidth));
                        }
                    }
                }
            }
            if (images.isEmpty()) {
                Log.e(TAG, "JSON string wasn't parsed");
            } else {
                Log.d(TAG, "Parsed data:");
                for(Image image: images){
                    Log.d(TAG, image.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error parsing json");
        }*/
        return images;
    }
}
