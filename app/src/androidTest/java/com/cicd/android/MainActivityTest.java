package com.cicd.android;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static java.lang.Thread.sleep;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
        Intents.init();
    }

    @Test
    public void loginButtonClick() {
        Espresso.onView(withId(R.id.buttonLoginMain)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void testToastDisplayedAfterDelay() throws InterruptedException {
        Espresso.onView(withId(R.id.buttonLoginMain)).perform(click());
        onView(withId(R.id.buttonLogin)).perform(click());
        sleep(1000);
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText("Please enter both email and password")));

    }

    @Test
    public void testValidCredentialsToastDisplayed() throws InterruptedException {
        Espresso.onView(withId(R.id.buttonLoginMain)).perform(click());
        Espresso.onView(withId(R.id.editTextEmail))
                .perform(replaceText("test@example.com"), closeSoftKeyboard());
        sleep(1000);
        Espresso.onView(withId(R.id.editTextPassword))
                .perform(replaceText("password123"), closeSoftKeyboard());
        sleep(1000);

        Espresso.onView(withId(R.id.buttonLogin)).perform(click());
        sleep(1000);

        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText("Login Successful")));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
