package com.lzk.lib_audio.audioplayer.app;

import android.content.Context;

import androidx.annotation.NonNull;

import com.lzk.lib_audio.audioplayer.core.AudioController;
import com.lzk.lib_audio.audioplayer.core.AudioPlayer;
import com.lzk.lib_audio.audioplayer.core.CustomMediaPlayer;
import com.lzk.lib_audio.audioplayer.model.AudioBean;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:初始化音频播放器
 */
public final class AudioPlayerManager {

    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    public static Context getContext(){
        return mContext;
    }

    public static void addAudio(AudioBean bean){
        AudioController.getInstance().addAudio(bean);
    }

    public static void pauseAudio(){
        AudioController.getInstance().pause();
    }

    public static void stopAudio(){
        AudioController.getInstance().stop();
    }

    public static void resumeAudio(){
        AudioController.getInstance().resume();
    }

    public static void release(){
        AudioController.getInstance().release();
    }

    public static void setPlayMode(AudioController.PlayMode playMode){
        AudioController.getInstance().setPlayMode(playMode);
    }
}
