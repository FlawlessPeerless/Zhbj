package com.magicsu.android.zhbj.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.magicsu.android.zhbj.R;

/**
 * 侧边栏fragment
 * Created by admin on 2018/1/16.
 */

public class LeftMenuFragment extends BaseFragment {
    @Override
    public View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_left_menu, null, false);
        return view;
    }

    @Override
    public void initData() {
    }
}
