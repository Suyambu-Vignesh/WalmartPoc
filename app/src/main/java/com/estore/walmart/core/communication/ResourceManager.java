package com.estore.walmart.core.communication;

import android.os.Handler;
import android.os.Looper;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.utils.WalmartAppException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class ResourceManager {
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private static final int CORE_POOL_SIZE = 8;
    private static final int MAXIMUM_POOL_SIZE = 8;

    private ThreadPoolExecutor mDownloadThreadPool;
    private BlockingQueue<Runnable> mDownloadWorkQueue;
    private Queue<ResourceRequesterCommand> mResourceRequesterCommandQueue;
    private HashMap<String, Request> mRequestMap;
    private Handler mHandler;

    private static ResourceManager mResourceManager;

    public synchronized static ResourceManager getInstance() {
        if (mResourceManager == null) {
            mResourceManager = new ResourceManager();
        }

        return mResourceManager;
    }

    private ResourceManager() {
        mDownloadWorkQueue = new LinkedBlockingQueue<Runnable>();
        mResourceRequesterCommandQueue = new LinkedList<>();
        mRequestMap = new HashMap<>();
        mHandler = new Handler(Looper.getMainLooper());
        mDownloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mDownloadWorkQueue);
    }


    public void processRequest(Request request) {
        if (request == null) {
            throw new WalmartAppException(WalmartAppException.RESOURCE_CANNOT_BE_NULL);
        }

        if (mRequestMap.containsKey(request.getId())) {
            return;
        }

        mRequestMap.put(request.getId(), request);

        ResourceRequesterCommand resourceRequesterCommand = mResourceRequesterCommandQueue.poll();

        if (resourceRequesterCommand == null) {
            resourceRequesterCommand = new ResourceRequesterCommand();
        }
        resourceRequesterCommand.setRequest(request);

        WalmartApp.getAppObjectGraph().getLogHandler().d("Suyambu", "Executing Making request " + toString());
        mDownloadThreadPool.execute(resourceRequesterCommand.getCommand());
    }

    public void cancelRequest(String id) {
        if (id == null) {
            throw new WalmartAppException(WalmartAppException.RESOURCE_CANNOT_BE_NULL);
        }
    }

    public void cancelAllRequest() {
    }

    public void postInUIThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    public void cleanUpResource(String resourceId) {
        if (mRequestMap.containsKey(resourceId)) {
            mRequestMap.remove(resourceId);
        }
    }
}
