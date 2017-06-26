package com.estore.walmart.core.communication;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;

import com.estore.walmart.views.AnimatedImageView;

import java.net.URL;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class ImageDownloadManager extends BaseResourceManager {
    static final int DOWNLOAD_FAILED = -1;
    static final int DOWNLOAD_STARTED = 1;
    static final int DOWNLOAD_COMPLETE = 2;
    static final int DECODE_STARTED = 3;
    static final int TASK_COMPLETE = 4;

    private static final int IMAGE_CACHE_SIZE = 1024 * 1024 * 4;
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private final LruCache<URL, byte[]> mPhotoCache;
    private final BlockingQueue<Runnable> mDownloadWorkQueue;
    private final BlockingQueue<Runnable> mDecodeWorkQueue;
    private final Queue<ImageRenderCommand> mImageRenderQueue;
    private final ThreadPoolExecutor mDownloadThreadPool;
    private final ThreadPoolExecutor mDecodeThreadPool;

    private Handler mHandler;

    private static ImageDownloadManager sInstance = null;

    static {
        sInstance = new ImageDownloadManager();
    }

    /**
     * Constructs the work queues and thread pools used to download and decode images.
     */
    private ImageDownloadManager() {
        mDownloadWorkQueue = new LinkedBlockingQueue<Runnable>();
        mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();
        mImageRenderQueue = new LinkedBlockingQueue<ImageRenderCommand>();
        mDownloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mDownloadWorkQueue);
        mDecodeThreadPool = new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mDecodeWorkQueue);
        mPhotoCache = new LruCache<URL, byte[]>(IMAGE_CACHE_SIZE) {

            @Override
            protected int sizeOf(URL paramURL, byte[] paramArrayOfByte) {
                return paramArrayOfByte.length;
            }
        };
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                ImageRenderCommand command = (ImageRenderCommand) inputMessage.obj;
                AnimatedImageView localView = command.getImageView();
                if (localView != null) {
                    URL localURL = localView.getLocation();
                    if (command.getImageURL() == localURL)
                        switch (inputMessage.what) {
                            case DOWNLOAD_STARTED:
                            case DOWNLOAD_COMPLETE:
                            case DECODE_STARTED:
                                localView.setStatusResource(localView.getAlternativeResourceId());
                                break;
                            case TASK_COMPLETE:
                                localView.setImageBitmap(command.getImage());
                                recycleTask(command);
                                break;
                            case DOWNLOAD_FAILED:
                                localView.setStatusResource(localView.getAlternativeResourceId());
                                recycleTask(command);
                                break;
                            default:
                                super.handleMessage(inputMessage);
                        }
                }
            }
        };
    }

    /**
     * Returns the ImageDownloadManager object
     *
     * @return The global ImageDownloadManager object
     */
    public static ImageDownloadManager getInstance() {
        if (sInstance == null) {
            sInstance = new ImageDownloadManager();
        }
        return sInstance;
    }

    /**
     * Handles state messages for a particular task object
     *
     * @param command A task object
     * @param state   The state of the task
     */
    @SuppressLint("HandlerLeak")
    public void handleState(ImageRenderCommand command, int state) {
        switch (state) {
            case TASK_COMPLETE:
                if (command.isCacheEnabled()) {
                    mPhotoCache.put(command.getImageURL(), command.getByteBuffer());
                }
                Message completeMessage = mHandler.obtainMessage(state, command);
                completeMessage.sendToTarget();
                break;
            case DOWNLOAD_COMPLETE:
                mDecodeThreadPool.execute(command.getPhotoDecodeRunnable());
            default:
                mHandler.obtainMessage(state, command).sendToTarget();
                break;
        }

    }

    /**
     * Cancels all Threads in the ThreadPool
     */
    public static void cancelAll() {
        ImageRenderCommand[] commandArray = new ImageRenderCommand[sInstance.mDownloadWorkQueue.size()];

        sInstance.mDownloadWorkQueue.toArray(commandArray);
        int length = commandArray.length;
        synchronized (sInstance) {
            for (int taskArrayIndex = 0; taskArrayIndex < length; taskArrayIndex++) {
                Thread thread = commandArray[taskArrayIndex].mThreadThis;
                if (null != thread) {
                    thread.interrupt();
                }
            }
        }
    }

    /**
     * Method to stops a download Thread and removes it from the threadpool
     *
     * @param imageRenderCommand The download task associated with the Thread
     * @param pictureURL         The URL being downloaded
     */
    static public void removeDownload(ImageRenderCommand imageRenderCommand, URL pictureURL) {
        if (imageRenderCommand != null && imageRenderCommand.getImageURL().equals(pictureURL)) {
            synchronized (sInstance) {
                Thread thread = imageRenderCommand.getCurrentThread();
                if (null != thread)
                    thread.interrupt();
            }
            sInstance.mDownloadThreadPool.remove(imageRenderCommand.getHTTPDownloadRunnable());
        }
    }

    /**
     * Starts an image download and decode
     *
     * @param imageView The ImageView that will get the resulting Bitmap
     * @param cacheFlag Determines if caching should be used
     * @return The task instance that will handle the work
     */
    static public ImageRenderCommand startDownload(AnimatedImageView imageView, boolean cacheFlag) {
        ImageRenderCommand downloadCommand = sInstance.mImageRenderQueue.poll();
        if (null == downloadCommand) {
            downloadCommand = new ImageRenderCommand();
        }
        downloadCommand.initializeDownloaderTask(ImageDownloadManager.sInstance, imageView, cacheFlag);

        downloadCommand.setByteBuffer(sInstance.mPhotoCache.get(downloadCommand.getImageURL()));
        if (null == downloadCommand.getByteBuffer()) {
            sInstance.mDownloadThreadPool.execute(downloadCommand.getHTTPDownloadRunnable());
            imageView.setStatusResource(imageView.getAlternativeResourceId());
        } else {
            sInstance.handleState(downloadCommand, DOWNLOAD_COMPLETE);
        }
        return downloadCommand;
    }

    /**
     * Method to recyle the command in
     * the queue.
     *
     * @param downloadCommand The command to recycle
     */
    void recycleTask(ImageRenderCommand downloadCommand) {
        downloadCommand.recycle();
        mImageRenderQueue.offer(downloadCommand);
    }
}

