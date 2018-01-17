package com.magicsu.android.zhbj.impl;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.magicsu.android.zhbj.activity.MainActivity;
import com.magicsu.android.zhbj.base.BasePager;
import com.magicsu.android.zhbj.domain.NewsMenu;
import com.magicsu.android.zhbj.fragment.LeftMenuFragment;
import com.magicsu.android.zhbj.global.GlobalConstants;
import com.magicsu.android.zhbj.util.CacheUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 新闻中心
 * Created by admin on 2018/1/17.
 */

public class NewsCenterPager extends BasePager {
    public NewsCenterPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        TextView view = new TextView(mActivity);
        view.setText("新闻中心");
        view.setTextColor(Color.RED);
        view.setTextSize(22);
        view.setGravity(Gravity.CENTER);

        mContentWrapper.addView(view);
        // 设置标题
        mTitleText.setText("新闻中心");

        // 先判断有没有缓存?有缓存使用缓存：没缓存请求服务端
        String cache = CacheUtil.getCache(mActivity, GlobalConstants.CATEGORY_URL);
        if (!TextUtils.isEmpty(cache)) {
            // 有缓存
            processData(cache);
        }

        getDataFromServer();
    }


    private void getDataFromServer() {
        RequestParams params = new RequestParams(GlobalConstants.CATEGORY_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("1011", "onSuccess: "+ result);
                processData(result);
                // 请求成功，写入缓存
                CacheUtil.setCache(mActivity, GlobalConstants.CATEGORY_URL, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json) {
        Gson gson = new Gson();
        NewsMenu newsMenu = gson.fromJson(json, NewsMenu.class);

        // 获取侧边栏对象
        MainActivity mainUI = (MainActivity) mActivity;
        LeftMenuFragment fragment = mainUI.getLeftMenuFragment();
        // 侧边栏设置数据
        fragment.setMenuData(newsMenu.data);

    }
}
