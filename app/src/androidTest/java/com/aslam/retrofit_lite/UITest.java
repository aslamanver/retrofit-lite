package com.aslam.retrofit_lite;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void before() {

        Tester.setMaxWaitTime(1000 * 60);
    }

    @Test
    public void test_samle() {
        assertTrue(true);
    }

    @After
    public void after() {

        Tester.sleep(3000);
        Tester.resetMaxWaitTime();
    }

}
