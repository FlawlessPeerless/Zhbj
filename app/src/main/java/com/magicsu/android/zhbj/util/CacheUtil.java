package com.magicsu.android.zhbj.util;

import android.content.Context;

/**
 * 缓存工具类
 * Created by admin on 2018/1/17.
 */

public class CacheUtil {
    /**
     * 设置缓存 保存在本地
     * @param context 上下文环境
     * @param key 以URL为key
     * @param value 以json为value
     */
    public static void setCache(Context context, String key, String value) {
        SpUtil.setString(context, key, value);
    }

    /**
     * 获取缓存
     * @param context 上下文环境
     * @param key 以URL为key
     * @return 返回json
     */
    public static String getCache(Context context, String key) {
        return SpUtil.getString(context, key, null);
    }
}
