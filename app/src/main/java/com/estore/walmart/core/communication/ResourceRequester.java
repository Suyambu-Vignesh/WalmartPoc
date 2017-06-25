package com.estore.walmart.core.communication;

import android.text.TextUtils;

import com.estore.walmart.WalmartApp;
import com.estore.walmart.core.NetworkManager;
import com.estore.walmart.model.BaseModel;
import com.estore.walmart.model.BussinessErrorModel;
import com.estore.walmart.model.NetworkErrorModel;
import com.estore.walmart.pojoconverter.ResponseDeSerialization;
import com.estore.walmart.utils.WalmartAppException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.util.Xml.Encoding.UTF_8;

/**
 * Created by Suyambu on 6/23/2017.
 */

public class ResourceRequester implements Runnable {
    public static final String TAG = ResourceRequester.class.getSimpleName();
    public static final String CONNECTION_TYPE = "Connection";
    public static final String CONTENT_TYPE = "Content-Type";

    private ResourceCommandInfo mResourceCommandInfo;
    private NetworkManager mNetworkManager;

    public ResourceRequester(ResourceCommandInfo resourceCommandInfo) {
        this.mResourceCommandInfo = resourceCommandInfo;
        mNetworkManager = WalmartApp.getAppObjectGraph().getNetworkManager();
    }

    /*
    Make the Network call to fetch or post the information and deliver back to command
     */
    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        WalmartApp.getAppObjectGraph().getLogHandler().d("Suyambu", "Process Started for making Server call");
        if (mResourceCommandInfo == null) {
            throw new WalmartAppException(WalmartAppException.EXCEPTION_RESOURCE_COMMAND_NULL);
        }

        if (!mNetworkManager.isOnline(WalmartApp.getAppContext())) {
            mResourceCommandInfo.processResponse(mResourceCommandInfo.getRequest().getId(), new NetworkErrorModel());
        }

        String responseString = "";
        HttpURLConnection httpURLConnection = null;
        try {

            WalmartApp.getAppObjectGraph().getLogHandler().d("Suyambu", "Process Started for making Server call " + mResourceCommandInfo.getUrl());
            httpURLConnection = (HttpURLConnection) mResourceCommandInfo.getUrl().openConnection();
            httpURLConnection.setRequestMethod(mResourceCommandInfo.getRequest().getRequestType());
            httpURLConnection.setDoInput(true);
            if (mResourceCommandInfo.getRequest().getRequestType().equals(Request.REQUEST_POST)) {
                httpURLConnection.setDoOutput(true);
            }
            httpURLConnection.setRequestProperty(CONNECTION_TYPE, mResourceCommandInfo.getRequest().getConnectionState());
            httpURLConnection.setRequestProperty(CONTENT_TYPE, mResourceCommandInfo.getRequest().getContentType());
            httpURLConnection.setConnectTimeout(mResourceCommandInfo.getTimeOut());
            httpURLConnection.connect();

            if (mResourceCommandInfo.getRequest().getRequestType().equals(Request.REQUEST_POST)) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, UTF_8.name());
                outputStreamWriter.write(mResourceCommandInfo.getRequest().toString());
                outputStreamWriter.flush();
                outputStreamWriter.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String response;
            while ((response = in.readLine()) != null) {
                responseString += response;
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }

        WalmartApp.getAppObjectGraph().getLogHandler().d("Suyambu", "Process Completed for making Server call " + responseString);
        if (!TextUtils.isEmpty(responseString)) {
            ResponseDeSerialization responseDeSerialization = WalmartApp.getAppObjectGraph().
                    getResponseDeSerialization();

            mResourceCommandInfo.processResponse(
                    mResourceCommandInfo.getRequest().getId(),
                    responseDeSerialization.parseResponse(responseString)
            );
        } else {
            mResourceCommandInfo.processResponse(mResourceCommandInfo.getRequest().getId(), new BussinessErrorModel());
        }
    }

    //----------------------------------------------------------------------------------------------------------

    interface ResourceCommandInfo {
        /*
        Method return the url for which we need to make network connection
         */
        public URL getUrl();


        /*
        Method return the timeout for network connection
         */
        public int getTimeOut();

        /*
         Method return the Request information
        */
        public Request getRequest();

        /*
        Method which return the success and failure response
         */
        public void processResponse(String resourceId, BaseModel response);
    }
}
