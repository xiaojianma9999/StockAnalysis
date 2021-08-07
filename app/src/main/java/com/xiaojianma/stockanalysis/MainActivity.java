package com.xiaojianma.stockanalysis;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaojianma.stockanalysis.okhttp.util.ExcelUtil;
import com.xiaojianma.stockanalysis.okhttp.util.FileUtil;
import com.xiaojianma.stockanalysis.okhttp.util.OKHttpUtil;
import com.xiaojianma.stockanalysis.okhttp.util.PermissionUtil;
import com.xiaojianma.stockanalysis.okhttp.util.TaskUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36";

    // 同花顺个股网网址
    private static final String STOCK_URL = "http://stockpage.10jqka.com.cn/";

    // 资产负债表url
    private static final String DEBT_URL = "http://basic.10jqka.com.cn/api/stock/export.php?export=debt&type=year&code=";

    // 利润表url
    private static final String BENEFIT_URL = "http://basic.10jqka.com.cn/api/stock/export.php?export=benefit&type=year&code=";

    // 现金流量表url
    private static final String CASH_URL = "http://basic.10jqka.com.cn/api/stock/export.php?export=cash&type=year&code=";

    // wps应用包名
    private static final String WPS_PKG1 = "cn.wps.moffice_eng";
    private static final String WPS_PKG = "com.huawei.filemanager";

    private WebView mWebView;

    private WebSettings mWebSettings;

    private String mCookie;

    // 等待三张表都
    private CountDownLatch countDownLatch;

    // 是否已经提示过股票代码有误
    private volatile boolean hasHint = false;

    // 股票代码
    private String stockNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtil.verifyStoragePermissions(this);
        initWebView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(final int featureId, @NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.get_year_report:
                downloadThreeTable();
                break;
            default:
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void downloadThreeTable() {
        hasHint = false;
        countDownLatch = new CountDownLatch(3);
        try {
            EditText stockNumText = findViewById(R.id.stock_num);
            stockNum = stockNumText.getText().toString().trim();
            Log.e(TAG, "yejian downloadThreeTable stockNum is: " + stockNum);
            if (TextUtils.isEmpty(stockNum)) {
                Log.e(TAG, "yejian downloadThreeTable stockNum is empty");
                Toast.makeText(MainActivity.this, R.string.stock_num_hint, Toast.LENGTH_SHORT).show();
                return;
            }
//            OKHttpUtil.asyncGet(mCookie, DEBT_URL + stockNum, getCallback());
//            OKHttpUtil.asyncGet(mCookie, BENEFIT_URL + stockNum, getCallback());
//            OKHttpUtil.asyncGet(mCookie, CASH_URL + stockNum, getCallback());
        } catch (Exception e) {
            Log.e(TAG, "yejian downloadThreeTable exception: " + e.toString());
            hintNumError();
        }
        TaskUtil.execute(new Runnable() {
            @Override
            public void run() {
//                try {
//                    countDownLatch.await();
//                } catch (InterruptedException e) {
//                    Log.e(TAG, "yejian await read excel exception: " + e.toString());
//                }
                // 储存下载文件的目录
                File storageDir = Environment.getExternalStorageDirectory();
                String path = storageDir + File.separator + "weimiao_learn" + File.separator + stockNum + "_debt_year.xls";
                ExcelUtil.readExcel(path);
            }
        });
        // 打开下载之后的文件
//        FileUtil.openFile(file, MainActivity.this);
    }

    private Callback getCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "yejian asyncGet onFailure: " + e.toString());
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "yejian response.isSuccessful: " + response.isSuccessful());
                if (!response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hintNumError();
                        }
                    });
                    return;
                }
                // 储存下载文件的目录
                File storageDir = Environment.getExternalStorageDirectory();
                Log.i(TAG, "yejian storageDir: " + storageDir.getAbsolutePath());
                File dir = new File(storageDir + File.separator + "weimiao_learn");
                // 如果目录不存在则创建目录
                if (!dir.exists()) {
                    boolean mkdirs = dir.mkdirs();
                    Log.i(TAG, "yejian mkdirs dir weimiao_learn: " + mkdirs);
                }
                String disposition = response.header("Content-Disposition");
                Log.i(TAG, "yejian disposition: " + disposition);
                if (TextUtils.isEmpty(disposition)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hintNumError();
                        }
                    });
                    return;
                }
                final String fileName = disposition.split(";")[1].split("=")[1];
                File file = new File(dir, fileName);
                if (file.exists()) {
                    boolean delete = file.delete();
                    Log.i(TAG, "yejian delete exist file: " + delete);
                }
                try (Sink sink = Okio.sink(file); BufferedSink bufferedSink = Okio.buffer(sink);) {
                    bufferedSink.writeAll(response.body().source());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, getString(R.string.debet_success, fileName), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAG, "yejian download " + fileName + " success");
                } catch (Exception e) {
                    Log.e(TAG, "yejian downloadBySink " + fileName + " error: " + e.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, getString(R.string.debet_fail, fileName), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                forceStopApp(MainActivity.this, WPS_PKG);
                countDownLatch.countDown();
            }
        };
    }

    private void hintNumError() {
        if (!hasHint) {
            Toast.makeText(MainActivity.this, R.string.stock_num_hint_need_correct, Toast.LENGTH_SHORT).show();
            hasHint = true;
        }
    }

    /**
     * 初始化和设置webView
     */
    private void initWebView() {
        mWebView = findViewById(R.id.web_view);
        mWebView.loadUrl(STOCK_URL);
        mWebView.setVisibility(View.GONE);

        mWebSettings = mWebView.getSettings();
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 设置js可以直接打开窗口，如window.open()，默认为false
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持js
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUserAgentString(USER_AGENT);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_analysis:
                downloadThreeTable();
            default:
                break;
        }
    }

    private class MyWeb extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url);
                return false;
            } else {
                // 非http或https请求，直接用浏览器打开
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //添加Cookie获取操作
            CookieManager cookieManager = CookieManager.getInstance();
            mCookie = cookieManager.getCookie(url);
            Log.i(TAG, "yejian url: " + url);
            Log.i(TAG, "yejian cookieStr: " + mCookie);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

    // 传入应用的包名即可kill掉应用
    private void forceStopApp(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        try {
            Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
            method.invoke(am, packageName);
        } catch (Exception e) {
            Log.e(TAG, "yejian forceStopApp: " + e.toString());
        }
    }
}