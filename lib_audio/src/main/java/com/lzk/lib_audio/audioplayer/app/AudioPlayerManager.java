package com.lzk.lib_audio.audioplayer.app;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.lzk.lib_audio.audioplayer.activity.MusicPlayerActivity;
import com.lzk.lib_audio.audioplayer.core.AudioController;
import com.lzk.lib_audio.audioplayer.core.AudioPlayer;
import com.lzk.lib_audio.audioplayer.core.CustomMediaPlayer;
import com.lzk.lib_audio.audioplayer.core.MusicService;
import com.lzk.lib_audio.audioplayer.model.AudioBean;

import java.util.ArrayList;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:初始化音频播放器
 */
public final class AudioPlayerManager {

    private static Context mContext;
    private static Class mTargetActivity;

    public static void init(Context context){
        mContext = context;
    }

    public static Context getContext(){
        return mContext;
    }

    /**
     * 添加歌曲
     * @param bean
     */
    public static void addAudio(AudioBean bean){
        addAudio(null,bean);
    }

    public static void addAudio(Activity activity , AudioBean bean){
        AudioController.getInstance().addAudio(bean);
        if (activity != null){
            MusicPlayerActivity.start(activity);
        }
    }

    /**
     * 暂停播放
     */
    public static void pauseAudio(){
        AudioController.getInstance().pause();
    }

    /**
     * 停止播放
     */
    public static void stopAudio(){
        AudioController.getInstance().stop();
    }

    /**
     * 恢复播放
     */
    public static void resumeAudio(){
        AudioController.getInstance().resume();
    }

    /**
     * 释放资源
     */
    public static void release(){
        AudioController.getInstance().release();
    }

    /**
     * 设置播放模式
     * @param playMode
     */
    public static void setPlayMode(AudioController.PlayMode playMode){
        AudioController.getInstance().setPlayMode(playMode);
    }

    /**
     * 是否正在播放
     * @return
     */
    public static boolean isPlaying(){
        return AudioController.getInstance().isPlaying();
    }

    /**
     * 获取当前播放的歌曲
     * @return
     */
    public static AudioBean getAudioBean(){
        return AudioController.getInstance().getAudio();
    }

    /**
     * 启动前台服务
     * @param list
     */
    public static void startMusicService(ArrayList<AudioBean> list){
        MusicService.startMusicService(list);
    }

    /**
     * 设置点击前台服务要跳转的activity
     */
    public static void setNotificationTargetActivity(Class clazz){
        mTargetActivity = clazz;
    }

    /**
     * 获取前台服务要跳转的activity
     * @return
     */
    public static Class getNotificationTargetActivity(){
        return mTargetActivity;
    }
}
