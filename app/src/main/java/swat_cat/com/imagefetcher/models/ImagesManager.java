package swat_cat.com.imagefetcher.models;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Dell on 21.08.2015.
 */
public class ImagesManager {
    private static ImagesManager manager = null;
    private static final Object mutex = new Object();

    private ArrayList<Image> searchedImages;
    private ArrayList<Image> faivoriteImages;
    private ArrayList<Image> displayingImages;
    public static String httpStartIndex = "1";
    public static int dbQueryLimit = 0;
    private Context context;


    private ImagesManager(Context context) {
        this.context = context;
        searchedImages = new ArrayList<>();
        faivoriteImages = new ArrayList<>();
        displayingImages = new ArrayList<>();
    }

    public static ImagesManager getInstance(Context context) {
        if(manager==null){
            synchronized (mutex){
                if (manager==null){
                    manager = new ImagesManager(context);
                }
            }
        }
        return manager;
    }

    public void deleteFromFavorites(Image image){
        for(int i =0 ; i<faivoriteImages.size(); i++){
            if(faivoriteImages.get(i).getUrl().equals(image.getUrl())){
                faivoriteImages.remove(i);
            }
        }
    }

    public void addToSearchList(ArrayList<Image> images){
        faivoriteImages.addAll(images);
    }

    public void clearSearchList(){
        faivoriteImages.clear();
    }

    public ArrayList<Image> getSearchedImages() {
        return searchedImages;
    }

    public ArrayList<Image> getFaivoriteImages() {
        return faivoriteImages;
    }

    public void setFaivoriteImages(ArrayList<Image> faivoriteImages) {
        this.faivoriteImages = faivoriteImages;
    }

    public ArrayList<Image> formDisplayingImages(){
        ArrayList<Image> images = new ArrayList<>();
        try {
            for(int i = dbQueryLimit; i<dbQueryLimit+10; i++){
                images.add(faivoriteImages.get(i));
            }
            displayingImages.addAll(images);
        } catch (IndexOutOfBoundsException e) {
            //images.clear();
            displayingImages.addAll(images);
            return images;
        }
        //dbQueryLimit+=10;
        return images;
    }

    public ArrayList<Image> getDisplayingImages() {
        return displayingImages;
    }

    public void addToFavorite(Image image){
        faivoriteImages.add(image);
    }

    public void resizeLimit(){
        dbQueryLimit+=10;
        if(dbQueryLimit>faivoriteImages.size()){
            dbQueryLimit =faivoriteImages.size();
        }
    }

}
