package com.walukustudio.kotlin

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.idling.CountingIdlingResource
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.IdlingRegistry
import org.junit.Before



@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var acitityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val espresso = IdlingRegistry.getInstance()
        val schedulePresenterIdlingResource = CountingIdlingResource("NetworkCallSchedule")
        val matchPresenterIdlingResource = CountingIdlingResource("NetworkCallMatch")
        espresso.register(schedulePresenterIdlingResource)
        espresso.register(matchPresenterIdlingResource)
    }

    @Test
    fun testAppBehaviour(){
        onView(withId(R.id.listTeam)).check(matches(isDisplayed()))
        onView(withId(R.id.listTeam)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.listTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10,click()))

        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Added to favorite")).inRoot(withDecorView(not(acitityRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(R.id.navigationView)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_next)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_next)).perform(click())

        onView(withId(R.id.listTeam)).check(matches(isDisplayed()))
        onView(withId(R.id.listTeam)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(12))
        onView(withId(R.id.listTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(12, click()))

        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Added to favorite")).inRoot(withDecorView(not(acitityRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(R.id.nav_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_fav)).perform(click())

        onView(withId(R.id.listTeam)).check(matches(isDisplayed()))
        onView(withId(R.id.listTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        onView(withText("Removed from favorite")).inRoot(withDecorView(not(acitityRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        pressBack()
    }
}