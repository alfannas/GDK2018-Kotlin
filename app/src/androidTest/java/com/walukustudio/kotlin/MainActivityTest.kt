package com.walukustudio.kotlin

import android.os.SystemClock
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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.IdlingRegistry
import org.junit.After
import org.junit.Before
import android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA
import org.hamcrest.Matchers.*


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val idle = CountingIdlingResource("GLOBAL")

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(idle)
    }

    @Test
    fun testAppBehaviour(){
        // check apakah tab sudah tampil dan click
        val matcher = allOf(withText("NEXT"), isDescendantOfA(withId(R.id.tabs_main)))
        onView(matcher).perform(click())

        // check apakah konten dari fragment sudah tampil
        onView(withId(R.id.viewpager_main)).check(matches(isDisplayed()))

        // check apakah recyclerview tab pertama sudah tampil dan click
        val matcher1 = allOf(withId(R.id.listTeam), withContentDescription("next"))
        onView(matcher1).check(matches(isDisplayed()))
        onView(matcher1).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(matcher1).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10,click()))

        // check apakah ada tombol favorit dan click
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        SystemClock.sleep(1000)
        onView(withText("Added to favorite")).inRoot(withDecorView(not(activityRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        pressBack()

        // check tabs dan click tab last match
        val matcher3 = allOf(withText("LAST"), isDescendantOfA(withId(R.id.tabs_main)))
        onView(matcher3).perform(click())

        // check recycler view
        val matcher4 = allOf(withId(R.id.listTeam), withContentDescription("last"))
        onView(matcher4).check(matches(isDisplayed()))
        onView(matcher4).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(matcher4).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10,click()))

        // check apakah ada tombol favorit dan click
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        SystemClock.sleep(1000)
        onView(withText("Added to favorite")).inRoot(withDecorView(not(activityRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        pressBack()

        // check navigation view an click teams
        onView(withId(R.id.navigationView)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_team)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_team)).perform(click())

        // check recycler view teams
        onView(withId(R.id.listTeam)).check(matches(isDisplayed()))
        onView(withId(R.id.listTeam)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.listTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        // check team apakah ada tombol favorit dan click favorite
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        SystemClock.sleep(1000)
        onView(withText("Added to favorite")).inRoot(withDecorView(not(activityRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        pressBack()

        // check navigation view an click favorite
        onView(withId(R.id.navigationView)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_fav)).check(matches(isDisplayed()))
        onView(withId(R.id.nav_fav)).perform(click())

        // check recycler view favorite match dan masuk ke list pertama
        val matcher5 = allOf(withId(R.id.listTeam), withContentDescription("match"))
        onView(matcher5).check(matches(isDisplayed()))
        onView(matcher5).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(matcher5).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))

        // remove match dari favorite
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        SystemClock.sleep(1000)
        onView(withText("Removed from favorite")).inRoot(withDecorView(not(activityRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        pressBack()

        // check tabs dan click tab last match
        val matcher6 = allOf(withText("TEAM"), isDescendantOfA(withId(R.id.tabs_main)))
        onView(matcher6).perform(click())

        // check recyclerview favorite team
        val matcher7 = allOf(withId(R.id.listTeam), withContentDescription("team"))
        onView(matcher7).check(matches(isDisplayed()))
        onView(matcher7).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(matcher7).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,click()))

        // masuk ke favorite team dan remove favorite
        onView(withId(R.id.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.add_to_favorite)).perform(click())
        SystemClock.sleep(1000)
        onView(withText("Removed from favorite")).inRoot(withDecorView(not(activityRule.activity.window.decorView)))
                .check(matches(isDisplayed()))
        pressBack()
    }

    @After
    fun setDown(){
        IdlingRegistry.getInstance().unregister(idle)
    }
}