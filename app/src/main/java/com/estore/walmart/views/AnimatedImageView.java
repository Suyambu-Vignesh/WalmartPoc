package com.estore.walmart.views;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.communication.ImageDownloadManager;
import com.estore.walmart.core.communication.ImageRenderCommand;

import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by Suyambu on 6/25/2017.
 */

public class AnimatedImageView extends AppCompatImageView {

    private boolean mCacheFlag;
    private boolean mIsDrawn;
    private WeakReference<View> mThisView;

    private URL mImageURL;
    private ImageRenderCommand mDownloadThread;
    private int mAlternativeResourceId;
    private Context mContext;

    {
        mAlternativeResourceId = -1;
    }

    public AnimatedImageView(Context context) {
        super(context);
        mContext = context;
    }

    public AnimatedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;

        parseAttributes(attributeSet, context);
    }

    public AnimatedImageView(Context context, AttributeSet attributeSet, int defaultStyle) {
        super(context, attributeSet, defaultStyle);
        mContext = context;

        parseAttributes(attributeSet, context);
    }

    private void parseAttributes(AttributeSet attributeSet, Context context) {
        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.AnimatedImageView, 0, 0);
        try {
            mAlternativeResourceId = R.drawable.walmart_app_logo;
        } finally {
            ta.recycle();
        }
    }

    /**
     * Method to set the visibility of the PhotoView
     *
     * @param visState The visibility state
     */
    private void showView(int visState) {
        if (mThisView != null) {
            View localView = mThisView.get();
            if (localView != null)
                localView.setVisibility(visState);
        }
    }

    /**
     * Sets the image in this ImageView to null, and makes the View visible
     */
    public void clearImage() {
        setImageDrawable(null);
        showView(View.VISIBLE);
    }

    /**
     * Returns the URL of the picture associated with this ImageView
     *
     * @return a URL
     */
    public URL getLocation() {
        return mImageURL;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        setImageURL(null, false);
        Drawable localDrawable = getDrawable();

        if (localDrawable != null) {
            localDrawable.setCallback(null);
        }

        if (mThisView != null) {
            mThisView.clear();
            mThisView = null;
        }
        this.mDownloadThread = null;

        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if ((!mIsDrawn) && (mImageURL != null)) {

            mDownloadThread = ImageDownloadManager.startDownload(this, mCacheFlag);

            mIsDrawn = true;
        }
        super.onDraw(canvas);
    }

    /**
     * Method to set the current View weak reference to be the incoming View. See the definition of
     * mThisView
     *
     * @param view the View to use as the new WeakReference
     */
    public void setHideView(View view) {
        this.mThisView = new WeakReference<View>(view);
    }

    @Override
    public void setImageBitmap(final Bitmap paramBitmap) {
        this.animate().alpha(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                AnimatedImageView.super.setImageBitmap(paramBitmap);
                AnimatedImageView.this.animate().alpha(1).setDuration(1000).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        int viewState;

        if (drawable == null) {
            viewState = View.VISIBLE;
        } else {
            viewState = View.INVISIBLE;
        }
        showView(viewState);

        super.setImageDrawable(drawable);
    }

    /*
     * Displays a drawable in the View
     */
    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
    }

    /*
     * Sets the URI for the Image
     */
    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
    }

    /**
     * Attempts to set the picture URL for this ImageView and then download the picture.
     * <p>
     * If the picture URL for this view is already set, and the input URL is not the same as the
     * stored URL, then the picture has moved and any existing downloads are stopped.
     * <p>
     * If the input URL is the same as the stored URL, then nothing needs to be done.
     * <p>
     * If the stored URL is null, then this method starts a download and decode of the picture
     *
     * @param pictureURL An incoming URL for a Picasa picture
     * @param cacheFlag  Whether to use caching when doing downloading and decoding
     */
    public void setImageURL(URL pictureURL, boolean cacheFlag) {
        WalmartApp.getAppObjectGraph().getLogHandler().d("Suyambu1", "Seeting url " + pictureURL);
        if (mImageURL != null) {

            if (!mImageURL.equals(pictureURL)) {
                ImageDownloadManager.removeDownload(mDownloadThread, mImageURL);
            } else {
                return;
            }
        }

        setImageDrawable(mContext.getResources().getDrawable(getAlternativeResourceId()));

        mImageURL = pictureURL;

        if ((mIsDrawn) && (pictureURL != null)) {
            mCacheFlag = cacheFlag;
            mDownloadThread = ImageDownloadManager.startDownload(this, cacheFlag);
        }
    }

    /**
     * Sets the Drawable for this ImageView
     *
     * @param drawable A Drawable to use for the ImageView
     */
    public void setStatusDrawable(Drawable drawable) {
        if (mThisView == null) {
            setImageDrawable(drawable);
        }
    }

    /**
     * Sets the content of this ImageView to be a Drawable resource
     *
     * @param resId
     */
    public void setStatusResource(int resId) {
        if (mThisView == null) {
            setImageResource(resId);
        }
    }

    public int getAlternativeResourceId() {
        // safety work...
        if (mAlternativeResourceId == -1) {
            return R.drawable.walmart_app_logo;
        }
        return mAlternativeResourceId;
    }

    public void setAlternativeResourceId(int id) {
        mAlternativeResourceId = id;
    }
}
