package com.magicsu.android.zhbj.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;

import com.magicsu.android.zhbj.R;

/**
 * app主页
 * Created by admin on 2018/1/16.
 */

public class ContentFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_content, null, false);
        return view;
    }

    @Override
    public void initData() {

    }
}
