package com.magicsu.android.zhbj.base;

import android.app.Application;

import org.xutils.x;

/**
 * Created by admin on 2018/1/17.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
