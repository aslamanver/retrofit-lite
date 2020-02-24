package com.aslam.retrofit_lite.services;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.aslam.retrofit_lite.BuildConfig;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/*
 * ASLAM - RETROFIT IMPLEMENTATION
 *
 */
public class APIClient {

    public static String TAG = "RFTLITE";
    public static String API_URL = "https://postman-echo.com";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context, ConfigBuilder builder) {

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(getOkHttpClient(context, builder))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        return retrofit;
    }

    public static OkHttpClient getOkHttpClient(Context context, final ConfigBuilder builder) {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (!message.contains("<html lang=\"en\">")) {
                    Log.e("LOG_" + TAG, message);
                }
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(interceptor);

        try {

            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            okHttpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return builder.hostnameVerifier.onVerify(hostname, session);
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        okHttpClientBuilder.callTimeout(builder.TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);

        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                setHeaders(requestBuilder);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return okHttpClientBuilder.build();
    }

    private static void setHeaders(Request.Builder requestBuilder) {
        requestBuilder.header("retrofit-lite-version", BuildConfig.VERSION_NAME);
    }

    public static class ConfigBuilder {

        public interface HostnameVerifier {
            boolean onVerify(String hostname, SSLSession session);
        }

        int TIMEOUT_MILLIS = 1000 * 60;

        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean onVerify(String hostname, SSLSession session) {
                return true;
            }
        };

        public ConfigBuilder setTimeout(int millis) {
            this.TIMEOUT_MILLIS = millis;
            return this;
        }

        public ConfigBuilder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }
    }
}

