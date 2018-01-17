package com.magicsu.android.zhbj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.magicsu.android.zhbj.activity.MainActivity;

/**
 * fragment基类
 * Created by admin on 2018/1/16.
 */

public abstract class BaseFragment extends Fragment {

    public Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
    }

    // 初始化fragment布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化数据
        initData();
    }

    public abstract View initView();

    public abstract void initData();

    public void toggle() {
        MainActivity mainUI = (MainActivity)mActivity;
        mainUI.toggle();
    }
}
