package com.magicsu.android.zhbj.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.magicsu.android.zhbj.base.BasePager;

/**
 * Created by admin on 2018/1/17.
 */

public class SettingPager extends BasePager {
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        TextView view = new TextView(mActivity);
        view.setText("设置");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);

        mContentWrapper.addView(view);

        // 设置标题
        mTitleText.setText("设置");
        mHeadMenu.setVisibility(View.GONE);
    }
}
