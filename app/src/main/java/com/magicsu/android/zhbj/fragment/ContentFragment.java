package com.magicsu.android.zhbj.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.magicsu.android.zhbj.R;
import com.magicsu.android.zhbj.activity.MainActivity;
import com.magicsu.android.zhbj.base.BasePager;
import com.magicsu.android.zhbj.impl.GovAffairsPager;
import com.magicsu.android.zhbj.impl.HomePager;
import com.magicsu.android.zhbj.impl.NewsCenterPager;
import com.magicsu.android.zhbj.impl.SettingPager;
import com.magicsu.android.zhbj.impl.SmartServicePager;
import com.magicsu.android.zhbj.view.NoScrollViewPager;

import java.util.ArrayList;

/**
 * app主页
 * Created by admin on 2018/1/16.
 */

public class ContentFragment extends BaseFragment {

    private NoScrollViewPager mViewPager;
    private ArrayList<BasePager> mPagers;
    private RadioGroup mRadioGroup;

    @Override
    public View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_content, null, false);
        mViewPager = view.findViewById(R.id.view_pager_content);
        mRadioGroup = view.findViewById(R.id.radio_group_tab);
        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<>();
        mPagers.add(new HomePager(mActivity));
        mPagers.add(new NewsCenterPager(mActivity));
        mPagers.add(new SmartServicePager(mActivity));
        mPagers.add(new GovAffairsPager(mActivity));
        mPagers.add(new SettingPager(mActivity));

        mPagers.get(0).initData();

        mViewPager.setAdapter(new ContentAdapter());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mPagers.get(position);
                pager.initData();
                MainActivity parent = (MainActivity)mActivity;
                if (position == 0 || position == 4) {
                    parent.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                } else {
                    parent.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.tab_home:
                    // 首页
                    mViewPager.setCurrentItem(0, false);
                    break;
                case R.id.tab_news:
                    // 新闻中心
                    mViewPager.setCurrentItem(1, false);
                    break;
                case R.id.tab_smart:
                    // 智慧服务
                    mViewPager.setCurrentItem(2, false);
                    break;
                case R.id.tab_gov:
                    // 政务
                    mViewPager.setCurrentItem(3, false);
                    break;
                case R.id.tab_setting:
                    // 设置
                    mViewPager.setCurrentItem(4, false);
                    break;
            }
        });
    }

    /**
     * 获取新闻中心pager
     */
    public NewsCenterPager getNewsCenterPager() {
        return (NewsCenterPager) mPagers.get(1);
    }

    class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager pager = mPagers.get(position);
            // pager.initData();
            View view = pager.mRootView;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}

