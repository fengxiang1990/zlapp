package com.zl.app.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    private static ExecutorService executorService = Executors
            .newSingleThreadExecutor();
    private static ExecutorService cacheService = Executors
            .newCachedThreadPool();

    private ThreadPool() {
    }

    public static ExecutorService getSingleThread() {

        return executorService;

    }

    public static ExecutorService getCacheService() {

        return cacheService;

    }
}
