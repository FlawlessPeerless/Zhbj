package com.magicsu.android.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 头条新闻自定义viewpager
 * Created by admin on 2018/1/19.
 */

public class TopNewsViewPager extends ViewPager {

    private float mStartX;
    private float mStartY;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                mStartY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();

                float distanceX = endX - mStartX;
                float distanceY = endY - mStartY;

                if (Math.abs(distanceY) < Math.abs(distanceX)) {
                    int currentItem = getCurrentItem();
                    // 左右滑动
                    if (distanceX > 0) {
                        // 向右
                        currentItem = getCurrentItem();
                        // 第一个页面
                        if (currentItem == 0) {
                            // 拦截
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        // 向左
                        int count = getAdapter().getCount();
                        // 最后一个页面
                        if (currentItem == count - 1) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {
                    // 上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
