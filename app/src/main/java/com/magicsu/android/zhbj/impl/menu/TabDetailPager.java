package com.magicsu.android.zhbj.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.magicsu.android.zhbj.base.BaseMenuDetailPager;
import com.magicsu.android.zhbj.domain.NewsMenu;

/**
 * 新闻详情 标签页
 * Created by admin on 2018/1/18.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private NewsMenu.NewsMenuData.NewsTabData mTabData;
    private TextView mTextView;

    public TabDetailPager(Activity activity, NewsMenu.NewsMenuData.NewsTabData tabData) {
        super(activity);
        mTabData = tabData;
    }

    @Override
    public View initView() {
        mTextView = new TextView(mActivity);
        mTextView.setText("页签");
        mTextView.setTextColor(Color.RED);
        mTextView.setTextSize(22);
        mTextView.setGravity(Gravity.CENTER);
        return mTextView;
    }

    @Override
    public void initData() {
        mTextView.setText(mTabData.title);
    }
}
