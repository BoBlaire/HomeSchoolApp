package com.example.gfgapp.misc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BackgroundTaskManager {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static <T> Future<T> submitTask(Callable<T> task) {
        return executorService.submit(task);
    }
}
