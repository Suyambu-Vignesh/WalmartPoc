package com.estore.walmart.core.dependencyinjection;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.ConnectivityReceiver;
import com.estore.walmart.core.communication.ModuleInjection;
import com.estore.walmart.core.NetworkManager;
import com.estore.walmart.core.communication.ResourceManager;
import com.estore.walmart.core.communication.ResourceRequester;
import com.estore.walmart.core.communication.ResourceRequesterCommand;
import com.estore.walmart.core.log.DebugLogHandler;
import com.estore.walmart.core.log.LogHandler;
import com.estore.walmart.model.ProductCatalogModel;
import com.estore.walmart.opertaions.UIObservable;
import com.estore.walmart.pojoconverter.ResponseDeSerialization;
import com.estore.walmart.presenter.ProductHomePresenter;
import com.estore.walmart.presenter.SplashPresenter;
import com.estore.walmart.views.adapterview.ProductHomeRecyclerViewAdapter;
import com.estore.walmart.views.fragments.SplashFragment;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class DebugModuleInjection implements ModuleInjection {
    private static int MAX_NUMBER_OF_ITEM_PER_PAGE = 30;
    private static String COMMAND_PRODUCT_CATALOG = "_ah/api/walmart/v1/walmartproducts/7837b28e-e381-4186-9a87-70ba25c4c68c/%s/%s";

    @Override
    public LogHandler getLogHandler() {
        return new DebugLogHandler();
    }

    @Override
    public ResourceRequesterCommand getResourceRequesterCommand() {
        return new ResourceRequesterCommand();
    }

    @Override
    public ResourceRequester getResourceRequester() {
        return new ResourceRequester(getResourceRequesterCommand());
    }

    @Override
    public SplashFragment getSplashFragment() {
        return new SplashFragment();
    }

    @Override
    public SplashPresenter getSplashPresenter() {
        return new SplashPresenter();
    }

    @Override
    public ProductCatalogModel getProductCatalogModel() {
        return ProductCatalogModel.getProductCatalogModel(MAX_NUMBER_OF_ITEM_PER_PAGE, COMMAND_PRODUCT_CATALOG);
    }

    @Override
    public ResourceManager getResourceManager() {
        return ResourceManager.getInstance();
    }

    @Override
    public NetworkManager getNetworkManager() {
        return NetworkManager.getInstance();
    }

    @Override
    public ConnectivityReceiver getConnectivityReceiver() {
        return new ConnectivityReceiver();
    }

    @Override
    public ProductHomePresenter getProductHomePresenter(ProductCatalogModel productCatalogModel) {
        return new ProductHomePresenter(productCatalogModel);
    }

    @Override
    public UIObservable getUIObservable() {
        return UIObservable.getInstance();
    }

    @Override
    public ResponseDeSerialization getResponseDeSerialization() {
        return new ResponseDeSerialization();
    }

    @Override
    public ProductHomeRecyclerViewAdapter getHomeRecylerViewAdapter(ProductHomePresenter mProductHomePresenter) {
        return new ProductHomeRecyclerViewAdapter(mProductHomePresenter);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WalmartApp.getAppContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return linearLayoutManager;
    }
}
