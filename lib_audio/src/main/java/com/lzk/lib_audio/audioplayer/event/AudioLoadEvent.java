package com.lzk.lib_audio.audioplayer.event;

import com.lzk.lib_audio.audioplayer.model.AudioBean;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:音频加载事件实体类
 */
public class AudioLoadEvent {

    public AudioBean mAudioBean;

    public AudioLoadEvent(AudioBean audioBean){
        mAudioBean = audioBean;
    }
}
