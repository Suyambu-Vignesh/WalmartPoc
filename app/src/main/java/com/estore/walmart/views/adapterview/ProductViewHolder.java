package com.estore.walmart.views.adapterview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.pojo.Product;
import com.estore.walmart.presenter.ProductHomePresenter;
import com.estore.walmart.utils.AppUtils;
import com.estore.walmart.views.AnimatedImageView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Suyambu on 6/24/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTitleText;
    private TextView mPriceText;
    private TextView mProductStatusText;
    private TextView mRattingText;
    private RatingBar mRatingBar;
    private AnimatedImageView mAnimatedImageView;
    private ProductHomePresenter mProductHomePresenter;
    private int mItemPosition;

    public ProductViewHolder(View itemView, ProductHomePresenter productHomePresenter) {
        super(itemView);
        mProductHomePresenter = productHomePresenter;
        mTitleText = (TextView) itemView.findViewById(R.id.text_title);
        mPriceText = (TextView) itemView.findViewById(R.id.text_price);
        mProductStatusText = (TextView) itemView.findViewById(R.id.text_stock_status);
        mRattingText = (TextView) itemView.findViewById(R.id.text_ratting);
        mRatingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
        mAnimatedImageView = (AnimatedImageView) itemView.findViewById(R.id.image_view);

        itemView.setOnClickListener(this);
    }

    public void updateView(Product product, int itemPosition) {
        if (product == null) {
            mItemPosition = -1;
            return;
        }
        mItemPosition = itemPosition;
        mTitleText.setText(AppUtils.formatText(product.getProductName()));
        mPriceText.setText(AppUtils.formatText(product.getPrice()));
        mRattingText.setText(AppUtils.formatText(
                AppUtils.appendBraces(product.getReviewRating().toString())
        ));
        mProductStatusText.setText(
                AppUtils.formatText(product.getInStock() ?
                        AppUtils.getString(R.string.in_stock) :
                        AppUtils.getString(R.string.out_stock))
        );
        mProductStatusText.setBackgroundColor(
                product.getInStock() ? AppUtils.getColor(R.color.light_green) :
                        AppUtils.getColor(R.color.orange_red)
        );
        mRatingBar.setRating(new Float(product.getReviewRating()));

        try {
            mAnimatedImageView.setImageURL(new URL(product.getProductImage()), true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (mItemPosition == -1) {
            return;
        }
        mProductHomePresenter.onProductSelected(mItemPosition);
    }
}
