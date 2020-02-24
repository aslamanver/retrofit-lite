package com.retrofit.lite.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.retrofit.lite.interfaces.APIInterface;

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

    private APITask(Context context, APIClient.ConfigBuilder builder) {
        this.retrofit = APIClient.getClient(context, builder);
        this.apiInterface = retrofit.create(APIInterface.class);
    }

    public static APITask from(Context context) {
        return new APITask(context, new APIClient.ConfigBuilder());
    }

    public static APITask from(Context context, APIClient.ConfigBuilder builder) {
        return new APITask(context, builder);
    }

    public void sendGET(int pid, String url, Map<String, String> headers, Listener apiListener) {
        Call call = headers == null ? apiInterface.sendGETRequest(url) : apiInterface.sendGETRequest(url, headers);
        call.enqueue(new CustomCallback(pid, apiListener));
    }

    public void sendPOST(int pid, String url, Map<String, String> headers, Listener apiListener) {
        sendPOST(pid, url, "{}", headers, apiListener);
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

        void onSuccess(int pid, int status, Map<String, String> headers, String body);

        void onFailed(int pid, Exception ex);
    }

    private class CustomCallback implements Callback<ResponseBody> {

        private Listener apiListener;
        private int pid;
        private boolean listening = false;

        private APITask.Listener defaultListener = new APITask.Listener() {

            @Override
            public void onSuccess(int pid, int status, Map<String, String> headers, String body) {
                APITask.console(pid, status, headers, body);
            }

            @Override
            public void onFailed(int pid, Exception ex) {
                APITask.console(pid, ex);
            }
        };

        CustomCallback(int pid, Listener apiListener) {

            this.pid = pid;

            if (apiListener != null) {
                listening = true;
            }

            this.apiListener = apiListener != null ? apiListener : defaultListener;
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

                if (listening) {
                    this.defaultListener.onSuccess(pid, response.code(), headers, body);
                }

                this.apiListener.onSuccess(pid, response.code(), headers, body);

            } catch (Exception ex) {

                if (listening) {
                    this.defaultListener.onFailed(pid, ex);
                }

                this.apiListener.onFailed(pid, ex);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            this.apiListener.onFailed(pid, new Exception(t));
        }
    }

    private static void console(int pid, int status, Map<String, String> headers, String body) {
        Log.d(APIClient.TAG, "--------- PID: " + pid + " ---------");
        Log.d(APIClient.TAG, "Status: " + status);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            Log.d(APIClient.TAG, "Header: " + entry.getKey() + " => " + entry.getValue());
        }
        Log.d(APIClient.TAG, "Body: " + body);
        Log.d(APIClient.TAG, "-----------------------------");
    }

    private static void console(int pid, Exception ex) {
        Log.e(APIClient.TAG, "--------- PID: " + pid + " ---------");
        Log.e(APIClient.TAG, "Exception: " + ex.toString() != null ? ex.toString() : "NULL");
        Log.e(APIClient.TAG, "-----------------------------");
    }
}
