package com.estore.walmart.core.communication;

import android.support.v7.widget.RecyclerView;

import com.estore.walmart.core.ConnectivityReceiver;
import com.estore.walmart.core.NetworkManager;
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
 * <p>
 * Simulation of Dependency Injection work like Dagger 2.
 * <p>
 * Dependency Injection will help us to configure the App elements at runtime based on the environment
 */
public interface ModuleInjection {
    /**
     * Method to return the logcat handler
     *
     * @return instance of LogHandler
     */
    LogHandler getLogHandler();


    /**
     * Method return the Command which operates the ResourceRequester
     *
     * @return instance of {@link ResourceRequesterCommand}
     */
    ResourceRequesterCommand getResourceRequesterCommand();

    /**
     * Method return the Network Requester Runnable
     *
     * @return instance of ResourceRequester
     */
    ResourceRequester getResourceRequester();

    /**
     * Method return the SplashActivity
     *
     * @return instance of {@link SplashFragment}
     */
    SplashFragment getSplashFragment();


    /**
     * Method return the SplashPresenter
     *
     * @return instance of {@link SplashPresenter}
     */
    SplashPresenter getSplashPresenter();


    /**
     * Method return the Product Model
     *
     * @return instance of {@link ProductCatalogModel}
     */
    ProductCatalogModel getProductCatalogModel();

    /**
     * Method which return ResourceManager.
     *
     * @return instance of {@link ResourceManager}
     */
    ResourceManager getResourceManager();


    /**
     * Method return the instnace of Class which work with connectivity service and broadcast
     *
     * @return instance of {@link NetworkManager}
     */
    NetworkManager getNetworkManager();

    /**
     * Method return the instance of Network  BroadcastReceiver
     *
     * @return instance of {@link ConnectivityReceiver}
     */
    ConnectivityReceiver getConnectivityReceiver();

    /**
     * Method return the instance of ProductHomePresenter
     *
     * @return instance of {@link ProductHomePresenter}
     */
    ProductHomePresenter getProductHomePresenter(ProductCatalogModel productCatalogModel);

    /**
     * Method return the instance of UI Notifier
     *
     * @return instance of UI Notifier {@link UIObservable}
     */
    UIObservable getUIObservable();

    /**
     * Method return the helper Method to Parse the response
     *
     * @return instance Of {@link ResponseDeSerialization}
     */
    ResponseDeSerialization getResponseDeSerialization();

    /**
     * Method return the instance of HomeRecyeler view Adapter
     *
     * @param mProductHomePresenter
     * @return
     */
    ProductHomeRecyclerViewAdapter getHomeRecylerViewAdapter(ProductHomePresenter mProductHomePresenter);

    /**
     * get the LayoutManager()
     *
     * @return the Recyler View LayoutManager.
     */
    RecyclerView.LayoutManager getLayoutManager();
}
