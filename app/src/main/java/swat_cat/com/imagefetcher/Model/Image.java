package swat_cat.com.imagefetcher.Model;

import android.net.Uri;

/**
 * Created by Dell on 18.08.2015.
 */
public class Image {
    private int id;
    private String title;
    private String url;
    private String thumbUrl;
    private Integer imageHeight;
    private Integer imageWidth;
    private Integer thumbHeight;
    private Integer thumbWidth;
    private Uri uri;
    private boolean isSaved;

    public Image(String title, String url, String thumbUrl, Integer imageHeight, Integer imageWidth, Integer thumbHeight, Integer thumbWidth) {
        this.title = title;
        this.url = url;
        this.thumbUrl = thumbUrl;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.thumbHeight = thumbHeight;
        this.thumbWidth = thumbWidth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getThumbHeight() {
        return thumbHeight;
    }

    public void setThumbHeight(Integer thumbHeight) {
        this.thumbHeight = thumbHeight;
    }

    public Integer getThumbWidth() {
        return thumbWidth;
    }

    public void setThumbWidth(Integer thumbWidth) {
        this.thumbWidth = thumbWidth;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "image title: "+title+'\n'
                +"image url: "+url+'\n'
                +"thumbnail url: "+thumbUrl;
    }
}
