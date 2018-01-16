package com.magicsu.android.zhbj.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import com.magicsu.android.zhbj.R;
import com.magicsu.android.zhbj.fragment.ContentFragment;
import com.magicsu.android.zhbj.fragment.LeftMenuFragment;

/**
 * 主页面
 * Created by admin on 2018/1/15.
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private FragmentManager mFragmentManager;

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
        transaction.replace(R.id.left_wrapper, new LeftMenuFragment());
        transaction.replace(R.id.main_wrapper, new ContentFragment());
        transaction.commit();

    }

    private void initUI() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

    }
}
