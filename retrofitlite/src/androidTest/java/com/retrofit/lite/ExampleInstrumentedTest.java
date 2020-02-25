package com.retrofit.lite;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    Context appContext;

    @Before
    public void before() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
