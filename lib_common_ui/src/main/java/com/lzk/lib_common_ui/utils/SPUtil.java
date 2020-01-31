package com.lzk.lib_common_ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: LiaoZhongKai.
 * Date: 2020/1/30
 * Function:SharedPreference工具类
 */
public class SPUtil {
    private static SharedPreferences sp = null;
    private static SPUtil sSPUtil = null;
    private static SharedPreferences.Editor editor = null;

    public static SPUtil getInstance() {
        if (sSPUtil == null){
            synchronized (SPUtil.class){
                if (sSPUtil == null){
                    sSPUtil = new SPUtil();
                }
            }
        }
        return sSPUtil;
    }

    /**
     * SPUtil初始化
     * @param context
     * @param spName SharedPreference文件名
     */
    public void init(Context context,String spName){
        sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void putLong(String key, Long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, int defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public boolean isKeyExist(String key) {
        return sp.contains(key);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }
}
