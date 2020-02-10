package com.aslam.retrofit_lite.services;

import android.content.Context;
import android.util.Log;

import com.aslam.retrofit_lite.interfaces.APIInterface;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class APITask {

    private Retrofit retrofit;
    private APIInterface apiInterface;

    private APITask(Context context) {
        this.retrofit = APIClient.getClient(context);
        this.apiInterface = retrofit.create(APIInterface.class);
    }

    public static APITask from(Context context) {
        return new APITask(context);
    }

    public void sendGET(int pid, String url, Map<String, String> headers, Listener apiListener) {
        Call call = headers == null ? apiInterface.sendGETRequest(url) : apiInterface.sendGETRequest(url, headers);
        call.enqueue(new CustomCallback(pid, apiListener));
    }

    public void sendPOST(int pid, String url, String apiRequest, Map<String, String> headers, Listener apiListener) {

        if (isValidJSON(apiRequest)) {
            if (headers == null) {
                headers = new HashMap<>();
            }
            headers.put("Content-Type", "application/json; charset=utf-8");
        }

        Call call = headers == null ? apiInterface.sendPOSTRequest(url, apiRequest) : apiInterface.sendPOSTRequest(url, headers, apiRequest);
        call.enqueue(new CustomCallback(pid, apiListener));
    }

    public void sendPOST(int pid, String url, APITask.Request apiRequest, Map<String, String> headers, Listener apiListener) {
        sendPOST(pid, url, new Gson().toJson(apiRequest), headers, apiListener);
    }

    private boolean isValidJSON(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    /************************************************************************************/

    public interface Request {
    }

    public interface Listener {

        void onSuccess(int pid, Map<String, String> headers, String body);

        void onFailed(int pid, Exception ex);
    }

    private class CustomCallback implements Callback<ResponseBody> {

        private Listener apiListener;
        private int pid;

        CustomCallback(int pid, Listener apiListener) {

            this.pid = pid;

            this.apiListener = apiListener != null ? apiListener : new APITask.Listener() {

                @Override
                public void onSuccess(int pid, Map<String, String> headers, String body) {
                    Log.e(APIClient.TAG, body);
                }

                @Override
                public void onFailed(int pid, Exception ex) {
                    Log.e(APIClient.TAG, ex.toString());
                }
            };
        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            try {

                String body = response.body() == null ? "{}" : response.body().string();

                if (response.body() == null) {
                    body = response.errorBody() == null ? "{}" : response.errorBody().string();
                }

                Map<String, String> headers = new HashMap<>();

                for (String header : response.headers().names()) {
                    headers.put(header, response.headers().get(header));
                }

                this.apiListener.onSuccess(pid, headers, body);

            } catch (Exception ex) {
                this.apiListener.onFailed(pid, ex);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            this.apiListener.onFailed(pid, new Exception(t));
        }
    }
}
