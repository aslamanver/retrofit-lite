package com.aslam.retrofit_lite_test;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class Tester {

    private static int MAX_WAIT_TIME = resetMaxWaitTime();
    private static int CURRENT_WAIT_TIME = 0;

    public static void waitPerform(int id, ViewAction... viewAction) {

        try {

            onView(withId(id)).perform(viewAction);
            CURRENT_WAIT_TIME = 0;

        } catch (NoMatchingViewException e) {

            noMatchingViewExceptionHandler();
            waitPerform(id, viewAction);
        }
    }

    public static void waitPerform(String text, int index, ViewAction... viewAction) {

        try {

            onView(withIndex(withText(text), index)).perform(viewAction);
            CURRENT_WAIT_TIME = 0;

        } catch (NoMatchingViewException e) {

            noMatchingViewExceptionHandler();
            waitPerform(text, index, viewAction);
        }
    }

    private static void noMatchingViewExceptionHandler() {

        if (CURRENT_WAIT_TIME > MAX_WAIT_TIME) {
            throw new NullPointerException("View does not exists");
        }

        sleep(1000);
        CURRENT_WAIT_TIME += 1000;
    }

    public static int setMaxWaitTime(int millis) {
        MAX_WAIT_TIME = millis;
        return MAX_WAIT_TIME;
    }

    public static int resetMaxWaitTime() {
        return setMaxWaitTime(10000);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {

        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }
}