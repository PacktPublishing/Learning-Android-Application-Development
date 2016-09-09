package com.packt.rrafols.calculatortestexample;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginUITest {

    private String username = "john@doe.net";
    private String password = "admin1234";
    private String incorrectPassword = "invalid1234";
    private String invalidUsername = "invalid1234";

    @Rule
    public ActivityTestRule<LoginActivity> calculatorActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void checkLogin_incorrectCredentials() {
        onView(withId(R.id.login_username)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(incorrectPassword), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.login_error_text)).check(matches(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withId(R.id.login_error_text)).check(matches(withText(
                calculatorActivityTestRule.getActivity().getString(R.string.incorrect_credentials))));

    }

    @Test
    public void checkLogin_invalidUsername() {
        onView(withId(R.id.login_username)).perform(typeText(invalidUsername), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.login_error_text)).check(matches(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withId(R.id.login_error_text)).check(matches(withText(
                calculatorActivityTestRule.getActivity().getString(R.string.invalid_username))));

    }

    @Test
    public void checkLogin_correctCredentials() {
        onView(withId(R.id.login_username)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.add_button)).check(matches(isDisplayed()));
    }
}
