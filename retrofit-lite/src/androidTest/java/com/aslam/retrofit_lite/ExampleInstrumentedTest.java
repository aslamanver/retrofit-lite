package com.aslam.retrofit_lite;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.aslam.retrofit_lite.services.APIClient;
import com.aslam.retrofit_lite.services.APITask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Context appContext;
    APITask apiTask;

    @Before
    public void before() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        apiTask = APITask.from(appContext);
    }

    @Test
    public void test_http_without_callback_and_header() {

        apiTask.sendGET(101, "https://reqres.in/api/users", null, new APITask.Listener() {

            @Override
            public void onSuccess(int pid, Map<String, String> headers, String body) {
                // Log.e(APIClient.TAG, body);
                int l = 0;
                assertTrue(true);
            }

            @Override
            public void onFailed(int pid, Exception ex) {
                Log.e(APIClient.TAG, ex.toString());
            }
        });

    }
}
