package swat_cat.com.imagefetcher.Model;

/**
 * Created by Dell on 18.08.2015.
 */
public class Image {
    private String title;
    private String url;
    private String thumbUrl;
    private int imageHeight;
    private int imageWidth;
    private int thumbHeight;
    private int thumbWidth;

    public Image(String title, String url, String thumbUrl, int imageHeight, int imageWidth, int thumbHeight, int thumbWidth) {
        this.title = title;
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.thumbHeight = thumbHeight;
        this.thumbWidth = thumbWidth;
    }

    @Override
    public String toString() {
        return "image title: "+title+'\n'
                +"image url: "+url+'\n'
                +"thumbnail url: "+thumbUrl;
    }
}
