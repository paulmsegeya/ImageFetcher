package swat_cat.com.imagefetcher.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import swat_cat.com.imagefetcher.Utils.AsyncAddThumbnailToFavorites;
import swat_cat.com.imagefetcher.Utils.AsyncRemoveFromFavorites;
import swat_cat.com.imagefetcher.models.Image;
import swat_cat.com.imagefetcher.R;

/**
 * Created by Dell on 21.08.2015.
 */
public class ImageListAdapter extends ArrayAdapter<Image> {

    private ArrayList<Image> images;
    private Context context;
    private int screenHeight;
    private int screenWidth;
    private int diagonal;
    public ImageListAdapter(Context context, int itemLayoutId, ArrayList<Image> images, int screenHeight, int screenWidth){
        super(context, itemLayoutId, images);
        this.context = context;
        this.images = new ArrayList<>();
        this.images.addAll(images);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        diagonal = hepotenuse(screenWidth,screenHeight);
    }

    static class ViewHolder{
        @Bind(R.id.image_name) TextView imageNameTextView;
        @Bind(R.id.favorite_button) ImageView addToFavoriteButton;
        @Bind(R.id.image_view) ImageView imageView;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        final Image image = images.get(position);
        holder.imageNameTextView.setText(image.getTitle());
        holder.addToFavoriteButton.setImageDrawable(context.getResources().getDrawable(image.getIsSaved() ? R.mipmap.star_y : R.mipmap.star_g));
        holder.addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image.getIsSaved()) {
                    holder.addToFavoriteButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.star_g));
                    new AsyncRemoveFromFavorites(context).execute(image);
                    YoYo.with(Techniques.Pulse).duration(700).playOn(holder.addToFavoriteButton);
                } else {
                    holder.addToFavoriteButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.star_y));
                    new AsyncAddThumbnailToFavorites(context).execute(image);
                    YoYo.with(Techniques.Pulse).duration(700).playOn(holder.addToFavoriteButton);
                }
            }
        });
        holder.addToFavoriteButton.setFocusable(false);
        //holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.swat_cat));
        String path = null;
        int srcHeight;
        int srcWidth;
        if(image.getIsSaved()){
            path = "file://"+image.getUri();
            srcHeight = setHeight(image.getImageHeight(),image.getImageWidth());
            srcWidth = setWidth(image.getImageHeight(),image.getImageWidth());
        }
        else {
            path = image.getUrl();
            srcHeight = setHeight(image.getImageHeight(),image.getImageWidth());
            srcWidth = setWidth(image.getImageHeight(),image.getImageWidth());
        }
        Picasso.with(this.context)
                .load(path)
                .resize(srcWidth,srcHeight)
                .placeholder(R.drawable.swat_cat)
                .error(R.drawable.no_data)
                .into(holder.imageView);
        return convertView;
    }

    public void add(Image image){
        this.images.add(image);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    private int setWidth(int srcHeight, int srcWidth){
        if(diagonal*0.8>hepotenuse(srcWidth,srcHeight)){
            return srcWidth;
        }
        else {
            return (int)(screenWidth*0.8);
        }
    }

    private int setHeight(int srcHeight, int srcWidth){
        if(diagonal*0.6>hepotenuse(srcWidth, srcHeight)){
            return srcHeight;
        }
        else {
            return (int)(screenHeight*0.6);
        }
    }

    private int hepotenuse(int width, int height){
        return (int)Math.sqrt((Math.pow(width,2)+Math.pow(height,2)));
    }
}
