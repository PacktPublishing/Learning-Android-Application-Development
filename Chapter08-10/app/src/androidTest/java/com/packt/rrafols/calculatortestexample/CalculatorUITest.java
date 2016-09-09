package com.packt.rrafols.calculatortestexample;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CalculatorUITest {

    private String value1 = "10";
    private String value2 = "20";
    private String result = "30";

    @Rule
    public ActivityTestRule<CalculatorActivity> calculatorActivityTestRule =
            new ActivityTestRule<CalculatorActivity>(CalculatorActivity.class);

    @Test
    public void checkResultShownAdd_calculatorActivity() {
        onView(withId(R.id.value1)).perform(typeText(value1), closeSoftKeyboard());
        onView(withId(R.id.value2)).perform(typeText(value2), closeSoftKeyboard());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.result_viewer)).check(matches(withText(result)));
    }

    @Test
    public void checkResultShownMultiply_calculatorActivity() {
        onView(withId(R.id.value1)).perform(typeText(value1), closeSoftKeyboard());
        onView(withId(R.id.value2)).perform(typeText(value2), closeSoftKeyboard());
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.result_viewer)).check(matches(withText(result)));
    }
}




