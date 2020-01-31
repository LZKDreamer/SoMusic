package com.lzk.somusic.app;

import android.app.Application;

import com.lzk.lib_common_ui.utils.SPUtil;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/1/30
 * Function:
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化SPUtil
        SPUtil.getInstance().init(this,Constants.SP_NAME);
    }
}
