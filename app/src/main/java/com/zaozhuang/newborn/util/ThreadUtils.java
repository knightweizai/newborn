package com.zaozhuang.newborn.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {

    private static ThreadPoolExecutor threadPoolExecutor;
    private static ExecutorService mSingleThreadExecutor;

    public static void init() {

        threadPoolExecutor = new ThreadPoolExecutor(3, 5, 1, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(100));

        /**
         * 单线程队列线程池
         */
        mSingleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public static ExecutorService getSingleThreadPoolExecutor(){
        return mSingleThreadExecutor;
    }
}
