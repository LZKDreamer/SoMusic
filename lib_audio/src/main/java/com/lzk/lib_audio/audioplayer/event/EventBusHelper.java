package com.lzk.lib_audio.audioplayer.event;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/2
 * Function:
 */
public class EventBusHelper {

    private static EventBusHelper sEventBusHelper;

    private EventBusHelper(){}

    public static EventBusHelper getInstance(){
        if (sEventBusHelper == null){
            sEventBusHelper = new EventBusHelper();
        }
        return sEventBusHelper;
    }

    public void register(Object object){
        EventBus.getDefault().register(object);
    }

    public void unregister(Object object){
        EventBus.getDefault().unregister(object);
    }

    public void post(Object object){
        EventBus.getDefault().post(object);
    }

}
