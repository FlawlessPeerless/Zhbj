package com.magicsu.android.zhbj.base;

import android.app.Activity;
import android.view.View;

/**
 * 侧边栏内容详情 基类
 * Created by admin on 2018/1/17.
 */

public abstract class BaseMenuDetailPager {
    protected Activity mActivity;
    public final View mRootView;

    public BaseMenuDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initView();
    }

    /**
     * 初始化布局
     * @return 返回指定布局
     */
    public abstract View initView();

    public void initData() {

    }
}
