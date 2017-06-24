package com.estore.walmart.core.communication;

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
 * Created by Suyambu on 6/22/2017.
 */

public class ResourceRequester implements Runnable {
    public static final String CONNECTION_TYPE = "Connection";
    public static final String CONTENT_TYPE = "Content-Type";

    private ResourceCommandInfo mResourceCommandInfo;

    public ResourceRequester(ResourceCommandInfo resourceCommandInfo) {
        this.mResourceCommandInfo = resourceCommandInfo;
    }

    /*
    Make the Network call to fetch or post the information and deliver back to command
     */
    @Override
    public void run() {
        if (mResourceCommandInfo == null) {
            throw new WalmartAppException(WalmartAppException.EXCEPTION_RESOURCE_COMMAND_NULL);
        }

        String responseString = null;
        HttpURLConnection httpURLConnection = null;
        try {
            String requestBody = mResourceCommandInfo.getRequest().toString();

            httpURLConnection = (HttpURLConnection) mResourceCommandInfo.getUrl().openConnection();
            httpURLConnection.setRequestMethod(mResourceCommandInfo.getRequest().getRequestType());
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty(CONNECTION_TYPE, mResourceCommandInfo.getRequest().getConnectionState());
            httpURLConnection.setRequestProperty(CONTENT_TYPE, mResourceCommandInfo.getRequest().getContentType());
            httpURLConnection.setConnectTimeout(mResourceCommandInfo.getTimeOut());
            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, UTF_8.name());
            outputStreamWriter.write(requestBody);
            outputStreamWriter.flush();
            outputStreamWriter.close();

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

        mResourceCommandInfo.processResponse(responseString);
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
        public void processResponse(String response);
    }
}
