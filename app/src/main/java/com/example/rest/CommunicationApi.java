package com.example.rest;

import android.text.TextUtils;

import com.example.igl.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;



public class CommunicationApi {

    private static final String API_CALLER_URL = DBManager.getApiBaseUrl();

    public static ApiInterface service;
    public static long time = System.currentTimeMillis();
    static long timeoutSeconds = 450000;

    static OkHttpClient client;

    static ApiInterface getIglService() {
        if (service == null) {
            String url = API_CALLER_URL;
            /*if (TextUtils.isEmpty(url)) {
                String urlType = Preferences.getData(AppConstants.PREF_KEYS.URLTYPE, "");
                if (TextUtils.isEmpty(urlType)) {
                    SplashScreen.updateUrls("", "");
                } else if (urlType.equalsIgnoreCase("uatUrls")) {
                    SplashScreen.uatUrls();
                } else if (urlType.equalsIgnoreCase("devUrls")) {
                    SplashScreen.devUrls();
                } else if (urlType.equalsIgnoreCase("prdUrls")) {
                    SplashScreen.prdUrls();
                } else if (urlType.equalsIgnoreCase("alphaUrls")) {
                    SplashScreen.alphaUrls();
                }
            }*/
            url = DBManager.getApiBaseUrl();
            service = CommunicationApi.restAdapterGen(url);
        }
        return service;
    }


    private static ApiInterface restAdapterGen(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //.addConverterFactory(JacksonConverterFactory.create())
               // .client(getDefaultOkHttpClient())
                .build();
        return retrofit.create(ApiInterface.class);
    }

   /* private static OkHttpClient getDefaultOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);


        Interceptor requestInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String verName = BuildConfig.VERSION_NAME;
                Request originalRequest = chain.request();
                originalRequest = originalRequest.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("App-Ver", verName)
                        .build();
                Response originalResponse = chain.proceed(originalRequest);
                return originalResponse.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .body(originalResponse.body())
                        .build();

            }
        };



        client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(requestInterceptor)
                //.addInterceptor(responseInterceptor)
                .readTimeout(45, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .callTimeout(45,TimeUnit.SECONDS)
                .build();

        return client;
    }*/


}
