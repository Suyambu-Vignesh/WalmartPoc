package com.estore.walmart.core.communication;


import com.estore.walmart.WalmartApp;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class ImageDownloader implements Runnable {
    // Sets the size for each read action (bytes)
    private static final int READ_SIZE = 1024 * 2;
    @SuppressWarnings("unused")
    private static final String TAG = "ImageDownloader";

    public static final String USER_AGENT = "Mozilla/5.0 (Linux; U; Android "
            + android.os.Build.VERSION.RELEASE + ";"
            + Locale.getDefault().toString() + "; " + android.os.Build.DEVICE
            + "/" + android.os.Build.ID + ")";

    static final int HTTP_STATE_FAILED = -1;
    static final int HTTP_STATE_STARTED = 0;
    static final int HTTP_STATE_COMPLETED = 1;

    final TaskRunnableDownloadMethods mCommand;

    ImageDownloader(TaskRunnableDownloadMethods photoTask) {
        mCommand = photoTask;
    }

    @SuppressWarnings("resource")
    @Override
    public void run() {
        mCommand.setDownloadThread(Thread.currentThread());

        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        byte[] byteBuffer = mCommand.getByteBuffer();

        try {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }

            if (null == byteBuffer) {
                mCommand.handleDownloadState(HTTP_STATE_STARTED);

                InputStream byteStream = null;

                try {
                    WalmartApp.getAppObjectGraph().getLogHandler().d("Suyambu1", "Downloading "+mCommand.getImageURL());
                    HttpURLConnection httpConn =
                            (HttpURLConnection) mCommand.getImageURL().openConnection();

                    httpConn.setRequestProperty("User-Agent", USER_AGENT);

                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    byteStream = httpConn.getInputStream();

                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                    int contentSize = httpConn.getContentLength();

                    if (-1 == contentSize) {

                        byte[] tempBuffer = new byte[READ_SIZE];
                        int bufferLeft = tempBuffer.length;

                        int bufferOffset = 0;
                        int readResult = 0;

                        outer:
                        do {
                            while (bufferLeft > 0) {

                                readResult = byteStream.read(tempBuffer, bufferOffset,
                                        bufferLeft);
                                if (readResult < 0) {
                                    break outer;
                                }

                                bufferOffset += readResult;
                                bufferLeft -= readResult;

                                if (Thread.interrupted()) {
                                    throw new InterruptedException();
                                }
                            }
                            bufferLeft = READ_SIZE;

                            int newSize = tempBuffer.length + READ_SIZE;

                            byte[] expandedBuffer = new byte[newSize];
                            System.arraycopy(tempBuffer, 0, expandedBuffer, 0,
                                    tempBuffer.length);
                            tempBuffer = expandedBuffer;
                        } while (true);

                        byteBuffer = new byte[bufferOffset];

                        // Copies the temporary buffer to the image buffer
                        System.arraycopy(tempBuffer, 0, byteBuffer, 0, bufferOffset);
                    } else {
                        byteBuffer = new byte[contentSize];

                        int remainingLength = contentSize;

                        int bufferOffset = 0;

                        while (remainingLength > 0) {
                            int readResult = byteStream.read(
                                    byteBuffer,
                                    bufferOffset,
                                    remainingLength);
                            if (readResult < 0) {
                                throw new EOFException();
                            }

                            bufferOffset += readResult;

                            remainingLength -= readResult;

                            if (Thread.interrupted()) {
                                throw new InterruptedException();
                            }
                        }
                    }

                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } finally {
                    if (null != byteStream) {
                        try {
                            byteStream.close();
                        } catch (Exception e) {

                        }
                    }
                }
            }

            mCommand.setByteBuffer(byteBuffer);

            mCommand.handleDownloadState(HTTP_STATE_COMPLETED);
        } catch (InterruptedException e1) {
        } finally {
            if (null == byteBuffer) {
                mCommand.handleDownloadState(HTTP_STATE_FAILED);
            }
            mCommand.setDownloadThread(null);
            Thread.interrupted();
        }
    }

    interface TaskRunnableDownloadMethods {

        /**
         * Sets the Thread that this instance is running on
         *
         * @param currentThread the current Thread
         */
        void setDownloadThread(Thread currentThread);

        /**
         * Returns the current contents of the download buffer
         *
         * @return The byte array downloaded from the URL in the last read
         */
        byte[] getByteBuffer();

        /**
         * Sets the current contents of the download buffer
         *
         * @param buffer The bytes that were just read
         */
        void setByteBuffer(byte[] buffer);

        /**
         * Defines the actions for each state of the PhotoTask instance.
         *
         * @param state The current state of the task
         */
        void handleDownloadState(int state);

        /**
         * Gets the URL for the image being downloaded
         *
         * @return The image URL
         */
        URL getImageURL();
    }
}


