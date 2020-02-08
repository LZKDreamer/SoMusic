package com.lzk.lib_audio.audioplayer.core;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.print.PrinterId;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.lzk.lib_audio.R;
import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.event.AudioErrorEvent;
import com.lzk.lib_audio.audioplayer.event.AudioLoadEvent;
import com.lzk.lib_audio.audioplayer.event.AudioPauseEvent;
import com.lzk.lib_audio.audioplayer.event.AudioStartEvent;
import com.lzk.lib_audio.audioplayer.event.EventBusHelper;
import com.lzk.lib_audio.audioplayer.model.AudioBean;
import com.lzk.lib_image_loader.ImageLoadManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/4
 * Function:音乐通知帮助类
 */
public class MusicNotificationHelper {
    private static MusicNotificationHelper sHelper;

    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private AudioBean mAudioBean;
    private String mPackageName;
    private MusicNotificationHelperListener mListener;
    private Context mContext;
    public static final int NOTIFICATION_ID = 0x001;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String CHANNEL_NAME = "CHANNEL_NAME";
    private RemoteViews mBigView;
    private RemoteViews mSmallView;

    private MusicNotificationHelper(){}

    public static MusicNotificationHelper getInstance(){
        if (sHelper == null){
            sHelper = new MusicNotificationHelper();
        }
        return sHelper;
    }

    /**
     * 初始化Notification
     */
    public void init(MusicNotificationHelperListener listener){
        mContext = AudioPlayerManager.getContext();
        mPackageName = AudioPlayerManager.getContext().getPackageName();
        mNotificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //获取播放列表中的歌曲对象
        mAudioBean = AudioPlayerManager.getAudioBean();
        initNotification();
        mListener = listener;
        if (mListener != null){
            //初始化之后启动前台服务
            mListener.onNotificationInit();
        }
        showLoadView(mAudioBean);
    }

    private void initNotification(){
        if (mNotification == null){
            EventBusHelper.getInstance().register(this);
            initRemoteViews();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableLights(false);
                channel.enableVibration(false);
                //以下两行代码设置通知无声音
                channel.setSound(null,null);
                channel.setImportance(NotificationManager.IMPORTANCE_LOW);
                mNotificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext,CHANNEL_ID)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setCustomBigContentView(mBigView) //大布局
                    .setContent(mSmallView)
                    .setSound(null)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    ;
            //点击通知启动MainActivity
            Class clazz = AudioPlayerManager.getNotificationTargetActivity();
            if (clazz != null){
                Intent intent = new Intent(mContext,clazz);
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
            }
            mNotification = builder.build();
        }
    }

    private void initRemoteViews(){
        int bigLayoutId = R.layout.music_notification_big;
        int smallLayoutId = R.layout.music_notification_small;
        mBigView = new RemoteViews(mPackageName,
                bigLayoutId);
        mSmallView = new RemoteViews(mPackageName,smallLayoutId);
        if (mAudioBean != null){
            mBigView.setTextViewText(R.id.music_notification_name_tv,mAudioBean.getName());
            mBigView.setTextViewText(R.id.music_notification_author_tv,mAudioBean.getAuthor());

            mSmallView.setTextViewText(R.id.music_notification_name_tv,mAudioBean.getName());
            mSmallView.setTextViewText(R.id.music_notification_author_tv,mAudioBean.getAuthor());
        }

        //上一首点击事件
        Intent preIntent = new Intent(NotificationEventReceiver.ACTION);
        preIntent.putExtra(NotificationEventReceiver.EXTRA,NotificationEventReceiver.EXTRA_PREVIOUS);
        PendingIntent prePendingIntent = PendingIntent.getBroadcast(mContext,0,preIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBigView.setOnClickPendingIntent(R.id.music_notification_pre_iv,prePendingIntent);
        mSmallView.setOnClickPendingIntent(R.id.music_notification_pre_iv,prePendingIntent);

        //播放点击事件
        Intent playIntent = new Intent(NotificationEventReceiver.ACTION);
        playIntent.putExtra(NotificationEventReceiver.EXTRA,NotificationEventReceiver.EXTRA_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(mContext,1,playIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBigView.setOnClickPendingIntent(R.id.music_notification_play_iv,playPendingIntent);
        mSmallView.setOnClickPendingIntent(R.id.music_notification_play_iv,playPendingIntent);

        //下一首点击事件
        Intent nextIntent = new Intent(NotificationEventReceiver.ACTION);
        nextIntent.putExtra(NotificationEventReceiver.EXTRA,NotificationEventReceiver.EXTRA_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(mContext,2,nextIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBigView.setOnClickPendingIntent(R.id.music_notification_next_iv,nextPendingIntent);
        mSmallView.setOnClickPendingIntent(R.id.music_notification_next_iv,nextPendingIntent);

        //关闭点击事件
        Intent closeIntent = new Intent(NotificationEventReceiver.ACTION);
        closeIntent.putExtra(NotificationEventReceiver.EXTRA,NotificationEventReceiver.EXTRA_CLOSE);
        PendingIntent closePendingIntent = PendingIntent.getBroadcast(mContext,3,closeIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBigView.setOnClickPendingIntent(R.id.music_notification_close_iv,closePendingIntent);
        mSmallView.setOnClickPendingIntent(R.id.music_notification_close_iv,closePendingIntent);
    }

    public void showLoadView(AudioBean bean){
        mAudioBean = bean;
        if (mBigView != null && mSmallView != null && mAudioBean != null ){
            mBigView.setImageViewResource(R.id.music_notification_play_iv,R.drawable.ic_play);
            mBigView.setTextViewText(R.id.music_notification_name_tv,mAudioBean.getName());
            mBigView.setTextViewText(R.id.music_notification_author_tv,mAudioBean.getAuthor());
            mSmallView.setImageViewResource(R.id.music_notification_play_iv,R.drawable.ic_play);
            mSmallView.setTextViewText(R.id.music_notification_name_tv,mAudioBean.getName());
            mSmallView.setTextViewText(R.id.music_notification_author_tv, mAudioBean.getAuthor());
            ImageLoadManager.getInstance().loadImgForNotification(mContext,mAudioBean.getPicUrl(),R.id.music_notification_img_iv,
                    mBigView,mNotification,NOTIFICATION_ID);
            mNotificationManager.notify(NOTIFICATION_ID,mNotification);
            ImageLoadManager.getInstance().loadImgForNotification(mContext,mAudioBean.getPicUrl(),R.id.music_notification_img_iv,
                    mSmallView,mNotification,NOTIFICATION_ID);
            mNotificationManager.notify(NOTIFICATION_ID,mNotification);
        }
    }

    public void showPlayView(){
        if (mBigView != null && mSmallView != null && mAudioBean != null){
            mBigView.setImageViewResource(R.id.music_notification_play_iv,R.drawable.ic_pause);

            mSmallView.setImageViewResource(R.id.music_notification_play_iv,R.drawable.ic_pause);

            mNotificationManager.notify(NOTIFICATION_ID,mNotification);
        }
    }

    public void showPauseView(){
        if (mBigView != null && mSmallView != null && mAudioBean != null){
            mBigView.setImageViewResource(R.id.music_notification_play_iv,R.drawable.ic_play);

            mSmallView.setImageViewResource(R.id.music_notification_play_iv,R.drawable.ic_play);
            mNotificationManager.notify(NOTIFICATION_ID,mNotification);
        }
    }

    public Notification getNotification(){
        return mNotification;
    }

    public void destroy(){
        EventBusHelper.getInstance().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioLoadEvent(AudioLoadEvent event) {
        //更新notifacation为load状态
        MusicNotificationHelper.getInstance().showLoadView(event.mAudioBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioPauseEvent(AudioPauseEvent event) {
        //更新notifacation为暂停状态
        MusicNotificationHelper.getInstance().showPauseView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioStartEvent(AudioStartEvent event) {
        //更新notifacation为播放状态
        MusicNotificationHelper.getInstance().showPlayView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioErrorEvent(AudioErrorEvent event){
        Toast.makeText(mContext,mContext.getResources().getString(R.string.play_error),Toast.LENGTH_SHORT).show();
    }

}
