package com.estore.walmart.core.communication;

import java.util.concurrent.TimeUnit;

/**
 * Created by Suyambu on 6/25/2017.
 */

class BaseResourceManager {
    protected static final int KEEP_ALIVE_TIME = 1;
    protected static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    protected static final int CORE_POOL_SIZE = 8;
    protected static final int MAXIMUM_POOL_SIZE = 8;
}
