package com.lzk.lib_audio.audioplayer.core;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.lzk.lib_common_ui.base.BaseHandler;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:管理音频焦点
 */
public class AudioFocusManager implements AudioManager.OnAudioFocusChangeListener{

    private AudioFocusListener mAudioFocusListener;
    private AudioManager mAudioManager;
    private AudioFocusRequest mAudioFocusRequest;

    public AudioFocusManager(Context context,AudioFocusListener listener){
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioFocusListener = listener;
    }

    /**
     * 获取焦点
     * @return
     */
    public boolean requestAudioFocus(){
        int result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            mAudioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(audioAttributes)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(this)
                    .build();
            result = mAudioManager.requestAudioFocus(mAudioFocusRequest);
        }else {
            result = mAudioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED || result == AudioManager.AUDIOFOCUS_REQUEST_DELAYED){
            return true;
        }
        return false;
    }

    /**
     * 释放焦点
     */
    public void abandonAudioFocus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if (mAudioFocusRequest != null){
                mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest);
            }
        }else {
            mAudioManager.abandonAudioFocus(this);
        }
    }

    @Override
    public void onAudioFocusChange(int i) {
        switch (i){
            case AudioManager.AUDIOFOCUS_GAIN:
                if (mAudioFocusListener != null) mAudioFocusListener.onGain();
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                if (mAudioFocusListener != null) mAudioFocusListener.onLoss();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (mAudioFocusListener != null) mAudioFocusListener.onLossTransient();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mAudioFocusListener != null) mAudioFocusListener.onLossTransientCanDuck();
                break;
        }
    }


    public interface AudioFocusListener{
        /**
         * 获得焦点
         */
        void onGain();

        /**
         * 永久失去焦点
         */
        void onLoss();

        /**
         * 短暂失去焦点,如来电
         */
        void onLossTransient();

        /**
         * 瞬间失去焦点,如通知或短信提示音
         */
        void onLossTransientCanDuck();
    }
}
