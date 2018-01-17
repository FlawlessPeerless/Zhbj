package com.magicsu.android.zhbj.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.magicsu.android.zhbj.R;
import com.magicsu.android.zhbj.activity.MainActivity;
import com.magicsu.android.zhbj.domain.NewsMenu;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 侧边栏fragment
 * Created by admin on 2018/1/16.
 */

public class LeftMenuFragment extends BaseFragment {

    private ListView mListView;
    private ArrayList<NewsMenu.NewsMenuData> mNewsMenuData;
    private int mCurrentPos;
    private LeftMenuAdapter mLeftMenuAdapter;

    @Override
    public View initView() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_left_menu, null, false);

        mListView = view.findViewById(R.id.list_view_left);
        return view;
    }

    @Override
    public void initData() {
    }


    // 设置侧边栏数据
    public void setMenuData(ArrayList<NewsMenu.NewsMenuData> data) {
        // 更新页面
        mNewsMenuData = data;
        // 设置adapter
        mLeftMenuAdapter = new LeftMenuAdapter();
        mListView.setAdapter(mLeftMenuAdapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            mCurrentPos = position;
            mLeftMenuAdapter.notifyDataSetChanged();
            toggle();
        });
    }

    class LeftMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNewsMenuData.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_left_menu, null, false);
            }
            TextView textView = convertView.findViewById(R.id.item_text);
            textView.setText(mNewsMenuData.get(position).title);

            if (position == mCurrentPos) {
                textView.setEnabled(true);
            } else {
                textView.setEnabled(false);
            }

            return convertView;
        }
    }
}
