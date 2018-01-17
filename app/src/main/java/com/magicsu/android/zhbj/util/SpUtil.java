package com.magicsu.android.zhbj.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 2018/1/15.
 */

public class SpUtil {
    private static SharedPreferences sharedPreferences;

    public static final String FIRST_ENTER = "is_first_enter";

    private static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(Context context, String key, boolean defval) {
        if (sharedPreferences == null) sharedPreferences = getSp(context);
        return sharedPreferences.getBoolean(key, defval);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        if (sharedPreferences == null) sharedPreferences = getSp(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value).apply();
    }

    public static String getString(Context context, String key, String defval) {
        if (sharedPreferences == null) sharedPreferences = getSp(context);
        return sharedPreferences.getString(key, defval);
    }

    public static void setString(Context context, String key, String value) {
        if (sharedPreferences == null) sharedPreferences = getSp(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }
}
