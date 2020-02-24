package com.aslam.retrofit_lite_test;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.aslam.retrofit_lite.services.APIClient;
import com.aslam.retrofit_lite.services.APITask;
import com.aslam.retrofit_lite_test.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSession;

public class MainActivity extends AppCompatActivity implements APITask.Listener {

    ActivityMainBinding binding;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{
                "Select one..",
                "101 - GET Raw Text",
                "102 - GET Request Headers",
                "103 - GET Response Headers",
                "104 - GET Response Status Code",
                "201 - POST Raw Text",
                "202 - POST JSON Text",
                "203 - POST Request Headers",
                "204 - Basic Auth",
                "205 - DigestAuth Success",
                "206 - POST JSON Text with Object",
                "207 - POST Empty Body Method",
                "301 - TIMEOUT",
                "302 - Host Verification"
        });

        binding.spinnerData.setAdapter(arrayAdapter);

        binding.spinnerData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                testAPITask(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void testAPITask(int position) {

        binding.spinnerData.setEnabled(false);

        binding.txtLog.setText(arrayAdapter.getItem(position) + "\n");

        // "101 - GET Request Data"
        if (arrayAdapter.getItem(position).contains("101")) {

            APITask.from(this).sendGET(101, APIClient.API_URL + "/get?leopard=animal", null, this);
        }
        // "102 - GET Request Headers"
        else if (arrayAdapter.getItem(position).contains("102")) {

            Map<String, String> headers = new HashMap<>();
            headers.put("leopard", "animal");

            APITask.from(this).sendGET(102, APIClient.API_URL + "/headers", headers, this);
        }
        // "103 - GET Response Headers"
        else if (arrayAdapter.getItem(position).contains("103")) {

            APITask.from(this).sendGET(103, APIClient.API_URL + "/response-headers?leopard=animal", null, this);
        }
        // "104 - GET Response Status Code"
        else if (arrayAdapter.getItem(position).contains("104")) {

            APITask.from(this).sendGET(104, APIClient.API_URL + "/status/400", null, this);
        }
        // "201 - POST Raw Text"
        else if (arrayAdapter.getItem(position).contains("201")) {

            APITask.from(this).sendPOST(201, APIClient.API_URL + "/post", "Leopard is an animal", null, this);
        }
        // "202 - POST JSON Text"
        else if (arrayAdapter.getItem(position).contains("202")) {

            APITask.from(this).sendPOST(201, APIClient.API_URL + "/post", "{ \"leopard\" : \"animal\" }", null, this);
        }
        // "203 - POST Request Headers"
        else if (arrayAdapter.getItem(position).contains("203")) {

            Map<String, String> headers = new HashMap<>();
            headers.put("leopard", "animal");

            APITask.from(this).sendPOST(203, APIClient.API_URL + "/post", "{}", headers, this);
        }
        // "204 - Basic Auth"
        else if (arrayAdapter.getItem(position).contains("204")) {

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Basic cG9zdG1hbjpwYXNzd29yZA==");

            APITask.from(this).sendGET(204, APIClient.API_URL + "/basic-auth", headers, this);
        }
        // "205 - DigestAuth Success"
        else if (arrayAdapter.getItem(position).contains("205")) {

            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Digest username=\"postman\", realm=\"Users\", nonce=\"ni1LiL0O37PRRhofWdCLmwFsnEtH1lew\", uri=\"/digest-auth\", response=\"254679099562cf07df9b6f5d8d15db44\", opaque=\"\"");

            APITask.from(this).sendGET(205, APIClient.API_URL + "/digest-auth", headers, this);
        }
        // "206 - POST JSON Text with Object"
        else if (arrayAdapter.getItem(position).contains("206")) {

            SampleReq req = new SampleReq();
            req.email = "google@gmail.com";

            APITask.from(this).sendPOST(206, APIClient.API_URL + "/post", req, null, this);
        }
        // "207 - POST Empty Body Method"
        else if (arrayAdapter.getItem(position).contains("207")) {

            APITask.from(this).sendPOST(207, APIClient.API_URL + "/post", null, this);
        }
        // "301 - TIMEOUT"
        else if (arrayAdapter.getItem(position).contains("301")) {

            APIClient.ConfigBuilder clientBuilder = new APIClient.ConfigBuilder()
                    .setTimeout(3000);

            APITask.from(this, clientBuilder).sendGET(301, APIClient.API_URL + "/delay/5", null, this);
        }
        // "302 - Host Verification"
        else if (arrayAdapter.getItem(position).contains("302")) {

            APIClient.ConfigBuilder clientBuilder = new APIClient.ConfigBuilder()
                    .setHostnameVerifier(new APIClient.ConfigBuilder.HostnameVerifier() {
                        @Override
                        public boolean onVerify(String hostname, SSLSession session) {
                            return false;
                        }
                    });

            APITask.from(this, clientBuilder).sendGET(302, APIClient.API_URL + "/get", null, this);
        }
        // Nothing
        else {

            binding.spinnerData.setEnabled(true);
        }
    }

    @Override
    public void onSuccess(int pid, int status, Map<String, String> headers, String body) {
        binding.spinnerData.setEnabled(true);
        updateUI(pid, status, headers, body);
    }

    @Override
    public void onFailed(int pid, Exception ex) {
        binding.spinnerData.setEnabled(true);
        updateUI(pid, ex);
    }

    private void updateUI(int pid, Exception ex) {

        String res = "\n--------- PID: " + pid + " ---------\n";
        res += "\nException: " + ex.toString() + "\n";
        res += "\n-----------------------------\n";

        binding.txtLog.setText(binding.txtLog.getText() + res);
    }

    private void updateUI(int pid, int status, Map<String, String> headers, String body) {

        String res = "\n--------- PID: " + pid + " ---------\n";
        res += "\nStatus: " + status + "\n";
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            res += "\nHeader: " + entry.getKey() + " => " + entry.getValue() + "\n";
        }
        res += "\nBody: " + body + "\n";
        res += "\n-----------------------------\n";

        binding.txtLog.setText(binding.txtLog.getText() + res);
    }
}
