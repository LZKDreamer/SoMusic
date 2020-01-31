package com.lzk.lib_common_ui.base;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.lzk.lib_common_ui.base.activity.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/1/31
 * Function:静态内部类+弱引用的Handler
 */
public class BaseHandler extends Handler {
    private WeakReference<BaseActivity> mWeakReference;
    private IHandler mIHandler;

    public BaseHandler(BaseActivity activity,IHandler iHandler){
        mWeakReference = new WeakReference<>(activity);
        mIHandler = iHandler;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        BaseActivity activity = mWeakReference.get();
        if (activity != null && mIHandler != null){
            mIHandler.handleMessage(msg);
        }
    }

    public interface IHandler{
        void handleMessage(Message msg);
    }
}
