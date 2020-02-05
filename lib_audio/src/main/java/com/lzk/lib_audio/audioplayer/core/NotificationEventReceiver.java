package com.lzk.lib_audio.audioplayer.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.lzk.lib_audio.audioplayer.app.AudioPlayerManager;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/4
 * Function:Notification点击事件广播接收器
 */
public class NotificationEventReceiver extends BroadcastReceiver {

    public static final String ACTION = "NotificationEventReceiver";
    public static final String EXTRA = "notification_extra";
    public static final String EXTRA_PREVIOUS = "extra_previous";
    public static final String EXTRA_NEXT = "extra_next";
    public static final String EXTRA_PLAY = "extra_play";
    public static final String EXTRA_CLOSE = "extra_close";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction())){
            return;
        }
        String extra = intent.getStringExtra(EXTRA);
        if (!TextUtils.isEmpty(extra)){
            switch (extra){
                case EXTRA_PREVIOUS:
                    AudioController.getInstance().previous();
                    break;
                case EXTRA_NEXT:
                    AudioController.getInstance().next();
                    break;
                case EXTRA_PLAY:
                    AudioController.getInstance().playOrPause();
                    break;
            }
        }
    }
}
