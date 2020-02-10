package com.aslam.retrofit_lite_test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aslam.retrofit_lite.services.APITask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APITask apiTask = APITask.from(this);
        SampleReq req = new SampleReq();
        req.email = "aslam";
        apiTask.sendPOST(101, "https://reqres.in/api/login", req, null, null);
    }
}
