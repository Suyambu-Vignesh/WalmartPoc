package com.estore.walmart.core.communication;

import com.estore.walmart.views.AnimatedImageView;


import android.graphics.Bitmap;

import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class ImageRenderCommand implements
        ImageDownloader.TaskRunnableDownloadMethods, ImageDecoder.TaskRunnableDecodeMethods {

    private WeakReference<AnimatedImageView> mImageWeakRef;

    private URL mImageURL;

    private int mTargetHeight;
    private int mTargetWidth;
    private boolean mCacheEnabled;
    private Runnable mDownloadRunnable;
    private Runnable mDecodeRunnable;
    private Bitmap mDecodedImage;

    public Thread mThreadThis;
    public byte[] mImageBuffer;
    private Thread mCurrentThread;
    private static ImageDownloadManager sImageDownloadManager;

    ImageRenderCommand() {
        mDownloadRunnable = new ImageDownloader(this);
        mDecodeRunnable = new ImageDecoder(this);
        sImageDownloadManager = ImageDownloadManager.getInstance();
    }

    /**
     * Method to initializes the Task
     *
     * @param imageDownloadManager A ThreadPool object
     * @param photoView    An ImageView instance that shows the downloaded image
     * @param cacheFlag    Whether caching is enabled
     */
    void initializeDownloaderTask(
            ImageDownloadManager imageDownloadManager,
            AnimatedImageView photoView,
            boolean cacheFlag) {
        sImageDownloadManager = imageDownloadManager;
        mImageURL = photoView.getLocation();
        mImageWeakRef = new WeakReference<AnimatedImageView>(photoView);
        mCacheEnabled = cacheFlag;
        mTargetWidth = photoView.getWidth();
        mTargetHeight = photoView.getHeight();
    }

    /**
     * Recycles an PhotoTask object before it's put back into the pool. One reason to do
     * this is to avoid memory leaks.
     */
    void recycle() {
        if (null != mImageWeakRef) {
            mImageWeakRef.clear();
            mImageWeakRef = null;
        }
        mImageBuffer = null;
        mDecodedImage = null;
    }

    // Implements HTTPDownloaderRunnable.getByteBuffer
    @Override
    public byte[] getByteBuffer() {
        return mImageBuffer;
    }

    @Override
    public int getTargetWidth() {
        return mTargetWidth;
    }

    @Override
    public int getTargetHeight() {
        return mTargetHeight;
    }

    boolean isCacheEnabled() {
        return mCacheEnabled;
    }

    @Override
    public URL getImageURL() {
        return mImageURL;
    }

    @Override
    public void setByteBuffer(byte[] imageBuffer) {
        mImageBuffer = imageBuffer;
    }

    void handleState(int state) {
        sImageDownloadManager.handleState(this, state);
    }

    Bitmap getImage() {
        return mDecodedImage;
    }

    Runnable getHTTPDownloadRunnable() {
        return mDownloadRunnable;
    }

    Runnable getPhotoDecodeRunnable() {
        return mDecodeRunnable;
    }

    public AnimatedImageView getImageView() {
        if (null != mImageWeakRef) {
            return mImageWeakRef.get();
        }
        return null;
    }

    /*
     * Method which return the Thread that this Task is running on. The method must first get a
     * lock on a static field, in this case the ThreadPool singleton. The lock is needed because the
     * Thread object reference is stored in the Thread object itself, and that object can be
     * changed by processes outside of this app.
     */
    public Thread getCurrentThread() {
        synchronized (sImageDownloadManager) {
            return mCurrentThread;
        }
    }

    /*
     * Sets the identifier for the current Thread. This must be a synchronized operation; see the
     * notes for getCurrentThread()
     */
    public void setCurrentThread(Thread thread) {
        synchronized (sImageDownloadManager) {
            mCurrentThread = thread;
        }
    }

    // Implements ImageCoderRunnable.setImage()
    @Override
    public void setImage(Bitmap decodedImage) {
        mDecodedImage = decodedImage;
    }

    @Override
    public void setDownloadThread(Thread currentThread) {
        setCurrentThread(currentThread);
    }

    @Override
    public void handleDownloadState(int state) {
        int outState;

        switch (state) {
            case ImageDownloader.HTTP_STATE_COMPLETED:
                outState = ImageDownloadManager.DOWNLOAD_COMPLETE;
                break;
            case ImageDownloader.HTTP_STATE_FAILED:
                outState = ImageDownloadManager.DOWNLOAD_FAILED;
                break;
            default:
                outState = ImageDownloadManager.DOWNLOAD_STARTED;
                break;
        }
        handleState(outState);
    }

    @Override
    public void setImageDecodeThread(Thread currentThread) {
        setCurrentThread(currentThread);
    }

    @Override
    public void handleDecodeState(int state) {
        int outState;

        switch (state) {
            case ImageDecoder.DECODE_STATE_COMPLETED:
                outState = ImageDownloadManager.TASK_COMPLETE;
                break;
            case ImageDecoder.DECODE_STATE_FAILED:
                outState = ImageDownloadManager.DOWNLOAD_FAILED;
                break;
            default:
                outState = ImageDownloadManager.DECODE_STARTED;
                break;
        }

        handleState(outState);
    }
}

