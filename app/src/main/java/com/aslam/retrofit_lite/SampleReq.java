package com.aslam.retrofit_lite;

import com.google.gson.annotations.Expose;
import com.retrofit.lite.services.APITask;

public class SampleReq implements APITask.Request {

    @Expose
    String email;

    @Expose
    String password;
}
