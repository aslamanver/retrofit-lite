package com.aslam.retrofit_lite;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    // @Before
    // public void before() {
    //
    //     Tester.setMaxWaitTime(1000 * 60);
    // }
    //
    // @Test
    // public void test_emv_with_key_entry_sale() {
    //
    // }
    //
    // @After
    // public void after() {
    //
    //     Tester.sleep(3000);
    //     Tester.resetMaxWaitTime();
    // }

}
