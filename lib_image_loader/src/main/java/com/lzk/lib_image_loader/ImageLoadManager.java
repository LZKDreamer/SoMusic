package com.lzk.lib_image_loader;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/1/31
 * Function:图片加载工具类
 */
public class ImageLoadManager {
    private static ImageLoadManager sImageLoadManager;

    private ImageLoadManager(){}

    private static DrawableCrossFadeFactory drawableCrossFadeFactory ;


    public static ImageLoadManager getInstance(){
        if (sImageLoadManager == null){
            sImageLoadManager = new ImageLoadManager();
            drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();
        }
        return sImageLoadManager;
    }

    /**
     * 加载圆角矩形图片
     * @param imageView
     * @param url
     * @param corner
     */
    public void loadRoundedRectImg(ImageView imageView, String url,int corner){
        Glide.with(imageView.getContext())
                .load(url)
                .thumbnail(0.3f)
                .apply(getOptions())
                .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                .transform(new RoundedCorners(corner))
                .into(imageView);
    }

    private RequestOptions getOptions(){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.bg_img_shape)
                .error(R.drawable.bg_img_shape)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop();
        return options;
    }
}
