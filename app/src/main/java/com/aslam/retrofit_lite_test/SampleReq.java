package com.aslam.retrofit_lite_test;

import com.aslam.retrofit_lite.services.APITask;
import com.google.gson.annotations.Expose;

public class SampleReq implements APITask.Request {

    @Expose
    String email;

    @Expose
    String password;
}
