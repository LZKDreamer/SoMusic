package com.lzk.lib_audio.audioplayer.core;

import android.util.Log;

import com.lzk.lib_audio.R;
import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;
import com.lzk.lib_audio.audioplayer.event.AudioErrorEvent;
import com.lzk.lib_audio.audioplayer.event.AudioEventCompletionEvent;
import com.lzk.lib_audio.audioplayer.event.EventBusHelper;
import com.lzk.lib_audio.audioplayer.model.AudioBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:基于AudioPlayer的封装，控制音乐的播放、暂停，播放模式，播放上/下一首...
 */
public class AudioController {

    private static AudioController sAudioController;

    private AudioPlayer mAudioPlayer;
    //播放队列
    private ArrayList<AudioBean> mQueue;
    //当前播放的歌曲在队列中的索引
    private int mCurIndex = 0;
    private PlayMode mPlayMode;

    public enum PlayMode{
        /**
         * 列表循环
         */
        LOOP,
        /**
         * 随机播放
         */
        RANDOM,
        /**
         * 单曲循环
         */
        REPEAT,

    }

    public static AudioController getInstance(){
        if (sAudioController == null){
            sAudioController = new AudioController();
        }
        return sAudioController;
    }

    private AudioController(){
        mAudioPlayer = new AudioPlayer();
        mQueue = new ArrayList<>();
        mPlayMode = PlayMode.LOOP;
        EventBusHelper.getInstance().register(this);
    }

    /**
     * 获取播放队列
     * @return
     */
    public ArrayList<AudioBean> getQueue(){
        return mQueue == null ? new ArrayList<AudioBean>() : mQueue;
    }

    /**
     * 设置播放队列
     * @param queue
     */
    public void setQueue(ArrayList<AudioBean> queue){
        this.setQueue(queue,0);
    }

    public void setQueue(ArrayList<AudioBean> queue,int index){
        mQueue.addAll(queue);
        mCurIndex = index;
    }

    /**
     * 获取播放模式
     * @return
     */
    public PlayMode getPlayMode(){
        return mPlayMode;
    }

    /**
     * 设置播放模式
     * @param mode
     */
    public void setPlayMode(PlayMode mode){
        mPlayMode = mode;
    }

    /**
     * 获取当前播放的歌曲的索引
     * @return
     */
    public int getQueueIndex(){
        return mCurIndex;
    }

    /**
     * 是否处于播放状态
     * @return
     */
    public boolean isPlaying(){
        return getStatus() == CustomMediaPlayer.Status.STARTED;
    }

    /**
     * 是否处于暂停状态
     * @return
     */
    public boolean isPause(){
        return getStatus() == CustomMediaPlayer.Status.PAUSED;
    }

    //获取播放状态
    private CustomMediaPlayer.Status getStatus(){
        return mAudioPlayer.getStatus();
    }

    /**
     * 播放
     * @param index
     */
    public void play(int index){
        mCurIndex = index;
        mAudioPlayer.load(getAudio());
    }

    /**
     * 暂停播放
     */
    public void pause(){
        mAudioPlayer.pause();
    }

    /**
     * 停止播放
     */
    public void stop(){
        mAudioPlayer.stop();
    }

    /**
     * 恢复播放
     */
    public void resume(){
        mAudioPlayer.resume();
    }

    /**
     * 释放资源
     */
    public void release(){
        mAudioPlayer.release();
        EventBusHelper.getInstance().unregister(this);
    }

    //获取mCurIndex对应的歌曲对象
    private AudioBean getAudio(){
        if (mQueue != null && mQueue.size() > 0 && mCurIndex < mQueue.size()){
            return mQueue.get(mCurIndex);
        }else {
            throw new NullPointerException(AudioPlayerManager.getContext()
                    .getResources().getString(R.string.not_found_music));
        }
    }

    /**
     * 播放下一首
     */
    public void next(){
        AudioBean bean = getNextAudio();
        if (bean != null){
            mAudioPlayer.load(bean);
        }
    }

    /**
     * 播放上一首
     */
    public void previous(){
        AudioBean bean = getPreviousAudio();
        if (bean != null){
            mAudioPlayer.load(bean);
        }
    }

    /**
     * 获取下一首歌曲对象
     */
    private AudioBean getNextAudio(){
        switch (mPlayMode){
            case LOOP:
                mCurIndex = (mCurIndex+1) % mQueue.size();
                return getAudio();
            case RANDOM:
                mCurIndex = ((new Random().nextInt(mQueue.size()))+1) % mQueue.size();
                return getAudio();
            case REPEAT:
                return getAudio();
        }
        return null;
    }

    /**
     * 获取上一首歌曲对象
     */
    private AudioBean getPreviousAudio(){
        switch (mPlayMode){
            case LOOP:
                mCurIndex = mCurIndex+(mQueue.size()-1) % mQueue.size();
                return getAudio();
            case RANDOM:
                mCurIndex = ((new Random().nextInt(mQueue.size()))+1) % mQueue.size();
                return getAudio();
            case REPEAT:
                return getAudio();
        }
        return null;
    }

    /**
     * 自动判断播放或暂停
     */
    public void playOrPause(){
        if (isPlaying()){
            pause();
        }else if (isPause()){
            resume();
        }
    }

    /**
     * 添加歌曲
     */
    public void addAudio(AudioBean newAudio){
        int index = queryAudio(newAudio);
        if (index <= -1){//未添加过
            mQueue.add(newAudio);
            play(0);
        }else {//要添加的歌曲已经添加
            AudioBean nowAudio = getAudio();
            //不是正在播放的歌曲
            if (!newAudio.getName().equals(nowAudio.getName()) &&
                    !newAudio.getAuthor().equals(nowAudio.getAuthor())){
                play(index);
            }
        }
    }

    private int queryAudio(AudioBean audioBean){
        return mQueue.indexOf(audioBean);
    }

    //插放完毕事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioCompleteEvent(AudioEventCompletionEvent event) {
        Log.d("shiZi","onAudioCompleteEvent");
        next();
    }

    //播放出错事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioErrorEvent(AudioErrorEvent event) {
        next();
    }
}
