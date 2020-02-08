package com.lzk.lib_audio.audioplayer.event;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/7
 * Function:音频进度事件
 */
public class AudioProgressEvent {

    public int progress;

    public int totalDuration;

    public AudioProgressEvent(int progress,int totalDuration){
        this.progress = progress;
        this.totalDuration = totalDuration;
    }
}
