package com.magicsu.android.zhbj.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.magicsu.android.zhbj.R;

import java.net.URL;


/**
 * 新闻详情页
 */
public class NewsDetailActivity extends AppCompatActivity {

    private LinearLayout mButtonGroup;
    private ImageButton mBackButton;
    private ImageButton mTextSizeButton;
    private ImageButton mShareButton;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private AlertDialog mDialog;
    private int mTempWhich; // dialog当前选中的选项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        initUI();
        initData();
    }

    private void initUI() {
        mButtonGroup = findViewById(R.id.button_group_control);
        mBackButton = findViewById(R.id.button_back);
        mTextSizeButton = findViewById(R.id.button_text_size);
        mShareButton = findViewById(R.id.button_share);
        mWebView = findViewById(R.id.web_view_detail);
        mProgressBar = findViewById(R.id.progress_bar_loading);

        ImageButton headButton = findViewById(R.id.button_head);
        mButtonGroup.setVisibility(View.VISIBLE);
        headButton.setVisibility(View.GONE);
        mBackButton.setVisibility(View.VISIBLE);

        mBackButton.setOnClickListener(v -> finish());
        mShareButton.setOnClickListener(v -> {

        });
        mTextSizeButton.setOnClickListener(v -> {
            showChooseDialog();
        });
    }

    /**
     * 显示选择字体弹窗
     */
    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体设置");
        String[] items = new String[] {"超大号字体","大号字体","正常字体","小号字体", "超小号字体"};
        int currentSize = mWebView.getSettings().getTextZoom();
        int currentPos = getPositionByTextSize(currentSize);

        builder.setSingleChoiceItems(items, currentPos, (dialog, which) -> {
            mTempWhich = which;
        });
        // 确定按钮
        builder.setPositiveButton("确定", (dialog, which) -> {
                WebSettings settings = mWebView.getSettings();

                int size = getTextSizeByPosition(mTempWhich);
                settings.setTextZoom(size);
        });
        // 取消按钮
        builder.setNegativeButton("取消", (dialog, which) -> {
            if (dialog != null)
                dialog.dismiss();
        });
        mDialog = builder.create();
        mDialog.show();

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initData() {
        String url = getIntent().getStringExtra("url");

        WebSettings settings = mWebView.getSettings();

        mWebView.loadUrl(url);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("URL", url);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                // super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    /**
     * 通过dialog选择的item返回字体大小
     * @param position 所选择的item
     * @return 字号
     */
    private int getTextSizeByPosition(int position) {
        int size;
        switch (position) {
            case 0:
                // 超大字体
                size = 200;
                break;
            case 1:
                size = 150;
                break;
            case 3:
                size = 75;
                break;
            case 4:
                size = 50;
                break;
            case 2:
            default:
                size = 100;
        }
        return size;
    }

    /**
     * 通过字号返回对应的item位置
     * @param size 字号
     * @return 位置
     */
    private int getPositionByTextSize(int size) {
        int position;
        switch (size) {
            case 200:
                position = 0;
                break;
            case 150:
                position = 1;
                break;
            case 75:
                position = 3;
                break;
            case 50:
                position = 4;
                break;
            case 100:
            default:
                position = 2;
        }
        return position;
    }

    @Override
    protected void onDestroy() {
        if( mWebView!=null) {

            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }

            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.loadUrl("about:blank");
            mWebView.removeAllViews();
            mWebView.destroy();

        }
        super.onDestroy();

    }
}
