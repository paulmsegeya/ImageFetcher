package swat_cat.com.imagefetcher.models;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Dell on 21.08.2015.
 */
public class ImagesManager {
    private static ImagesManager manager = null;
    private static final Object mutex = new Object();

    private ArrayList<swat_cat.com.imagefetcher.Model.Image> searchedImages;
    private ArrayList<swat_cat.com.imagefetcher.Model.Image> faivoriteImages;
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

    public ArrayList<swat_cat.com.imagefetcher.Model.Image> getSearchedImages() {
        return searchedImages;
    }

    public void setSearchedImages(ArrayList<swat_cat.com.imagefetcher.Model.Image> searchedImages) {
        this.searchedImages = searchedImages;
    }

    public boolean hasSearchedImages(){
        return searchedImages!=null&&!searchedImages.isEmpty();
    }

    public ArrayList<swat_cat.com.imagefetcher.Model.Image> getFaivoriteImages() {
        return faivoriteImages;
    }

    public void setFaivoriteImages(ArrayList<swat_cat.com.imagefetcher.Model.Image> faivoriteImages) {
        this.faivoriteImages = faivoriteImages;
    }

    public boolean hasFavoriteImages(){
        return faivoriteImages!=null&&!faivoriteImages.isEmpty();
    }
}
