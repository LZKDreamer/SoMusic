package com.lzk.lib_image_loader;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/1/31
 * Function:图片加载工具类
 */
public class ImageLoadManager {
    private static ImageLoadManager sImageLoadManager;

    private ImageLoadManager(){}

    private static ImageLoadManager getInstance(){
        if (sImageLoadManager == null){
            sImageLoadManager = new ImageLoadManager();
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
        RoundedCorners roundedCorners = new RoundedCorners(corner);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(imageView.getContext())
                .load(url)
                .thumbnail(0.3f)
                .placeholder(R.drawable.bg_img_shape)
                .transition(withCrossFade())
                .apply(options)
                .into(imageView);
    }
}
