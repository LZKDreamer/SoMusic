package com.lzk.lib_common_ui.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lzk.lib_common_ui.utils.StatusBarUtil;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/1/31
 * Function:全局UI基类Activity
 */
public class BaseUIActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode(this);
    }
}
