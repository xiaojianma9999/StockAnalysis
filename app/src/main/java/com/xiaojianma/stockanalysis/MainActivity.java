package com.xiaojianma.stockanalysis;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.xiaojianma.stockanalysis.database.MyDatabaseHelper;
import com.xiaojianma.stockanalysis.okhttp.util.ExcelUtil;
import com.xiaojianma.stockanalysis.okhttp.util.FileUtil;
import com.xiaojianma.stockanalysis.okhttp.util.OKHttpUtil;
import com.xiaojianma.stockanalysis.okhttp.util.PermissionUtil;
import com.xiaojianma.stockanalysis.okhttp.util.TaskUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36";

    // ????????????????????????
    private static final String STOCK_URL = "http://stockpage.10jqka.com.cn/";

    // ???????????????url
    private static final String DEBT_URL = "http://basic.10jqka.com.cn/api/stock/export.php?export=debt&code=";

    // ?????????url
    private static final String BENEFIT_URL = "http://basic.10jqka.com.cn/api/stock/export.php?export=benefit&code=";

    // ???????????????url
    private static final String CASH_URL = "http://basic.10jqka.com.cn/api/stock/export.php?export=cash&code=";

    // wps????????????
    private static final String WPS_PKG1 = "cn.wps.moffice_eng";
    private static final String WPS_PKG = "com.huawei.filemanager";

    // ??????????????????????????????
    private static final String[] SELECTED = {"?????????", "????????????"};

    private ArrayAdapter<String> mAdapter;

    private WebView mWebView;

    // ??????i??????????????????????????????url
    private WebView mUrlWebView;

    private WebSettings mWebSettings;

    private String mCookie;

    // ??????????????????
    private volatile CountDownLatch countDownLatch;

    private volatile boolean noName = false;

    // ???????????????????????????????????????
    private volatile boolean hasHint = false;

    // ????????????
    private volatile String stockNum;

    // ????????????
    private volatile String stockName;

    private volatile File analysisFile;

    // ???????????????????????????????????????
    private AlertDialog mDialog;

    // ?????????handler??????
    private Handler handler = new MyHandler();

    private Map<String, String> stockMap = new ConcurrentHashMap<>();

    private MyDatabaseHelper dbHelper;

    private LinearLayout eighteenSteps;

    private LinearLayout indexStand;

    private Spinner mSpinner;

    // ???????????????
    private volatile boolean isSelf = false;

    // ???????????????01
    private volatile boolean isSelf01 = false;

    // ???????????????????????????????????????
    private static volatile boolean byYear = true;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new MyDatabaseHelper(this, "FinancialReport.db", null, 6);
        setContentView(R.layout.activity_main);
        eighteenSteps = findViewById(R.id.stock_analysis);
        indexStand = findViewById(R.id.index_standard);
        mSpinner = findViewById(R.id.spinner);
        initSpinner();
        PermissionUtil.verifyStoragePermissions(this);
        mWebView = findViewById(R.id.web_view);
        mUrlWebView = findViewById(R.id.url_web_view);
        initWebView(mWebView);
        initWebView(mUrlWebView);
        mUrlWebView.setWebViewClient(new WebViewClient());
    }

    private void initSpinner() {
        //??????????????????ArrayAdapter????????????
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SELECTED);

        //???????????????????????????
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //???adapter ?????????spinner???
        mSpinner.setAdapter(mAdapter);

        //????????????Spinner????????????
        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //???????????????
        mSpinner.setVisibility(View.VISIBLE);
    }

    private static class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            byYear = arg2 == 0;
        }

        public void onNothingSelected(AdapterView<?> arg0) {
            byYear = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PermissionUtil.REQUEST_EXTERNAL_STORAGE) {
            Toast.makeText(this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(final int featureId, @NonNull MenuItem item) {
        String srcFileName;
        String dstFilePath;
        String basePath = FileUtil.getBasePath("");
        File baseFile = new File(basePath);
        if (!baseFile.exists()) {
            baseFile.mkdirs();
        }
        File dstFile;
        switch (item.getItemId()) {
            case R.id.eighteen_step_analysis_self:
                isSelf = true;
                isSelf01 = false;
                setViewVisible(indexStand, View.GONE);
                setViewVisible(mUrlWebView, View.GONE);
                setViewVisible(eighteenSteps, View.VISIBLE);
                setActionBar(R.string.eighteen_step_analysis_self);
                break;
            case R.id.eighteen_step_analysis_self01:
                isSelf = false;
                isSelf01 = true;
                setViewVisible(indexStand, View.GONE);
                setViewVisible(mUrlWebView, View.GONE);
                setViewVisible(eighteenSteps, View.VISIBLE);
                setActionBar(R.string.eighteen_step_analysis_self01);
                break;
            case R.id.eighteen_step_analysis:
                isSelf = false;
                isSelf01 = false;
                setViewVisible(indexStand, View.GONE);
                setViewVisible(mUrlWebView, View.GONE);
                setViewVisible(eighteenSteps, View.VISIBLE);
                setActionBar(R.string.eighteen_step_analysis);
                break;
            case R.id.links:
                setViewVisible(eighteenSteps, View.GONE);
                setViewVisible(indexStand, View.GONE);
                setViewVisible(mUrlWebView, View.VISIBLE);
                if (mUrlWebView != null) {
                    mUrlWebView.loadUrl("http://rongming.mikecrm.com/pfCyxZQ");
                }
                setActionBar(R.string.links);
                break;
            case R.id.menu_index_standard:
                setViewVisible(eighteenSteps, View.GONE);
                setViewVisible(mUrlWebView, View.GONE);
                setViewVisible(indexStand, View.VISIBLE);
                setActionBar(R.string.view_index_standard);
                break;
            case R.id.i_wencai:
                setViewVisible(eighteenSteps, View.GONE);
                setViewVisible(indexStand, View.GONE);
                setViewVisible(mUrlWebView, View.VISIBLE);
                if (mUrlWebView != null) {
                    mUrlWebView.loadUrl("http://www.iwencai.com/?allow_redirect=false");
                }
                setActionBar(R.string.view_i_wencai);
                break;
            case R.id.i_wencai_new:
                setViewVisible(eighteenSteps, View.GONE);
                setViewVisible(indexStand, View.GONE);
                setViewVisible(mUrlWebView, View.VISIBLE);
                if (mUrlWebView != null) {
                    mUrlWebView.loadUrl("http://www.iwencai.com/unifiedwap/home/index");
                }
                setActionBar(R.string.view_i_wencai_new);
                break;
            case R.id.ge_gu_wang:
                setViewVisible(eighteenSteps, View.GONE);
                setViewVisible(indexStand, View.GONE);
                setViewVisible(mUrlWebView, View.VISIBLE);
                if (mUrlWebView != null) {
                    mUrlWebView.loadUrl("http://stockpage.10jqka.com.cn/");
                }
                setActionBar(R.string.view_ge_gu_wang);
                break;
            case R.id.view_debt_seven_step:
                dstFilePath = basePath + File.separator + "?????????????????????7??????.xlsx";
                dstFile = new File(dstFilePath);
                srcFileName = "?????????-?????????????????????7??????-?????????.xlsx";
                if (!dstFile.exists()) {
                    FileUtil.copy(srcFileName, dstFile, this);
                }
                FileUtil.openFile(dstFile, this);
                break;
            case R.id.view_case_mind_map:
                dstFilePath = basePath + File.separator + "2.0??????-?????????????????????????????????(11-20).xlsx";
                dstFile = new File(dstFilePath);
                srcFileName = "2.0??????-?????????????????????????????????(11-20).xlsx";
                if (!dstFile.exists()) {
                    FileUtil.copy(srcFileName, dstFile, this);
                }
                FileUtil.openFile(dstFile, this);
                break;
            case R.id.view_big_gift:
                dstFilePath = basePath + File.separator + "??????????????? .xlsx";
                dstFile = new File(dstFilePath);
                srcFileName = "??????????????? .xlsx";
                if (!dstFile.exists()) {
                    FileUtil.copy(srcFileName, dstFile, this);
                }
                FileUtil.openFile(dstFile, this);
                break;
            default:
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void setActionBar(int title) {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    private void setViewVisible(View view, int visible) {
        if (view != null) {
            view.setVisibility(visible);
        }
    }

    private synchronized void downloadThreeTable() {
        hasHint = false;
        countDownLatch = new CountDownLatch(3);
        noName = false;
        try {
            stockName = "";
            EditText stockNumText = findViewById(R.id.stock_num);
            stockNum = stockNumText.getText().toString().trim();
            Log.i(TAG, "yejian downloadThreeTable stockNum is: " + stockNum);
            if (TextUtils.isEmpty(stockNum)) {
                Log.e(TAG, "yejian downloadThreeTable stockNum is empty");
                Toast.makeText(MainActivity.this, R.string.stock_num_hint, Toast.LENGTH_SHORT).show();
                return;
            }
            stockMap.clear();
            mWebView.loadUrl("http://stockpage.10jqka.com.cn/" + stockNum);
//            mWebView.loadUrl("http://news.10jqka.com.cn/public/index_keyboard_" + stockNum + "_0_5_jsonp.html");
            boolean delete = FileUtil.hasAnalysisAndDelete(stockNum);
            Log.i(TAG, "yejian downloadThreeTable delete " + stockNum + " dir " + delete);
//            OKHttpUtil.asyncGet(mCookie, "http://news.10jqka.com.cn/public/index_keyboard_" + stockNum + "_0_5_jsonp.html", getCallback());
            String type = byYear ? "&type=year" : "&type=report";
            OKHttpUtil.asyncGet(mCookie, DEBT_URL + stockNum + type, getCallback());
            OKHttpUtil.asyncGet(mCookie, BENEFIT_URL + stockNum + type, getCallback());
            OKHttpUtil.asyncGet(mCookie, CASH_URL + stockNum + type, getCallback());

            TaskUtil.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        int time = 0;
                        while (TextUtils.isEmpty(stockMap.get(stockNum))) {
                            if (noName || time >= 5000) {
                                break;
                            }
                            Thread.sleep(200);
                            time += 200;
                        }
                        String type = byYear ? "year" : "report";
                        analysisFile = FileUtil.getAnalysisFile(stockNum, type, stockMap.get(stockNum));
                        String suffixes = byYear ? ".xlsx" : "-report.xlsx";
                        String srcPath = isSelf ? "18?????????????????????????????????????????????????????????2020?????????" + suffixes : (isSelf01 ? "2020????????????18?????????????????????????????????????????????-self01" + suffixes :
                                "18?????????????????????.xlsx");
                        FileUtil.copy(srcPath, analysisFile, MainActivity.this);
//                        ExcelUtil.updateExcel(analysisFile, FileUtil.getDebtFile(stockNum), FileUtil.getBenefitFile(stockNum), FileUtil.getCashFile(stockNum));
                        ExcelUtil.updateExcelByPOI(analysisFile, FileUtil.getDebtFile(stockNum, byYear), FileUtil.getBenefitFile(stockNum, byYear), FileUtil.getCashFile(stockNum, byYear));
                        handler.obtainMessage().sendToTarget();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "yejian await read excel exception: " + e.toString());
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "yejian downloadThreeTable exception: " + e.toString());
            hintNumError();
        }
    }

    private Callback getCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "yejian asyncGet onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.i(TAG, "yejian response body: " + response.body().toString());
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
                // ???????????????????????????
                File storageDir = Environment.getExternalStorageDirectory();
                Log.i(TAG, "yejian storageDir: " + storageDir.getAbsolutePath());
                File dir = new File(storageDir + File.separator + "weimiao_learn" + File.separator + stockNum);
                // ????????????????????????????????????
                if (!dir.exists()) {
                    int count = 0;
                    while (!dir.mkdirs() && count < 5) {
                        Log.i(TAG, "yejian mkdirs dir weimiao_learn: " + false);
                        PermissionUtil.verifyStoragePermissions(MainActivity.this);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "?????????" + dir + "????????????", Toast.LENGTH_SHORT).show();
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "?????????" + dir + "????????????, ???????????????????????????" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        count++;
                    }
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
                            Toast.makeText(MainActivity.this, getString(R.string.debt_success, fileName), Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAG, "yejian download " + fileName + " success");
                } catch (Exception e) {
                    Log.e(TAG, "yejian downloadBySink " + fileName + " error: " + e.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, getString(R.string.debt_fail, fileName) + ": " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
//                (MainActivity.this, WPS_PKG);
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
     * ??????????????????webView
     */
    private void initWebView(WebView webView) {
        webView.loadUrl(STOCK_URL);
        webView.setVisibility(View.GONE);

        mWebSettings = webView.getSettings();
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // ??????js??????????????????????????????window.open()????????????false
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //??????js
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUserAgentString(USER_AGENT);
        //???????????????
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        //????????????
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setSupportZoom(true);
        //????????????????????????
        webView.requestFocusFromTouch();
        if (webView == mWebView) {
            webView.setWebViewClient(new MyWeb());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
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
                // ???http???https?????????????????????????????????
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //??????Cookie????????????
            CookieManager cookieManager = CookieManager.getInstance();
            mCookie = cookieManager.getCookie(url);
            Log.i(TAG, "yejian url: " + url);
            Log.i(TAG, "yejian cookieStr: " + mCookie);
            if (stockNum == null || !url.contains(stockNum)) {
                return;
            }
            mWebView.evaluateJavascript("document.title", value -> {
                Log.i(TAG, "yejian document.title: " + value);
                if (value != null) {
                    int first = value.indexOf('(');
                    int second = value.indexOf(')');
                    Log.i(TAG, "yejian evaluateJavascript first index: " + first);
                    Log.i(TAG, "yejian evaluateJavascript second index: " + first);
                    if (first != -1) {
                        String stockName = value.substring(0, first);
                        stockName = stockName.replace("\"", "");
                        if (second != -1) {
                            String stockNum = value.substring(first + 1, second);
                            stockMap.put(stockNum, stockName);
                            Log.i(TAG, "yejian stockNum: " + stockNum + ", stockName: " + stockName);
                        }
                    }
                    noName = (first == -1 && second == -1);
                    Log.i(TAG, "yejian stockNum: " + stockNum + ", noName: " + noName);
                }
                countDownLatch.countDown();
            });
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }

//    // ???????????????????????????kill?????????
//    private void forceStopApp(Context context, String packageName) {
//        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//        try {
//            Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
//            method.invoke(am, packageName);
//        } catch (Exception e) {
//            Log.e(TAG, "yejian forceStopApp: " + e.toString());
//        }
//    }

    private final class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            mDialog = new AlertDialog.Builder(MainActivity.this).setNegativeButton(R.string.open_directly, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FileUtil.openFile(analysisFile, MainActivity.this);
                    dialog.dismiss();
                }
            }).setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    FileUtil.shareFile(analysisFile, MainActivity.this);
                    dialog.dismiss();
                }
            }).setTitle(R.string.eighteen_step_analysis_table).setMessage(R.string.description).create();
            buildDialogStyle(mDialog);
            mDialog.show();
        }
    }

    /**
     * ????????????
     *
     * @param dialog ??????
     */
    private static void buildDialogStyle(AlertDialog dialog) {
        try {
            //TODO mMessageView mTitleView??????????????????AlertController?????????????????????
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mController = mAlert.get(dialog);
            //??????????????????Text
            Field mMessage = mController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mController);
            mMessageView.setTextSize(18);
            mMessageView.setTextColor(Color.GREEN);//title???????????????``???

            //??????????????????View?????????????????????
            Field mTitle = mController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mController);
            mTitleView.setTextSize(18);
            mTitleView.setTextColor(Color.RED);

            //???????????????
            Button pButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);//????????????
            pButton.setTextColor(Color.RED);

            //????????????
            Button nButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);//??????
            nButton.setTextColor(Color.BLUE);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}