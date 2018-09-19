package com.walukustudio.kotlin

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.walukustudio.kotlin.R.id.list_team
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        onView(withId(list_team)).check(matches(isDisplayed()))
        onView(withId(list_team)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(list_team)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
    }

    @Test
    fun testAppBehaviour() {
        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).perform(click())
        onView(withText("Spanish La Liga")).perform(click())

        onView(withText("Barcelona")).check(matches(isDisplayed()))
        onView(withText("Barcelona")).perform(click())

        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        //onView(withText("Added to favorite")).check(matches(isDisplayed()))
        pressBack()

        onView(withId(R.id.navigationView)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_fav)).perform(click())
    }
}