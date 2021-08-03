package com.xiaojianma.stockanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private WebView mWebView;

    private WebSettings mWebSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                httpConnection();
//            }
//        }).start();
        initWebView();
    }

    private void httpConnection() {
        try {
            URL url = new URL("http://basic.10jqka.com.cn/evaluate/finance.txt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5 * 1000);
            connection.setReadTimeout(5 * 1000);
            connection.setRequestMethod("GET");
            // 得到sslContext对象，有两种情况：1.需要安全证书，2.不需要安全证书
            Log.e(TAG, "是否为https请求==" + (connection instanceof HttpsURLConnection));
            if (connection instanceof HttpsURLConnection) {// 判断是否为https请求
//            SSLContext sslContext = HttpsUtil.getSSLContextWithCer();
//              SSLContext sslContext = HttpsUtil.getSSLContextWithoutCer();
//            if (sslContext != null) {
//                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//                ((HttpsURLConnection) connection).setDefaultSSLSocketFactory(sslSocketFactory);
//                ((HttpsURLConnection) connection).setHostnameVerifier(HttpsUtil.hostnameVerifier);
//            }
            }
            int responseCode = connection.getResponseCode();
            Log.e(TAG, "responseCode==" + responseCode);
//            InputStream is = connection.getInputStream();
//            Log.e(TAG, "is==" + is);
//                is.close();
//            OutputStream out = connection.getOutputStream();
//            Log.e(TAG, "out==" + out);
//            if (responseCode == 200) {
//                InputStream is = connection.getInputStream();
//                Log.e(TAG, "is==" + is);
////                is.close();
//                OutputStream out = connection.getOutputStream();
//                Log.e(TAG, "out==" + out);
//
//            }
            Object content = connection.getContent();
            Log.i(TAG, "onCreate: content: " + content);
            connection.disconnect();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: url error: " + e.toString());
        }
    }

    /**
     * 初始化和设置webview
     */
    private void initWebView() {
        mWebView = findViewById(R.id.web_view);
        mWebView.loadUrl("http://stockpage.10jqka.com.cn/");
        mWebSettings = mWebView.getSettings();
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //支持js
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");
        //自适应屏幕
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        //自动缩放
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setSupportZoom(true);
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
        mWebView.setWebViewClient(new MyWeb());
    }

    //    go back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyWeb extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
                return false;
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

}