package com.estore.walmart.core;

import com.estore.walmart.core.communication.ResourceManager;
import com.estore.walmart.core.communication.ResourceRequester;
import com.estore.walmart.core.communication.ResourceRequesterCommand;
import com.estore.walmart.core.log.LogHandler;
import com.estore.walmart.model.ProductCatalogModel;
import com.estore.walmart.presenter.SplashPresenter;
import com.estore.walmart.views.fragments.SplashFragment;

/**
 * Created by Suyambu on 6/22/2017.
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

    ResourceManager getResourceManager();

}
