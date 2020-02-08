package com.lzk.lib_audio.utils;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/2/7
 * Function:
 */
public class Util {

    /**
     * 将毫秒转换成时分
     * @param duration
     * @return
     */
    public static String timeParse(long duration) {
        String min = (duration / (1000 * 60)) + "";
        String second = (duration % (1000 * 60) / 1000) + "";
        if (min.length() < 2) {
            min = 0 + min;
        }
        if (second.length() < 2) {
            second = 0 + second;
        }
        return min + ":" + second;
    }
}
