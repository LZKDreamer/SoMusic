package com.lzk.lib_audio.audioplayer.core;

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

    /**
     * 是否处于空闲状态
     * @return
     */
    public boolean isIdle(){
        return getStatus() == CustomMediaPlayer.Status.IDLE;
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
        mQueue.clear();
        mAudioPlayer.stop();
    }

    /**
     * 恢复播放
     */
    public void resume(){
        mAudioPlayer.resume();
    }

    /**
     * 从某位置开始播放
     * @param duration
     */
    public void seekTo(int duration){
        mAudioPlayer.seekTo(duration);
    }

    /**
     * 释放资源
     */
    public void release(){
        mAudioPlayer.release();
        EventBusHelper.getInstance().unregister(this);
    }

    /**
     * 获取当前的歌曲对象
     * @return
     */
    public AudioBean getAudio(){
        if (mQueue != null && mQueue.size() > 0 && mCurIndex < mQueue.size()){
            return mQueue.get(mCurIndex);
        }
        return null;
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
                mCurIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
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
        if (mQueue.size() > 0){
            switch (mPlayMode){
                case LOOP:
                    mCurIndex = (mCurIndex+mQueue.size()-1) % mQueue.size();
                    return getAudio();
                case RANDOM:
                    mCurIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
                    return getAudio();
                case REPEAT:
                    return getAudio();
            }
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
        }else if (isIdle()){
            play(mCurIndex);
        }
    }

    /**
     * 添加歌曲
     */
    public void addAudio(AudioBean newAudio){
        int index = queryAudio(newAudio);
        if (index <= -1){//未添加过
            mQueue.add(0,newAudio);
            play(0);
        }else {//要添加的歌曲已经添加
            AudioBean nowAudio = getAudio();
            //不是正在播放的歌曲
            if (!newAudio.getUrl().equals(nowAudio.getUrl())){
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
        next();
    }

    //播放出错事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioErrorEvent(AudioErrorEvent event) {
        next();
    }
}
