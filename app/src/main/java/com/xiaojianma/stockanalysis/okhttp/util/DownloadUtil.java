package com.xiaojianma.stockanalysis.okhttp.util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * 文件下载工具类（单例模式）
 */
public class DownloadUtil {

    private static final String TAG = "DownloadUtil";

    private OkHttpClient okHttpClient;

    public interface OnDownloadListener {

        /**
         * 下载成功之后的文件
         */
        void onDownloadSuccess(File file);

        /**
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载异常信息
         */

        void onDownloadFailed(Exception e);
    }

    public void downloadBySink(final String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        //异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    sink = Okio.sink(file);
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    Log.e(TAG, "downloadBySink error: " + e.toString());
                    listener.onDownloadFailed(e);
                } finally {
                    if (bufferedSink != null) {
                        bufferedSink.close();
                    }
                }
            }
        });
    }

    public void downloadByAsync(final String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        //异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[4096];
                int len = 0;
                FileOutputStream fos = null;

                // 储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);

                try {
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    int size = 0;
                    long total = response.body().contentLength();
                    while ((size = is.read(buf)) != -1) {
                        len += size;
                        fos.write(buf, 0, size);
                        int process = (int) Math.floor(((double) len / total) * 100);
                        // 控制台打印文件下载的百分比情况
                        listener.onDownloading(process);
                    }

                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    Log.e(TAG, "downloadByAsync error: " + e.toString());
                    listener.onDownloadFailed(e);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        });
    }

    public void downloadBySync(final String url, final String destFileDir, final String destFileName) {
        Request request = new Request.Builder().url(url).build();
        // 异步请求
        Response response = null;
        InputStream is = null;
        byte[] buf = new byte[4096];
        int len = 0;
        FileOutputStream fos = null;

        try {
            response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {

                // 储存下载文件的目录
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);

                is = response.body().byteStream();
                fos = new FileOutputStream(file);
                int size = 0;
                long total = response.body().contentLength();
                while ((size = is.read(buf)) != -1) {
                    len += size;
                    fos.write(buf, 0, size);
                    int process = (int) Math.floor(((double) len / total) * 100);
                    Log.i(TAG, "下载进度: " + process);
                }
                fos.flush();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            Log.e(TAG, "downloadBySync error: " + e.toString());
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
