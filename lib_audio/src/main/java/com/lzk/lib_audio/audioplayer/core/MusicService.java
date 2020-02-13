package com.lzk.lib_audio.audioplayer.core;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lzk.lib_audio.R;
import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.event.AudioErrorEvent;
import com.lzk.lib_audio.audioplayer.event.AudioLoadEvent;
import com.lzk.lib_audio.audioplayer.event.AudioPauseEvent;
import com.lzk.lib_audio.audioplayer.event.AudioReleaseEvent;
import com.lzk.lib_audio.audioplayer.event.AudioStartEvent;
import com.lzk.lib_audio.audioplayer.event.EventBusHelper;
import com.lzk.lib_audio.audioplayer.model.AudioBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/4
 * Function:音乐播放前台服务
 */
public class MusicService extends Service implements MusicNotificationHelperListener{

    private static final String INTENT_AUDIO_LIST = "INTENT_AUDIO_LIST";
    private static final String MUSIC_SERVICE_ACTION = "MUSIC_SERVICE_ACTION";
    private ArrayList<AudioBean> mAudioBeans;
    private NotificationEventReceiver mEventReceiver;

    public static void startMusicService(ArrayList<AudioBean> list){
        Intent intent = new Intent(AudioPlayerManager.getContext(),MusicService.class);
        intent.setAction(MUSIC_SERVICE_ACTION);
        intent.putExtra(INTENT_AUDIO_LIST,list);
        AudioPlayerManager.getContext().startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusHelper.getInstance().register(this);
        registerMusicReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusHelper.getInstance().unregister(this);
        MusicNotificationHelper.getInstance().destroy();
        unregisterMusicReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mAudioBeans = (ArrayList<AudioBean>) intent.getSerializableExtra(INTENT_AUDIO_LIST);
        AudioController.getInstance().setQueue(mAudioBeans);
        if (intent.getAction().equals(MUSIC_SERVICE_ACTION)){
            MusicNotificationHelper.getInstance().init(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReleaseEvent(AudioReleaseEvent event){
        stopForeground(true);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void registerMusicReceiver(){
        if (mEventReceiver == null){
            mEventReceiver = new NotificationEventReceiver();
            IntentFilter filter = new IntentFilter(NotificationEventReceiver.ACTION);
            registerReceiver(mEventReceiver,filter);
        }
    }

    private void unregisterMusicReceiver(){
        if (mEventReceiver != null){
            unregisterReceiver(mEventReceiver);
        }
    }


    @Override
    public void onNotificationInit() {
        startForeground(MusicNotificationHelper.NOTIFICATION_ID,MusicNotificationHelper.getInstance().getNotification());
    }

}
