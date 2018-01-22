package com.magicsu.android.zhbj.impl.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.magicsu.android.zhbj.R;
import com.magicsu.android.zhbj.activity.NewsDetailActivity;
import com.magicsu.android.zhbj.base.BaseMenuDetailPager;
import com.magicsu.android.zhbj.domain.NewsMenu;
import com.magicsu.android.zhbj.domain.NewsTabBean;
import com.magicsu.android.zhbj.global.GlobalConstants;
import com.magicsu.android.zhbj.util.CacheUtil;
import com.magicsu.android.zhbj.view.TopNewsViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 新闻详情 标签页
 * Created by admin on 2018/1/18.
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private NewsMenu.NewsMenuData.NewsTabData mTabData;
    private TopNewsViewPager mViewPager;
    private ArrayList<NewsTabBean.TopNews> mTopNews;
    private TextView mTitleView;
    private CirclePageIndicator mIndicator;
    private RecyclerView mRecyclerView;
    private ArrayList<NewsTabBean.NewsData> mNewsList;
    private NewsAdapter mNewsAdapter;
    private View mRecyclerHeaderView;

    public TabDetailPager(Activity activity, NewsMenu.NewsMenuData.NewsTabData tabData) {
        super(activity);
        mTabData = tabData;
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pager_tab_detail, null, false);
//        mViewPager = view.findViewById(R.id.view_pager_top_news);
//        mTitleView = view.findViewById(R.id.text_view_title);
//        mIndicator = view.findViewById(R.id.circle_page_indicator);
        mRecyclerView = view.findViewById(R.id.list_news);
        // 初始化recycler view header
        mRecyclerHeaderView = LayoutInflater.from(mActivity).inflate(R.layout.recycler_view_header_news, null, false);
        mViewPager = mRecyclerHeaderView.findViewById(R.id.view_pager_top_news);
        mTitleView = mRecyclerHeaderView.findViewById(R.id.text_view_title);
        mIndicator = mRecyclerHeaderView.findViewById(R.id.circle_page_indicator);
        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtil.getCache(mActivity,GlobalConstants.SERVER_URL + "/zhbj" + mTabData.url);
        if (!TextUtils.isEmpty(cache)) {
            processData(cache);
        }
        getDataFromServer();
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(GlobalConstants.SERVER_URL + "/zhbj" + mTabData.url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("1011", "onSuccess: "+ result);

                processData(result);
                CacheUtil.setCache(mActivity, GlobalConstants.SERVER_URL + "/zhbj" + mTabData.url, result);
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
        // 头条新闻数据
        mTopNews = newsTabBean.data.topnews;
        if (mTopNews != null) {
            mViewPager.setAdapter(new TopNewsAdapter());
            mTitleView.setText(mTopNews.get(0).title);
            mIndicator.setViewPager(mViewPager);
            mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    String title = mTopNews.get(position).title;
                    mTitleView.setText(title);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            mIndicator.setSnap(true);
            // 页面初始化时需要更新indicator初始位置
            mIndicator.onPageSelected(0);
        }

        // 更新新闻列表
        mNewsList = newsTabBean.data.news;
        if (mNewsList != null) {
            mNewsAdapter = new NewsAdapter();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
            mRecyclerView.setAdapter(mNewsAdapter);

            // 设置头部
            mNewsAdapter.setHeaderView(mRecyclerHeaderView);
        }
    }

    /**
     * 头条新闻适配器
     */
    class TopNewsAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(mActivity);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageOptions options = new ImageOptions.Builder()
                    .setLoadingDrawableId(R.drawable.topnews_item_default)
                    .setUseMemCache(true)
                    .build();
            Log.d("1012", "instantiateItem: "+mTopNews.get(position).topimage);
            x.image().bind(view, mTopNews.get(position).topimage, options);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mTopNews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    /**
     * recycler view 适配器
     */
    public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        static final int TYPE_HEADER = 0;
        static final int TYPE_NORMAL = 1;

        private View mHeaderView;

        public void setHeaderView(View headerView) {
            mHeaderView = headerView;
            notifyItemInserted(0);
        }
        public View getHeaderView() {
            return mHeaderView;
        }

        @Override
        public int getItemViewType(int position) {
            if (mHeaderView == null) {
                return TYPE_NORMAL;
            }
            if (position == 0) {
                return TYPE_HEADER;
            }
            return TYPE_NORMAL;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder;
            if (mHeaderView != null && viewType == TYPE_HEADER) {
                // 这里初始化header视图
                holder = new ViewHolder(mHeaderView);
                return holder;
            }

            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_recycler_view_news, null, false);
            view.setOnClickListener(v -> {
                TextView textView = v.findViewById(R.id.text_view_title);
                textView.setTextColor(Color.GRAY);

            });
            holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemViewType(position) != TYPE_HEADER) {
                int pos = getRealPosition(holder);
                // 普通holder
                ImageOptions options = new ImageOptions.Builder()
                        .setLoadingDrawableId(R.drawable.news_pic_default)
                        .setUseMemCache(true)
                        .build();
                x.image().bind(holder.mImageView, mNewsList.get(pos).listimage, options);
                holder.mTextViewTitle.setText(mNewsList.get(pos).title);
                holder.mTextViewDate.setText(mNewsList.get(pos).pubdate);
                ((View)holder.mTextViewTitle.getParent()).setOnClickListener(v -> {
                    // 跳转web view
                    Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                    intent.putExtra("url", mNewsList.get(pos).url);
                    mActivity.startActivity(intent);
                });
            }
        }

        @Override
        public int getItemCount() {
            if (mHeaderView == null) {
                return mNewsList.size();
            } else {
                return mNewsList.size() + 1;
            }
        }

        public int getRealPosition(RecyclerView.ViewHolder holder) {
            int position = holder.getLayoutPosition();
            return mHeaderView == null ? position : position - 1;
        }

        /**
         * recycler view view holder
         */
        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView mImageView;
            private TextView mTextViewTitle;
            private TextView mTextViewDate;

            ViewHolder(View itemView) {
                super(itemView);
                if (itemView == mHeaderView) {
                    // 这里如果是header，不做处理直接返回
                    return;
                }
                mImageView = itemView.findViewById(R.id.image_left);
                mTextViewTitle = itemView.findViewById(R.id.text_view_title);
                mTextViewDate = itemView.findViewById(R.id.text_view_date);
            }
        }

    }

}
