package com.lzk.lib_audio.audioplayer.core;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lzk.lib_audio.R;
import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.db.AudioGreenDaoHelper;
import com.lzk.lib_audio.audioplayer.event.AudioErrorEvent;
import com.lzk.lib_audio.audioplayer.event.AudioEventCompletionEvent;
import com.lzk.lib_audio.audioplayer.event.AudioLoadEvent;
import com.lzk.lib_audio.audioplayer.event.AudioPauseEvent;
import com.lzk.lib_audio.audioplayer.event.AudioProgressEvent;
import com.lzk.lib_audio.audioplayer.event.AudioReleaseEvent;
import com.lzk.lib_audio.audioplayer.event.AudioStartEvent;
import com.lzk.lib_audio.audioplayer.event.AudioStopEvent;
import com.lzk.lib_audio.audioplayer.event.EventBusHelper;
import com.lzk.lib_audio.audioplayer.model.AudioBean;

import java.io.IOException;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:1：处理音频相关事件（加载、播放、暂停、恢复播放、释放资源）
 *          2：(使用EventBus)对外发送事件
 */
public class AudioPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,AudioFocusManager.AudioFocusListener{

    private static final String TAG = "AudioPlayer";
    private static final int TIME_MSG = 100;
    private static final int HANDLER_MUSIC_PROGRESS = 0x001;

    private Context mContext;

    private CustomMediaPlayer mCustomMediaPlayer;
    //起保活作用
    private WifiManager.WifiLock mWifiLock;
    private AudioFocusManager mAudioFocusManager;

    //是否是因系统原因失去焦点
    private boolean isRealPause = false;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HANDLER_MUSIC_PROGRESS:
                    if (getStatus() == CustomMediaPlayer.Status.STARTED || getStatus() == CustomMediaPlayer.Status.PAUSED){
                        EventBusHelper.getInstance().post(new AudioProgressEvent(getCurrentPosition(),getDuration()));
                        sendEmptyMessageDelayed(HANDLER_MUSIC_PROGRESS,TIME_MSG);
                    }
                    break;
            }
        }
    };

    public AudioPlayer(){
        mContext = AudioPlayerManager.getContext();
        init();
    }

    private void init(){
        mCustomMediaPlayer = new CustomMediaPlayer();
        mCustomMediaPlayer.setWakeMode(mContext,PowerManager.PARTIAL_WAKE_LOCK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            mCustomMediaPlayer.setAudioAttributes(audioAttributes);
        }else {
            mCustomMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        mCustomMediaPlayer.setOnCompletionListener(this);
        mCustomMediaPlayer.setOnBufferingUpdateListener(this);
        mCustomMediaPlayer.setOnPreparedListener(this);
        mCustomMediaPlayer.setOnErrorListener(this);
        mWifiLock = ((WifiManager) mContext
                .getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE)).createWifiLock(WifiManager.WIFI_MODE_FULL, TAG);
        mAudioFocusManager = new AudioFocusManager(mContext,this);
    }

    /**
     * 加载音乐
     * @param audioBean
     */
    public void load(AudioBean audioBean){
        if (audioBean == null){
            return;
        }
        try {
            mCustomMediaPlayer.reset();
            mCustomMediaPlayer.setDataSource(audioBean.getUrl());
            mCustomMediaPlayer.prepareAsync();
            //对外发送加载事件
            EventBusHelper.getInstance().post(new AudioLoadEvent(audioBean));
            //添加到播放记录
            AudioGreenDaoHelper.getInstance().addPlayBackRecord(audioBean);
        }catch (IOException e){
            //对外发送失败事件
            EventBusHelper.getInstance().post(new AudioErrorEvent());
            e.printStackTrace();
        }
    }

    //播放音乐
    private void start(){
        if (mAudioFocusManager.requestAudioFocus()){
            mCustomMediaPlayer.start();
            mWifiLock.acquire();
            //对外发送开始事件
            EventBusHelper.getInstance().post(new AudioStartEvent());
            mHandler.sendEmptyMessageDelayed(HANDLER_MUSIC_PROGRESS,TIME_MSG);
        }else {//音频焦点被占用
            showOccupiedToast();
        }
    }

    /**
     * 暂停音乐
     */
    public void pause(){
        if (getStatus() == CustomMediaPlayer.Status.STARTED){
            if (mCustomMediaPlayer != null){
                mCustomMediaPlayer.pause();
            }
            if (mWifiLock != null && mWifiLock.isHeld()){
                mWifiLock.release();
            }
            //暂停时不释放焦点，否则在挂掉电话后系统不会回调AUDIOFOCUS_GAIN
            //释放焦点
//            if (mAudioFocusManager != null){
//                mAudioFocusManager.abandonAudioFocus();
//            }
            //发送暂停事件
            EventBusHelper.getInstance().post(new AudioPauseEvent());
        }
    }

    /**
     * 恢复播放音乐
     */
    public void resume(){
        if (getStatus() == CustomMediaPlayer.Status.PAUSED){
            start();
        }
    }

    /**
     * 从某位置开始播放
     * @param duration
     */
    public void seekTo(int duration){
        if (getStatus() == CustomMediaPlayer.Status.STARTED || getStatus() == CustomMediaPlayer.Status.PAUSED ||
        getStatus() == CustomMediaPlayer.Status.COMPLETED){
            if (mCustomMediaPlayer != null){
                mCustomMediaPlayer.seekTo(duration);
            }
        }
    }

    /**
     * 停止播放
     * 将所有状态重置为初始状态
     */
    public void stop(){
        if (mCustomMediaPlayer != null){
            if (mCustomMediaPlayer.getStatus() == CustomMediaPlayer.Status.STARTED){
                mCustomMediaPlayer.stop();
            }
            mCustomMediaPlayer.reset();
        }
        if (mWifiLock != null && mWifiLock.isHeld()){
            mWifiLock.release();
        }
        if (mAudioFocusManager != null){
            mAudioFocusManager.abandonAudioFocus();
        }

        //发送停止事件
        EventBusHelper.getInstance().post(new AudioStopEvent());

        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 释放资源
     */
    public void release(){
        if (mCustomMediaPlayer != null){
            mCustomMediaPlayer.release();
            mCustomMediaPlayer = null;
        }
        if (mWifiLock != null && mWifiLock.isHeld()){
            mWifiLock.release();
            mWifiLock = null;
        }
        if (mAudioFocusManager != null){
            mAudioFocusManager.abandonAudioFocus();
            mAudioFocusManager = null;
        }
        mHandler.removeCallbacksAndMessages(null);
        //发送销毁事件
        EventBusHelper.getInstance().post(new AudioReleaseEvent());
    }

    /**
     * 获取当前音乐总时长
     */
    public int getDuration() {
        if (getStatus() == CustomMediaPlayer.Status.STARTED
                || getStatus() == CustomMediaPlayer.Status.PAUSED) {
            return mCustomMediaPlayer.getDuration();
        }
        return 0;
    }

    public int getCurrentPosition() {
        if (getStatus() == CustomMediaPlayer.Status.STARTED
                || getStatus() == CustomMediaPlayer.Status.PAUSED) {
            return mCustomMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public CustomMediaPlayer.Status getStatus(){
        if (mCustomMediaPlayer != null){
            return mCustomMediaPlayer.getStatus();
        }
        return CustomMediaPlayer.Status.STOPPED;
    }

    //设置音量
    private void setVolume(float leftVolume,float rightVolume){
        if (mCustomMediaPlayer != null) mCustomMediaPlayer.setVolume(leftVolume, rightVolume);
    }

    //提示焦点被占用
    private void showOccupiedToast(){
        Toast.makeText(mContext,mContext.getResources().getString(R.string.audio_focus_occupied),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        EventBusHelper.getInstance().post(new AudioEventCompletionEvent());
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        EventBusHelper.getInstance().post(new AudioErrorEvent());
        //return false会回调onCompletion（）
        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        start();
    }

    @Override
    public void onGain() {
        setVolume(1f,1f);
        if (isRealPause){
            resume();
        }
        isRealPause = false;
    }

    @Override
    public void onLoss() {
        pause();
    }

    @Override
    public void onLossTransient() {
        pause();
        isRealPause = true;
    }

    @Override
    public void onLossTransientCanDuck() {
        setVolume(0.5f,0.5f);
    }
}
