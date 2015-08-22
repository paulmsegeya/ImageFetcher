package swat_cat.com.imagefetcher.Controller;

import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    public ImageListAdapter(Context context, int itemLayoutId, ArrayList<Image> images, int screenHeight, int screenWidth){
        super(context, itemLayoutId, images);
        this.context = context;
        this.images = new ArrayList<>();
        this.images.addAll(images);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    static class ViewHolder{
        @Bind(R.id.image_name) TextView imageNameTextView;
        @Bind(R.id.favorite_button) ImageButton addToFavoriteButton;
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
        holder.addToFavoriteButton.setImageDrawable(context.getResources().getDrawable(image.isSaved() ? R.mipmap.star_y : R.mipmap.star_g));
        holder.addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image.isSaved()) {
                    holder.addToFavoriteButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.star_g));
                    //TODO delete image from db
                } else {
                    holder.addToFavoriteButton.setImageDrawable(context.getResources().getDrawable(R.mipmap.star_y));
                    //TODO add to db
                }
            }
        });
        //holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.swat_cat));
        Picasso.with(this.context)
                .load(image.getThumbUrl())
                .resize(setWidth(screenWidth,image),setHeight(screenHeight,image))
                .placeholder(R.drawable.swat_cat)
                .error(R.drawable.swat_cat)
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

    private int setWidth(int dispWidth, Image image){
        if(image.getThumbWidth()>(dispWidth*0.8)){
            return  (int)(dispWidth*0.8) ;
        }
        else return image.getThumbWidth();
    }

    private int setHeight(int dispHeight, Image image){
        if(image.getThumbHeight()>(dispHeight*0.6)){
            return  (int)(dispHeight*0.6) ;
        }
        else return image.getThumbHeight();
    }
}
