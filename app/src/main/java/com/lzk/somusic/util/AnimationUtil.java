package com.lzk.somusic.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/1/31
 * Function:动画工具类
 */
public class AnimationUtil {

    public static ObjectAnimator rotation(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0f,360f);
        animator.setDuration(2000);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }
}
