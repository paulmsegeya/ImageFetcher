package swat_cat.com.imagefetcher.Model;

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
}
