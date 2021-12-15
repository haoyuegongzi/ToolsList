package com.mydemo.toolslist.h5;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;
import com.mydemo.toolslist.BaseActivity;
import com.mydemo.toolslist.R;
import com.mydemo.toolslist.tools.WebViewUtils;

public class H5Activity extends BaseActivity {

    private String url = "www.ifemg.com";

    private AgentWeb mAgentWeb;
    private WebView mWebView;
    private RelativeLayout rlLayout;
    private TextView tvUatmob, tvUatpc, tvJueJin, tvIFeng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);

        rlLayout = findViewById(R.id.rlLayout);
        tvUatmob = findViewById(R.id.tvUatmob);
        tvUatpc = findViewById(R.id.tvUatpc);
        tvJueJin = findViewById(R.id.tvJueJin);
        tvIFeng = findViewById(R.id.tvIFeng);
        mWebView = findViewById(R.id.mWebView);

        initView();
        WebViewUtils.setViewClient(mWebView);
        WebViewUtils.setChromeClient(mWebView, this);
    }

    private void initView() {
        mWebView.loadUrl(url);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(rlLayout, new RelativeLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .createAgentWeb()
                .ready()
                .go(url);
        FrameLayout frameLayout = mAgentWeb.getWebCreator().getWebParentLayout();
        frameLayout.setBackgroundColor(Color.BLACK);


    }

    WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("TAGTAG", "加载链接: " + url);
        }

        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };

    WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.e("TAGTAG", "加载进度: " + newProgress);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAgentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
        AgentWebConfig.clearDiskCache(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!mAgentWeb.back()) {
            finish();
        }
    }

    //监听BACK按键，有可以返回的页面时返回页面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            onBackPressed();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}