package com.magicsu.android.zhbj.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.magicsu.android.zhbj.R;
import com.magicsu.android.zhbj.base.BaseMenuDetailPager;
import com.magicsu.android.zhbj.domain.NewsMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 侧边栏 新闻
 * Created by admin on 2018/1/17.
 */

public class NewsMenuDetailPager extends BaseMenuDetailPager {

    private TabPageIndicator mTabPageIndicator;
    private ViewPager mViewPager;
    private ImageButton mNextButton;

    private ArrayList<NewsMenu.NewsMenuData.NewsTabData> mNewsTabData;
    private ArrayList<TabDetailPager> mTabDetailPagers;
    private NewsMenuDetailAdapter mNewsMenuDetailAdapter;

    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsMenuData.NewsTabData> children) {
        super(activity);
        mNewsTabData = children;
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pager_news_detail, null, false);
        mTabPageIndicator = view.findViewById(R.id.indicator);
        mViewPager = view.findViewById(R.id.view_pager_news_menu_detail);
        mNextButton = view.findViewById(R.id.button_next);
        return view;
    }

    @Override
    public void initData() {
        mTabDetailPagers = new ArrayList<>();
        for (NewsMenu.NewsMenuData.NewsTabData tabData : mNewsTabData) {
            TabDetailPager tabDetailPager = new TabDetailPager(mActivity, tabData);
            mTabDetailPagers.add(tabDetailPager);
        }
        mNewsMenuDetailAdapter = new NewsMenuDetailAdapter();
        mViewPager.setAdapter(mNewsMenuDetailAdapter);
        // 绑定tabpager 和 viewpager(必须在setAdapter之后)
        mTabPageIndicator.setViewPager(mViewPager);
        mTabPageIndicator.setCurrentItem(0);
        mNextButton.setOnClickListener(v -> {
            int currentItem = mViewPager.getCurrentItem();
            if (currentItem == mTabDetailPagers.size()-1) return;
            currentItem++;
            mViewPager.setCurrentItem(currentItem);
        });
    }

    class NewsMenuDetailAdapter extends PagerAdapter {
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TabDetailPager pager = mTabDetailPagers.get(position);
            View view = pager.mRootView;
            container.addView(view);
            pager.initData();
            return view;
        }

        @Override
        public int getCount() {
            return mTabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        /**
         * 指定指示器的标题
         * @param position 下标
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabData.get(position).title;
        }
    }
}
