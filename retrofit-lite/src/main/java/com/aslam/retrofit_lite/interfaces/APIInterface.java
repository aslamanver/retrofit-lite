package com.aslam.retrofit_lite.interfaces;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/*
 * ASLAM - RETROFIT IMPLEMENTATION
 */
public interface APIInterface {

    @POST()
    Call<ResponseBody> sendPOSTRequest(@Url String url, @Body String apiRequest);

    @GET()
    Call<ResponseBody> sendGETRequest(@Url String url);

    @POST()
    Call<ResponseBody> sendPOSTRequest(@Url String url, @HeaderMap Map<String, String> headers, @Body String apiRequest);

    @GET()
    Call<ResponseBody> sendGETRequest(@Url String url, @HeaderMap Map<String, String> headers);
}
