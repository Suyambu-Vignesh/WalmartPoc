package com.estore.walmart.core.dependencyinjection;

import com.estore.walmart.core.ModuleInjection;
import com.estore.walmart.core.communication.ResourceRequester;
import com.estore.walmart.core.communication.ResourceRequesterCommand;
import com.estore.walmart.core.log.DebugLogHandler;
import com.estore.walmart.core.log.LogHandler;
import com.estore.walmart.model.ProductCatalogModel;
import com.estore.walmart.presenter.SplashPresenter;
import com.estore.walmart.views.fragments.SplashFragment;

/**
 * Created by Suyambu on 6/22/2017.
 */

public class DebugModuleInjection implements ModuleInjection {
    private static int MAX_NUMBER_OF_ITEM_PER_PAGE = 25;
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
        return ProductCatalogModel.getProductCatalogModel(MAX_NUMBER_OF_ITEM_PER_PAGE);
    }
}
