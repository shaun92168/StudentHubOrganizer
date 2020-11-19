package com.example.studenthuborganizer;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.studenthuborganizer.Views.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class UITest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testViewPage() {
        onView(withId(R.id.addRecord2)).perform(click());
        onView(withText("Assignment 1")).check(matches(isDisplayed()));
    }

    @Test
    public void testAddPageFromMain() {
        onView(withId(R.id.addRecord)).perform(click());
        onView(withText("Assignment Name")).check(matches(isDisplayed()));
    }

    @Test
    public void testAddPageFromView() {
        onView(withId(R.id.addRecord2)).perform(click());
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Assignment Name")).check(matches(isDisplayed()));
    }
}
