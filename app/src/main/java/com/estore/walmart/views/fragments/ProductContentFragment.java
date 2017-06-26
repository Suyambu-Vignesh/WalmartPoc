package com.estore.walmart.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.walmart.R;
import com.estore.walmart.WalmartApp;
import com.estore.walmart.model.ProductContentModel;
import com.estore.walmart.opertaions.ProductContentPresenterOperation;
import com.estore.walmart.pojo.Product;
import com.estore.walmart.presenter.ProductContentPresenter;
import com.estore.walmart.utils.AppUtils;
import com.estore.walmart.views.AnimatedImageView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Suyambu on 6/25/2017.
 */

/**
 * This Fragment act as a  detail view of single product
 */
public class ProductContentFragment extends BaseFragment implements ProductContentPresenterOperation.ViewOperation, View.OnClickListener {
    private ProductContentPresenter mProductContentPresenter;

    private AnimatedImageView mAnimatedImageView;
    private TextView mShortDescriptionText;
    private TextView mTitleView;
    private TextView mPriceText;
    private TextView mViewMoreText;
    private TextView mRattingCountText;
    private RatingBar mRatingBar;
    private Button mCartButton;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_content;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void initFragment(View rootView) {
        ProductContentModel productContentModel = getArguments().getParcelable(ProductContentModel.TAG);
        mProductContentPresenter = WalmartApp.getAppObjectGraph().getProductContentPresenter(productContentModel);

        mAnimatedImageView = (AnimatedImageView) rootView.findViewById(R.id.image_detail);
        mShortDescriptionText = (TextView) rootView.findViewById(R.id.text_short_description);
        mTitleView = (TextView) rootView.findViewById(R.id.text_title);
        mPriceText = (TextView) rootView.findViewById(R.id.text_price);
        mViewMoreText = (TextView) rootView.findViewById(R.id.text_view_more);
        mRattingCountText = (TextView) rootView.findViewById(R.id.text_ratting_count);
        mRatingBar = (RatingBar) rootView.findViewById(R.id.rating_bar);

        mCartButton = (Button) rootView.findViewById(R.id.button_add_to_cart);

        mCartButton.setOnClickListener(this);
        mViewMoreText.setOnClickListener(this);

        refreshUI(mProductContentPresenter.getProduct());
    }

    public static Fragment getInstnace(ProductContentModel productDetailModel) {
        ProductContentFragment fragment = new ProductContentFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ProductContentModel.TAG, productDetailModel);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void refreshUI(Product product) {
        if (product == null) {
            return;
        }

        mRattingCountText.setText(AppUtils.frameRatting(product.getReviewCount()));
        mPriceText.setText(product.getPrice());
        mTitleView.setText(AppUtils.formatText(product.getProductName()));
        mShortDescriptionText.setText(AppUtils.formatText(product.getShortDescription()));
        try {
            mAnimatedImageView.setImageURL(new URL(product.getProductImage()), true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mRatingBar.setRating(new Float(product.getReviewRating()));

        mViewMoreText.setVisibility(TextUtils.isEmpty(product.getLongDescription()) ||
                TextUtils.isEmpty(product.getShortDescription()) ? View.GONE : View.VISIBLE);

        mCartButton.setEnabled(product.getInStock());
        mCartButton.setClickable(product.getInStock());
        if (!product.getInStock()) {
            mCartButton.setAlpha((float) .3);
        } else {
            mCartButton.setAlpha(1);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_view_more) {
            mProductContentPresenter.showFullDescription();
        } else {
            Toast.makeText(getContext(), getString(R.string.work_in_progress), Toast.LENGTH_SHORT).show();
        }
    }
}
