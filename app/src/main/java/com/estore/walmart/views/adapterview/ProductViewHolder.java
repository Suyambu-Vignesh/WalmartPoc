package com.estore.walmart.views.adapterview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.estore.walmart.R;
import com.estore.walmart.pojo.Product;
import com.estore.walmart.utils.AppUtils;

/**
 * Created by Suyambu on 6/24/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private TextView mTitleText;
    private TextView mPriceText;
    private TextView mProductStatusText;
    private TextView mRattingText;
    private RatingBar mRatingBar;

    public ProductViewHolder(View itemView) {
        super(itemView);
        mTitleText = (TextView) itemView.findViewById(R.id.text_title);
        mPriceText = (TextView) itemView.findViewById(R.id.text_price);
        mProductStatusText = (TextView) itemView.findViewById(R.id.text_stock_status);
        mRattingText = (TextView) itemView.findViewById(R.id.text_ratting);
        mRatingBar = (RatingBar) itemView.findViewById(R.id.rating_bar);
    }

    public void updateView(Product product) {
        if (product == null) {
            return;
        }
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
        mRatingBar.setRating(new Float(product.getReviewRating()));
    }
}
