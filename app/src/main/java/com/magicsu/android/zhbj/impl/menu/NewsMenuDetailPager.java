package com.magicsu.android.zhbj.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.magicsu.android.zhbj.base.BaseMenuDetailPager;

/**
 * 侧边栏 新闻
 * Created by admin on 2018/1/17.
 */

public class NewsMenuDetailPager extends BaseMenuDetailPager {
    public NewsMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText("菜单详情页-新闻");
        textView.setTextColor(Color.RED);
        textView.setTextSize(22);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
