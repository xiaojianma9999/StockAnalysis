package com.xiaojianma.stockanalysis.okhttp.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class TaskUtil {

    private static final String TAG = "TaskUtil";

    /**
     * 定义执行线程池，核心线程2，最大5，线程最大闲置60s，如果超过最大线程数，添加到等待队列，超过等待队列大小，直接拒绝任务。
     */
    private static final ExecutorService SERVICE = new ThreadPoolExecutor(2, 5,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadPoolExecutor.AbortPolicy());

    private TaskUtil() {

    }

    public static void execute(Runnable runnable) {
        SERVICE.execute(runnable);
    }
}
