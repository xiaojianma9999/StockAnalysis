package com.xiaojianma.stockanalysis.okhttp.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OKHttp基本操作工具类
 */
public final class OKHttpUtil {

    private static final String TAG = "OKHttpUtil";

    //cookie存储
    private static ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();

    /**
     * 定义执行线程池，核心线程2，最大5，线程最大闲置60s，如果超过最大线程数，添加到等待队列，超过等待队列大小，直接拒绝任务。
     */
    private static final ExecutorService SERVICE = new ThreadPoolExecutor(2, 5,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadPoolExecutor.AbortPolicy());

    /**
     * 工具类私有化构造函数
     */
    private OKHttpUtil() {

    }


    public static void asyncGet(String cookie, String url, Callback callback) {
        cookieStore.clear();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {//这里可以做cookie传递，保存等操作
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        // 可以做保存cookies操作
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        // 加载新的cookies
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
        final Request request = new Request.Builder()
                .url(url)
//                .addHeader("Cookie", "uid=t4MMtWBxeb2jsz9XAwXMAg==; searchGuide=sg; spversion=20130314; user=MDpteF81NzYyNTA1NzQ6Ok5vbmU6NTAwOjU4NjI1MDU3NDo3LDExMTExMTExMTExLDQwOzQ0LDExLDQwOzYsMSw0MDs1LDEsNDA7MSwxMDEsNDA7MiwxLDQwOzMsMSw0MDs1LDEsNDA7OCwwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMSw0MDsxMDIsMSw0MDoxNjo6OjU3NjI1MDU3NDoxNjI3ODAwMDc3Ojo6MTYxODA1MDQyMDoyMjc5MjM6MDoxYjEzYTYyNjFjNzM1NDRkYjM4MTk5MDQ2NDk0MzQxMTE6ZGVmYXVsdF80OjA%3D; userid=576250574; u_name=mx_576250574; escapename=mx_576250574; ticket=7eaf746006e201e432653b88f239f9a1; utk=70747d5ae82201cabc28c2439378b322; Hm_lvt_78c58f01938e4d85eaf619eae71b4ed1=1627797713,1628004337,1628261661,1628315689; historystock=300919%7C*%7C002714%7C*%7C601318%7C*%7C002641; Hm_lpvt_78c58f01938e4d85eaf619eae71b4ed1=1628328214; v=Azg7_h7TrZX-tcERc7jymw-1Ce3JoZzz_gdwq3KphAcnPdbbGrFsu04VQDjB")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Host", "basic.10jqka.com.cn")
                .addHeader("Connection", "keep-alive")
                .addHeader("Pragma", "no-cache")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("Cookie", cookie)
//                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36")
                .get() //默认就是GET请求，可以不写
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public static Response syncGet(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
            Log.i(TAG, "syncGet: " + response.body().string());
        } catch (IOException e) {
            Log.e(TAG, "syncGet IOException: " + e.toString());
        }
        return response;
    }

    public static void post(String url, MediaType mediaType) {
//        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    public static String getCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie("cookie");
        Log.d(TAG, "yejian getCookie: "+ cookie);
        return cookie;
    }
}
