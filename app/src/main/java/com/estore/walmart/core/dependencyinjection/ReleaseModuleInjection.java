package com.estore.walmart.core.dependencyinjection;

import com.estore.walmart.core.ModuleInjection;
import com.estore.walmart.core.communication.ResourceManager;
import com.estore.walmart.core.communication.ResourceRequester;
import com.estore.walmart.core.communication.ResourceRequesterCommand;
import com.estore.walmart.core.log.LogHandler;
import com.estore.walmart.core.log.ReleaseLogHandler;
import com.estore.walmart.model.ProductCatalogModel;
import com.estore.walmart.presenter.SplashPresenter;
import com.estore.walmart.views.fragments.SplashFragment;

/**
 * Created by Suyambu on 6/22/2017.
 */

public class ReleaseModuleInjection implements ModuleInjection {
    private static int MAX_NUMBER_OF_ITEM_PER_PAGE = 25;
    private static int COMMAND_PRODUCT_CATALOG = ""

    @Override
    public LogHandler getLogHandler() {
        return new ReleaseLogHandler();
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

    @Override
    public ResourceManager getResourceManager() {
        return null;
    }
}
