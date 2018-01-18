package com.magicsu.android.zhbj.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.magicsu.android.zhbj.R;
import com.magicsu.android.zhbj.fragment.ContentFragment;
import com.magicsu.android.zhbj.fragment.LeftMenuFragment;

/**
 * 主页面
 * Created by admin on 2018/1/15.
 */

public class MainActivity extends AppCompatActivity {
    public static final String TAG_LEFT_MENU = "fragment_left_menu";
    public static final String TAG_CONTENT_DETAIL = "fragment_content_detail";

    public DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;
    private FrameLayout mLeftWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initFragment();
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.left_wrapper, new LeftMenuFragment(), TAG_LEFT_MENU);
        transaction.replace(R.id.main_wrapper, new ContentFragment(), TAG_CONTENT_DETAIL);
        transaction.commit();

    }

    private void initUI() {
        mLeftWrapper = findViewById(R.id.left_wrapper);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public LeftMenuFragment getLeftMenuFragment() {
        return (LeftMenuFragment) mFragmentManager.findFragmentByTag(TAG_LEFT_MENU);
    }

    public ContentFragment getContentFragment() {
        return (ContentFragment) mFragmentManager.findFragmentByTag(TAG_CONTENT_DETAIL);
    }

    public void toggle() {
        boolean isOpen = mDrawerLayout.isDrawerOpen(mLeftWrapper);
        if (isOpen) {
            mDrawerLayout.closeDrawers();
        } else {
            mDrawerLayout.openDrawer(mLeftWrapper);
        }
    }
}
