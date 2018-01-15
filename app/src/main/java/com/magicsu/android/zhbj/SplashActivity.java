package com.magicsu.android.zhbj;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.magicsu.android.activity.GuideActivity;
import com.magicsu.android.activity.MainActivity;
import com.magicsu.android.zhbj.util.SpUtil;

/**
 * 启动页
 * Created by admin on 2018/1/15.
 */

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout mRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initUI();

    }

    private void initUI() {
        mRoot = findViewById(R.id.splash_root);

        RotateAnimation rotateAnimation = new RotateAnimation(
                0f, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isFirstEnter = SpUtil.getBoolean(SplashActivity.this, SpUtil.FIRST_ENTER, true);
                if (isFirstEnter) {
                    // 第一次登陆 -> 新手引导
                    SpUtil.setBoolean(SplashActivity.this, SpUtil.FIRST_ENTER, false);
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                } else {
                    // 正常登陆
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        mRoot.startAnimation(animationSet);
    }
}
