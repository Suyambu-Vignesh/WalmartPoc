package com.estore.walmart.core.communication;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.estore.walmart.WalmartApp;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class ImageDecoder implements Runnable {

    private static final int NUMBER_OF_DECODE_TRIES = 2;
    private static final long SLEEP_TIME_MILLISECONDS = 250;
    private static final String LOG_TAG = "ImageDecoder";

    static final int DECODE_STATE_FAILED = -1;
    static final int DECODE_STATE_STARTED = 0;
    static final int DECODE_STATE_COMPLETED = 1;

    final TaskRunnableDecodeMethods mCommand;


    /**
     * This constructor creates an instance of ImageDownloader and stores in it a reference
     * to the PhotoTask instance that instantiated it.
     *
     * @param downloadTask The PhotoTask, which implements ImageDecoderRunnableCallback
     */
    ImageDecoder(TaskRunnableDecodeMethods downloadTask) {
        mCommand = downloadTask;
    }

    /*
     * Defines this object's task, which is a set of instructions designed to be run on a Thread.
     */
    @Override
    public void run() {
        mCommand.setImageDecodeThread(Thread.currentThread());

        byte[] imageBuffer = mCommand.getByteBuffer();

        Bitmap returnBitmap = null;

        try {
            mCommand.handleDecodeState(DECODE_STATE_STARTED);

            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

            int targetWidth = mCommand.getTargetWidth();
            int targetHeight = mCommand.getTargetHeight();

            if (Thread.interrupted()) {

                return;
            }

            bitmapOptions.inJustDecodeBounds = true;

            BitmapFactory
                    .decodeByteArray(imageBuffer, 0, imageBuffer.length, bitmapOptions);

            int hScale = bitmapOptions.outHeight / targetHeight;
            int wScale = bitmapOptions.outWidth / targetWidth;

            int sampleSize = Math.max(hScale, wScale);

            if (sampleSize > 1) {
                bitmapOptions.inSampleSize = sampleSize;
            }

            if (Thread.interrupted()) {
                return;
            }

            bitmapOptions.inJustDecodeBounds = false;

            for (int i = 0; i < NUMBER_OF_DECODE_TRIES; i++) {
                try {
                    returnBitmap = BitmapFactory.decodeByteArray(
                            imageBuffer,
                            0,
                            imageBuffer.length,
                            bitmapOptions
                    );
                } catch (Throwable e) {
                    WalmartApp.getAppObjectGraph().getLogHandler()
                            .e(LOG_TAG, "Out of memory in decode stage. Throttling.");
                    java.lang.System.gc();

                    if (Thread.interrupted()) {
                        return;

                    }
                    try {
                        Thread.sleep(SLEEP_TIME_MILLISECONDS);
                    } catch (java.lang.InterruptedException interruptException) {
                        return;
                    }
                }
            }
        } finally {
            if (null == returnBitmap) {
                mCommand.handleDecodeState(DECODE_STATE_FAILED);

                WalmartApp.getAppObjectGraph().getLogHandler()
                        .e(LOG_TAG, "Download failed in ImageDecoder");

            } else {
                mCommand.setImage(returnBitmap);
                mCommand.handleDecodeState(DECODE_STATE_COMPLETED);
            }

            mCommand.setImageDecodeThread(null);
            Thread.interrupted();
        }

    }


    //________________________________________________________________________________________________

    /**
     * An interface that defines methods that PhotoCommmand implements. An instance of
     * PhotoCommand passes itself to an runnable instance through the runnable constructor, after
     * which the two instances can access each other's variables.
     */
    interface TaskRunnableDecodeMethods {

        /**
         * Sets the Thread that this instance is running on
         *
         * @param currentThread the current Thread
         */
        void setImageDecodeThread(Thread currentThread);

        /**
         * Returns the current contents of the download buffer
         *
         * @return The byte array downloaded from the URL in the last read
         */
        byte[] getByteBuffer();

        /**
         * Sets the actions for each state of the PhotoTask instance.
         *
         * @param state The state being handled.
         */
        void handleDecodeState(int state);

        /**
         * Returns the desired width of the image, based on the ImageView being created.
         *
         * @return The target width
         */
        int getTargetWidth();

        /**
         * Returns the desired height of the image, based on the ImageView being created.
         *
         * @return The target height.
         */
        int getTargetHeight();

        /**
         * Sets the Bitmap for the ImageView being displayed.
         *
         * @param image
         */
        void setImage(Bitmap image);
    }
}

