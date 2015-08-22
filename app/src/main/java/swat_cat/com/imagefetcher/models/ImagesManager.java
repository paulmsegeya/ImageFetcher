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
    private Context context;


    private ImagesManager(Context context) {
        this.context = context;
        searchedImages = new ArrayList<>();
        faivoriteImages = new ArrayList<>();
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

    public void changeToSearchList(){
        displayingImages = searchedImages;
    }

    public void changeToFavoritesList(){
        displayingImages = faivoriteImages;
    }

    public ArrayList<Image> getSearchedImages() {
        return searchedImages;
    }

    public void setSearchedImages(ArrayList<Image> searchedImages) {
        this.searchedImages = searchedImages;
    }

    public boolean hasSearchedImages(){
        return searchedImages!=null&&!searchedImages.isEmpty();
    }

    public ArrayList<Image> getFaivoriteImages() {
        return faivoriteImages;
    }

    public void setFaivoriteImages(ArrayList<Image> faivoriteImages) {
        this.faivoriteImages = faivoriteImages;
    }

    public boolean hasFavoriteImages(){
        return faivoriteImages!=null&&!faivoriteImages.isEmpty();
    }

    public ArrayList<Image> getDisplayingImages() {
        return displayingImages;
    }

    public void setDisplayingImages(ArrayList<Image> displayingImages) {
        this.displayingImages = displayingImages;
    }
}
