package swat_cat.com.imagefetcher.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import swat_cat.com.imagefetcher.models.Image;

/**
 * Created by Dell on 22.08.2015.
 */
public class FileUtils {
    private OkHttpRetriever retriever;
    public FileUtils() {
        retriever = new OkHttpRetriever();
    }

    public String saveImage(String name, String url){
        File imagesFolder = null;
        File imageFile= null;
        FileOutputStream fout = null;
        try {
            Bitmap bitmap = retriever.retrieveBitmap(url);
            imagesFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"/ImageFetcher");
            if (!imagesFolder.exists())
            {
                imagesFolder.mkdirs();
            }
            imageFile = new File(imagesFolder,name+".jpg");
            fout = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            fout.flush();
            fout.close();
            Log.d(FileUtils.class.getSimpleName(), "File saved");
            return imageFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(FileUtils.class.getSimpleName(), "Error saving image");
            return null;
        }
    }

    public boolean removeImageFile(Uri uri){
        File file = new File(uri.getPath());
        if(file.exists()){
            return file.delete();
        }
        return true;
    }
}
