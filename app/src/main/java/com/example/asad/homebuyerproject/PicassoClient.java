package com.example.asad.homebuyerproject;

/**
 * Created by hassan on 08/01/2017.
 */import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

/**
 * Created by Oclemmy on 4/12/2016 for ProgrammingWizards Channel.
 */
public class PicassoClient {
    public static void downloadImage(Context c, String url, ImageView img, int d,Float fl)
    {
        if(url != null && url.length()>0)
        {

            Glide.with(c)
                    .load(url)
                    .diskCacheStrategy( DiskCacheStrategy.NONE )
                    .skipMemoryCache( true )
                    .thumbnail(fl )
                    .placeholder(d) // resizes the image to these dimensions (in pixel)
                    .fitCenter()
                    .into(img);

        }else {

            Glide.with(c)
                    .load(url)
                    .diskCacheStrategy( DiskCacheStrategy.NONE )
                    .skipMemoryCache( true )
                    .thumbnail( fl )
                    .placeholder(d) // resizes the image to these dimensions (in pixel)
                    .fitCenter()
                    .into(img);

        }
    }
}