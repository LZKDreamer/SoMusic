package com.lzk.lib_audio.audioplayer.core;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:带状态的MediaPlayer，因为系统的MediaPlayer的状态可能不准确
 */
public class CustomMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener {

    private Status mStatus;

    private OnCompletionListener mOnCompletionListener;

    public enum Status {
        IDLE, INITIALIZED, STARTED, PAUSED, STOPPED, COMPLETED;
    }

    public CustomMediaPlayer() {
        super();
        mStatus = Status.IDLE;
        super.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mStatus = Status.COMPLETED;
        if (mOnCompletionListener != null){
            mOnCompletionListener.onCompletion(mediaPlayer);
        }
    }

    @Override
    public void reset() {
        super.reset();
        mStatus = Status.IDLE;
    }

    @Override
    public void setDataSource(String path) throws IOException {
        super.setDataSource(path);
        mStatus = Status.INITIALIZED;
    }

    @Override
    public int getCurrentPosition() {
        return super.getCurrentPosition();
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
        mStatus = Status.STARTED;
    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        mStatus = Status.PAUSED;
    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        mStatus = Status.STOPPED;
    }

    public Status getStatus(){
        return mStatus;
    }

    public boolean isCompleted(){
        return mStatus == Status.COMPLETED;
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener){
        this.mOnCompletionListener = listener;
    }
}

