package com.magicsu.android.zhbj.domain;

import java.util.ArrayList;

/**
 * 分类信息封装
 * Created by admin on 2018/1/17.
 */

public class NewsMenu {
    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<NewsMenuData> data;

    /**
     * 侧边栏对象
     */
    public class NewsMenuData {
        public int id;
        public String title;
        public int type;
        public ArrayList<NewsTabData> children;

        /**
         * 页签对象
         */
        public class NewsTabData {
            public int id;
            public String title;
            public int type;
            public String url;
        }
    }
}
