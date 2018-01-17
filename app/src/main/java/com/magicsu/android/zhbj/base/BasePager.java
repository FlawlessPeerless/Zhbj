package com.magicsu.android.zhbj.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.magicsu.android.zhbj.R;

/**
 * 内容主页基类
 * Created by admin on 2018/1/17.
 */

public abstract class BasePager {
    protected Activity mActivity;
    protected TextView mTitleText;
    protected ImageButton mHeadMenu;
    protected FrameLayout mContentWrapper;
    public final View mRootView;

    public BasePager(Activity activity) {
        mActivity = activity;

        mRootView = initView();
    }

    private View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.base_pager, null, false);
        mTitleText = view.findViewById(R.id.text_title);
        mHeadMenu = view.findViewById(R.id.button_head);
        mContentWrapper = view.findViewById(R.id.content_wrapper);

        return view;
    }

    public abstract void initData();
}
