package com.example.studenthuborganizer;

import androidx.test.espresso.contrib.PickerActions;
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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class UITest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

//    @Test
//    public void testViewPage() {
//        onView(withId(R.id.addRecord2)).perform(click());
//        onView(withText("Sort By")).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void testAddPageFromView() {
//        onView(withId(R.id.addRecord2)).perform(click());
//        onView(withId(R.id.floatingActionButton)).perform(click());
//        onView(withText("Assignment Name")).check(matches(isDisplayed()));
//    }

    @Test
    public void testAddandDeleteAssignment() {
        onView(withId(R.id.addRecord2)).perform(click());
        onView(withText("Sort By")).check(matches(isDisplayed()));
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Assignment Name")).check(matches(isDisplayed()));

        // Add Assignment 1
        onView(withId(R.id.etTaskName)).perform(clearText());
        onView(withId(R.id.etTaskName)).perform(typeText("Assignment 1"), closeSoftKeyboard());
        onView(withId(R.id.etDescription)).perform(clearText());
        onView(withId(R.id.etDescription)).perform(typeText("Assignment 1 Description"), closeSoftKeyboard());
        onView(withId(R.id.rdIndividual)).perform(click());
        onView(withId(R.id.etCourseName)).perform(clearText());
        onView(withId(R.id.etCourseName)).perform(typeText("COMP7903"), closeSoftKeyboard());
        onView(withId(R.id.date_picker)).perform(PickerActions.setDate(2020, 11, 30));
        onView(withId(R.id.time_picker)).perform(scrollTo(), PickerActions.setTime(23,59));
        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.btSave)).perform(scrollTo(), click());

        // Back to View Screen
        onView(withText("Sort By")).check(matches(isDisplayed()));
        onView(withText("Assignment 1")).check(matches(isDisplayed()));
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Assignment Name")).check(matches(isDisplayed()));

        // Add Assignment 2
        onView(withId(R.id.etTaskName)).perform(clearText());
        onView(withId(R.id.etTaskName)).perform(typeText("Assignment 2"), closeSoftKeyboard());
        onView(withId(R.id.etDescription)).perform(clearText());
        onView(withId(R.id.etDescription)).perform(typeText("Assignment 2 Description"), closeSoftKeyboard());
        onView(withId(R.id.rdIndividual)).perform(click());
        onView(withId(R.id.etCourseName)).perform(clearText());
        onView(withId(R.id.etCourseName)).perform(typeText("COMP7051"), closeSoftKeyboard());
        onView(withId(R.id.date_picker)).perform(PickerActions.setDate(2020, 12, 1));
        onView(withId(R.id.time_picker)).perform(scrollTo(), PickerActions.setTime(23,59));
        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.btSave)).perform(scrollTo(), click());

        // Back to View Screen
        onView(withText("Sort By")).check(matches(isDisplayed()));
        onView(withText("Assignment 2")).check(matches(isDisplayed()));
        onView(withId(R.id.floatingActionButton)).perform(click());
        onView(withText("Assignment Name")).check(matches(isDisplayed()));

        // Add Assignment 3
        onView(withId(R.id.etTaskName)).perform(clearText());
        onView(withId(R.id.etTaskName)).perform(typeText("Assignment 3"), closeSoftKeyboard());
        onView(withId(R.id.etDescription)).perform(clearText());
        onView(withId(R.id.etDescription)).perform(typeText("Assignment 3 Description"), closeSoftKeyboard());
        onView(withId(R.id.rdIndividual)).perform(click());
        onView(withId(R.id.etCourseName)).perform(clearText());
        onView(withId(R.id.etCourseName)).perform(typeText("COMP7082"), closeSoftKeyboard());
        onView(withId(R.id.date_picker)).perform(PickerActions.setDate(2020, 12, 3));
        onView(withId(R.id.time_picker)).perform(scrollTo(), PickerActions.setTime(23,59));
        onView(withId(R.id.scrollView)).perform(swipeUp());
        onView(withId(R.id.btSave)).perform(scrollTo(), click());

        // Back to View Screen
        onView(withText("Sort By")).check(matches(isDisplayed()));

        // Ensure all assignments are seen
        onView(withText("Assignment 1")).check(matches(isDisplayed()));
        onView(withText("Assignment 2")).check(matches(isDisplayed()));
        onView(withText("Assignment 3")).check(matches(isDisplayed()));

        // Check Assignment 1
        onView(withText("Assignment 1")).check(matches(isDisplayed()));
        onView(withText("11/30/20")).perform(click());

        // Check Assignment 2
        onView(withText("Assignment 2")).check(matches(isDisplayed()));
        onView(withText("12/1/20")).perform(click());

        // Delete Assignment 1 and 2
        onView(withId(R.id.add_button)).perform(click());
        onView(withText("Assignment 1")).check(doesNotExist());
        onView(withText("Assignment 2")).check(doesNotExist());

        // Check Assignment 1
        onView(withText("Assignment 3")).check(matches(isDisplayed()));
        onView(withText("12/3/20")).perform(click());

        // Delete Assignment 3
        onView(withId(R.id.add_button)).perform(click());
        onView(withText("Assignment 3")).check(doesNotExist());
    }
}
