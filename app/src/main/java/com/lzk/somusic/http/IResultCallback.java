package com.lzk.somusic.http;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/1
 * Function:网络请求回调接口
 */
public interface IResultCallback {
    void onSuccess(Object object);
    void onFailed(int errCode,String errMsg);
}
