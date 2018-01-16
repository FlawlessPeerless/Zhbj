package com.magicsu.android.zhbj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.magicsu.android.zhbj.R;
import com.magicsu.android.zhbj.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 新手引导页
 * Created by admin on 2018/1/15.
 */

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private int[] mGuideImages;
    private List<ImageView> mImageViewList;
    private GuideAdapter mGuideAdapter;
    private LinearLayout mLinearLayout;
    private ImageView mRedPoint;
    private int mPointDistance;
    private Button mStartButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除默认头部
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        initUI();
        initData();
        initAdapter();
    }

    private void initUI() {
        mViewPager = findViewById(R.id.guide_view_pager);
        mLinearLayout = findViewById(R.id.point_list);
        mRedPoint = findViewById(R.id.red_point);
        mStartButton = findViewById(R.id.start_button);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 更新小红点距离
                int leftMargin = (int) (mPointDistance * positionOffset) + position * mPointDistance;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mRedPoint.getLayoutParams();
                params.leftMargin = leftMargin + 10;
                mRedPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == mImageViewList.size() - 1) {
                    mStartButton.setVisibility(View.VISIBLE);
                } else {
                    mStartButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ViewTreeObserver treeObserver = mRedPoint.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(() -> {
            // 界面绘制结束之后开始计算两个原点间距
            mPointDistance = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
        });

        mStartButton.setOnClickListener(v -> {
            SpUtil.setBoolean(GuideActivity.this ,SpUtil.FIRST_ENTER, false);
            startActivity(new Intent(GuideActivity.this, MainActivity.class));
            finish();
        });

    }

    private void initData() {
        mGuideImages = new int[] {
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3
        };

        mImageViewList = new ArrayList<>();
        for (int mGuideImage : mGuideImages) {
            ImageView view = new ImageView(this);
            view.setBackgroundResource(mGuideImage);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViewList.add(view);

            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            point.setLayoutParams(params);
            mLinearLayout.addView(point);
        }

    }

    private void initAdapter() {
        mGuideAdapter = new GuideAdapter();
        mViewPager.setAdapter(mGuideAdapter);
    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = mImageViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
