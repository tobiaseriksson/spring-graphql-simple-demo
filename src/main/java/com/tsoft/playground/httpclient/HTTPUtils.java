package com.tsoft.playground.httpclient;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class HTTPUtils {

    private static Logger logger = LoggerFactory.getLogger(HTTPUtils.class);

    public static final MediaType JSON
                    = MediaType.get("application/json; charset=utf-8");

    public static String post(String url, String json) throws IOException, InterruptedException {
        return post(url,json,3);
    }

    public static String post(String url, String json, int retries) throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
        logger.debug("HTTP POST Query "+request.toString());
        try (Response response = client.newCall(request).execute()) {
            // System.out.println( "Resp: "+response.toString() );
            // System.out.println( "Code : "+response.code() );
            return response.body().string();
        } catch( SocketTimeoutException e ) {
            if( retries <= 0 ) {
                throw e;
            }
            Thread.sleep(3*1000);
            return post(url,json,retries-1);
        }
    }

    public static String get(String url) throws IOException, InterruptedException {
        return get(url,3);
    }

    public static String get(String url,int retries) throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
        logger.debug("HTTP GET Query "+request.toString());
        try (Response response = client.newCall(request).execute()) {
            // System.out.println( "Resp: "+response.toString() );
            // System.out.println( "Code : "+response.code() );
            return response.body().string();
        } catch( SocketTimeoutException e ) {
            if( retries <= 0 ) {
                throw e;
            }
            Thread.sleep(3 * 1000);
            return get(url,retries-1);
        }
    }
}
