package com.aslam.retrofit_lite;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

    @Rule
    public ActivityTestRule<MainActivity> activityScenarioRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void before() {

        Tester.setMaxWaitTime(1000 * 60);
    }

    @Test
    public void test() {
        assertTrue(true);
    }

    @After
    public void after() {

        Tester.sleep(3000);
        Tester.resetMaxWaitTime();
    }

}
