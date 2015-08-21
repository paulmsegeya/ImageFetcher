package swat_cat.com.imagefetcher.Utils;

import android.util.Log;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

import java.io.IOException;
import java.util.ArrayList;

import swat_cat.com.imagefetcher.Model.Image;

/**
 * Created by Dell on 21.08.2015.
 */
public class JsonToImageParser {

    private static final String TAG = JsonToImageParser.class.getSimpleName();
    public static ArrayList<Image> parseJson(String json){
        JsonFactory factory = new JsonFactory();
        ArrayList<Image> images = new ArrayList<>();
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
                    while(parser.nextToken()!=JsonToken.END_ARRAY){
                        while(parser.nextToken()!=JsonToken.END_OBJECT){
                            String innerToken = parser.getCurrentName();
                            if("snippet".equals(innerToken)){
                                title = parser.getText();
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
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error parsing json");
        }
        return images;
    }
}
