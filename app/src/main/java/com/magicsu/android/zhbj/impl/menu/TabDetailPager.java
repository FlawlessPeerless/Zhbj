package com.magicsu.android.zhbj.impl.menu;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.magicsu.android.zhbj.R;
import com.magicsu.android.zhbj.base.BaseMenuDetailPager;
import com.magicsu.android.zhbj.domain.NewsMenu;
import com.magicsu.android.zhbj.domain.NewsTabBean;
import com.magicsu.android.zhbj.global.GlobalConstants;
import com.magicsu.android.zhbj.util.CacheUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 新闻详情 标签页
 * Created by admin on 2018/1/18.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private NewsMenu.NewsMenuData.NewsTabData mTabData;
    private TextView mTextView;
    private ViewPager mViewPager;

    public TabDetailPager(Activity activity, NewsMenu.NewsMenuData.NewsTabData tabData) {
        super(activity);
        mTabData = tabData;
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pager_tab_detail, null, false);
        mViewPager = view.findViewById(R.id.view_pager_top_news);
        return view;
    }

    @Override
    public void initData() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(GlobalConstants.SERVER_URL + "/static/zhbj" + mTabData.url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("1011", "onSuccess: "+ result);

                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mActivity, "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String result) {
        Gson gson = new Gson();
        NewsTabBean newsTabBean = gson.fromJson(result, NewsTabBean.class);
        // TODO 数据填充完成...
    }

    /**
     * 头条新闻适配器
     */
    class TopNewsAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
