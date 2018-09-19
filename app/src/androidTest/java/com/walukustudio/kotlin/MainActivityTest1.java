package com.walukustudio.kotlin;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest1 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest1() {
        ViewInteraction _LinearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.list_team),
                                childAtPosition(
                                        withClassName(is("org.jetbrains.anko._RelativeLayout")),
                                        0)),
                        4),
                        isDisplayed()));
        _LinearLayout.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.add_to_favorite), withContentDescription("Favourites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        pressBack();

        ViewInteraction spinner = onView(
                allOf(withId(R.id.spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                0),
                        isDisplayed()));
        spinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(5);
        appCompatCheckedTextView.perform(click());

        ViewInteraction _LinearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.list_team),
                                childAtPosition(
                                        withClassName(is("org.jetbrains.anko._RelativeLayout")),
                                        0)),
                        4),
                        isDisplayed()));
        _LinearLayout2.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.add_to_favorite), withContentDescription("Favourites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.nav_fav), withContentDescription("Favourites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction _LinearLayout3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withClassName(is("android.support.v4.widget.SwipeRefreshLayout")),
                                0),
                        1),
                        isDisplayed()));
        _LinearLayout3.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.add_to_favorite), withContentDescription("Favourites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.action_bar),
                                        2),
                                0),
                        isDisplayed()));
        actionMenuItemView3.perform(click());

        pressBack();

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
