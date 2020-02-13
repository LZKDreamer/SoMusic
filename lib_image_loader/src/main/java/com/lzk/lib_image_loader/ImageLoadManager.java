package com.lzk.lib_image_loader;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;


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

    /**
     * 为Notification加载图片
     * @param context
     * @param url
     * @param id
     * @param rv
     * @param notification
     * @param NOTIFICATION_ID
     */
    public void loadImgForNotification(Context context,String url, int id, RemoteViews rv,
                                       Notification notification, int NOTIFICATION_ID){
        Target target = initNotificationTarget(context,id,rv,notification,NOTIFICATION_ID);
        Glide.with(context)
                .asBitmap()
                .load(url)
                .thumbnail(0.7f)
                .fitCenter()
                .apply(getOptions())
                .into(target);
    }

    /**
     * 加载圆形图片
     * @param context
     * @param imageView
     * @param url
     */
    public void loadRoundedImg(final ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .asBitmap()
                .load(url)
                .centerCrop()
                .apply(getOptions())
                .into(new BitmapImageViewTarget(imageView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(),resource);
                        bitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(bitmapDrawable);
                    }
                });
    }

    private NotificationTarget initNotificationTarget(Context context, int id, RemoteViews rv,
                                                      Notification notification, int NOTIFICATION_ID) {
        NotificationTarget notificationTarget =
                new NotificationTarget(context, id, rv, notification, NOTIFICATION_ID);
        return notificationTarget;
    }

    private RequestOptions getOptions(){
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.bg_img_shape)
                .error(R.drawable.bg_img_shape)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                ;
        return options;
    }
}
